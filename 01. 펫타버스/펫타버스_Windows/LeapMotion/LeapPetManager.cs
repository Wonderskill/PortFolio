using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LeapPetManager : MonoBehaviour
{
    #region 01). Memberfield
    public GameObject Dog;
    public GameObject Cat;
    public GameObject Chick;
    #endregion

    #region 02). Start
    void Start()
    {
        if (NodeMain.PetType == "Dog")
        {
            Dog.SetActive(true);
        }
        else if (NodeMain.PetType == "Cat")
        {
            Cat.SetActive(true);
        }
        else if (NodeMain.PetType == "Chick")
        {
            Chick.SetActive(true);
        }
    }
    #endregion
}
