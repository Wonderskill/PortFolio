using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;
using UnityEngine.XR.ARFoundation;
using UnityEngine.XR.ARSubsystems;
using UnityEngine.EventSystems;

public class ARPlaceOnPlane : MonoBehaviour
{
    public ARRaycastManager arRaycaster;
    public GameObject Dog;
    public GameObject Cat;
    public GameObject Chick;

    public string pettype;

    GameObject spawnObject;
    public bool isSpawn;
    public bool isCheck;

    // Start is called before the first frame update
    void Start()
    {
        pettype = NodeMain.SelectedPet;
        isSpawn = false;
        isCheck = false;
    }

    // Update is called once per frame
    void Update()
    {        
        if(Input.touchCount > 1)
        {                    
                PlaceObjectByTouch();         
        }
        
        if(isSpawn == true)
        {
            if (isCheck == false)
            {
                FindDog();
                FindCat();
                FindChick();
            }
        }
        
    }

    private void PlaceObjectByTouch()
    {        
            Touch touch = Input.GetTouch(0);
       
            List<ARRaycastHit> hits = new List<ARRaycastHit>();
            if (arRaycaster.Raycast(touch.position, hits, TrackableType.Planes))
            {
                Pose hitpose = hits[0].pose;

                if (!isSpawn)
                {
                    if (pettype == "Dog")
                    {
                        spawnObject = Instantiate(Dog);
                        isSpawn = true;
                    }
                    else if (pettype == "Cat")
                    {
                        spawnObject = Instantiate(Cat);
                        isSpawn = true;
                    }
                    else if (pettype == "Chick")
                    {
                        spawnObject = Instantiate(Chick);
                        isSpawn = true;
                    }
                }
                else
                {
                    spawnObject.transform.position = hitpose.position;
                    spawnObject.transform.rotation = hitpose.rotation;
                }
        }
    }

    public void FindDog()
    {
        Dog = GameObject.FindGameObjectWithTag("DogTarget");
        
        if(Dog != null)
        {
            isCheck = true;
        }
    }
    public void FindCat()
    {
        Cat = GameObject.FindGameObjectWithTag("CatTarget");

        if (Cat != null)
        {
            isCheck = true;
        }
    }
    public void FindChick()
    {
        Chick = GameObject.FindGameObjectWithTag("ChickTarget");

        if (Chick != null)
        {
            isCheck = true;
        }
    }
}
