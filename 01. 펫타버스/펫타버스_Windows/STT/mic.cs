using UnityEngine.Networking;
using System.Collections;
using System.Text;
using UnityEngine;
using System.IO;
using System;
using TMPro;
using Photon.Voice.Unity;
using Photon.Voice;

public class mic : MonoBehaviour
{
    #region 01). MemberField
    [Header("Mic 관련")]
    public Recorder        recorder; // 자신의 Recorder 컴포넌트 
    public GameObject      micOnImg; // 마이크 On 이미지
    public GameObject      micOffImg;// 마이크 Off 이미지


    public AudioSource     recoding; // 마이크로 녹음된 소리를 담을 오디오 소스 컴포넌트
    public TextMeshProUGUI TTtext;   // STT 결과 텍스트
    public TMP_Dropdown    dropdown; // 현재 기기에서 사용할 수 있는 마이크 종류

    private bool qDown;      // STT 마이크 키(Q)
    private bool mDown;      // Photon Voice 마이크 OnOff 키(M)
    private bool IsRecoding; // 녹음 중 표시, 녹음 중이면 키 입력 방지

    private string SelectedMic;            // 선택된 마이크 이름  
    private int EnterCount = 0;            // 가끔씩 생기는 이벤트 오류를 막기 위한 count
    private const int BlockSize_16Bit = 2; // ???

    [Header("Player의 Animal")]
    public GameObject animal = null; // Player가 선택한 동물
    #endregion

    #region 02). VoiceRecongize : STT결과를 json으로 받기위한 class
    [Serializable]
    public class VoiceRecognize
    {
        public string text;
    }
    #endregion

    #region 03). Start
    private IEnumerator Start()
    {
        // 마이크 허용
        yield return Application.RequestUserAuthorization(UserAuthorization.WebCam | UserAuthorization.Microphone);

        Debug.Log("마이크 개수 : " + Microphone.devices.Length);

        // 입력 장치 변경 시 사용할 이벤트
        AudioSettings.OnAudioConfigurationChanged += OnAudioConfigurationChanged;
        
        // 드롭다운에 마이크 리스트 추가 함수
        MicDropDownModify();
    }
    #endregion

    #region 04). Update
    private void Update()
    {
        // Chat Panel이 켜져있지 않다면
        if (!ButtonManager.is_chat)
        {
            qDown = Input.GetButtonDown("Mic"); // STT Mic(= Q) 입력값을 저장한다.
            mDown = Input.GetButtonDown("PhotonMic"); // Photon Voice Mic(= M) 입력값을 저장한다.
        }
        PhotonMicOnOff();
        Record(); // 목소리를 녹음한다.
    }
    #endregion

    #region 05-01). OnAudioConfigurationChanged : 입력 장치 변경 시 사용할 이벤트
    void OnAudioConfigurationChanged(bool deviceWasChanged)
    {
        // 마이크 장치가 변경되었을 때
        if (deviceWasChanged && EnterCount == 0)
        {
            EnterCount++;
            AudioConfiguration config = AudioSettings.GetConfiguration();

            config.dspBufferSize = 64;
            AudioSettings.Reset(config);
            MicDropDownModify(); // Mic DropDown을 수정한다.

            EnterCount--;
        }
    }
    #endregion

    #region 05-02). Record : Q키를 사용하여 녹음 기능을 실행한다.
    public void Record()
    {
        // Q키를 입력 + 녹음 상태가 아님 + 동물이 있을 때
        if (qDown && !IsRecoding && animal != null)
        {         
            Debug.Log(SelectedMic);

            // 마이크 없으면 결과에 마이크없음을 텍스트에 넣은 후 리턴
            if (SelectedMic == "마이크없음")
            {
                TTtext.text = "마이크없음";
                return;
            }

            // 마이크가 있으면 코루틴 시작
            StartCoroutine(Recording());
        }
    }
    #endregion

    #region 05-03). Recording : 실제 녹음 기능을 실행한다.
    private IEnumerator Recording()
    {
        // 01). 녹음 중 텍스트와 IsRecoding을 true로 해서 중복 녹음을 막음
        IsRecoding = true;
        TTtext.text = "녹음중";

        // 02). 2초동안 SelectedMic 이름으로 마이크 시작 후 recoding.clip에 임시 저장
        recoding.clip = Microphone.Start(SelectedMic, false, 2, 44100);

        // 2초동안 녹음 기다림
        yield return new WaitForSeconds(2f);
        
        if (recoding.clip == null)
        {
            Debug.LogError("Could not start the Microphone.");
        }

        // 03). 오디오클립을 바이트 배열로 변환
        byte[] byteData = getByteFromAudioClip(recoding.clip);

        // 04). 녹음된 audioclip api 서버로 보냄
        StartCoroutine(NaverAPI(byteData));    
    }
    #endregion

    #region 05-04). ChangMic : 드롭다운에 변경된 마이크 선택
    public void ChangeMic()
    {
        SelectedMic = dropdown.options[dropdown.value].text;

        // recorder의 녹음 마이크를 바꿈 (타입이 Photon 일 때 안바뀜) 
        DeviceInfo device = new DeviceInfo(SelectedMic);
        recorder.MicrophoneDevice = device; 
    }
    #endregion

