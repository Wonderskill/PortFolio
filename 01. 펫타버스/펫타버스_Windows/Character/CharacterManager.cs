using UnityEngine;
using Photon.Pun;

public class CharacterManager : MonoBehaviourPunCallbacks
{
    #region 01). MemberField
    [Header("UI_Panel")]
    public GameObject panel_char; // 캐릭터 선택 UI Panel
    public GameObject panel_pet;  // 펫 선택 UI Panel

    [Space(10)]

    [Header("SFX Sound")]
    public AudioSource SFX; // 버튼 효과음

    public static bool select_character; // 현재 캐릭터를 보유하고 있는지 여부
    #endregion

    #region 02). Awake
    private void Awake()
    {
        // 초기화
        select_character = false; // 아직 캐릭터를 선택하지 않았다.
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

    #region 05-01). Select_Boy : 캐릭터를 Boy로 설정한다.
    public void Select_Boy()
    {
        // Resources 폴더 내부에 있는 Boy 프리팹을 해당 Vector3 위치로 불러온다.
        if (PhotonNetwork.InRoom)
        {
            SFX.Play(); // 버튼 효과음 재생

            PhotonNetwork.Instantiate("Boy", new Vector3(104.0f, 8.0f, 88.0f), Quaternion.identity);
            select_character = true; // 캐릭터를 선택했다는 뜻

            panel_pet.SetActive(true);   // 펫 선택 UI 활성화
            panel_char.SetActive(false); // 캐릭터 선택 UI 비활성화    
        }
    }
    #endregion

    #region 05-02). Select_Girl : 캐릭터를 Girl로 설정한다.
    public void Select_Girl()
    {
        // Resources 폴더 내부에 있는 Boy 프리팹을 해당 Vector3 위치로 불러온다.
        if (PhotonNetwork.InRoom)
        {
            SFX.Play(); // 버튼 효과음 재생

            PhotonNetwork.Instantiate("Girl", new Vector3(104.0f, 8.0f, 88.0f), Quaternion.identity);
            select_character = true; // 캐릭터를 선택했다는 뜻

            panel_pet.SetActive(true);   // 펫 선택 UI 활성화
            panel_char.SetActive(false); // 캐릭터 선택 UI 비활성화    
        }
    }
    #endregion

    #region 05-03). Select_Soldier : 캐릭터를 Soldier로 설정한다.
    public void Select_Soldier()
    {
        // Resources 폴더 내부에 있는 Boy 프리팹을 해당 Vector3 위치로 불러온다.
        if (PhotonNetwork.InRoom)
        {
            SFX.Play(); // 버튼 효과음 재생

            PhotonNetwork.Instantiate("Soldier", new Vector3(104.0f, 8.0f, 88.0f), Quaternion.identity);
            select_character = true; // 캐릭터를 선택했다는 뜻

            panel_pet.SetActive(true);   // 펫 선택 UI 활성화
            panel_char.SetActive(false); // 캐릭터 선택 UI 비활성화    
        }
    }
    #endregion

    #region 05-04). Select_Princess : 캐릭터를 Princess로 설정한다.
    public void Select_Princess()
    {
        // Resources 폴더 내부에 있는 Boy 프리팹을 해당 Vector3 위치로 불러온다.
        if (PhotonNetwork.InRoom)
        {
            SFX.Play(); // 버튼 효과음 재생

            PhotonNetwork.Instantiate("Princess", new Vector3(104.0f, 8.0f, 88.0f), Quaternion.identity);
            select_character = true; // 캐릭터를 선택했다는 뜻

            panel_pet.SetActive(true);   // 펫 선택 UI 활성화
            panel_char.SetActive(false); // 캐릭터 선택 UI 비활성화    
        }
    }
    #endregion
}
