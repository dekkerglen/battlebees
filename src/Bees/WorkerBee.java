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
public class WorkerBee extends Bee
{

    public WorkerBee(Room spawned, Beehive ownedBy, BeeAttributes attr)
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
        } else if (owner.getRoom((int) xPos, (int) yPos) != null && !owner.getRoom((int) xPos, (int) yPos).isBuilt())
        {
            //build here
            if (owner.hasFood())
            {
                build();
            }
        } else
        {
            //go to the nearest unbuilt room
            Random random = new Random();
            List<Room> rooms = owner.getRooms();
            for (Room room : rooms)
            {
                if (!room.isBuilt())
                {
                    search(room.getX(), room.getY());
                    return;
                }
            }
            //start our own room
            if (owner.getRoom(xPos, yPos) == null)
            {
                if (!Apiary.getApiary().cellTaken((int) xPos, (int) yPos))
                {
                    Apiary.getApiary().AddCollision((int) xPos, (int) yPos);
                    Room room = new Room((int) xPos, (int) yPos, false, owner.getRooms().size());
                    owner.getRooms().add(room);
                }
            } else
            {
                searchEmpty();
            }
        }
    }

    @Override
    public String getType()
    {
        return "Worker";
    }
}
