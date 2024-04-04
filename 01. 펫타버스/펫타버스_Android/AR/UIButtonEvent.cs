using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class UIButtonEvent : MonoBehaviour
{
    public Text textUI;

    public void EatBtnClick()
    {
        Debug.Log("Eat ��ư Ŭ��");
        textUI.text = "Button Eat";
    }

    public void RollBtnClick()
    {
        Debug.Log("Roll ��ư Ŭ��");
        textUI.text = "Button Roll";
    }

    public void SpinBtnClick()
    {
        Debug.Log("Spin ��ư Ŭ��");
        textUI.text = "Button Spin";
    }

    public void BounceBtnClick()
    {
        Debug.Log("Bounce ��ư Ŭ��");
        textUI.text = "Button Bounce";
    }

    public void ExitBtnClick()
    {
        Debug.Log("Exit ��ư Ŭ��");
        textUI.text = "Button Exit";
    }
}
