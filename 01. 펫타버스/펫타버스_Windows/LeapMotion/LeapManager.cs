using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Leap;
using Leap.Unity;

public class LeapManager : MonoBehaviour
{
    #region 01). Memberfield
    public static bool isShoot;
    public static Vector3 ballScale;

    [Header("Hand")]
    public LeapProvider leapProvider;
    public HandModelBase leftHandModelBase;
    public HandModelBase rightHandModelBase;

    [Header("Collider")]
    public BoxCollider leftcolider;
    public BoxCollider rightcolider;

    [Header("Animation")]
    public Animator anim;

    [Header("RigidBody")]
    public Rigidbody leftHandRd;// = LeftHandFind.Instance.leftRd;
    public Rigidbody rightHandRd;// = RightHandFind.Instance.rightRd;

    [Header("Ball")]
    public Transform Ball;

    [Header("Food")]
    public Transform[] foodArray;
    private Transform currentFood;
    private int index;

    [SerializeField]
    private Rigidbody ballRd;

    private Transform currentBallTransform;

    private bool isBounce;
    private bool isBall;

    private float distance;
    #endregion

    #region 02). Singleton
    // instance 멤버변수는 private하게 선언
    private static LeapManager instance = null;

    private void Awake()
    {
        print("Awake");
        isShoot = false;
        CreateFood();
        //Invoke("CreateFood", 1f);

        if (null == instance)
        {
            // 씬 시작될때 인스턴스 초기화, 씬을 넘어갈때도 유지되기위한 처리
            instance = this;
            //DontDestroyOnLoad(this.gameObject);
        }
        else
        {
            // instance가, GameManager가 존재한다면 GameObject 제거 
            //Destroy(this.gameObject);
        }
    }

    // Public 프로퍼티로 선언해서 외부에서 private 멤버변수에 접근만 가능하게 구현
    public static LeapManager Instance
    {
        get
        {
            if (null == instance)
            {
                return null;
            }
            return instance;
        }
    }
    #endregion

   
    #region 03). Start
    // Start is called before the first frame update
    void Start()
    {
        print("Start");
        isShoot = false;
        //Invoke("CreateFood", 1f);
    }
    #endregion

    #region 04). Update
    // Update is called once per frame
    void Update()
    {
        CursorVisible();                            // 마우스 커서의 보이기 여부를 결정한다.
        Cursor.lockState = CursorLockMode.Confined; // 마우스 커서를 화면 밖으로 벗어나지 않게 한다.
        //print(isShoot);
        //for (int i = 0; i < leapProvider.CurrentFrame.Hands.Count; i++)
        //{
        //    Hand _hand = leapProvider.CurrentFrame.Hands[i];

        //    if (!isBounce)
        //    {
        //        //StartCoroutine(OnBounce(_hand));
        //    }
        //}
    }
    #endregion

    #region 05). FixedUpdate
    void FixedUpdate()
    {
        //Hand leftHand = leftHandModelBase.GetLeapHand();
        //Hand rightHand = rightHandModelBase.GetLeapHand();

        //print("GrabStrength : " + rightHand.GrabStrength);
        //print("PinchStrength : " + rightHand.PinchStrength);

        //if (leftHand == null || rightHand == null)
        //    return;

        //distance = Vector3.Distance(leftHand.PalmPosition, rightHand.PalmPosition);

        ////print(distance);

        //if (distance < 0.1f && !isBall && !isShoot)
        //    CreateBall();

        //if (!isBall || currentBallTransform == null)
        //    return;

        //ShootBall(leftHand, rightHand);
    }
    #endregion

    #region 06). CursorVisible : 마우스 커서를 보이게 만들거나 숨긴다.
    void CursorVisible()
    {
        // Tab 키를 입력할 경우
        if (Input.GetKeyDown(KeyCode.Tab))
        {
            Cursor.visible = true; // 마우스 커서가 보인다.
        }
        // 마우스 왼쪽을 클릭할 경우
        else if (Input.GetMouseButtonDown(0))
        {
            Cursor.visible = false; // 마우스 커서을 숨긴다.
        }
    }
    #endregion

    #region 지금은 안쓰는 코드
    
    //#region 07). OnBounce : 손을 특정 Y 좌표에 놓으면 동물이 Bounce한다.
    //IEnumerator OnBounce(Hand _hand)
    //{
    //    yield return new WaitForSeconds(1f);
    //    Vector3 pridictedPinchPosition = _hand.GetPredictedPinchPosition();
    //    float ycords = pridictedPinchPosition.y;

    //    if (ycords.To(0.40f - 0.14f) > 0)
    //    {
    //        anim.SetBool("IsBounce", true);
    //        print("Bounce");
    //        isBounce = true;
    //    }

    //    isBounce = false;
    //    yield return new WaitForSeconds(1f);
    //    anim.SetBool("IsBounce", false);
    //    yield return new WaitForSeconds(5f);
    //}
    //#endregion

    //#region 08-01). CreateBall : 두 손의 거리가 0.1 이상이면 공을 두 손의 중간에 생성한다.
    //void CreateBall()
    //{
    //    isBall = true;

    //    currentBallTransform = Instantiate(Ball);

    //    ballRd = currentBallTransform.GetComponent<Rigidbody>();
    //}
    //#endregion

    //#region 08-02). ShootBall : 두 손의 힘이 1 이상이면 공을 손의 방향으로 쏜다.
    //void ShootBall(Hand leftHand, Hand rightHand)
    //{
    //    if (distance > 0.5f)
    //    {
    //        isBall = false;
    //        ballRd.AddForce(Vector3.forward * 2, ForceMode.Impulse);
    //        ballRd.useGravity = true;
    //        isShoot = true;
    //        return;
    //    }

    //    currentBallTransform.position = (leftHand.PalmPosition + rightHand.PalmPosition) * 0.5f;

    //    ballScale = currentBallTransform.transform.localScale = new Vector3(distance, distance, distance);

    //    if (leftHandRd.velocity.magnitude > 1f && rightHandRd.velocity.magnitude > 1f && distance > 0.1f)
    //    {
    //        isBall = false;
    //        var direction = leftHandRd.velocity.normalized;

    //        direction = new Vector3(Mathf.Clamp(direction.x, -0.4f, 0.4f), Mathf.Clamp(direction.y, -0.05f, 0.05f), Mathf.Clamp(direction.z, 0.1f, 10));

    //        ballRd.AddForce(direction * 5, ForceMode.Impulse);
    //        ballRd.useGravity = true;
    //        isShoot = true;
    //        return;
    //    }
    //}
    //#endregion

    #endregion

    #region 09). CreateFood : 배열안에 있는 음식 무작위 생성
    public void CreateFood()
    {
        index = Random.Range(0, foodArray.Length);
        Instantiate(foodArray[index]);
    }
    #endregion
}
