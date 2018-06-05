package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class SalesPerCategory
{
    @Id
    private int categoryId;

    private String categoryName;
    private long totalSales;
    private BigDecimal totalSalePrice;

    public SalesPerCategory(int categoryId, String categoryName,BigDecimal totalSalePrice)
    {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.totalSalePrice = totalSalePrice;
    }

    public int getCategoryId()
    {
        return categoryId;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public long getTotalSales()
    {
        return totalSales;
    }

    public BigDecimal getTotalSalePrice()
    {
        return totalSalePrice;
    }
}
