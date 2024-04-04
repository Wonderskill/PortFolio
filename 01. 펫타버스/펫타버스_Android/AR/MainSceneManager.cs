using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class MainSceneManager : MonoBehaviour
{
    public static bool back_main; // 시작점 확인 : MainScene -> LeapMotionScene
    
    public void GotoMain()
    {
        back_main = true;

        StartCoroutine(WaitPeta());
        // Screen.orientation = ScreenOrientation.LandscapeLeft;

        //SceneManager.LoadScene("MainScene", LoadSceneMode.Single);
    }

    IEnumerator WaitPeta()
    {
        //Screen.orientation = ScreenOrientation.LandscapeLeft;

        //yield return new WaitForSeconds(1.0f);
        yield return null;

        SceneManager.LoadScene("LoadScene", LoadSceneMode.Single);
    }
}
