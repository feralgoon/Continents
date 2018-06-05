package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

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
    private String stateId;
    private BigDecimal Salary;
    private int titleOfCourtesyId;

    public int getTitleOfCourtesyId()
    {
        return titleOfCourtesyId;
    }

    public BigDecimal getSalary()
    {
        return Salary;
    }

    public void setTitleOfCourtesyId(int titleOfCourtesyId)
    {
        this.titleOfCourtesyId = titleOfCourtesyId;
    }

    public String getStateId()
    {
        return stateId;
    }

    public void setStateId(String stateId)
    {
        this.stateId = stateId;
    }

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

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }
}
