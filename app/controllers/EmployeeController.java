package controllers;

import models.Employee;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class EmployeeController extends Controller
{
    private JPAApi jpaApi;
    private FormFactory formFactory;

    @Inject
    public EmployeeController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.jpaApi = jpaApi;
        this.formFactory = formFactory;
    }

    @Transactional(readOnly = true)
    public Result getEmployees()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String searchCriteria = form.get("searchCriteria");
        String searchDisplay = searchCriteria;

        if (searchCriteria == null)
        {
            searchCriteria = "";
        }
        searchCriteria = "%" + searchCriteria;
        searchCriteria += "%";

        String sql = "SELECT e FROM Employee e " +
                     "WHERE lastName LIKE :searchCriteria " +
                     "OR firstName LIKE :searchCriteria " +
                     "ORDER BY lastName";

        List<Employee> employees = jpaApi.em().createQuery(sql, Employee.class).setParameter("searchCriteria",searchCriteria).getResultList();

        return ok(views.html.employees.render(employees, searchDisplay));
    }

    @Transactional(readOnly = true)
    public Result getEmployee(Integer employeeId)
    {
        String sql = "SELECT e FROM Employee e " +
                     "WHERE employeeId = :employeeId";
        Employee employee = jpaApi.em().createQuery(sql,Employee.class).setParameter("employeeId",employeeId).getSingleResult();
        return ok(views.html.employee.render(employee));
    }

}
