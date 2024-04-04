using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using WebSocketSharp;
using System.Threading;
using UnityEngine.UI;


public class Node : MonoBehaviour
{
    #region 01). MemberField
    // Thread 관련
    private Thread thread;
    private Queue<string> queue = new Queue<string>(); // 자료관리
    public static string MSG { get; set; }

    public bool IDCheck;
    public bool PWCheck;

    // node.js 서버열기
    public static WebSocket ws; // 서버에대한 정보를 가져오기위한 STATIC
    public static string ID { get; set; } //검색용 User의 ID값 저장용

    // Login 관련
    public InputField loginidinput;
    public InputField loginpwinput;

    public Text resulttext;
    public Text loginchecktext;
    public bool isLoginOK;
    public bool isLoginCheck;
    public bool isLoginClick;

    // Register 관련
    public InputField idinput;
    public InputField pwinput;
    public InputField nameinput;
    #endregion

    #region 02). Start
    private void Start()
    {
        isLoginOK = false;
        isLoginCheck = false;
        isLoginClick = false;

        // WebSocketSharp.lib 파일을 사용해서 MySQL과 연결한다.

        ws = new WebSocket("ws://10.101.34.54:3334"); // 127.0.0.1은 본인의 아이피 주소이다. 3334포트로 연결한다는 의미이다.
        ws.OnMessage += ws_OnMessage;                  // 서버에서 유니티 쪽으로 메세지가 올 경우 실행할 함수를 등록한다.
        ws.OnOpen += ws_OnOpen;                        // 서버가 연결된 경우 실행할 함수를 등록한다
        ws.OnClose += ws_OnClose;                      // 서버가 닫힌 경우 실행할 함수를 등록한다.
        ws.Connect();                                  // 서버에 연결한다.

        // 쓰레드 동작시킬꺼 생성 (Run 함수)
        thread = new Thread(Run);
        thread.Start();
    }
    #endregion

    #region 03). Update
    private void Update()
    {
        if (queue.Count > 0)
        {
            resulttext.text = queue.Dequeue();
        }

        if (isLoginClick == true)
        {
            Login();
        }

        if (resulttext.text == "로그인성공")
        {
            ID = loginidinput.text;
            
            if (isLoginOK == false)
            {
                ws.Send(string.Format("LoginOK@{0}", loginidinput.text));
                isLoginOK = true;
            }        
            StartCoroutine(NextSeane());          
        }
    }
    #endregion

    #region 04-01). Run : 서버에서 받은 메세지 자료화 (크로스 스레드 방지)
    void Run()
    {
        while (true)
        {
            queue.Enqueue(MSG);
            Thread.Sleep(100);
        }
    }
    #endregion

    #region 04-02). SignUpBtnSendMessage : 회원가입 Button
    public void SignUpBtnSendMessage()
    {
        if(idinput.text != pwinput.text)
        {
            ws.Send(string.Format("SignUp@{0}@{1}@{2}", idinput.text, pwinput.text, nameinput.text));
            Debug.Log("회원가입");
        }
        else
        {
            Debug.Log("아이디와 비밀번호는 서로 달라야합니다.");
        }   
    }
    #endregion

    #region 04-03). LoginBtnSendMessage : 로그인 Button
    public void LoginBtnSendMessage()
    {
        ws.Send(string.Format("Login@{0}@{1}", loginidinput.text, loginpwinput.text));
        Debug.Log("로그인");
    }
    #endregion

    #region 04-04). LoginCheck : 로그인 체크
    public void LoginCheck()
    {
        ws.Send(string.Format("LoginCheck@{0}", loginidinput.text));
    }

    public void LoginCheckin()
    {
        if (MSG == "온라인")
        {
            loginchecktext.text = "현재 로그인중인 계정입니다.";
        }
        else if (MSG == "오프라인")
        {
            loginchecktext.text = "오프라인";
        }
    }
    #endregion

    #region 콜백
    void ws_OnMessage(object sender, MessageEventArgs e)
    {
        Debug.Log(e.Data);//받은 메세지를 디버그 콘솔에 출력한다.
        MSG = e.Data;

        if (MSG == "ID확인완료") 
        {
            IDCheck = true;
            Debug.Log("ID인증성공");
        }
        else if (MSG == "PW확인완료")
        {
            PWCheck = true;
            Debug.Log("PW인증성공");
        }
        else if (MSG == "오프라인")
        {
            ws.Send("로그인가능@");
        }
        
    }

    void ws_OnOpen(object sender, System.EventArgs e)
    {
        Debug.Log("open"); //디버그 콘솔에 "open"이라고 찍는다.
    }

    void ws_OnClose(object sender, CloseEventArgs e)
    {
        Debug.Log("close"); //디버그 콘솔에 "close"이라고 찍는다.
    }
    #endregion

    #region 코루틴
    IEnumerator NextSeane()
    {
        yield return new WaitForSeconds(1f);
        LoginManager.Login_GoToLogin();
    }
    #endregion

    public void OnApplicationQuit()
    {
        ws.Send(string.Format("LogOut@{0}", Node.ID));
    }


    public void Login()
    {
        if (IDCheck == true && PWCheck == true)
        {
            if (isLoginCheck == false)
            {
                LoginCheck();
                isLoginCheck = true;
            }  
        }
    }

    public void LoginClick()
    {
        isLoginClick = true;
    }
}
