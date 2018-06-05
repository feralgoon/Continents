package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OrderDetail
{
    @Id
    private int orderDetailId;

    private int productId;
    private long quantity;
    private int shipperId;

    public OrderDetail(int orderDetailId, int productId, long quantity, int shipperId)
    {
        this.orderDetailId = orderDetailId;
        this.productId = productId;
        this.quantity = quantity;
        this.shipperId = shipperId;
    }

    public int getOrderDetailId()
    {
        return orderDetailId;
    }

    public int getProductId()
    {
        return productId;
    }

    public long getQuantity()
    {
        return quantity;
    }

    public int getShipperId()
    {
        return shipperId;
    }
}
