package controllers;

import models.Product;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class ProductController extends Controller
{
    private JPAApi jpaApi;
    private FormFactory formFactory;

    @Inject
    public ProductController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.jpaApi = jpaApi;
        this.formFactory = formFactory;
    }

    @Transactional(readOnly = true)
    public Result getProducts()
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

        String sql = "SELECT p FROM Product p WHERE productName LIKE :searchCriteria ORDER BY productName";

        List<Product> products = jpaApi.em().createQuery(sql, Product.class).setParameter("searchCriteria",searchCriteria).getResultList();

        return ok(views.html.products.render(products,searchDisplay));
    }

    @Transactional(readOnly = true)
    public Result getProduct(Integer productId)
    {
        {
            String sql = "SELECT p FROM Product p " +
                    "WHERE productId = :productId";
            Product product = jpaApi.em().createQuery(sql,Product.class).setParameter("productId",productId).getSingleResult();
            return ok(views.html.product.render(product));
        }
    }
}
