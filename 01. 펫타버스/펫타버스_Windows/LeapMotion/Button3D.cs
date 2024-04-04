using Leap.Unity.Interaction;
using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;
using UnityEngine.Events;

public class Button3D : MonoBehaviour
{
    #region 01). Memberfield
    public static int spherecount = 0;

    public GameObject button;
    public UnityEvent onPress;
    public UnityEvent onRelease;

    private bool isPressed;
    #endregion

    #region 02). Start
    // Start is called before the first frame update
    void Start()
    {
        isPressed = false;
    }
    #endregion

    #region 03). OnTriggerEnter
    void OnTriggerEnter(Collider other)
    {
        if (!isPressed)
        {
            button.transform.localPosition = new Vector3(0, 0.003f, 0);
            onPress.Invoke();
            Debug.Log("버튼 눌러짐");
            isPressed = true;
        }
    }
    #endregion

    #region 04). OnTriggerExit
    void OnTriggerExit(Collider other)
    {
        if (other.gameObject.layer == 6)
        {
            button.transform.localPosition = new Vector3(0, 0.015f, 0);
            onRelease.Invoke();
            Debug.Log("버튼 떼짐");
            isPressed = false;
        }
    }
    #endregion

    #region 05). SpawnSphere : 버튼을 누르면 공이 생성된다.
    public void SpawnSphere()
    {
        if (spherecount == 0)
        {
            GameObject sphere = GameObject.CreatePrimitive(PrimitiveType.Sphere);
            sphere.transform.localScale = new Vector3(0.5f, 0.5f, 0.5f);
            sphere.transform.localPosition = new Vector3(0, 1, 2);
            sphere.AddComponent<Rigidbody>();
            sphere.AddComponent<InteractionBehaviour>();
            sphere.AddComponent<Sphere>();
            sphere.tag = "Target";
            spherecount++;
        }
        else
        {
            return;
        }  
    }
    #endregion
}
