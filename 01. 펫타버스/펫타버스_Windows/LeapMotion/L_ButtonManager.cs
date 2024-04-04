using UnityEngine.SceneManagement;
using UnityEngine;
using Photon.Pun;

public class L_ButtonManager : MonoBehaviour
{
    #region 01). MemberField
    public static bool back_main; // 시작점 확인 : LeapMotionScene -> MainScene
    #endregion

    #region 02). Awake
    private void Awake()
    {
        back_main = false;
    }
    #endregion

    #region 03). Start
    private void Start()
    {
        //Cursor.visible = true; // 마우스 커서가 보인다.
    }
    #endregion

    #region 04). Update
    private void Update()
    {
        
    }
    #endregion

    #region 05). Leap_Exit : 립모드 화면에서 나간다.
    public void Leap_Exit()
    {
        // PhotonNetwork에 연결되어 있는 상태라면
        if (PhotonNetwork.IsConnected)
        {
            // SFX.Play(); // 버튼 효과음 재생
            back_main = true; // 출발 준비 완료 : LeapMotionScene -> MainScene

            SceneManager.LoadScene("LoadScene");   // LoadScene으로 이동한다.
        }
    }
    #endregion
}
