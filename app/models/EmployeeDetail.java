package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmployeeDetail
{
    @Id
    private int employeeId;

    private String firstName;
    private String lastName;
    private String city;
    private String titleOfCourtesy;

    public EmployeeDetail(int employeeId, String firstName, String lastName, String city, String titleOfCourtesy)
    {
        this.employeeId      = employeeId;
        this.firstName       = firstName;
        this.lastName        = lastName;
        this.city            = city;
        this.titleOfCourtesy = titleOfCourtesy;
    }

    public int getEmployeeId()
    {
        return employeeId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getCity()
    {
        return city;
    }

    public String getTitleOfCourtesy()
    {
        return titleOfCourtesy;
    }
}
