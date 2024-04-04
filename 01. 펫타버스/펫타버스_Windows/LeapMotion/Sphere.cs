using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;
using Leap;
using Leap.Unity;
using System;

public class Sphere : MonoBehaviour
{
    #region 01). Memberfield
    public static bool isThrown;

    public Hand leftHand;
    public Hand rightHand;

    public HandModelBase leftHandModelBase;
    public HandModelBase rightHandModelBase;

    [Header("RigidBody")]
    public Rigidbody sphereRigidbody;

    public Rigidbody leftHandRd;
    public Rigidbody rightHandRd;

    private BoxCollider floorCollider;

    [SerializeField]
    private GameObject floor;
    #endregion

    #region 02). Awake
    void Awake()
    {
        isThrown = false;
        sphereRigidbody = GetComponent<Rigidbody>();
        sphereRigidbody.useGravity = false;

        floor = GameObject.Find("Floor");

        floorCollider = floor.GetComponent<BoxCollider>();

        leftHandModelBase = GameObject.Find("LeapManager").GetComponent<LeapManager>().leftHandModelBase;
        rightHandModelBase = GameObject.Find("LeapManager").GetComponent<LeapManager>().rightHandModelBase;
    }
    #endregion

    #region 03). Start
    void Start()
    {
        isThrown = false;
        floor = GameObject.Find("Floor");

        floorCollider = floor.GetComponent<BoxCollider>();

        leftHandModelBase = GameObject.Find("LeapManager").GetComponent<LeapManager>().leftHandModelBase;
        rightHandModelBase = GameObject.Find("LeapManager").GetComponent<LeapManager>().rightHandModelBase;
    }
    #endregion

    #region 04). Update
    void Update()
    {
        if (Button3D.spherecount == 1)
        {
            rightHandRd = GameObject.Find("LeapManager").GetComponent<LeapManager>().rightHandRd;
            leftHandRd = GameObject.Find("LeapManager").GetComponent<LeapManager>().leftHandRd;
        }
    }
    #endregion

    void FixedUpdate()
    {
        leftHand = leftHandModelBase.GetLeapHand();
        rightHand = rightHandModelBase.GetLeapHand();

        //print(rightHand.PinchStrength);
    }

    #region 05). OnTriggerEnter
    void OnTriggerEnter(Collider other)
    {
        try
        {
            print(other);
            //if (other.gameObject.layer == 6)
            //{
            //    sphereRigidbody.useGravity = true;
            //}
            if (other.gameObject.layer == 6 && (rightHand.PinchStrength > 0.4f) && !LeapManager.isShoot)
            {
                Debug.Log("StartCoroutine(Shoot(other))");
                sphereRigidbody.useGravity = true;
                //LeapManager.isShoot = true;
                Shoot();
            }
            else if (other.gameObject.layer == 6 && (leftHand.PinchStrength > 0.4f) && !LeapManager.isShoot)
            {
                Debug.Log("StartCoroutine(Shoot(other))");
                sphereRigidbody.useGravity = true;
                //LeapManager.isShoot = true;
                Shoot();
            }
            if (LeapManager.isShoot == true && other.gameObject == floor)
            {
                Debug.Log("Sphere.cs : isThrown = true");
                isThrown = true;
            }
        }
        catch
        {
        }
    }
    #endregion

    void Shoot()
    {
        if (floorCollider)
        {
            LeapManager.isShoot = true;
            Debug.Log("Sphere.cs : isShoot = true");
        }
    }
}
