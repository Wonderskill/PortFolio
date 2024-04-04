using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LogOut : MonoBehaviour
{
    public void OnApplicationQuit()
    {
        Node.ws.Send(string.Format("LogOut@{0}", Node.ID));
    }

}
