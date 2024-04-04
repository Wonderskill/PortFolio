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
    public AudioSource recoding;    // ����ũ�� ������ �Ҹ��� ���� ����� �ҽ� ������Ʈ
    // public GameObject wpanel;       // ���� �� ������
    public TextMeshProUGUI TTtext;  // ��� �ؽ�Ʈ    

    private bool IsRecoding;         // ���� �� ǥ��, ���� ���̸� Ű �Է� ����

    private string SelectedMic;     // ���õ� ����ũ �̸�  
    private int EnterCount = 0;     // ������ ����� �̺�Ʈ ������ ���� ���� count
    private const int BlockSize_16Bit = 2;

    public GameObject DogBtn;

    //[SerializeField]
    //public GameObject animal = null;      // ���� ������ ����
    //[SerializeField]
    //private GameObject[] animals;      // ���� �����ϴ� ��� ������

    // STT����� json���� �ޱ����� class
    [Serializable]
    public class VoiceRecognize
    {
        public string text;
    }

    private IEnumerator Start()
    {
        // ����ũ ���
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
            SelectedMic = "����ũ����";
        }
        else
        {
            SelectedMic = Microphone.devices[0];
        }

        Debug.Log("����ũ ���� : " + Microphone.devices.Length);

        // �Է� ��ġ ���� �� ����� �̺�Ʈ
        //AudioSettings.OnAudioConfigurationChanged += OnAudioConfigurationChanged;
    }

    private void Update()
    {
        //qDown = Input.GetButtonDown("Mic");
        //Record();
    }

    //void OnAudioConfigurationChanged(bool deviceWasChanged)
    //{
    //    Debug.Log("�̺�Ʈ ����ũ ���� : " + Microphone.devices.Length);
    //    Debug.Log(deviceWasChanged ? "Device was changed" : "Reset was called");
    //    Debug.Log("����" + deviceWasChanged + "ī��Ʈ" + EnterCount);

    //    if (deviceWasChanged && EnterCount == 0)
    //    {
    //        Debug.Log("�߰�" + deviceWasChanged + "ī��Ʈ" + EnterCount);
    //        EnterCount++;
    //        AudioConfiguration config = AudioSettings.GetConfiguration();
    //        config.dspBufferSize = 64;
    //        AudioSettings.Reset(config);
    //        Debug.Log("��" + deviceWasChanged + "ī��Ʈ" + EnterCount);
    //        EnterCount--;
    //    }
    //}

    // ���� ���� (��ư���� �ߵ���Ű�� �س�)
    public void Record()
    {
        Debug.Log("����ũ : " + SelectedMic);
        if (!IsRecoding) //&& animal != null)
        {
            // ����ũ ������ ����� ����ũ������ �ؽ�Ʈ�� ���� �� ����
            Debug.Log(SelectedMic);
            if (SelectedMic == "����ũ����")
            {
                TTtext.text = "����ũ����";
                return;
            }
            // ����ũ�� ������ �ڷ�ƾ ����
            StartCoroutine(Recording());
        }
    }

    private IEnumerator Recording()
    {
        // ���� �� �ؽ�Ʈ�� IsRecoding�� true�� �ؼ� �ߺ� ������ ����
        IsRecoding = true;
        TTtext.text = "������";

        // 3�ʵ��� SelectedMic �̸����� ����ũ ���� �� recoding.clip�� �ӽ� ����
        recoding.clip = Microphone.Start(SelectedMic, false, 3, 44100);

        // 3�ʵ��� ���� ��ٸ�
        yield return new WaitForSeconds(3f);

        if (recoding.clip == null)
        {
            Debug.LogError("Could not start the Microphone.");
        }

        // audio clip to byte array (�����Ŭ���� ����Ʈ �迭�� ��ȯ)
        byte[] byteData = getByteFromAudioClip(recoding.clip);

        // ������ audioclip api ������ ����
        StartCoroutine(NaverAPI(byteData));

        // ����� �ҽ� Ŭ���� .wav ���·� �ٲ� 
        // ����Ǵ� ��Ҵ� ����Ƽ ���� �Լ� ��� (PC�� Android ������� �ٸ�)
        //SavWav.Save(Application.persistentDataPath + "/voice", recoding.clip);

        // �����̸� ���̱� ���� �߰��� �ڵ�
        // ����ϸ� ����, �Ƹ� microphoen.start���� ������ true�� �Ҷ� ����ϴ� �� ����
        // while (!(Microphone.GetPosition(Microphone.devices[0].ToString()) > 0)) { }

        //Debug.Log("���� ���� �Ϸ�");

    }

    // ���̹� API�� ����ϱ� ���� �ڷ�ƾ
    private IEnumerator NaverAPI(byte[] data)
    {
        string lang = "Kor";    // ��� �ڵ� ( Kor, Jpn, Eng, Chn )
        string url = $"https://naveropenapi.apigw.ntruss.com/recog/v1/stt?lang={lang}";

        // request ����
        WWWForm form = new WWWForm();
        UnityWebRequest request = UnityWebRequest.Post(url, form);

        // ��û ��� ����
        request.SetRequestHeader("X-NCP-APIGW-API-KEY-ID", "z75crsxaoe");
        request.SetRequestHeader("X-NCP-APIGW-API-KEY", "WYA3irjGeMK8bGYqnkCNiBEe5zi34uZcn8hKI5iG");
        request.SetRequestHeader("Content-Type", "application/octet-stream");

        // �ٵ� ó�������� ��ģ Audio Clip data�� �Ǿ���
        request.uploadHandler = new UploadHandlerRaw(data);

        // ��û�� ���� �� response�� ���� ������ ���
        yield return request.SendWebRequest();

        // ���� response�� ����ִٸ� error
        if (request == null)
        {
            Debug.LogError(request.error);
        }
        else
        {
            // json ���·� ���� {"text":"�νİ��"}
            string message = request.downloadHandler.text;
            VoiceRecognize voiceRecognize = JsonUtility.FromJson<VoiceRecognize>(message);

            Debug.Log("���� ��� : " + voiceRecognize.text);
            // Voice Server responded: �νİ��

            // ������ �ൿ ���� �Լ�
            //AnimalAct(voiceRecognize.text);

            // (����) �� �κ��� ���� ���α׷��̶� ������ �� �����ؾ� ��
            DogBtn.GetComponent<DogBtn>().STTAct(voiceRecognize.text);

            // ��� ���
            TTtext.text = voiceRecognize.text;
            IsRecoding = false;
        }
    }

    #region  Audio Clip�� byte array�� ��ȯ�� �� �ʿ��� �Լ���
    private byte[] getByteFromAudioClip(AudioClip audioClip)
    {
        MemoryStream stream = new MemoryStream();
        const int headerSize = 44;
        ushort bitDepth = 16;


        int fileSize = audioClip.samples * BlockSize_16Bit + headerSize;

        // audio clip�� �������� file stream�� �߰�(��ũ ���� �Լ� ����)
        WriteFileHeader(ref stream, fileSize);
        WriteFileFormat(ref stream, audioClip.channels, audioClip.frequency, bitDepth);
        WriteFileData(ref stream, audioClip, bitDepth);

        // stream�� array���·� �ٲ�
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