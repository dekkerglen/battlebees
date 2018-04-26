/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apiary;

/**
 *
 * @author Glen
 */
public class Room
{

    public static final int POINTS_NEEDED = 100;
    public static final int MAX_CAPACITY = 5;

    private boolean isBuilt;
    private float buildPoints;
    private int xPos;
    private int yPos;
    private int capacity;
    private int pointsneeded;

    public Room(int x, int y, boolean built, int roomnum)
    {
        xPos = x;
        yPos = y;

        isBuilt = built;
        buildPoints = 0;
        capacity = 0;

        pointsneeded = POINTS_NEEDED * (roomnum + 1);
    }

    public void build(float a)
    {
        buildPoints += a;
        if (buildPoints >= pointsneeded)
        {
            isBuilt = true;
        }
    }

    public int getX()
    {
        return xPos;
    }

    public int getY()
    {
        return yPos;
    }

    public boolean hasCapacity()
    {
        return capacity < MAX_CAPACITY;
    }

    public void addCapacity()
    {
        capacity++;
    }

    public void removeCapacity()
    {
        capacity--;
    }

    public boolean isBuilt()
    {
        return isBuilt;
    }

    public float percentageDone()
    {
        return buildPoints / pointsneeded;
    }

    public int getCapacity()
    {
        return capacity;
    }

}
