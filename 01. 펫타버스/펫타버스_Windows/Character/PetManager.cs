using UnityEngine;
using Photon.Pun;

public class PetManager : MonoBehaviour
{
    #region 01). MemberField
    [Header("UI_Panel")]
    public GameObject panel_pet;  // 펫 선택 UI Panel
    public GameObject panel_help; // 도움말 UI Panel

    [Header("SFX Sound")]
    public AudioSource SFX; // 버튼 효과음

    public static bool select_pet; // 현재 캐릭터를 보유하고 있는지 여부

    public static bool is_resolution; // 해상도 증가 여부

    #endregion

    #region 02). Awake
    private void Awake()
    {
        is_resolution = false;
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

    #region 05-01). Select_Dog : 펫을 Dog로 설정한다.
    public void Select_Dog()
    {
        // Resources 폴더 내부에 있는 Boy 프리팹을 해당 Vector3 위치로 불러온다.
        if (PhotonNetwork.InRoom)
        {
            SFX.Play(); // 버튼 효과음 재생

            PhotonNetwork.Instantiate("Dog", new Vector3(104.0f, 8.0f, 88.0f), Quaternion.identity);

            panel_help.SetActive(true); // 도움말 UI 활성화
            panel_pet.SetActive(false); // 펫 선택 UI 비활성화
            NodeMain.PetType = "Dog";
            is_resolution = true; // 해상도 변경이 시작된다.
            select_pet = true;
        }
    }
    #endregion

    #region 05-02). Select_Cat :  펫을 Cat으로 설정한다.
    public void Select_Cat()
    {
        // Resources 폴더 내부에 있는 Boy 프리팹을 해당 Vector3 위치로 불러온다.
        if (PhotonNetwork.InRoom)
        {
            SFX.Play(); // 버튼 효과음 재생

            PhotonNetwork.Instantiate("Cat", new Vector3(104.0f, 8.0f, 88.0f), Quaternion.identity);

            panel_help.SetActive(true); // 도움말 UI 활성화
            panel_pet.SetActive(false); // 펫 선택 UI 비활성화
            NodeMain.PetType = "Cat";
            is_resolution = true; // 해상도 변경이 시작된다.
            select_pet = true;
        }
    }
    #endregion

    #region 05-03). Select_Chick :  펫을 Chick으로 설정한다.
    public void Select_Chick()
    {
        // Resources 폴더 내부에 있는 Boy 프리팹을 해당 Vector3 위치로 불러온다.
        if (PhotonNetwork.InRoom)
        {
            SFX.Play(); // 버튼 효과음 재생

            PhotonNetwork.Instantiate("Chick", new Vector3(104.0f, 8.0f, 88.0f), Quaternion.identity);

            panel_help.SetActive(true); // 도움말 UI 활성화
            panel_pet.SetActive(false); // 펫 선택 UI 비활성화
            NodeMain.PetType = "Chick";
            is_resolution = true; // 해상도 변경이 시작된다.
            select_pet = true;
        }
    }
    #endregion
}
