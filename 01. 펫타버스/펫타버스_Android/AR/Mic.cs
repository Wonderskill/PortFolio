using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Text;
using UnityEngine;
using TMPro;
using System;
using UnityEngine.Networking;


//using Photon.Pun;

public class Mic : MonoBehaviour
{
    public AudioSource recoding;    // 마이크로 녹음된 소리를 담을 오디오 소스 컴포넌트
    // public GameObject wpanel;       // 녹음 중 가림막
    public TextMeshProUGUI TTtext;  // 결과 텍스트    

    private bool IsRecoding;         // 녹음 중 표시, 녹음 중이면 키 입력 방지

    private string SelectedMic;     // 선택된 마이크 이름  
    private int EnterCount = 0;     // 가끔씩 생기는 이벤트 오류를 막기 위한 count
    private const int BlockSize_16Bit = 2;

    public GameObject DogBtn;

    //[SerializeField]
    //public GameObject animal = null;      // 내가 선택한 동물
    //[SerializeField]
    //private GameObject[] animals;      // 현재 존재하는 모든 동물들

    // STT결과를 json으로 받기위한 class
    [Serializable]
    public class VoiceRecognize
    {
        public string text;
    }

    private IEnumerator Start()
    {
        // 마이크 허용
        yield return Application.RequestUserAuthorization(UserAuthorization.WebCam | UserAuthorization.Microphone);
        if (Application.HasUserAuthorization(UserAuthorization.WebCam | UserAuthorization.Microphone))
        {
        }
        else
        {
        }


        //animals = GameObject.FindGameObjectsWithTag("Animal");
        //for (int i = 0; i < animals.Length; i++)
        //{
        //    PhotonView view = animals[i].GetComponent<PhotonView>();
        //    if (view.IsMine)
        //    {
        //        animal = animals[i].gameObject;
        //    }
        //}

        if(Microphone.devices.Length == 0)
        {
            SelectedMic = "마이크없음";
        }
        else
        {
            SelectedMic = Microphone.devices[0];
        }

        Debug.Log("마이크 개수 : " + Microphone.devices.Length);

        // 입력 장치 변경 시 사용할 이벤트
        //AudioSettings.OnAudioConfigurationChanged += OnAudioConfigurationChanged;
    }

    private void Update()
    {
        //qDown = Input.GetButtonDown("Mic");
        //Record();
    }

    //void OnAudioConfigurationChanged(bool deviceWasChanged)
    //{
    //    Debug.Log("이벤트 마이크 개수 : " + Microphone.devices.Length);
    //    Debug.Log(deviceWasChanged ? "Device was changed" : "Reset was called");
    //    Debug.Log("시작" + deviceWasChanged + "카운트" + EnterCount);

    //    if (deviceWasChanged && EnterCount == 0)
    //    {
    //        Debug.Log("중간" + deviceWasChanged + "카운트" + EnterCount);
    //        EnterCount++;
    //        AudioConfiguration config = AudioSettings.GetConfiguration();
    //        config.dspBufferSize = 64;
    //        AudioSettings.Reset(config);
    //        Debug.Log("끝" + deviceWasChanged + "카운트" + EnterCount);
    //        EnterCount--;
    //    }
    //}

    // 녹음 변수 (버튼으로 발동시키게 해놈)
    public void Record()
    {
        Debug.Log("마이크 : " + SelectedMic);
        if (!IsRecoding) //&& animal != null)
        {
            // 마이크 없으면 결과에 마이크없음을 텍스트에 넣은 후 리턴
            Debug.Log(SelectedMic);
            if (SelectedMic == "마이크없음")
            {
                TTtext.text = "마이크없음";
                return;
            }
            // 마이크가 있으면 코루틴 시작
            StartCoroutine(Recording());
        }
    }

    private IEnumerator Recording()
    {
        // 녹음 중 텍스트와 IsRecoding을 true로 해서 중복 녹음을 막음
        IsRecoding = true;
        TTtext.text = "녹음중";

        // 3초동안 SelectedMic 이름으로 마이크 시작 후 recoding.clip에 임시 저장
        recoding.clip = Microphone.Start(SelectedMic, false, 3, 44100);

        // 3초동안 녹음 기다림
        yield return new WaitForSeconds(3f);

        if (recoding.clip == null)
        {
            Debug.LogError("Could not start the Microphone.");
        }

        // audio clip to byte array (오디오클립을 바이트 배열로 변환)
        byte[] byteData = getByteFromAudioClip(recoding.clip);

        // 녹음된 audioclip api 서버로 보냄
        StartCoroutine(NaverAPI(byteData));

        // 오디오 소스 클립을 .wav 형태로 바꿈 
        // 저장되는 장소는 유니티 제공 함수 사용 (PC와 Android 저장공간 다름)
        //SavWav.Save(Application.persistentDataPath + "/voice", recoding.clip);

        // 딜레이를 줄이기 위해 추가한 코드
        // 사용하면 멈춤, 아마 microphoen.start에서 루프를 true로 할때 사용하는 것 같음
        // while (!(Microphone.GetPosition(Microphone.devices[0].ToString()) > 0)) { }

        //Debug.Log("녹음 저장 완료");

    }

    // 네이버 API를 사용하기 위한 코루틴
    private IEnumerator NaverAPI(byte[] data)
    {
        string lang = "Kor";    // 언어 코드 ( Kor, Jpn, Eng, Chn )
        string url = $"https://naveropenapi.apigw.ntruss.com/recog/v1/stt?lang={lang}";

        // request 생성
        WWWForm form = new WWWForm();
        UnityWebRequest request = UnityWebRequest.Post(url, form);

        // 요청 헤더 설정
        request.SetRequestHeader("X-NCP-APIGW-API-KEY-ID", "z75crsxaoe");
        request.SetRequestHeader("X-NCP-APIGW-API-KEY", "WYA3irjGeMK8bGYqnkCNiBEe5zi34uZcn8hKI5iG");
        request.SetRequestHeader("Content-Type", "application/octet-stream");

        // 바디에 처리과정을 거친 Audio Clip data를 실어줌
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
            // json 형태로 받음 {"text":"인식결과"}
            string message = request.downloadHandler.text;
            VoiceRecognize voiceRecognize = JsonUtility.FromJson<VoiceRecognize>(message);

            Debug.Log("녹음 결과 : " + voiceRecognize.text);
            // Voice Server responded: 인식결과

            // 동물의 행동 결정 함수
            //AnimalAct(voiceRecognize.text);

            // (수정) 이 부분은 메인 프로그램이랑 연결할 때 수정해야 함
            DogBtn.GetComponent<DogBtn>().STTAct(voiceRecognize.text);

            // 결과 출력
            TTtext.text = voiceRecognize.text;
            IsRecoding = false;
        }
    }

    #region  Audio Clip을 byte array로 변환할 때 필요한 함수들
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