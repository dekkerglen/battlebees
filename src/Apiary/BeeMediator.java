package Apiary;

import Bees.Mediator;
import Bees.Bee;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//for mediator pattern
public class BeeMediator implements Mediator
{

    private List<Bee> bees;
    private List<Beehive> hives;
    private int ticks;

    private static BeeMediator instance;

    protected BeeMediator()
    {
        bees = new ArrayList<Bee>();
        hives = new ArrayList<Beehive>();
        ticks = 0;
    }

    public void reset()
    {
        bees = new ArrayList<Bee>();
        hives = new ArrayList<Beehive>();
        ticks = 0;
    }

    public static BeeMediator getMediator()
    {
        if (instance == null)
        {
            instance = new BeeMediator();
        }
        return instance;
    }

    public void addBee(Bee bee)
    {
        bees.add(bee);
    }

    public void addHive(Beehive hive)
    {
        hives.add(hive);
    }

    public List<Bee> getBees()
    {
        return bees;
    }

    @Override
    public void sendTick()
    {
        //if a hive has no more bees we are done
        if (SimulationEnded())
        {
            return;
        }

        ticks++;
        //make sure we update hives in random order
        Collections.shuffle(hives);

        for (Beehive hive : hives)
        {
            hive.receiveTick();
        }

        //make sure we update bees in random order
        Collections.shuffle(bees);
        for (Bee bee : bees)
        {
            bee.receiveTick();
        }
        //clean up dead beas
        for (int i = 0; i < bees.size(); i++)
        {
            if (bees.get(i).isDead())
            {
                bees.remove(i);
                i--;
            }
        }
    }

    private boolean hiveHasBee(int id)
    {
        for (Bee bee : bees)
        {
            if (bee.getOwner().getID() == id)
            {
                return true;
            }
        }
        return false;
    }

    public boolean SimulationEnded()
    {
        int hivescount = 0;
        for (Beehive hive : hives)
        {
            if (hiveHasBee(hive.getID()))
            {
                hivescount++;
            }
        }
        return hivescount <= 1;
    }

    public int getTicks()
    {
        return ticks;
    }
}
