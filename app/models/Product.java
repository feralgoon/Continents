package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Product
{
    @Id
    private int productId;

    private String productName;
    private BigDecimal unitPrice;
    private int unitsInStock;
    private int unitsOnOrder;
    private int reorderLevel;
    private int categoryId;

    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }

    public int getCategoryId()
    {
        return categoryId;
    }

    public String getProductName()
    {
        return productName;
    }

    public BigDecimal getUnitPrice()
    {
        return unitPrice;
    }

    public int getProductId()
    {
        return productId;
    }

    public int getUnitsInStock()
    {
        return unitsInStock;
    }

    public int getUnitsOnOrder()
    {
        return unitsOnOrder;
    }

    public int getReorderLevel()
    {
        return reorderLevel;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }
}
