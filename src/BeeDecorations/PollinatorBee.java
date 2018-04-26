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
public class PollinatorBee extends BeeAttributes
{

	BeeAttributes decorated;

	public PollinatorBee(BeeAttributes decorated)
	{
		this.decorated = decorated;
	}

	public BeeAttributes copy(BeeAttributes current)
	{
		return new PollinatorBee(decorated.copy(current));
	}

	public float getSpeed()
	{
		return decorated.getSpeed();
	}

	public int getAttack()
	{
		return decorated.getAttack();
	}

	public int getMaxHealth()
	{
		return decorated.getMaxHealth();
	}

	public int getCarryingCapacity()
	{
		return decorated.getCarryingCapacity() + 4;
	}

	public float getBuildRate()
	{
		return decorated.getBuildRate();
	}

	// methods to get attributes for decorator pattern
	public String getSpecies()
	{
		return "Pollinator " + decorated.getSpecies();
	}
}
