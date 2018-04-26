package Bees;

import Apiary.BeeMediator;
import Apiary.Beehive;
import Apiary.Room;
import BeeDecorations.BeeAttributes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Glen
 */
public class QueenBee extends Bee
{

    public QueenBee(Room spawned, Beehive ownedBy, BeeAttributes attr)
    {
        isQueen = true;
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
        } else
        {
            if (owner.hasFood())
            {
                owner.consumeFood();
                owner.addEgg();
            }
        }
    }

    @Override
    public String getType()
    {
        return "Queen";
    }
}
