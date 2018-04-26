/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Apiary.Apiary;
import Apiary.BeeMediator;
import Apiary.Beehive;
import Bees.Bee;
import java.util.List;

/**
 *
 * @author Glen
 */
public class Interface
{

	public static void command(String in)
	{
		String[] tokens = in.split(" ");
		switch (tokens[0].toLowerCase())
		{
		case "reset":
			reset(in);
			break;
		case "spawn":
			spawn(in);
			break;
		case "tick":
			tick(in);
			break;
		case "summary":
			summary(in);
			break;
		case "help":
			help(in);
			break;
		case "stats":
			stats(in);
			break;
		default:
			unknownCommand();
		}
	}

	private static void stats(String in)
	{
		String[] tokens = in.split(" ");
		try
		{
			int colony = Integer.parseInt(tokens[1]);
			Beehive hive = null;
			for (Beehive beehive : Apiary.getApiary().getHives())
			{
				if (beehive.getID() == colony)
				{
					hive = beehive;
				}
			}
			if (hive == null)
			{
				System.out.println("No hive with that ID found.");
			}
			else
			{
				System.out.println("Stats for hive " + hive.getID());
				System.out.println("\tSpecies:" + hive.getSpecies());
				System.out.println("\tAttack:" + hive.getAttributes().getAttack());
				System.out.println("\tBuild Rate:" + hive.getAttributes().getBuildRate());
				System.out.println("\tCarrying capacity:" + hive.getAttributes().getCarryingCapacity());
				System.out.println("\tMax Health:" + hive.getAttributes().getMaxHealth());
				System.out.println("\tSpeed:" + hive.getAttributes().getSpeed());

			}
		}
		catch (Exception e)
		{
			System.out.println("Invalid command arguments");
		}
	}

	private static void unknownCommand()
	{
		System.out.println("Unknown command.");
	}

	private static void reset(String in)
	{
		String[] tokens = in.split(" ");
		try
		{
			BeeMediator.getMediator().reset();
			if (tokens.length > 1)
			{
				Apiary.getApiary().Instantiate(Integer.parseInt(tokens[1]));
				System.out.println("Created new simulation with " + Integer.parseInt(tokens[1]) + " beehives");
			}
			else
			{
				Apiary.getApiary().Instantiate(2);
				System.out.println("Created new simulation with 2 beehives");
			}
		}
		catch (Exception e)
		{
			System.out.println("Invalid command arguments");
			Apiary.getApiary().Instantiate(2);
			System.out.println("Created new simulation with 2 beehives");
		}
	}

	private static void tick(String in)
	{
		String[] tokens = in.split(" ");
		try
		{
			if (tokens.length > 1)
			{
				for (int i = 0; i < Integer.parseInt(tokens[1]); i++)
				{
					BeeMediator.getMediator().sendTick();
				}
				System.out.println("Simulated " + Integer.parseInt(tokens[1]) + " tick");
			}
			else
			{
				BeeMediator.getMediator().sendTick();
				System.out.println("Simulated 1 tick");
			}
		}
		catch (Exception e)
		{
			System.out.println("Invalid command arguments");
		}
		if (BeeMediator.getMediator().SimulationEnded())
		{
			System.out.println("The simulation has ended at tick " + BeeMediator.getMediator().getTicks());
		}
	}

	private static void help(String in)
	{

		String[] tokens = in.split(" ");
		if (tokens.length < 2)
		{
			System.out.println("Available commands:");
			System.out.println("\treset [n]");
			System.out.println("\ttick [n]");
			System.out.println("\tsummary [n]");
			System.out.println("\tstats [n]");
			System.out.println("\tspawn [x] [y] [t]");
			System.out.println("For more specific instructions, try help [command]");
		}
		else
		{
			switch(tokens[1].toLowerCase())
			{
			case "reset":
				System.out.println("Starts a new simulation with n beehives.");
				break;
			case "spawn":
				System.out.println("Spawns a new beehive at coordinates x and y, of type t. See readme for bee types.");
				break;
			case "tick":
				System.out.println("Advances simulation n ticks. If no parameter is passed, advances just one tick.");
				break;
			case "summary":
				System.out.println("Prints a summary of beehive n.");
				break;
			case "stats":
				System.out.println("Prints the base stats for beehive n.");
				break;
			default:
				System.out.println("Invalid argument");
			}
		}
	}

	private static void spawn(String in)
	{
		String[] tokens = in.split(" ");
		if (tokens.length < 4)
		{
			System.out.println("Invalid command.");
		}
		else
		{
			try
			{
				int x = Integer.parseInt(tokens[1]);
				int y = Integer.parseInt(tokens[2]);
				// we have everything offset by 100 100 b/c the visualization starts there
				if (Apiary.getApiary().cellTaken(x + 100, y + 100))
				{
					System.out.println("This cell is already occupied.");
				}
				else
				{
					String type = tokens[3];
					if (type.toLowerCase().equals("flower"))
					{
						System.out.println("Spawned flower at location " + x + ", " + y + ".");
						Apiary.getApiary().spawnFlower(x, y);
					}
					else
					{
						Apiary.getApiary().spawnHive(type.toLowerCase(), x, y);
						switch (type.toLowerCase())
						{
						case "architect":
						case "durable":
						case "fast":
						case "killer":
						case "pollinator":
							System.out.println("Spawned hive of " + type + " bees at location " + x + ", " + y + ".");
							break;
						default:
							System.out.println("Spawned hive of generic bees (unrecognized type) at location " + x
									+ ", " + y + ".");
						}
					}
				}
			}
			catch (Exception e)
			{
				System.out.println("Invalid command arguments");
			}
		}
	}

	private static void summary(String in)
	{
		String[] tokens = in.split(" ");
		try
		{
			int colony = Integer.parseInt(tokens[1]);
			Beehive hive = null;
			for (Beehive beehive : Apiary.getApiary().getHives())
			{
				if (beehive.getID() == colony)
				{
					hive = beehive;
				}
			}
			if (hive == null)
			{
				System.out.println("No hive with that ID found.");
			}
			else
			{
				System.out.println("Summary for hive " + hive.getID());
				System.out.println("\tSpecies:" + hive.getSpecies());
				int population = 0;
				int worker = 0;
				int drone = 0;
				int warrior = 0;

				List<Bee> bees = BeeMediator.getMediator().getBees();
				for (Bee bee : bees)
				{
					if (bee.getOwner().getID() == hive.getID())
					{
						population++;
						switch (bee.getType())
						{
						case "Worker":
							worker++;
							break;
						case "Drone":
							drone++;
							break;
						case "Warrior":
							warrior++;
							break;
						}
					}
				}
				System.out.println("\tPopulation:" + population);
				System.out.println("\t\tWorkers:" + worker);
				System.out.println("\t\tDrones:" + drone);
				System.out.println("\t\tWarriors:" + warrior);
				System.out.println("\tFood:" + hive.getFood());
				System.out.println("\tRooms:" + hive.getRooms().size());
				System.out.println("\tBee kills:" + hive.getBeeKills());
				System.out.println("\tHive kills:" + hive.getHiveKills());
			}
		}
		catch (Exception e)
		{
			System.out.println("Invalid command arguments");
		}
	}
}
