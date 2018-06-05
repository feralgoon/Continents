package controllers;

import models.Category;
import models.Product;
import models.ProductDetail;
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

        searchCriteria = "%" + searchCriteria + "%";

        String sql = "SELECT NEW models.ProductDetail(p.productId,p.productName,p.unitPrice,c.categoryName) " +
                "FROM Product p " +
                "JOIN Category c ON p.categoryId = c.categoryId " +
                "WHERE productName LIKE :searchCriteria " +
                "ORDER BY productName";

        List<ProductDetail> products = jpaApi.em().createQuery(sql, ProductDetail.class).setParameter("searchCriteria",searchCriteria).getResultList();

        return ok(views.html.products.render(products,searchDisplay));
    }

    @Transactional
    public Result postProduct(Integer productId)
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        Product product = findProduct(productId);

        String productName = form.get("productName");
        int categoryId = Integer.parseInt(form.get("category"));

        product.setProductName(productName);
        product.setCategoryId(categoryId);

        jpaApi.em().persist(product);
        return redirect(routes.ProductController.getProducts());
    }

    @Transactional(readOnly = true)
    private Product findProduct(Integer productId)
    {
        String sql = "SELECT p FROM Product p " +
                "WHERE productId = :productId";
        return jpaApi.em().createQuery(sql,Product.class).setParameter("productId",productId).getSingleResult();
    }

    @Transactional(readOnly = true)
    public Result getProduct(Integer productId)
    {
        {
            Product product = findProduct(productId);

            String sql = "SELECT c FROM Category c";
            List<Category> categories = jpaApi.em().createQuery(sql,Category.class).getResultList();

            return ok(views.html.product.render(product,categories));
        }
    }

    public Result getNewCategory()
    {
        return ok(views.html.newcategory.render());
    }

    @Transactional
    public Result postNewCategory()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String categoryName = form.get("category");
        String description = form.get("description");

        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setDescription(description);

        jpaApi.em().persist(category);

        return ok("Saved " + category.getCategoryId());
    }
}
