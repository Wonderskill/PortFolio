using UnityEngine.AI;
using UnityEngine;
using Photon.Pun;

public class Animal : MonoBehaviour
{
    #region 01). Memberfield
    [Header("Pet Nav AI 관련")]

    [SerializeField]
    private float ChaseDistance = 4.0f; // 이 거리 이상이 되면 쫓아감, 이 거리 안에 들어오면 멈춤
    [SerializeField]
    private float RunDistance = 10.0f;  // 이 거리 이상이 되면 뛰어서 쫓아감 
    [SerializeField]
    private float WalkSpeed = 3.5f;     // 걸어갈 때 속도 
    [SerializeField]
    private float RunSpeed = 7.0f;      // 뛰어갈 때 속도  

    [Space(10)]

    [SerializeField]
    private GameObject target;     // 쫓아갈 타겟
    [SerializeField]
    private GameObject[] targets;  // 모든 타겟

    [Header("Pet PhotonView + Animation + Nav")]

    public PhotonView PV;     // Pet PhotonView
    public Animator ani;      // Pet Animation
    private NavMeshAgent nav; // Pet Navigation

    // 유니티 입력에서 키 입력을 만듦
    private bool oneDown;   // Pet Bounceing Button 클릭 여부
    private bool twoDown;   // Pet Rolling Button 클릭 여부
    private bool threeDown; // Pet Eating Button 클릭 여부
    private bool fourDown;  // Pet Spining Button 클릭 여부
    private bool fiveDown;  // Pet Clicking Button 클릭 여부

    private bool isAct; // 행동중일 때, 움직임에 제약을 주는 bool 값
    #endregion

    #region 02). Start
    void Start()
    {
        // Nav 컴포넌트와 Player Gameobject를 가져옴
        ani = GetComponentInChildren<Animator>();
        nav = GetComponent<NavMeshAgent>();

        if (PV.IsMine)
        {
            GameObject.Find("MicManager").GetComponent<mic>().animal = this.gameObject;

            // Player 전체를 target에 저장한다.
            targets = GameObject.FindGameObjectsWithTag("Player");

            // 모든 Player가 가지고 있는 PhotonView를 view에 저장한다.
            for (int i = 0; i < targets.Length; i++)
            {
                PhotonView view = targets[i].GetComponent<PhotonView>();

                // 그 중 본인의 PhotonView라면
                if (view.IsMine)
                {
                    target = targets[i].gameObject; // Pet의 Target을 설정한다.
                }
            }
        }
    }
    #endregion

    #region 03). Update
    private void Update()
    {
        // Player의 PhotonView가 아니라면
        if (!PV.IsMine)
        {
            return;
        }

        Move();      // Pet 이동 
        GetInput();  // Pet 상호작용할 버튼 값을 받는다.
        NumberAct(); // Pet 상호작용할 입력 값을 받는다.
    }
    #endregion

    #region 04-01). GetInput : Pet 상호작용할 버튼 값을 받는다.
    // 키 입력을 받음
    private void GetInput()
    {
        oneDown = Input.GetButtonDown("One");     // Pet Bounceing Button
        twoDown = Input.GetButtonDown("Two");     // Pet Rolling Button
        threeDown = Input.GetButtonDown("Three"); // Pet Eating Button
        fourDown = Input.GetButtonDown("Four");   // Pet Spining Button
        fiveDown = Input.GetButtonDown("Five");   // Pet Clicking Button
    }
    #endregion

    #region 04-02). Move :  Pet 이동(Nav 컴포넌트의 목적지를 정해줌)
    private void Move()
    {
        // 행동중이면 멈춤
        if(isAct)
        {
            // 목적지를 자기 위치로 잡아 멈추고 리턴
            nav.SetDestination(transform.position);
            return;
        }

        try
        {
            // 거리에 따른 추격과 달리기를 위해 거리를 측정
            float distance = Vector3.Distance(target.transform.position, transform.position);

            // 거리가 4이상이면 (수정가능) - Pet이 걸을 때
            if (distance > ChaseDistance)
            {
                ani.SetBool("IsWalk", true);

                // 거리가 10이상이면 (수정가능) - Pet이 달릴 때
                if (distance > RunDistance) 
                {
                    ani.SetBool("IsRun", true);
                    nav.speed = RunSpeed;
                    nav.SetDestination(target.transform.position);
                }
                else
                {
                    ani.SetBool("IsRun", false);
                    nav.speed = WalkSpeed;
                    nav.SetDestination(target.transform.position);
                }
            }
            else
            {
                ani.SetBool("IsWalk", false);
                ani.SetBool("IsRun", false);
                nav.SetDestination(transform.position);
            }
        }
        catch (System.Exception ex)
        {
            Debug.Log(ex.Message);
        }
    }
    #endregion

