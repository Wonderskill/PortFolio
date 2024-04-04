using UnityEngine.SceneManagement;
using UnityEngine.UI;
using UnityEngine;
using Photon.Pun;

public class LoginManager : MonoBehaviourPunCallbacks
{
    #region 01). MemberField
    [Header("Panel")]
    public GameObject register_panel; // Register 화면
    public GameObject login_panel;    // Login 화면
    public GameObject exit_panel;     // Exit 화면

    [Space(10)]

    [Header("Login - InputField")]
    public InputField input_id; // ID InputField
    public InputField input_pw; // PW InputField

    [Space(10)]

    [Header("Login - Button")]
    public Button button_login; // Login Button

    [Space(10)]

    [Header("SFX Sound")]
    public AudioSource SFX; // 버튼 효과음

    public static bool do_login; // 시작점 확인 : LoginScene -> LoadingScene
    #endregion

    #region 02). Awake
    private void Awake()
    {
    }
    #endregion

    #region 03). Start
    private void Start()
    {
        do_login = false;
        Screen.orientation = ScreenOrientation.LandscapeLeft;
    }
    #endregion

    #region 04). Update
    private void Update()
    {
    }
    #endregion

    #region 05-01). Login_GoToLogin : 로그인 기능을 수행한다.
    public static void Login_GoToLogin()
    {
        if (PhotonNetwork.IsConnected)
        {
            do_login = true;                     // 출발 준비 완료 : LoginScene -> LoadingScene
            SceneManager.LoadScene("LoadScene"); // LoadScene으로 이동한다.
        }
    }
    #endregion

    #region 05-02). Login_GoToRegister : 회원가입 화면으로 이동한다.
    public void Login_GoToRegister()
    {
        register_panel.SetActive(true); // 회원가입 화면 In
        login_panel.SetActive(false);   // 로그인 화면 Out
    }
    #endregion

    #region 05-03). Login_GoToExit : 나가기 UI를 출력한다.
    public void Login_GoToExit()
    {
        SFX.Play(); // 버튼 효과음 재생

        register_panel.SetActive(false); // 회원가입 화면 Out
        login_panel.SetActive(false);    // 로그인 화면 Out
        exit_panel.SetActive(true);      // 나가기 화면 In
    }
    #endregion

    #region 05-04). Register_GoToBack : 회원가입 화면 → 로그인 화면
    public void Register_GoToBack()
    {
        register_panel.SetActive(false); // 회원가입 화면 Out
        login_panel.SetActive(true);     // 로그인 화면 In
    }
    #endregion

}
