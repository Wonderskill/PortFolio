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
        Screen.SetResolution(1176, 664, false); // 화면 해상도를 다음과 같이 설정한다.
    }
    #endregion

    #region 03). Start
    private void Start()
    {
        do_login = false;

        if (!Cursor.visible) Cursor.visible = true; // 사라진 마우스 커서를 보이게 만든다.
        Cursor.lockState = CursorLockMode.None;     // 마우스 커서를 화면 밖으로 벗어날 수 있게 되돌린다.

        input_id.Select();   // ID InputField에 커서가 잡힌다.
    }
    #endregion

    #region 04). Update
    private void Update()
    {
        Login_Tab();   // Tab 키를 누르면 다음 InputField로 이동한다.
        Login_Enter(); // Enter 키를 누르면 로그인을 진행한다.
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
        RegisterManager.isSelect = false;

        register_panel.SetActive(false); // 회원가입 화면 Out
        login_panel.SetActive(true);     // 로그인 화면 In

        input_id.Select();   // ID InputField에 커서가 잡힌다.
    }
    #endregion

    #region 05-05). Login_Tab : Tab 키를 누르면 다음 InputField로 이동한다.
    private void Login_Tab()
    {
        if(Input.GetKeyDown(KeyCode.Tab) && input_id.isFocused)
        {
            input_pw.Select();
        }
        else if(Input.GetKeyDown(KeyCode.Tab) && input_pw.isFocused)
        {
            input_id.Select();
        }
    }
    #endregion

    #region 05-06). Login_Enter : Enter 키를 누르면 로그인을 진행한다.
    private void Login_Enter()
    {
        if ((Input.GetKeyDown(KeyCode.Return) || Input.GetKeyDown(KeyCode.KeypadEnter)) && login_panel.activeSelf)
        {
            button_login.onClick.Invoke(); // Login 버튼의 기능을 수행한다.

        }
    }
    #endregion
}
