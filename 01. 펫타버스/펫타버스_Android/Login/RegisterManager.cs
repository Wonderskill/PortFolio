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

    #endregion

    #region 02). Start
    private void Start()
    {

    }
    #endregion

    #region 03). Update
    private void Update()
    {
    }
    #endregion

    #region 04-01). Register_Tab : Tab 키를 누르면 다음 InputField로 이동한다.
    private void Register_Tab()
    {
        
    }
    #endregion
}
