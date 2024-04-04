using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Dog : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
        GameObject Controller = GameObject.Find("ButtonControler");
        Controller.GetComponent<DogBtn>().anim = this.gameObject.GetComponent<Animator>();
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
