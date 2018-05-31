package models;

import java.util.Map;
import java.util.TreeMap;

public class Continents
{
    private Map<String,Continent> continents;

    public Continents()
    {
        continents = new TreeMap<>();

        addContinents();
    }

    private void addContinents()
    {
        Continent asia      = new Continent("AS","Asia", 44391162, 4406273622L);
        Continent africa    = new Continent("AF","Africa", 3024409, 1215770813L);

        continents.put(asia.getId(),asia);
        continents.put(africa.getId(),africa);
    }

    public Map<String, Continent> getContinents()
    {
        return continents;
    }
}
