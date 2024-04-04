// LoginScene -> MainScene으로 이동할 때 사용

using UnityEngine.SceneManagement;
using UnityEngine.UI;
using UnityEngine;
using Photon.Pun;
using TMPro;

public class LoadManager : MonoBehaviour
{
    #region 01). MemberField
    [Header("현재 연결 상태")]
    public Text            status_txt;     // 현재 접속 상태

    [Header("Loading PrograssBar")]
    public TextMeshProUGUI loading_txt;    // Loading ... 0%
    public Slider          loading_slider; // Loading PrograssBar

    private float loading_time = 1.0f; // PrograssBar 완료 시간
    private float current_time;        // PrograssBar 현재 시간
    private float start_time;          // PrograssBar 시작 시간
    #endregion

    #region 02). Awake
    private void Awake()
    {
        
    }
    #endregion

    #region 03). Start
    private void Start()
    {  
        current_time = loading_time;
        start_time   = Time.time;

        FillAmount(0); // PrgrassBar 0%
    }
    #endregion

    #region 04). Update
    private void Update()
    {
        if (PhotonNetwork.IsConnected) status_txt.text = "현재 상태 : Connect";    // Photon 서버에 연결된 상태일 때
        else                           status_txt.text = "현재 상태 : DisConnect"; // Photon 서버에 연결되지 않은 상태일 때

        LoadingStay();
    }
    #endregion

    #region 05-01). PrograssBar 작동
    private void LoadingStay()
    {
        current_time = Time.time - start_time;

        if (current_time < loading_time)
        {
            FillAmount(current_time / loading_time);
        }
        else
        {
            LoadingEnd();
        }
    }
    #endregion

    #region 05-02). PrograssBar 완료
    private void LoadingEnd()
    {
        FillAmount(1);
        // 상황에 맞게 다음 Scene으로 이동한다.
        // LoginScene -> MainScene
        if (LoginManager.do_login)
        {
            SceneManager.LoadScene("MainScene");  // MainScene로 이동
            LoginManager.do_login = false;        // 도착 완료
        }
        // MainScene -> LoginScene
        else if (ReadyExitManager.do_main)
        {
            SceneManager.LoadScene("LoginScene"); // LoginScene로 이동
            ReadyExitManager.do_main = false;     // 도착 완료
        }
        // MainScene -> LeapMotionScene
        else if (ButtonManager.do_leapmotion)
        {
            SceneManager.LoadScene("LeapMotionScene"); // LoginScene로 이동
            ButtonManager.do_leapmotion = false;       // 도착 완료
        }
        else if (L_ButtonManager.back_main)
        {
            SceneManager.LoadScene("MainScene");  // MainScene로 이동
            L_ButtonManager.back_main = false;    // 도착 완료
        }
    }
    #endregion

    #region 05-03). PrograssBar 갱신
    private void FillAmount(float result)
    {
        loading_slider.value = result;
        string result_text   = (result.Equals(1) ? "Completed ... " : "Loading ... ") + result.ToString("P"); // P : 값 형식을 %로 변환한다.
        loading_txt.text     = result_text;
    }
    #endregion
}
