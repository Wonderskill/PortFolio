using UnityEngine.UI;
using UnityEngine;

public class RegisterManager : MonoBehaviour
{
    #region 01). MemberField
    [Header("Register - UI")]
    public GameObject panel_register; // 회원가입 Panel

    public InputField input_id;   // ID InputField
    public InputField input_pw;   // PW InputField
    public InputField input_name; // Name InputField

    [Space(10)]

    [Header("Register - Bool")]
    public static bool isSelect; // ID InputField 강조 유무
    #endregion

    #region 02). Start
    private void Start()
    {
        isSelect = false; // ID InputField 강조 X
    }
    #endregion

    #region 03). Update
    private void Update()
    {
        // 회원가입 Register가 활성화되어 있을 경우
        if (panel_register.activeSelf && !isSelect)
        {
            input_id.Select(); // ID InputField에 커서가 잡힌다.
            isSelect = true;   // ID InputField 강조 O
        }

        Register_Tab(); // Tab 키를 누르면 다음 InputField로 이동
    }
    #endregion

    #region 04-01). Register_Tab : Tab 키를 누르면 다음 InputField로 이동한다.
    private void Register_Tab()
    {
        // ID InputField에서 Tab키를 입력할 경우
        if (Input.GetKeyDown(KeyCode.Tab) && input_id.isFocused)
        {
            input_pw.Select(); // PW InputField에 커서가 잡힌다.
        }
        // PW InputField에서 Tab키를 입력할 경우
        else if (Input.GetKeyDown(KeyCode.Tab) && input_pw.isFocused)
        {
            input_name.Select(); // Name InputField에 커서가 잡힌다.
        }
        // Name InputField에서 Tab키를 입력할 경우
        else if (Input.GetKeyDown(KeyCode.Tab) && input_name.isFocused)
        {
            input_id.Select(); // ID InputField에 커서가 잡힌다.
        }
    }
    #endregion
}
