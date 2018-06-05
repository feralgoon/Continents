package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Shipper
{
    @Id
    private int shipperId;

    private String shipperName;

    public Shipper(int shipperId, String shipperName)
    {
        this.shipperId = shipperId;
        this.shipperName = shipperName;
    }

    public int getShipperId()
    {
        return shipperId;
    }

    public String getShipperName()
    {
        return shipperName;
    }
}
