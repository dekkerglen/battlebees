/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeeDecorations;

/**
 *
 * @author Glen
 */
// for decorated pattern
public class KillerBee extends BeeAttributes
{

	BeeAttributes decorated;

	public KillerBee(BeeAttributes decorated)
	{
		this.decorated = decorated;
	}

	public BeeAttributes copy(BeeAttributes current)
	{
		return new KillerBee(decorated.copy(current));
	}

	public float getSpeed()
	{
		return decorated.getSpeed();
	}

	public int getAttack()
	{
		return decorated.getAttack() + 1;
	}

	public int getMaxHealth()
	{
		return decorated.getMaxHealth();
	}

	public int getCarryingCapacity()
	{
		return decorated.getCarryingCapacity();
	}

	public float getBuildRate()
	{
		return decorated.getBuildRate();
	}

	// methods to get attributes for decorator pattern
	public String getSpecies()
	{
		return "Killer " + decorated.getSpecies();
	}
}