    #region 04-03). STTAct : STT의 텍스트를 받아서 동물의 행동을 결정
    public void STTAct(string text)
    {
        if (!isAct)
        {
            int count = 0;
            for (int i = 0; i < text.Length - 1; i++)
            {
                string word = string.Format("{0}{1}",text[i],  text[i + 1]);
                Debug.Log(word);
                switch (word)
                {
                    case "뛰어": Bounceing(); count++; break;
                    case "굴러": Rolling();   count++; break;
                    case "먹어": Eating();    count++; break;
                    case "돌아": Spining();   count++; break;
                    case "클릭": Clicking();  count++; break;
                }

                if (count > 0) break;
            }
        }
    }
    #endregion

    #region 04-04). ButtonAct : Button의 값을 받아서 동물의 행동을 결정
    public void ButtonAct(int num)
    {
        if (!isAct)
        {
            switch (num)
            {
                case 0: Bounceing(); break;
                case 1: Rolling();   break;
                case 2: Eating();    break;
                case 3: Spining();   break;
                case 4: Clicking();  break;
            }
        }  
    }
    #endregion

    #region 04-05). NumberAct : Keyboard의 입력값을 받아서 동물의 행동을 결정
    private void NumberAct()
    {
        if (!isAct)
        {
            if      (oneDown && !ButtonManager.is_chat && !ButtonManager.is_setting)   Bounceing(); // 1번 키 입력
            else if (twoDown && !ButtonManager.is_chat && !ButtonManager.is_setting)   Rolling();   // 2번 키 입력
            else if (threeDown && !ButtonManager.is_chat && !ButtonManager.is_setting) Eating();    // 3번 키 입력
            else if (fourDown && !ButtonManager.is_chat && !ButtonManager.is_setting)  Spining();   // 4번 키 입력
            else if (fiveDown && !ButtonManager.is_chat && !ButtonManager.is_setting)  Clicking();  // 5번 키 입력
        }
    }
    #endregion

    #region 05-01). Bounceing : Pet과 1번째 상호작용(= 뛰어)
    public void Bounceing()
    {
        isAct = true; // Pet이 행동했다는 뜻

        ani.SetTrigger("Bounce"); // Animation 작동
        Invoke("ActEnd", 0.5f);
    }
    #endregion

    #region 05-02). Rolling : Pet과 2번째 상호작용(= 굴러)
    public void Rolling()
    {
        isAct = true; // Pet이 행동했다는 뜻

        ani.SetTrigger("Roll"); // Animation 작동
        Invoke("ActEnd", 0.5f);
    }
    #endregion

    #region 05-03). Eating : Pet과 3번째 상호작용(= 먹어)
    public void Eating()
    {
        isAct = true; // Pet이 행동했다는 뜻

        ani.SetTrigger("Peck"); // Animation 작동
        Invoke("ActEnd", 1.08f);
    }
    #endregion

    #region 05-04). Spining : Pet과 4번째 상호작용(= 돌아)
    public void Spining()
    {
        isAct = true; // Pet이 행동했다는 뜻

        ani.SetTrigger("Spin"); // Animation 작동
        Invoke("ActEnd", 0.5f);
    }
    #endregion

    #region 05-05). Clicking : Pet과 5번째 상호작용(= 클릭)
    public void Clicking()
    {
        isAct = true; // Pet이 행동했다는 뜻

        ani.SetTrigger("Clicked"); // Animation 작동
        Invoke("ActEnd", 0.5f);
    }
    #endregion

    #region 05-06). ActEnd : Pet의 Animation이 끝났을 때 작동
    private void ActEnd()
    {
        PV.RPC("End",RpcTarget.All);
    }

    [PunRPC]
    private void End()
    {
        isAct = false; // Pet의 행동이 끝났으니 다시 false로 변경

        ani.SetTrigger("Idle A"); // Animation 작동    
    }
    #endregion
}
