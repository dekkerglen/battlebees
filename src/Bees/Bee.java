package Bees;

import Apiary.Apiary;
import Apiary.Beehive;
import Apiary.Room;
import BeeDecorations.BeeAttributes;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Bee
{

    //this is honestly to prevent stack overflow from stealing too many traits
//     one warrior bee might defeat so many bees that it gets so many attributes,
//	when it copies a new attribute it has a nested call so deep it results in a stack overflow.
//	This happens because of 'snowballing' the bee gets more powerful,
    //which lets it take even more attributes, which makes it more powerful, etc
//	A large number of beehives causes this because the simulation ends up being much longer.
    public static final int MAX_TRAITS = 100;
    int traitsStolen;

    public static final float HUNGER_TICK = .05f;

    // for mediator pattern
    private Mediator mediator;

    public abstract void receiveTick();

    // get access to the mediator
    public Mediator getMediator()
    {
        return mediator;
    }

    public Bee()
    {

    }

    // attributes
    protected boolean isQueen;
    // from 0 to 1, 0 is hungry
    protected float hunger;
    // how much nectar is being carried
    protected int nectar;
    // current health;
    protected int health;
    // position
    protected float xPos;
    protected float yPos;
    // the owner of this bee
    protected Beehive owner;
    protected BeeAttributes attributes;
    protected boolean resting;

    public Beehive getOwner()
    {
        return owner;
    }

    public abstract String getType();

    // actions
    // damage a bee with your attack, get damaged by that bees attack
    // handle the case if either bee dies
    protected void attack()
    {
        Apiary apiary = Apiary.getApiary();
        List<Bee> bees = apiary.enemyBeesInSector((int) xPos, (int) yPos, owner);

        //this is so we can only kill the queen if it's the last bee there
        if (bees.size() == 1)
        {
            bees.get(0).health -= attributes.getAttack();
            if (bees.get(0).health <= 0)
            {
                if (traitsStolen < MAX_TRAITS)
                {
                    attributes = bees.get(0).attributes.copy(attributes);
                    traitsStolen += bees.get(0).traitsStolen;
                    owner.incrementBeeKills();
                }
                if (bees.get(0).isQueen)
                {
                    owner.TakeOverHive(bees.get(0).getOwner());
                    owner.incrementHiveKills();
                }
            }
        } else
        {
            Random random = new Random();
            Bee target;
            do
            {
                target = bees.get(Math.abs(random.nextInt() % bees.size()));
            } while (target.isQueen);

            target.health -= attributes.getAttack();

            if (target.health <= 0)
            {
                if (traitsStolen < MAX_TRAITS)
                {
                    attributes = target.attributes.copy(attributes);
                    traitsStolen += target.traitsStolen;
                    owner.incrementBeeKills();
                }
            }
        }
    }

    // contribute building to the room at the current location
    protected void build()
    {
        owner.getRoom(xPos, yPos).build(attributes.getBuildRate());
        owner.consumeFood();
    }

    // grab nectar from flower at current location
    protected void collect()
    {
        nectar = attributes.getCarryingCapacity();
    }

    // move the bee closer to the nearest target
    protected void search(int x, int y)
    {
        Random random = new Random();
        Point2D target = new Point2D.Float(x + random.nextFloat(), y + random.nextFloat());
        float distance = (float) Point2D.distance(target.getX(), target.getY(), xPos, yPos);
        if (distance < attributes.getSpeed())
        {
            xPos = (float) target.getX();
            yPos = (float) target.getY();
        } else
        {
            float vx = (float) (target.getX() - xPos);
            float vy = (float) (target.getY() - yPos);
            vx = (float) (vx / Math.sqrt(vx * vx + vy * vy));
            vy = (float) (vy / Math.sqrt(vx * vx + vy * vy));

            xPos += vx * attributes.getSpeed();
            yPos += vy * attributes.getSpeed();
        }
    }

    protected void startRest()
    {
        resting = true;
        Room current = owner.getRoom(xPos, yPos);
        current.addCapacity();
    }

    protected void rest()
    {
        if (hunger < 1 && owner.hasFood())
        {
            //one fourth of our time is spent resting
            hunger += HUNGER_TICK * 3;
        } else if (owner.hasFood())
        {
            health = attributes.getMaxHealth();
            hunger = 1;
            owner.consumeFood();
            resting = false;
            Room current = owner.getRoom(xPos, yPos);
            current.removeCapacity();
        }
    }

    protected void searchForRest()
    {
        //starving functionality
        //we want to queen to only be able to die by another bee
        if (!isQueen)
        {
            health--;
        }

        List<Room> rooms = owner.getRooms();
        Room current = rooms.get(0);
        for (Room room : rooms)
        {
            if (room.isBuilt())
            {
                if (!current.hasCapacity() && room.hasCapacity())
                {
                    current = room;
                } else if (room.hasCapacity())
                {
                    float distanceCurrent = (float) Point2D.distance(xPos, yPos, current.getX(), current.getY());
                    float distanceRoom = (float) Point2D.distance(xPos, yPos, room.getX(), room.getY());
                    if (distanceRoom < distanceCurrent)
                    {
                        current = room;
                    }
                }
            }
        }
        search(current.getX(), current.getY());
    }

    protected void searchForNectarDropoff()
    {

        List<Room> rooms = owner.getRooms();
        Room current = rooms.get(0);
        for (int i = 1; i < rooms.size(); i++)
        {
            float distanceCurrent = (float) Point2D.distance(xPos, yPos, current.getX(), current.getY());
            float distanceRoom = (float) Point2D.distance(xPos, yPos, rooms.get(i).getX(), rooms.get(i).getY());
            if (distanceRoom < distanceCurrent)
            {
                current = rooms.get(i);
            }
        }
        search(current.getX(), current.getY());
    }

    protected void nectarDropoff()
    {
        owner.addFood(nectar);
        nectar = 0;
    }

    protected void searchFight()
    {
        List<Bee> enemies = Apiary.getApiary().getEnemies(owner.getID());
        if (enemies.size() > 0)
        {
            Bee current = enemies.get(0);

            for (int i = 1; i < enemies.size(); i++)
            {
                float distanceCurrent = (float) Point2D.distance(xPos, yPos, current.getX(), current.getY());
                float distanceRoom = (float) Point2D.distance(xPos, yPos, enemies.get(i).getX(), enemies.get(i).getY());
                if (distanceRoom < distanceCurrent)
                {
                    current = enemies.get(i);
                }
            }
            search((int) current.getX(), (int) current.getY());
        }
    }

    protected void searchFlower()
    {
        List<Point2D> flowers = Apiary.getApiary().getFlowers();

        Point2D current = flowers.get(0);

        for (int i = 1; i < flowers.size(); i++)
        {
            float distanceCurrent = (float) Point2D.distance(xPos, yPos, current.getX(), current.getY());
            float distanceRoom = (float) Point2D.distance(xPos, yPos, flowers.get(i).getX(), flowers.get(i).getY());
            if (distanceRoom < distanceCurrent)
            {
                current = flowers.get(i);
            }
        }
        search((int) current.getX(), (int) current.getY());
    }

    protected void searchEmpty()
    {
        List<Point2D> targets = new ArrayList<Point2D>();
        List<Point2D> visited = new ArrayList<Point2D>();
        targets.add(new Point2D.Float(xPos, yPos));

        //if the first target is not good...
        while (Apiary.getApiary().cellTaken((int) targets.get(0).getX(), (int) targets.get(0).getY()))
        {
            Point2D current = targets.get(0);
            Point2D left = new Point2D.Float((int) current.getX() - 1, (int) current.getY());
            Point2D right = new Point2D.Float((int) current.getX() + 1, (int) current.getY());
            Point2D up = new Point2D.Float((int) current.getX(), (int) current.getY() + 1);
            Point2D down = new Point2D.Float((int) current.getX(), (int) current.getY() - 1);

            visited.add(current);
            targets.remove(0);

            if (!visited.contains(left))
            {
                targets.add(left);
            }
            if (!visited.contains(right))
            {
                targets.add(right);
            }
            if (!visited.contains(up))
            {
                targets.add(up);
            }
            if (!visited.contains(down))
            {
                targets.add(down);
            }
        }

        search((int) targets.get(0).getX(), (int) targets.get(0).getY());

    }

    public float getX()
    {
        return xPos;
    }

    public float getY()
    {
        return yPos;
    }

    public boolean isDead()
    {
        return health <= 0;
    }

    public void setOwner(Beehive newowner)
    {
        owner = newowner;
    }

    public void setAttributes(BeeAttributes attributes)
    {
        this.attributes = attributes;
    }
}
