using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ARDB : MonoBehaviour
{
    public bool state;

    private void Start()
    {
        Screen.orientation = ScreenOrientation.Portrait;
        //Screen.orientation = ScreenOrientation.AutoRotation;
    }

    public void OnApplicationQuit()
    {
        Node.ws.Send(string.Format("LogOut@{0}", Node.ID));
    }
  
    private void OnApplicationFocus(bool focus)
    {
        if (focus == false && state == true)
        {
            Node.ws.Send(string.Format("LogOut@{0}", Node.ID));
            state = false;
        }
        else if (focus == true && state == false)
        {
            Node.ws.Send(string.Format("ArFocus@{0}", Node.ID));
            state = true;
        }
    }
}
