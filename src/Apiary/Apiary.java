package Apiary;

import BeeDecorations.ArchitectBee;
import BeeDecorations.BeeAttributes;
import BeeDecorations.DurableBee;
import BeeDecorations.FastBee;
import BeeDecorations.KillerBee;
import BeeDecorations.PollinatorBee;
import Bees.Bee;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Apiary
{
	// Singleton design pattern

	private static Apiary instance;
	private List<Beehive> beehives;
	private List<Point2D> roomCollision;
	private List<Point2D> flowers;

	int size;

	protected Apiary()
	{
		// Exists only to defeat instantiation.
	}

	public void Instantiate(int num)
	{
		size = 10;
		roomCollision = new ArrayList<>();

		Random random = new Random();
		Point2D pos1 = new Point2D.Float(Math.abs(random.nextInt() % 10), Math.abs(random.nextInt() % 10));
		Point2D pos2;
		do
		{
			pos2 = new Point2D.Float(Math.abs(random.nextInt() % 10), Math.abs(random.nextInt() % 10));
		} while (pos1.equals(pos2));

		beehives = new ArrayList<Beehive>();
		flowers = new ArrayList<Point2D>();
		for (int i = 0; i < num; i++)
		{
			spawnHive();
			spawnFlower();

		}
		while (flowers.size() < 10)
		{
			spawnFlower();
		}

	}

	private void spawnFlower()
	{
		if (roomCollision.size() > size * size / 2)
		{
			size += 10;
		}
		Random random = new Random();
		Point2D pos;
		do
		{
			pos = new Point2D.Float(100 + Math.abs(random.nextInt() % size), 100 + Math.abs(random.nextInt() % size));
		} while (cellTaken((int) pos.getX(), (int) pos.getY()));
		flowers.add(pos);
		roomCollision.add(pos);
	}

	public void spawnHive(String type, int x, int y)
	{
		Point2D pos = new Point2D.Float(x + 100, y + 100);
		Beehive hive;

		switch (type)
		{
		case "fast":
			hive = new BeehiveBuilder<FastBee>().setXPosition(pos.getX()).setYPosition(pos.getY())
					.setAttributes(new FastBee(new BeeAttributes())).createBeehive();
			break;
		case "killer":
			hive = new BeehiveBuilder<KillerBee>().setXPosition(pos.getX()).setYPosition(pos.getY())
					.setAttributes(new KillerBee(new BeeAttributes())).createBeehive();
			break;
		case "pollinator":
			hive = new BeehiveBuilder<PollinatorBee>().setXPosition(pos.getX()).setYPosition(pos.getY())
					.setAttributes(new PollinatorBee(new BeeAttributes())).createBeehive();
			break;
		case "durable":
			hive = new BeehiveBuilder<DurableBee>().setXPosition(pos.getX()).setYPosition(pos.getY())
					.setAttributes(new DurableBee(new BeeAttributes())).createBeehive();
			break;
		case "architect":
			hive = new BeehiveBuilder<ArchitectBee>().setXPosition(pos.getX()).setYPosition(pos.getY())
					.setAttributes(new ArchitectBee(new BeeAttributes())).createBeehive();
			break;
		default:
			hive = new BeehiveBuilder<FastBee>().setXPosition(pos.getX()).setYPosition(pos.getY())
					.setAttributes(new BeeAttributes()).createBeehive();
		}

		beehives.add(hive);
		roomCollision.add(pos);
	}

	private void spawnHive()
	{
		if (roomCollision.size() > size * size / 2)
		{
			size += 10;
		}
		Random random = new Random();
		Point2D pos;
		do
		{
			pos = new Point2D.Float(100 + Math.abs(random.nextInt() % size), 100 + Math.abs(random.nextInt() % size));
		} while (cellTaken((int) pos.getX(), (int) pos.getY()));
		Beehive hive;

		switch (Math.abs(random.nextInt() % 5))
		{
		case 0:
			hive = new BeehiveBuilder<FastBee>().setXPosition(pos.getX()).setYPosition(pos.getY())
					.setAttributes(new FastBee(new BeeAttributes())).createBeehive();
			break;
		case 1:
			hive = new BeehiveBuilder<KillerBee>().setXPosition(pos.getX()).setYPosition(pos.getY())
					.setAttributes(new KillerBee(new BeeAttributes())).createBeehive();
			break;
		case 2:
			hive = new BeehiveBuilder<PollinatorBee>().setXPosition(pos.getX()).setYPosition(pos.getY())
					.setAttributes(new PollinatorBee(new BeeAttributes())).createBeehive();
			break;
		case 3:
			hive = new BeehiveBuilder<DurableBee>().setXPosition(pos.getX()).setYPosition(pos.getY())
					.setAttributes(new DurableBee(new BeeAttributes())).createBeehive();
			break;
		case 4:
			hive = new BeehiveBuilder<ArchitectBee>().setXPosition(pos.getX()).setYPosition(pos.getY())
					.setAttributes(new ArchitectBee(new BeeAttributes())).createBeehive();
			break;
		default:
			hive = new BeehiveBuilder<FastBee>().setXPosition(pos.getX()).setYPosition(pos.getY())
					.setAttributes(new BeeAttributes()).createBeehive();

		}

		beehives.add(hive);
		roomCollision.add(pos);
	}

	public boolean cellTaken(int x, int y)
	{
		for (Point2D p : roomCollision)
		{
			if (p.getX() == x && p.getY() == y)
			{
				return true;
			}
		}
		return false;
	}

	public static Apiary getApiary()
	{
		if (instance == null)
		{
			instance = new Apiary();
			instance.Instantiate(5);
		}
		return instance;
	}

	public void AddCollision(int x, int y)
	{
		roomCollision.add(new Point2D.Float(x, y));
	}

	public List<Bee> enemyBeesInSector(int x, int y, Beehive hive)
	{
		List<Bee> result = new ArrayList<Bee>();
		for (Bee bee : BeeMediator.getMediator().getBees())
		{
			if (bee.getOwner().getID() != hive.getID())
			{
				if ((int) bee.getX() == x && (int) bee.getY() == y)
				{
					result.add(bee);
				}
			}
		}
		return result;
	}

	public List<Beehive> getHives()
	{
		return beehives;
	}

	public List<Point2D> getFlowers()
	{
		return flowers;
	}

	public boolean isFlower(float xPos, float yPos)
	{
		for (Point2D flower : flowers)
		{
			if (flower.getX() == (int) xPos && flower.getY() == (int) yPos)
			{
				return true;
			}
		}
		return false;
	}

	public List<Bee> getEnemies(int id)
	{
		List<Bee> result = new ArrayList<Bee>();
		for (Bee bee : BeeMediator.getMediator().getBees())
		{
			if (bee.getOwner().getID() != id)
			{
				result.add(bee);
			}
		}
		return result;
	}

	public void spawnFlower(int x, int y)
	{
		Point2D pos = new Point2D.Float(100 + x, 100 + y);
		flowers.add(pos);
		roomCollision.add(pos);
	}
}
