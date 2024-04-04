using Photon.Voice.Unity;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MySpeaker : MonoBehaviour
{
    private GameObject recorder;

    private void Start()
    {
        recorder = GameObject.Find("MicManager");

        recorder.GetComponent<Recorder>().VoiceDetectionThreshold = 0.13f;
    }
}
