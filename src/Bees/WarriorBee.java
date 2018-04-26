package Bees;

import Apiary.Apiary;
import Apiary.BeeMediator;
import Apiary.Beehive;
import Apiary.Room;
import BeeDecorations.BeeAttributes;
import static Bees.Bee.HUNGER_TICK;
import java.util.List;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Glen
 */
public class WarriorBee extends Bee
{

    public WarriorBee(Room spawned, Beehive ownedBy, BeeAttributes attr)
    {
        isQueen = false;
        hunger = 1;
        attributes = attr;
        xPos = spawned.getX() + .5f;
        yPos = spawned.getY() + .5f;
        owner = ownedBy;
        nectar = 0;
        resting = false;
        traitsStolen = 0;

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
        } else
        {
            List<Bee> bees = apiary.enemyBeesInSector((int) xPos, (int) yPos, owner);

            if (bees.isEmpty())
            {
                searchFight();
            } else
            {
                attack();
            }
        }
    }

    @Override
    public String getType()
    {
        return "Warrior";
    }
}
