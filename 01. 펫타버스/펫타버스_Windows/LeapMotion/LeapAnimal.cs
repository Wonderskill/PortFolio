using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;
using UnityEngine.AI;
using Leap;
using Leap.Unity;

public class LeapAnimal : MonoBehaviour
{
    #region 01). Memberfield
    [Header("Animator")]
    public Animator anim;

    [Header("Hand")]
    public LeapProvider leapProvider;
    public HandModelBase leftHandModelBase;
    public HandModelBase rightHandModelBase;
    public static Hand leftHand;
    public static Hand rightHand;

    [Space(10f)]
    public Transform faketarget;
    
    [Space(10f)]
    public float followDistance;
    
    [SerializeField]
    private GameObject target;
    public GameObject player;

    private float walkSpeed = 3.5f;

    private NavMeshAgent nav;
    
    private bool isChase;
    private bool isBounce;
    private bool isReturn;
    private bool isDestroy;

    // 유니티 입력에서 키 입력을 만듦
    private bool oneDown;   // Pet Bounceing Button 클릭 여부
    private bool twoDown;   // Pet Rolling Button 클릭 여부
    private bool threeDown; // Pet Eating Button 클릭 여부
    private bool fourDown;  // Pet Spining Button 클릭 여부
    private bool fiveDown;  // Pet Clicking Button 클릭 여부

    private bool isAct; // 행동중일 때, 움직임에 제약을 주는 bool 값
    #endregion

    #region 02). Start
    // Start is called before the first frame update
    void Start()
    {
        //anim = GetComponent<Animator>();
        nav = GetComponent<NavMeshAgent>();
        isBounce = false;
    }
    #endregion

    #region 03). Update
    // Update is called once per frame
    void Update()
    {
        if (Sphere.isThrown == true)
        {
            target = GameObject.FindGameObjectWithTag("Target");
            isDestroy = false;
            if (target == null)
                return;

            StartCoroutine(Chase()); // 공 쫒아가기
        }
        player = GameObject.FindGameObjectWithTag("RealPlayer");

        Follow();       // 플레이어 쫒아가기
        GetInput();     // Pet 상호작용할 버튼 값을 받는다.
        NumberAct();    // Pet 상호작용할 입력 값을 받는다.
    }
    #endregion

    #region 04). FixedUpdate
    void FixedUpdate()
    {
        leftHand = leftHandModelBase.GetLeapHand();
        rightHand = rightHandModelBase.GetLeapHand();
    }
    #endregion

    #region 05). OnTriggerEnter
    void OnTriggerEnter(Collider other)
    {
        if (isChase && other.gameObject.tag == "Target")
        {
            Debug.Log("Trigger Start");
            Destroy(other.gameObject);
            target = null;
            isDestroy = true;
            Button3D.spherecount--;
            faketarget.gameObject.SetActive(true);
            faketarget.transform.localScale = new Vector3(1.5f, 1.5f, 1.5f);
            if (isChase)
            { 
                Debug.Log("Return Start");
                Return();
                if (isReturn)
                {
                    Debug.Log("DestoryBall Start");
                    Invoke("DestoryBall", 2.5f);
                }
            }
        }

        //손이 Pet에게 닿았을 때
        if (other.gameObject.layer == 6)
        {
            anim.SetBool("IsBounce", true);
            print("Bounce");
            isBounce = true;
        }

        //음식이 Pet에게 닿았을 때
        if(other.gameObject.tag == "Food")
        {
            Eating();
            Destroy(other.gameObject);
            Invoke("Spining", 1.5f);
            LeapManager.Instance.CreateFood();
        }
    }
    #endregion

    #region 06). OnTriggerExit
    void OnTriggerExit(Collider other)
    {
        if (other.gameObject.layer == 6)
        {
            isBounce = false;
            anim.SetBool("IsBounce", false);
        }
    }
    #endregion

    #region 07). Follow : 플레이어 쫒아가기
    void Follow()
    {
        try
        {
            float distance = Vector3.Distance(player.transform.position, transform.position);

            // 거리가 4이상이면 (수정가능) - Pet이 걸을 때
            if (distance > followDistance)
            {
                anim.SetBool("IsWalk", true);
                nav.speed = walkSpeed;
                nav.SetDestination(player.transform.position);
            }
            else
            {
                anim.SetBool("IsWalk", false);
                anim.SetBool("IsRun", false);
                nav.SetDestination(transform.position);
                //nav.gameObject.transform.rotation = Quaternion.LookRotation(new Vector3(0, 180, 0));
            }
        }
        catch (System.Exception ex)
        {
            Debug.Log(ex.Message);
        }

    }
    #endregion

