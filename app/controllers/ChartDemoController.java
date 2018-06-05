package controllers;

import models.Employee;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class ChartDemoController extends Controller
{
    private JPAApi jpaApi;

    @Inject
    public ChartDemoController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result getChart()
    {
        String sql = "SELECT e FROM Employee e ";
        List<Employee> employees = jpaApi.em().createQuery(sql,Employee.class).getResultList();
        return ok(views.html.salarychart.render(employees));
    }
}
