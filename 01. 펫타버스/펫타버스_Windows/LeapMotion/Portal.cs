using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using Photon.Pun;

public class Portal : MonoBehaviour
{
    void OnTriggerEnter(Collider other)
    {
        // PhotonNetwork에 연결되어 있는 상태라면
        if (other.tag == "RealPlayer" && PhotonNetwork.IsConnected)
        {
            // SFX.Play(); // 버튼 효과음 재생
            L_ButtonManager.back_main = true; // 출발 준비 완료 : LeapMotionScene -> MainScene

            SceneManager.LoadScene("LoadScene");   // LoadScene으로 이동한다.
        }
    }
}
