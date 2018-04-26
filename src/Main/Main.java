package Main;

import Apiary.Apiary;
import Apiary.BeeMediator;
import Apiary.Beehive;
import Bees.Bee;
import java.util.List;
import java.util.Scanner;

public class Main
{

	public static void main(String[] args)
	{
		Apiary apiary = Apiary.getApiary();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Would you like to view visual representation? (y/n, no for text only)");
		char response = scanner.nextLine().toLowerCase().charAt(0);
		if (response == 'y')
		{
			ApiaryForm af = new ApiaryForm();
			af.setVisible(true);
		}
		else
		{
			System.out.println("Starting text simulation.");
			TextSimulation(scanner);
		}
	}

	public static void TextSimulation(Scanner input)
	{
		String in;

		System.out.println(
				"Simulation started with 5 hives. Use tick [n] to play the simulation for n units of time. Type help for more commands.");
		do
		{
			System.out.println("Press enter to continue");
			input.nextLine();
			for (Beehive hive : Apiary.getApiary().getHives())
			{

				int population = 0;
				List<Bee> bees = BeeMediator.getMediator().getBees();
				for (Bee bee : bees)
				{
					if (bee.getOwner().getID() == hive.getID())
					{
						population++;
					}
				}
				if (population > 0)
				{
					System.out.println("Hive " + hive.getID() + "\t Pop: " + population + "\t Food: " + hive.getFood()
							+ "\tSpecies: " + hive.getSpecies());
				}
			}
			System.out.println("What would you like to do next? Type quit to end");
			in = input.nextLine();
			if (!in.toLowerCase().equals("quit"))
			{
				Interface.command(in);
			}

		} while (!in.toLowerCase().equals("quit"));
	}
}
