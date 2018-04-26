package Apiary;

import BeeDecorations.BeeAttributes;
import Bees.Bee;
import Bees.DroneBee;
import Bees.Mediator;
import Bees.QueenBee;
import Bees.WarriorBee;
import Bees.WorkerBee;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Beehive<type extends BeeAttributes>
{

	public static final int HATCH_FOOD = 10;
	// for mediator pattern
	private Mediator mediator;

	public void receiveTick()
	{
		// edge case where we might get conquered and have no rooms
		if (currentRooms.size() > 0)
		{
			Random random = new Random();
			Room room;
			do
			{
				room = currentRooms.get(Math.abs(random.nextInt() % currentRooms.size()));
			} while (!room.isBuilt());
			if (eggs > 0 && currentFood >= HATCH_FOOD)
			{
				currentFood -= HATCH_FOOD;
				switch (Math.abs(random.nextInt() % 3))
				{
				case 0:
					new DroneBee(currentRooms.get(0), this, attributes);
					break;
				case 1:
					new WarriorBee(currentRooms.get(0), this, attributes);
					break;
				case 2:
					new WorkerBee(currentRooms.get(0), this, attributes);
					break;
				}
			}
		}
	}

	private static int count = 0;

	private int eggs;
	private int ID;
	private int currentFood;
	private List<Bee> bees;
	private List<Room> currentRooms;
	private int xpos;
	private int ypos;
	private BeeAttributes attributes;
	private int beekills;
	private int hivekills;

	public Beehive(int x, int y, type master)
	{
		attributes = master;

		currentFood = 100;
		eggs = 0;
		ID = count++;
		xpos = x;
		ypos = y;
		beekills = 0;
		hivekills = 0;

		Apiary apiary = Apiary.getApiary();
		apiary.AddCollision(x, y);

		currentRooms = new ArrayList<Room>();
		currentRooms.add(new Room(x, y, true, 0));

		Bee queen = new QueenBee(currentRooms.get(0), this, attributes);

		BeeMediator.getMediator().addHive(this);
	}

	public int getID()
	{
		return ID;
	}

	public void addFood(int add)
	{
		currentFood += add;
	}

	public void consumeFood()
	{
		currentFood--;
	}

	public List<Room> getRooms()
	{
		return currentRooms;
	}

	public boolean hasFood()
	{
		return currentFood > 0;
	}

	public boolean isRoomWithCapacity(float x, float y)
	{
		for (Room room : currentRooms)
		{
			if (room.getX() == (int) x && room.getY() == (int) y && room.isBuilt())
			{
				return room.hasCapacity();
			}
		}
		return false;
	}

	public int getFood()
	{
		return currentFood;
	}

	public Room getRoom(float x, float y)
	{
		for (Room room : currentRooms)
		{
			if (room.getX() == (int) x && room.getY() == (int) y)
			{
				return room;
			}
		}
		return null;
	}

	public void addEgg()
	{
		eggs++;
	}

	public void TakeOverHive(Beehive other)
	{
		attributes = other.attributes.copy(attributes);
		for (Bee bee : BeeMediator.getMediator().getBees())
		{
			if (bee.getOwner().getID() == other.getID())
			{
				bee.setOwner(this);
				bee.setAttributes(attributes);
			}
		}
		while (!other.getRooms().isEmpty())
		{
			currentRooms.add((Room) other.getRooms().get(0));
			other.getRooms().remove(0);
		}
	}

	public String getSpecies()
	{
		return attributes.getSpecies();
	}

	public int getBeeKills()
	{
		return beekills;
	}

	public int getHiveKills()
	{
		return hivekills;
	}

	public void incrementBeeKills()
	{
		beekills++;
	}

	public void incrementHiveKills()
	{
		hivekills++;
	}

	public BeeAttributes getAttributes()
	{
		return attributes;
	}
}
