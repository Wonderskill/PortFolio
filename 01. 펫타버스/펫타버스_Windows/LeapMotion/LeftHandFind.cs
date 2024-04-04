using Leap.Unity.Interaction;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LeftHandFind : MonoBehaviour
{
    #region 01). Memberfield
    [SerializeField] private Rigidbody leftRd;
    [SerializeField] private BoxCollider leftclidr;
    private GameObject leftHd;
    private Transform trans;
    #endregion

    #region 02). Singleton
    //private static LeftHandFind instance;

    //public static LeftHandFind Instance
    //{
    //    get
    //    {
    //        if(null == instance)
    //        {
    //            instance = new LeftHandFind();
    //        }
    //        return instance;
    //    }
    //}
    #endregion

    #region 03). Update
    void Update()
    {
        if (Button3D.spherecount == 1)
            GameObject.Find("Sphere").GetComponent<Sphere>().leftHandRd = leftRd;
    }
    #endregion

    #region 04). OnEnable
    void OnEnable()
    {
        try
        {
            Invoke("FindRigid", 0.3f);
            Invoke("ChangeLayer", 0.3f);
        }
        catch
        {

        }
    }
    #endregion

    #region 05). FindRigid : 손에 있는 Rigidbody를 찾는다.
    void FindRigid()
    {
        try
        {
            var a = GameObject.Find("Left Interaction Hand Contact Bones/Contact Palm Bone");
            leftRd = a.GetComponent<Rigidbody>();
            GameObject.Find("LeapManager").GetComponent<LeapManager>().leftHandRd = leftRd;

            leftclidr = a.GetComponent<BoxCollider>();
            GameObject.Find("LeapManager").GetComponent<LeapManager>().leftcolider = leftclidr;

            leftHd = GameObject.Find("Left Interaction Hand Contact Bones");
            trans = leftHd.transform;
        }
        catch
        {

        }
    }
    #endregion

    #region 06-01). ChangeLayer : Interaction Hand 에 있는 Layer를 바꾼다.
    public void ChangeLayer()
    {
        try
        {
            ChangeLayersRecursively(trans);
        }
        catch
        {

        }
    }
    #endregion

    #region 06-02). ChangeLayersRecursively : Interaction Hand 에 있는 자식들의 Layer를 모두 바꾼다.
    public void ChangeLayersRecursively(Transform trans)
    {
        try
        {
            trans.gameObject.layer = 6;
            foreach (Transform child in trans)
            {
                ChangeLayersRecursively(child);
            }
        }
        catch
        {

        }
    }
    #endregion
}
