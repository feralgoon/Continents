package controllers;

import models.*;
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

    /*
    SELECT c.categoryid, c.CategoryName, count(*) AS ProductsInCategory
    FROM Product p
    JOIN Category c ON p.categoryid = c.categoryid
    GROUP BY c.categoryname
    ORDER BY c.categoryname
     */

    @Transactional(readOnly = true)
    public Result getProductsByCategory()
    {
        String sql =    "SELECT NEW ProductCountByCategory(c.categoryId, c.categoryName, COUNT(*)) " +
                        "FROM Product p " +
                        "JOIN Category c ON p.categoryId = c.categoryId " +
                        "GROUP BY c.categoryName " +
                        "ORDER BY c.categoryName";

        List<ProductCountByCategory> productCounts = jpaApi.em().createQuery(sql,ProductCountByCategory.class).getResultList();

        return ok(views.html.productcountbycategory.render(productCounts));
    }

    @Transactional(readOnly = true)
    public Result getInStockByCategory()
    {
        String sql = "SELECT NEW InStockPerCategory(c.categoryId, c.categoryName,SUM(p.unitsInStock)) " +
                        "FROM Product p " +
                        "JOIN Category c ON p.categoryId = c.categoryId " +
                        "GROUP BY c.categoryName " +
                        "ORDER BY c.categoryName";
        List<InStockPerCategory> stockCounts = jpaApi.em().createQuery(sql,InStockPerCategory.class).getResultList();

        return ok(views.html.instockbycategory.render(stockCounts));
    }

    @Transactional(readOnly = true)
    public Result getSalesByCategory()
    {
        String sql = "SELECT NEW SalesPerCategory(c.categoryId,c.categoryName,SUM(od.quantity * p.unitPrice)) " +
                        "FROM Product p " +
                        "JOIN Category c ON c.categoryId = p.categoryId " +
                        "JOIN OrderDetail od ON od.productId = p.productId " +
                        "GROUP BY c.categoryId,c.categoryName " +
                        "ORDER BY c.categoryName";

        List<SalesPerCategory> sales = jpaApi.em().createQuery(sql,SalesPerCategory.class).getResultList();

        return ok(views.html.salesbycategory.render(sales));
    }

    @Transactional(readOnly = true)
    public Result getShippersUsed()
    {
        String sql = "SELECT NEW ShipperUsage(s.shipperId,s.shipperName,COUNT(oh.shipperId)) " +
                        "FROM OrderHeader oh " +
                        "JOIN Shipper s ON oh.shipperId = s.shipperId " +
                        "GROUP BY s.shipperName " +
                        "ORDER BY s.shipperName";

        List<ShipperUsage> shipperUsages = jpaApi.em().createQuery(sql,ShipperUsage.class).getResultList();

        return ok(views.html.shipperusage.render(shipperUsages));
    }
}
