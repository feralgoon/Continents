package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OrderHeader
{
    @Id
    private int orderHeaderId;

    private int shipperId;

    public OrderHeader(int orderHeaderId, int shipperId)
    {
        this.orderHeaderId = orderHeaderId;
        this.shipperId = shipperId;
    }

    public int getOrderHeaderId()
    {
        return orderHeaderId;
    }

    public int getShipperId()
    {
        return shipperId;
    }
}
