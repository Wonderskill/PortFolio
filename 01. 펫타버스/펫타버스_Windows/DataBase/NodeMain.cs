using System.Collections;
using UnityEngine;
using Photon.Pun;

public class NodeMain : MonoBehaviourPun
{
    #region 01). MemberField
    [Header("UI_Panel")]
    public GameObject panel_char;    // 캐릭터 선택 UI Panel
    public GameObject panel_pet;     // 펫 선택 UI Panel
    public GameObject panel_help;    // 도움말 선택 UI Panel
    public GameObject panel_loading; // 캐릭터 선택 전 로딩 Panel

    public string playername;
    public static string Name { get; set; }
    public static string PetType { get; set; }

    [Header("ETC 변수")]
    public bool isNameCheck; // 닉네임 체크 여부
    public bool charcheck;   // 캐릭터 선택 여부
    public bool petcheck;    // 펫 선택 여부
    public bool sendcharcheck;
    public static bool r_isNameCheck; // Temp 변수

    public static bool select_node; // 현재 캐릭터를 보유하고 있는지 여부
    #endregion

    #region 02). Start
    private void Start()
    {
        // 초기화
        isNameCheck = false;
        charcheck   = false;
        petcheck    = false;
        sendcharcheck = false;
        Invoke("N", 2.5f);
    }
    #endregion

    private void Awake()
    {
        isNameCheck = false;
        charcheck = false;
        petcheck = false;
        sendcharcheck = false;
        Invoke("N", 2.5f);
    }

    #region 03). Update
    private void Update()
    {
        playername = Name;
        
        // 캐릭터가 선택되어 있지 않다면 CharCheck를 진행한다.
        if (charcheck == false)
        {
           CharCheck();
        }

        // 펫이 선택되어 있지 않다면 PetCheck를 진행한다.
        if (charcheck == true && petcheck == false)
        {
            Node.ws.Send(string.Format("PetCheck@{0}", Node.ID));
            PetCheck();
        }

        // 캐릭터는 보유하고 있는데 닉네임을 체크하지 않았다면 NameCheck를 진행한다.
        if (isNameCheck == false && (petcheck == true || PetManager.select_pet) && (select_node || CharacterManager.select_character))
        {
            Invoke("NameCheck", 1);
        }        
    }
    #endregion

    #region 04-01). NameCheck : Player의 Nickname을 받아온다.
    public void NameCheck()
    {
        // 01).
        Node.ws.Send(string.Format("NameCheck@{0}", Node.ID));

        // 02). DB로부터 받아온 Nickname을 저장
        Name = Node.MSG;
        isNameCheck = true; // Nickname을 받아왔으니 True
        r_isNameCheck = true;

        PhotonNetwork.LocalPlayer.NickName = Name;
    }
    #endregion

    #region 04-02). CharCheck : Player의 Character를 받아온다.
    public void CharCheck()
    {
        if(sendcharcheck == false)
        {
            if (Node.MSG == "Boy")
            {
                Select_Boy();
                Node.MSG = "";
                sendcharcheck = true;
            }
            else if (Node.MSG == "Girl")
            {
                Select_Girl();
                Node.MSG = "";
                sendcharcheck = true;
            }
            else if (Node.MSG == "Solider")
            {
                Select_Soldier();
                Node.MSG = "";
                sendcharcheck = true;
            }
            else if (Node.MSG == "Princess")
            {
                Select_Princess();
                Node.MSG = "";
                sendcharcheck = true;
            }
            else if (Node.MSG == "결과가없습니다")
            {
                panel_loading.SetActive(false);
                Node.MSG = "";
                sendcharcheck = true;
            }
        }       
    }
    #endregion

    #region 04-03). PetCheck : Player의 Pet을 받아온다.
    public void PetCheck()
    {
        if (Node.MSG == "Dog")
        {
            Select_Dog();
            Node.MSG = "";
        }
        else if (Node.MSG == "Cat")
        {
            Select_Cat();
            Node.MSG = "";
        }
        else if (Node.MSG == "Chick")
        {
            Select_Chick();
            Node.MSG = "";
        }
    }
    #endregion

    #region 04-04). 캐릭터선택 DB
    // 캐릭터가 Boy일 때
    public void SelectBoy()
    {
        Node.ws.Send(string.Format("Boy@{0}@Boy", Node.ID));
        Debug.Log("SelectBoy");
    }

    // 캐릭터가 Girl일 때
    public void SelectGirl()
    {
        Node.ws.Send(string.Format("Girl@{0}@Girl", Node.ID));
        Debug.Log("SelectGirl");
    }

    // 캐릭터가 Solider일 때
    public void SelectSolider()
    {
        Node.ws.Send(string.Format("Solider@{0}@Solider", Node.ID));
        Debug.Log("SelectSolider");
    }

    // 캐릭터가 Princess일 때
    public void SelectPrincess()
    {
        Node.ws.Send(string.Format("Princess@{0}@Princess", Node.ID));
        Debug.Log("SelectPrincess");
    }
    #endregion

    #region 04-05). Select_Boy : 캐릭터를 Boy로 설정한다.
    public void Select_Boy()
    {
        // Resources 폴더 내부에 있는 Boy 프리팹을 해당 Vector3 위치로 불러온다.
        if (PhotonNetwork.InRoom)
        {
            PhotonNetwork.Instantiate("Boy", new Vector3(104.0f, 8.0f, 88.0f), Quaternion.identity);
            select_node = true;

            panel_loading.SetActive(false);
            panel_char.SetActive(false); // 캐릭터 선택 UI 비활성화
            panel_pet.SetActive(true);   // 펫 선택 UI 활성화
            charcheck = true;
        }
    }
    #endregion