    #region 05-05). MicDropDownModify : 드롭다운 리스트 수정용 
    private void MicDropDownModify()
    {
        // 마이크가 없으면 드롭다운에 마이크 없음 추가    
        if (Microphone.devices.Length == 0)
        {
            dropdown.options.Clear();
            TMP_Dropdown.OptionData option = new TMP_Dropdown.OptionData();
            option.text = "마이크없음";
            dropdown.options.Add(option);
        }
        // 있으면 드롭다운에 각각 마이크 이름 추가
        else
        {
            dropdown.options.Clear();

            // 0부터 마이크 총 갯수까지
            for (int i = 0; i < Microphone.devices.Length; i++)
            {
                TMP_Dropdown.OptionData option = new TMP_Dropdown.OptionData();
                option.text = Microphone.devices[i].ToString();
                dropdown.options.Add(option);
            }
        }

        // 0번째 드롭다운 선택 
        dropdown.value = 0;
        // 선택된 마이크 이름은 지금 선택된 드롭다운 이름
        ChangeMic();
    }
    #endregion

    #region 05-06). PhotonMicOnOff : Photon Voice Mic 켜고 끄는 함수 
    private void PhotonMicOnOff()
    {
        // mDown이 눌렸을 때
       if(mDown)
       {
            // recorder.TransmitEnabled이 true일 때 
            if (recorder.TransmitEnabled)
            {
                // recorder.TransmitEnabled을 false로 바꿈
                recorder.TransmitEnabled = false;
                micOnImg.SetActive(false);
                micOffImg.SetActive(true);
            }
            else
            {
                recorder.TransmitEnabled = true;
                micOffImg.SetActive(false);
                micOnImg.SetActive(true);
            }
       }

    }
    #endregion

    #region 06-01). NaverAPI : 네이버 API를 사용하기 위한 코루틴
    private IEnumerator NaverAPI(byte[] data)
    {
        string lang = "Kor";    // 언어 코드 ( Kor, Jpn, Eng, Chn )
        string url = $"https://naveropenapi.apigw.ntruss.com/recog/v1/stt?lang={lang}";

        // 01). request 생성
        WWWForm form = new WWWForm();
        UnityWebRequest request = UnityWebRequest.Post(url, form);

        // 02). 요청 헤더 설정
        request.SetRequestHeader("X-NCP-APIGW-API-KEY-ID", "z75crsxaoe");
        request.SetRequestHeader("X-NCP-APIGW-API-KEY", "WYA3irjGeMK8bGYqnkCNiBEe5zi34uZcn8hKI5iG");
        request.SetRequestHeader("Content-Type", "application/octet-stream");

        // 03). 바디에 처리과정을 거친 Audio Clip data를 실어줌
        request.uploadHandler = new UploadHandlerRaw(data);

        // 요청을 보낸 후 response를 받을 때까지 대기
        yield return request.SendWebRequest();

        // 만약 response가 비어있다면 error
        if (request == null)
        {
            Debug.LogError(request.error);
        }
        else
        {
            // 04). json 형태로 받음 {"text":"인식결과"}
            string message = request.downloadHandler.text;
            VoiceRecognize voiceRecognize = JsonUtility.FromJson<VoiceRecognize>(message);

            // Voice Server responded: 인식결과
            Debug.Log("녹음 결과 : " + voiceRecognize.text);

            // (수정) 이 부분은 메인 프로그램이랑 연결할 때 수정해야 함
            animal.GetComponent<Animal>().STTAct(voiceRecognize.text);
            
            // 05). 결과 출력
            TTtext.text = voiceRecognize.text;
            IsRecoding = false;
        }
        request.Dispose();
    }
    #endregion

    #region 07-01). ButtonPush : Pet과 상호작용할 버튼의 Int값을 받는다.
    public void ButtonPush(int num)
    {
        animal.GetComponent<Animal>().ButtonAct(num);
        GameManager.is_camera_stop = true;  // 카메라 회전을 멈춘다.
        Cursor.visible = true;              // 마우스 커서가 보인다.
    }
    #endregion

    #region  08-01). Audio Clip을 byte array로 변환할 때 필요한 함수들(절대 건드리지 말 것!)
    private byte[] getByteFromAudioClip(AudioClip audioClip)
    {
        MemoryStream stream = new MemoryStream();
        const int headerSize = 44;
        ushort bitDepth = 16;
 

        int fileSize = audioClip.samples * BlockSize_16Bit + headerSize;

        // audio clip의 정보들을 file stream에 추가(링크 참고 함수 선언)
        WriteFileHeader(ref stream, fileSize);
        WriteFileFormat(ref stream, audioClip.channels, audioClip.frequency, bitDepth);
        WriteFileData(ref stream, audioClip, bitDepth);

        // stream을 array형태로 바꿈
        byte[] bytes = stream.ToArray();

        return bytes;
    }

