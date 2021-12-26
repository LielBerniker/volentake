
package com.example.model_voluntake_class;
public class Address {
String city;
String street;
int number;
public Address(String city, String street, int num )
{
    this.city = city;
    this.street = street;
    this.number = num;
}
    public Address( )
    {

    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
