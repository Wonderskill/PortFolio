using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ChickBtn : MonoBehaviour
{
    public Animator anim;

    private void Update()
    {
        try
        {
            anim = GameObject.FindGameObjectWithTag("Player").GetComponent<Animator>();
        }
        catch
        {

        }
    }
    void Start()
    {        
        anim.SetBool("Peck", false);
        anim.SetBool("Roll", false);
        anim.SetBool("Spin", false);
        anim.SetBool("Bounce", false);
    }

    public void Peck()
    {
        StartCoroutine(Peckstart());
    }

    public IEnumerator Peckstart()
    {
        anim.SetBool("Peck", true);
        yield return new WaitForSeconds(1f);
        anim.SetBool("Peck", false);
    }

    public void Roll()
    {
        StartCoroutine(Rollstart());
    }

    public IEnumerator Rollstart()
    {
        anim.SetBool("Roll", true);
        yield return new WaitForSeconds(1f);
        anim.SetBool("Roll", false);
    }

    public void Spin()
    {
        StartCoroutine(Spinstart());
    }

    public IEnumerator Spinstart()
    {
        anim.SetBool("Spin", true);
        yield return new WaitForSeconds(1f);
        anim.SetBool("Spin", false);
    }

    public void Bounce()
    {
        StartCoroutine(Bouncestart());
    }

    public IEnumerator Bouncestart()
    {
        anim.SetBool("Bounce", true);
        yield return new WaitForSeconds(1f);
        anim.SetBool("Bounce", false);
    }

    public void STTAct(string text)
    {
        switch (text)
        {
            case "뛰어":
                Bounce();
                break;
            case "돌아":
                Spin();
                break;
            case "굴러":
                Roll();
                break;
            case "먹어":
                Peck();
                break;
        }

    }
}
