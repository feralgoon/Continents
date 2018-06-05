package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ShipperUsage
{
    @Id
    private int shipperId;

    private String shipperName;
    private long shipperUsage;

    public ShipperUsage(int shipperId, String shipperName, long shipperUsage)
    {
        this.shipperId = shipperId;
        this.shipperName = shipperName;
        this.shipperUsage = shipperUsage;
    }

    public int getShipperId()
    {
        return shipperId;
    }

    public String getShipperName()
    {
        return shipperName;
    }

    public long getShipperUsage()
    {
        return shipperUsage;
    }
}
