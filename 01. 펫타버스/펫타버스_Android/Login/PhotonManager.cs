using UnityEngine.UI;
using UnityEngine;
using Photon.Pun;

public class PhotonManager : MonoBehaviourPunCallbacks
{
    #region 01). MemberField
    [Header("현재 연결 상태")]
    public Text status_txt; // 현재 접속 상태
    #endregion

    #region 02). Awake
    private void Awake()
    {
        // 초기화
        status_txt.text = "현재 상태 : DisConnect";

        if(!PhotonNetwork.IsConnected) Connect(); // Photon 서버와 연결한다.
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
        if (PhotonNetwork.IsConnected) status_txt.text = "현재 상태 : Connect"; // 결과 출력
    }
    #endregion

    #region 05-01). Connect : Photon 서버와 연결한다.
    private void Connect()
    {
        PhotonNetwork.ConnectUsingSettings(); // Photon 서버와 연결한다.
    }

    public override void OnConnectedToMaster()
    {
        // if (PhotonNetwork.IsConnected) Debug.Log("Connect Code 01"); // Test Code
    }

    private void OnApplicationQuit()
    {
        
    }
    #endregion
}