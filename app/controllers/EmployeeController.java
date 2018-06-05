package controllers;

import models.Employee;
import models.EmployeeDetail;
import models.State;
import models.TitleOfCourtesy;
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

        String sql = "SELECT NEW models.EmployeeDetail(e.employeeId,e.firstName,e.lastName,e.city,t.titleOfCourtesyName) " +
                     "FROM Employee e " +
                     "JOIN TitleOfCourtesy t ON e.titleOfCourtesyId = t.titleOfCourtesyId " +
                     "WHERE lastName LIKE :searchCriteria " +
                     "OR firstName LIKE :searchCriteria " +
                     "ORDER BY lastName";

        List<EmployeeDetail> employees = jpaApi.em().createQuery(sql, EmployeeDetail.class).setParameter("searchCriteria",searchCriteria).getResultList();

        return ok(views.html.employees.render(employees, searchDisplay));
    }

    @Transactional
    public Result postEmployee(Integer employeeId)
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        Employee employee = findEmployee(employeeId);

        String firstName = form.get("firstName");
        String lastName  = form.get("lastName");
        String address   = form.get("address");
        String city      = form.get("city");
        int titleOfCourtesyId = Integer.parseInt(form.get("titleOfCourtesy"));
        String stateId   = form.get("state");

        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setAddress(address);
        employee.setCity(city);
        employee.setTitleOfCourtesyId(titleOfCourtesyId);
        employee.setStateId(stateId);

        jpaApi.em().persist(employee);

        return redirect(routes.EmployeeController.getEmployees());
    }

    @Transactional(readOnly = true)
    public Result getEmployee(Integer employeeId)
    {
        Employee employee = findEmployee(employeeId);

        String titleSQL = "SELECT t FROM TitleOfCourtesy t";
        String stateSQL = "SELECT s FROM State s";

        List<TitleOfCourtesy> titlesOfCourtesy = jpaApi.em().createQuery(titleSQL,TitleOfCourtesy.class).getResultList();
        List<State> states = jpaApi.em().createQuery(stateSQL,State.class).getResultList();

        return ok(views.html.employee.render(employee,titlesOfCourtesy,states));
    }

    @Transactional
    public Result deleteEmployee(int employeeId)
    {
        Employee employee = findEmployee(employeeId);
        jpaApi.em().remove(employee);

        return ok("bye " + employeeId);
    }

    @Transactional(readOnly = true)
    private Employee findEmployee(Integer employeeId)
    {
        String sql = "SELECT e FROM Employee e " +
                "WHERE employeeId = :employeeId";
        return jpaApi.em().createQuery(sql,Employee.class).setParameter("employeeId",employeeId).getSingleResult();
    }

    @Transactional
    public Result postNewTitleOfCourtesy()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String title = form.get("title");

        TitleOfCourtesy titleOfCourtesy = new TitleOfCourtesy();
        titleOfCourtesy.setTitleOfCourtesyName(title);

        jpaApi.em().persist(titleOfCourtesy);

        return ok("Saved " + titleOfCourtesy.getTitleOfCourtesyId());
    }

    public Result getNewTitleOfCourtesy()
    {
        return ok(views.html.newtitleofcourtesy.render());
    }

    public Result getNewEmployee()
    {


        return ok(views.html.createemployee.render());
    }
}
