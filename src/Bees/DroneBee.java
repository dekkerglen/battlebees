package Bees;

import Apiary.Apiary;
import Apiary.BeeMediator;
import Apiary.Beehive;
import Apiary.Room;
import BeeDecorations.BeeAttributes;
import java.awt.geom.Point2D;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Glen
 */
public class DroneBee extends Bee
{

    public DroneBee(Room spawned, Beehive ownedBy, BeeAttributes attr)
    {
        isQueen = false;
        hunger = 1;
        attributes = attr;
        xPos = spawned.getX() + .5f;
        yPos = spawned.getY() + .5f;
        owner = ownedBy;
        nectar = 0;
        resting = false;

        health = attributes.getMaxHealth();

        BeeMediator.getMediator().addBee(this);
    }

    @Override
    public void receiveTick()
    {
        Apiary apiary = Apiary.getApiary();
        hunger -= HUNGER_TICK;
        if (resting)
        {
            rest();
        } else if (hunger <= 0)
        {
            //search for closest room with capacity
            if (owner.isRoomWithCapacity(xPos, yPos))
            {
                startRest();
            } else
            {
                searchForRest();
            }
        } else if (nectar > 0)
        {
            if (owner.getRoom(xPos, yPos) != null)
            {
                nectarDropoff();
            } else
            {
                searchForNectarDropoff();
            }
            //go to nearest room
        } else if (apiary.isFlower(xPos, yPos))
        {
            collect();
        } else
        {
            //go to nearest flower
            searchFlower();
        }
    }

    @Override
    public String getType()
    {
        return "Drone";
    }
}
