package com.example.pertemuan9.Model;

public class PesananModel {
    // Variable to store data corresponding
    // to firstname keyword in database
    private String name;

    // Variable to store data corresponding
    // to lastname keyword in database
    private String alamat;

    // Mandatory empty constructor
    // for use of FirebaseUI
    public pesananmodel() {}

    // Getter and setter method
    public String getFirstname()
    {
        return name;
    }
    public void setFirstname(String firstname)
    {
        this.name = firstname;
    }
    public String getLastname()
    {
        return alamat;
    }
    public void setLastname(String lastname)
    {
        this.alamat = lastname;
    }
}
