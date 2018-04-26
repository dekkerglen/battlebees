/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeeDecorations;

// for decorator pattern
public class BeeAttributes
{

    public BeeAttributes copy(BeeAttributes current)
    {
        return current;
    }

    // methods to get attributes for decorator pattern
    public String getSpecies()
    {
        return "Bee";
    }

    public float getSpeed()
    {
        return 1;
    }

    public int getAttack()
    {
        return 2;
    }

    public int getMaxHealth()
    {
        return 20;
    }

    public int getCarryingCapacity()
    {
        return 8;
    }

    public float getBuildRate()
    {
        return 1;
    }
}
