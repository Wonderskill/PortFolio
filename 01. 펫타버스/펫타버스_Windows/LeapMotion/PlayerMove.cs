// https://acredev.tistory.com/18 참고

using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerMove : MonoBehaviour
{
    #region 01). Memberfield
    //Transform 값은 카메라 움직임에 따라 달라지므로,
    public Transform cameraTransform;
    public Transform handTransform;

    public CharacterController characterController;

    public float moveSpeed;
    public float jumpSpeed;
    public float gravity;
    public float yVelocity;
    #endregion

    #region 02). Start
    void Start()
    {
        moveSpeed = 5f;
        jumpSpeed = 7f;
        gravity = -20f;
        yVelocity = 0f;
    }
    #endregion

    #region 03). Update
    void Update()
    {
        float h = Input.GetAxis("Horizontal");
        float v = Input.GetAxis("Vertical");

        Vector3 moveDirection = new Vector3(h, 0, v);

        moveDirection = cameraTransform.TransformDirection(moveDirection);
        //moveDirection = handTransform.TransformDirection(moveDirection);
        moveDirection *= moveSpeed;

        if (characterController.isGrounded)
        {
            yVelocity = 0f;
            if (Input.GetKeyDown(KeyCode.Space))
            {
                yVelocity = jumpSpeed;
            }
        }

        yVelocity += (gravity * Time.deltaTime);

        moveDirection.y = yVelocity;

        characterController.Move(moveDirection * Time.deltaTime);
    }
    #endregion
}