    private static int WriteFileHeader(ref MemoryStream stream, int fileSize)
    {
        int count = 0;
        int total = 12;

        // riff chunk id
        byte[] riff = Encoding.ASCII.GetBytes("RIFF");
        count += WriteBytesToMemoryStream(ref stream, riff, "ID");

        // riff chunk size
        int chunkSize = fileSize - 8; // total size - 8 for the other two fields in the header
        count += WriteBytesToMemoryStream(ref stream, BitConverter.GetBytes(chunkSize), "CHUNK_SIZE");

        byte[] wave = Encoding.ASCII.GetBytes("WAVE");
        count += WriteBytesToMemoryStream(ref stream, wave, "FORMAT");

        // Validate header
        Debug.AssertFormat(count == total, "Unexpected wav descriptor byte count: {0} == {1}", count, total);

        return count;
    }

    private static int WriteFileFormat(ref MemoryStream stream, int channels, int sampleRate, UInt16 bitDepth)
    {
        int count = 0;
        int total = 24;

        byte[] id = Encoding.ASCII.GetBytes("fmt ");
        count += WriteBytesToMemoryStream(ref stream, id, "FMT_ID");

        int subchunk1Size = 16; // 24 - 8
        count += WriteBytesToMemoryStream(ref stream, BitConverter.GetBytes(subchunk1Size), "SUBCHUNK_SIZE");

        UInt16 audioFormat = 1;
        count += WriteBytesToMemoryStream(ref stream, BitConverter.GetBytes(audioFormat), "AUDIO_FORMAT");

        UInt16 numChannels = Convert.ToUInt16(channels);
        count += WriteBytesToMemoryStream(ref stream, BitConverter.GetBytes(numChannels), "CHANNELS");

        count += WriteBytesToMemoryStream(ref stream, BitConverter.GetBytes(sampleRate), "SAMPLE_RATE");

        int byteRate = sampleRate * channels * BytesPerSample(bitDepth);
        count += WriteBytesToMemoryStream(ref stream, BitConverter.GetBytes(byteRate), "BYTE_RATE");

        UInt16 blockAlign = Convert.ToUInt16(channels * BytesPerSample(bitDepth));
        count += WriteBytesToMemoryStream(ref stream, BitConverter.GetBytes(blockAlign), "BLOCK_ALIGN");

        count += WriteBytesToMemoryStream(ref stream, BitConverter.GetBytes(bitDepth), "BITS_PER_SAMPLE");

        // Validate format
        Debug.AssertFormat(count == total, "Unexpected wav fmt byte count: {0} == {1}", count, total);

        return count;
    }

    private static int WriteFileData(ref MemoryStream stream, AudioClip audioClip, UInt16 bitDepth)
    {
        int count = 0;
        int total = 8;

        // Copy float[] data from AudioClip
        float[] data = new float[audioClip.samples * audioClip.channels];
        audioClip.GetData(data, 0);

        byte[] bytes = ConvertAudioClipDataToInt16ByteArray(data);

        byte[] id = Encoding.ASCII.GetBytes("data");
        count += WriteBytesToMemoryStream(ref stream, id, "DATA_ID");

        int subchunk2Size = Convert.ToInt32(audioClip.samples * BlockSize_16Bit); // BlockSize (bitDepth)
        count += WriteBytesToMemoryStream(ref stream, BitConverter.GetBytes(subchunk2Size), "SAMPLES");

        // Validate header
        Debug.AssertFormat(count == total, "Unexpected wav data id byte count: {0} == {1}", count, total);

        // Write bytes to stream
        count += WriteBytesToMemoryStream(ref stream, bytes, "DATA");

        // Validate audio data
        Debug.AssertFormat(bytes.Length == subchunk2Size, "Unexpected AudioClip to wav subchunk2 size: {0} == {1}", bytes.Length, subchunk2Size);

        return count;
    }
    private static int BytesPerSample(UInt16 bitDepth)
    {
        return bitDepth / 8;
    }

    private static int WriteBytesToMemoryStream(ref MemoryStream stream, byte[] bytes, string tag = "")
    {
        int count = bytes.Length;
        stream.Write(bytes, 0, count);
        //Debug.LogFormat ("WAV:{0} wrote {1} bytes.", tag, count);
        return count;
    }

    private static byte[] ConvertAudioClipDataToInt16ByteArray(float[] data)
    {
        MemoryStream dataStream = new MemoryStream();

        int x = sizeof(Int16);

        Int16 maxValue = Int16.MaxValue;

        int i = 0;
        while (i < data.Length)
        {
            dataStream.Write(BitConverter.GetBytes(Convert.ToInt16(data[i] * maxValue)), 0, x);
            ++i;
        }
        byte[] bytes = dataStream.ToArray();

        // Validate converted bytes
        Debug.AssertFormat(data.Length * x == bytes.Length, "Unexpected float[] to Int16 to byte[] size: {0} == {1}", data.Length * x, bytes.Length);

        dataStream.Dispose();

        return bytes;
    }
    #endregion
}