    #region 04-06). Select_Girl : 캐릭터를 Girl로 설정한다.
    public void Select_Girl()
    {
        // Resources 폴더 내부에 있는 Girl 프리팹을 해당 Vector3 위치로 불러온다.
        if (PhotonNetwork.InRoom)
        {
            PhotonNetwork.Instantiate("Girl", new Vector3(104.0f, 8.0f, 88.0f), Quaternion.identity);
            select_node = true;

            panel_loading.SetActive(false);
            panel_char.SetActive(false); // 캐릭터 선택 UI 비활성화
            panel_pet.SetActive(true);   // 펫 선택 UI 활성화
            charcheck = true;
        }
    }
    #endregion

    #region 04-07). Select_Soldier : 캐릭터를 Soldier로 설정한다.
    public void Select_Soldier()
    {
        // Resources 폴더 내부에 있는 Soldier 프리팹을 해당 Vector3 위치로 불러온다.
        if (PhotonNetwork.InRoom)
        {
            PhotonNetwork.Instantiate("Soldier", new Vector3(104.0f, 8.0f, 88.0f), Quaternion.identity);
            select_node = true;

            panel_loading.SetActive(false);
            panel_char.SetActive(false); // 캐릭터 선택 UI 비활성화
            panel_pet.SetActive(true);   // 펫 선택 UI 활성화
            charcheck = true;
        }
    }
    #endregion

    #region 04-08). Select_Princess : 캐릭터를 Princess로 설정한다.
    public void Select_Princess()
    {
        // Resources 폴더 내부에 있는 Princess 프리팹을 해당 Vector3 위치로 불러온다.
        if (PhotonNetwork.InRoom)
        {
            PhotonNetwork.Instantiate("Princess", new Vector3(104.0f, 8.0f, 88.0f), Quaternion.identity);
            select_node = true;

            panel_loading.SetActive(false);
            panel_char.SetActive(false); // 캐릭터 선택 UI 비활성화
            panel_pet.SetActive(true);   // 펫 선택 UI 활성화
            charcheck = true;
        }
    }
    #endregion

    #region 05-01). 펫 DB
    public void SelectDog()
    {
        Node.ws.Send(string.Format("Dog@{0}@Dog", Node.ID));
        Debug.Log("Dog선택");
    }
    public void SelectCat()
    {
        Node.ws.Send(string.Format("Cat@{0}@Cat", Node.ID));
        Debug.Log("Cat선택");
    }
    public void SelectChick()
    {
        Node.ws.Send(string.Format("Chick@{0}@Chick", Node.ID));
        Debug.Log("Chick선택");
    }
    #endregion

    #region 05-02). Select_Dog : 펫을 Dog로 설정한다.
    public void Select_Dog()
    {
        // Resources 폴더 내부에 있는 Dog 프리팹을 해당 Vector3 위치로 불러온다.
        if (PhotonNetwork.InRoom)
        {
            PhotonNetwork.Instantiate("Dog", new Vector3(104.0f, 8.0f, 88.0f), Quaternion.identity);

            panel_help.SetActive(true); // 도움말 UI 활성화
            panel_pet.SetActive(false); // 펫 선택 UI 활성화
            petcheck = true;
            PetType = "Dog";
        }
    }
    #endregion

    #region 05-03). Select_Dog : 펫을 Cat로 설정한다.
    public void Select_Cat()
    {
        // Resources 폴더 내부에 있는 Cat 프리팹을 해당 Vector3 위치로 불러온다.
        if (PhotonNetwork.InRoom)
        {
            PhotonNetwork.Instantiate("Cat", new Vector3(104.0f, 8.0f, 88.0f), Quaternion.identity);

            panel_help.SetActive(true); // 도움말 UI 활성화
            panel_pet.SetActive(false); // 펫 선택 UI 활성화
            petcheck = true;
            PetType = "Cat";
        }
    }
    #endregion

    #region 05-04). Select_Chick : 펫을 Chick로 설정한다.
    public void Select_Chick()
    {
        // Resources 폴더 내부에 있는 Chick 프리팹을 해당 Vector3 위치로 불러온다.
        if (PhotonNetwork.InRoom)
        {
            PhotonNetwork.Instantiate("Chick", new Vector3(104.0f, 8.0f, 88.0f), Quaternion.identity);

            panel_help.SetActive(true);  // 도움말 UI 활성화
            panel_pet.SetActive(false);  // 펫 선택 UI 활성화
            petcheck = true;
            PetType = "Chick";
        }
    }
    #endregion

    #region 05-05). 로그아웃
    public void OnApplicationQuit()
    {
        Node.ws.Send(string.Format("LogOut@{0}", Node.ID));
    }

    public void Logout()
    {
        Node.ws.Send(string.Format("LogOut@{0}", Node.ID));
    }
    #endregion

    #region 06-01). 클라이언트에서 캐릭터 여부를 체크한다. <- 이 메세지를 서버에게 전달한다.
    public void N()
    {
        Node.ws.Send(string.Format("CharCheck@{0}", Node.ID));
    }
    #endregion
}