    #region 08-01). Chase : 공 따라가기
    IEnumerator Chase()
    {
        anim.SetBool("IsRun", true);
        yield return new WaitForSeconds(1.5f);
        Debug.Log("Chase Start");
        if (!isDestroy && Button3D.spherecount == 1)
            isChase = nav.SetDestination(target.transform.position);
        else
        {
            yield break;
        }
        yield return new WaitUntil(() => isDestroy == true);
        //yield return new WaitForSeconds(1.5f);
        if (isChase)
        {
            LeapManager.isShoot = false;
            Sphere.isThrown = false;
            yield break;
        }
    }
    #endregion

    #region 08-02). Return : 공 따라간후 다시 플레이어에게 돌아오기
    void Return()
    {
        try
        {
            float distance = Vector3.Distance(player.transform.position, transform.position);
            if (distance > followDistance)
            {
                isReturn = nav.SetDestination(player.transform.position);
                isChase = false;
            }
            else
            {
                anim.SetBool("IsWalk", false);
                anim.SetBool("IsRun", false);
                nav.SetDestination(transform.position);
                //nav.gameObject.transform.rotation = Quaternion.LookRotation(new Vector3(0, 180, 0));
            }
        }
        catch (System.Exception ex)
        {
            Debug.Log(ex.Message);
        }
    }
    #endregion

    #region 08-03). DestoryBall : 플레이어에게 돌아온 후 물고 있던 공 삭제
    void DestoryBall()
    {
        faketarget.gameObject.SetActive(false);
        LeapManager.isShoot = false;
        Sphere.isThrown = false;
    }
    #endregion

    #region 09-01). GetInput : Pet 상호작용할 버튼 값을 받는다.
    private void GetInput()
    {
        oneDown = Input.GetButtonDown("One");     // Pet Bounceing Button
        twoDown = Input.GetButtonDown("Two");     // Pet Rolling Button
        threeDown = Input.GetButtonDown("Three"); // Pet Eating Button
        fourDown = Input.GetButtonDown("Four");   // Pet Spining Button
        fiveDown = Input.GetButtonDown("Five");   // Pet Clicking Button
    }
    #endregion

    #region 09-02). NumberAct : Keyboard의 입력값을 받아서 동물의 행동을 결정
    private void NumberAct()
    {
        if (!isAct)
        {
            if (oneDown) Bounceing(); // 1번 키 입력
            else if (twoDown) Rolling();   // 2번 키 입력
            else if (threeDown) Eating();    // 3번 키 입력
            else if (fourDown) Spining();   // 4번 키 입력
            else if (fiveDown) Clicking();  // 5번 키 입력
        }
    }
    #endregion

    #region 10-01). Bounceing : Pet과 1번째 상호작용(= 뛰어)
    public void Bounceing()
    {
        isAct = true; // Pet이 행동했다는 뜻

        anim.SetTrigger("Bounce"); // Animation 작동
        Invoke("End", 0.5f);
    }
    #endregion

    #region 10-02). Rolling : Pet과 2번째 상호작용(= 굴러)
    public void Rolling()
    {
        isAct = true; // Pet이 행동했다는 뜻

        anim.SetTrigger("Roll"); // Animation 작동
        Invoke("End", 0.5f);
    }
    #endregion

    #region 10-03). Eating : Pet과 3번째 상호작용(= 먹어)
    public void Eating()
    {
        isAct = true; // Pet이 행동했다는 뜻

        anim.SetTrigger("Peck"); // Animation 작동
        Invoke("End", 1.08f);
    }
    #endregion

    #region 10-04). Spining : Pet과 4번째 상호작용(= 돌아)
    public void Spining()
    {
        isAct = true; // Pet이 행동했다는 뜻

        anim.SetTrigger("Spin"); // Animation 작동
        Invoke("End", 0.5f);
    }
    #endregion

    #region 10-05). Clicking : Pet과 5번째 상호작용(= 클릭)
    public void Clicking()
    {
        isAct = true; // Pet이 행동했다는 뜻

        anim.SetTrigger("Clicked"); // Animation 작동
        Invoke("End", 0.5f);
    }
    #endregion

    #region 10-06). ActEnd : Pet의 Animation이 끝났을 때 작동
    private void End()
    {
        isAct = false; // Pet의 행동이 끝났으니 다시 false로 변경

        anim.SetTrigger("Idle A"); // Animation 작동    
    }
    #endregion
}
