using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class ARSceneManager : MonoBehaviour
{
    public void GotoUI()
    {
        SceneManager.LoadScene("UIButton", LoadSceneMode.Single);
    }

    public void GotoPeta()
    {

    }

    public void GoToScene(string sceneName)
    {
        
    }
}
