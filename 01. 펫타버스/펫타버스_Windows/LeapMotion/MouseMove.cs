// https://acredev.tistory.com/18 참고

using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MouseMove : MonoBehaviour
{
    #region 01). Memberfield
    public float sensitivity;
    public float rotationX;
    public float rotationY;
    #endregion

    #region 02). Start
    // Start is called before the first frame update
    void Start()
    {
        sensitivity = 200f;
    }
    #endregion

    #region 03). Update
    // Update is called once per frame
    void Update()
    {
        float mouseMoveX = Input.GetAxis("Mouse X");
        float mouseMoveY = Input.GetAxis("Mouse Y");

        rotationY += mouseMoveX * sensitivity * Time.deltaTime;
        rotationX += mouseMoveY * sensitivity * Time.deltaTime;

        if(rotationX > 35f)
        {
            rotationX = 35f;
        }

        if(rotationX < -30f)
        {
            rotationX = -30f;
        }

        transform.eulerAngles = new Vector3(-rotationX, rotationY, 0);
    }
    #endregion
}
