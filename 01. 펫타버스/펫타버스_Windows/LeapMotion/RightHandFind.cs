using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RightHandFind : MonoBehaviour
{
    #region 01). Memberfield
    [SerializeField] private Rigidbody rightRd;
    [SerializeField] private BoxCollider rightclidr;
    private GameObject rightHd;
    private Transform trans;
    #endregion

    #region 02). Singleton
    //private static RightHandFind instance;

    //public static RightHandFind Instance
    //{
    //    get
    //    {
    //        if (null == instance)
    //        {
    //            instance = new RightHandFind();
    //        }
    //        return instance;
    //    }
    //}
    #endregion

    #region 03). Update
    void Update()
    {
        if (Button3D.spherecount == 1)
            GameObject.Find("Sphere").GetComponent<Sphere>().rightHandRd = rightRd;
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
            var b = GameObject.Find("Right Interaction Hand Contact Bones/Contact Palm Bone");
            rightRd = b.GetComponent<Rigidbody>();
            GameObject.Find("LeapManager").GetComponent<LeapManager>().rightHandRd = rightRd;
            

            rightclidr = b.GetComponent<BoxCollider>();
            GameObject.Find("LeapManager").GetComponent<LeapManager>().rightcolider = rightclidr;

            rightHd = GameObject.Find("Right Interaction Hand Contact Bones");
            trans = rightHd.transform;
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
