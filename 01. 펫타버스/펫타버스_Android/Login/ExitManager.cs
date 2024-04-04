using UnityEngine.UI;
using UnityEngine;
using Photon.Pun;

public class ExitManager : MonoBehaviourPunCallbacks
{
    #region 01). MemberField
    [Header("UI_Panel")]
    public GameObject login_panel; // Login 화면
    public GameObject exit_panel;  // Exit 화면

    public Button exit_button;     // Exit 버튼

    [Header("SFX Sound")]
    public AudioSource SFX; // 버튼 효과음
    #endregion

    #region 02). Awake
    private void Awake()
    {

    }
    #endregion

    #region 03). Start
    private void Start()
    {

    }
    #endregion

    #region 04). Update
    private void Update()
    {
        
    }
    #endregion

    #region 05-01). Exit_OnExit : 프로그램을 종료한다.
    public void Exit_OnExit()
    {
        SFX.Play(); // 버튼 효과음 재생

        DisConnect();       // Photon 서버와 연결을 해제한다.
        Application.Quit(); // 프로그램을 종료한다.
    }
    #endregion

    #region 05-02). Exit_OnBack : 로그인 화면으로 돌아간다.
    public void Exit_OnBack()
    {
        SFX.Play(); // 버튼 효과음 재생

        login_panel.SetActive(true); // 로그인 화면 In
        exit_panel.SetActive(false); // 나가기 화면 Out
    }
    #endregion

    #region 05-03). DisConnect : Photon 서버와 연결을 해제한다.
    private void DisConnect()
    {
        PhotonNetwork.Disconnect(); // Photon 서버와 연결을 해제한다.
    }
    #endregion

}
