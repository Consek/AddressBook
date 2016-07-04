/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addressbook;

/**
 *
 * @author Consek
 */
public class Person {
    private String phone;
    private String name;
    
    public void set(String Name,String Phone){
        name = Name;
        phone = Phone;
    }
    
    public String getPhone(){
        return phone;
    }
    
    public String getName(){
        return name;
    }
    
}
