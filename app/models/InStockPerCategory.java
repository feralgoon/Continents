package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class InStockPerCategory
{
    @Id
    private int categoryId;

    private String categoryName;
    private long unitsInStock;

    public InStockPerCategory(int categoryId, String categoryName, long unitsInStock)
    {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.unitsInStock = unitsInStock;
    }

    public int getCategoryId()
    {
        return categoryId;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public long getUnitsInStock()
    {
        return unitsInStock;
    }
}
