package Apiary;

import Apiary.Beehive;
import BeeDecorations.BeeAttributes;
import Bees.Bee;

// builder pattern
public class BeehiveBuilder<type extends BeeAttributes>
{

    private double xpos;
    private double ypos;
    private type master;

    public BeehiveBuilder()
    {

    }

    public BeehiveBuilder setXPosition(double x)
    {
        xpos = x;
        return this;
    }

    public BeehiveBuilder setYPosition(double y)
    {
        ypos = y;
        return this;
    }

    public BeehiveBuilder setAttributes(type b)
    {
        master = b;
        return this;
    }

    public Beehive<type> createBeehive()
    {
        return new Beehive<type>((int) xpos, (int) ypos, master);
    }
}
