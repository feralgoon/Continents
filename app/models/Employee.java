package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employee
{
    @Id
    private int employeeId;

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String notes;

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getAddress()
    {
        return address;
    }

    public String getCity()
    {
        return city;
    }

    public String getNotes()
    {
        return notes;
    }

    public int getEmployeeId()
    {
        return employeeId;
    }
}
