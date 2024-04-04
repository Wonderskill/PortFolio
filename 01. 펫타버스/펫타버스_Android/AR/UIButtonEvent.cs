using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class UIButtonEvent : MonoBehaviour
{
    public Text textUI;

    public void EatBtnClick()
    {
        Debug.Log("Eat 버튼 클릭");
        textUI.text = "Button Eat";
    }

    public void RollBtnClick()
    {
        Debug.Log("Roll 버튼 클릭");
        textUI.text = "Button Roll";
    }

    public void SpinBtnClick()
    {
        Debug.Log("Spin 버튼 클릭");
        textUI.text = "Button Spin";
    }

    public void BounceBtnClick()
    {
        Debug.Log("Bounce 버튼 클릭");
        textUI.text = "Button Bounce";
    }

    public void ExitBtnClick()
    {
        Debug.Log("Exit 버튼 클릭");
        textUI.text = "Button Exit";
    }
}
