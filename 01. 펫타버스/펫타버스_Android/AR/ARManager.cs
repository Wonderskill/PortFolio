using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class ARManager : MonoBehaviour
{
    public void GotoAR()
    {
        SceneManager.LoadScene("UIButton", LoadSceneMode.Single);
    }

    public void GotoMain()
    {
        SceneManager.LoadScene("MainScene", LoadSceneMode.Single);
    }

    public void GoToScene(string sceneName)
    {
        SceneManager.LoadScene(sceneName, LoadSceneMode.Single);
    }
}
