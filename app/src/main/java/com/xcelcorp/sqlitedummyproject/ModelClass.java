package com.xcelcorp.sqlitedummyproject;

public class ModelClass {

    private String id,name,age,contact,email,address;

    public ModelClass(String id, String name, String age, String contact, String email, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.contact = contact;
        this.email = email;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }
}
