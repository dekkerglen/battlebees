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
public class ArchitectBee extends BeeAttributes
{

	BeeAttributes decorated;

	public ArchitectBee(BeeAttributes decorated)
	{
		this.decorated = decorated;
	}

	public BeeAttributes copy(BeeAttributes current)
	{
		return new ArchitectBee(decorated.copy(current));
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
		return decorated.getCarryingCapacity();
	}

	public float getBuildRate()
	{
		return decorated.getBuildRate() + .5f;
	}

	// methods to get attributes for decorator pattern
	public String getSpecies()
	{
		return "Architect " + decorated.getSpecies();
	}
}
