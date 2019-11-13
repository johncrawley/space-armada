package com.jacstuff.spacearmada.actor.player;

import com.jacstuff.spacearmada.actors.ships.player.Energy;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class EnergyTest {

    private int initialEnergy = 200;
    private int warningLevel = 80;
    private int emergencyLevel = 30;
    private Energy energy;

    @Before
    public void setup(){
        energy = new Energy(initialEnergy, warningLevel, emergencyLevel);
    }

    @Test
    public void energyDrainsProperly(){

        Energy energy = new Energy(initialEnergy, warningLevel, emergencyLevel);

        int amountToDrain = 10;
        energy.drain(amountToDrain);
        int expectedEnergy = initialEnergy - amountToDrain;
        assertEquals("Energy after drain() is incorrect", expectedEnergy, energy.get());
        energy.drain(amountToDrain);
        expectedEnergy -= amountToDrain;
        assertEquals("Energy after 2nd drain() is incorrect", expectedEnergy, energy.get());
    }

    @Test
    public void energyDrainsFromCollision(){

        int energyLevel2 = initialEnergy / 2;
        Energy energy2 = createEnergy(energyLevel2);
        energy.collideWith(energy2);
        int expectedEnergy = initialEnergy - energyLevel2;
        assertEquals("Energy level is incorrect after collision.", expectedEnergy, energy.get());
        assertTrue("Other energy should be depleted after collision.", energy2.isDepleted());

        int smallerEnergy = initialEnergy /3;
        energy = createEnergy(smallerEnergy);
        energy2 = createEnergy(initialEnergy);

        energy.collideWith(energy2);
        assertTrue("energy should be depleted on principal Energy instance", energy.isDepleted());
        assertEquals("Incorrect energy on secondary Energy instance after collision", energy2.get(), initialEnergy - smallerEnergy);

    }

    // used to create an Enery instance where we don't really care about the warning or emergency values
    private Energy createEnergy(int initialValue){

        return new Energy(initialValue, initialValue /2, initialValue/4);
    }


    @Test
    public void energyGetsDepleted(){

        assertTrue("Energy should not start at 0 at the start of this test", energy.get() > 0);
        assertFalse("Energy should not be empty at the start of this test", energy.isDepleted());

        energy.deplete();
        assertTrue("Energy should be 0 after depletion", energy.get() == 0);
        assertTrue("Energy should be 0 after depletion", energy.isDepleted());

    }

    @Test
    public void doesntDrainPastZero(){

        energy.drain(initialEnergy + 100);
        assertEquals("Energy shouldn't go below 0", 0, energy.get());
    }

    @Test
    public void verifyWarningLevel(){
        assertFalse("There should be no warnings on full energyInt", energy.isEnergyLevelLow());
        assertFalse("There should be no emergency on full energyInt", energy.isEnergyLevelDangerouslyLow());

        int amountToDrain =(initialEnergy - warningLevel) + 10;
        energy.drain(amountToDrain);
        assertEnergyLevel("warning", energy.isEnergyLevelLow());
        assertEnergyLevel("emergency", !energy.isEnergyLevelDangerouslyLow());
        amountToDrain = (energy.get() - emergencyLevel) + 10;
        energy.drain(amountToDrain);
        assertEnergyLevel("warning", energy.isEnergyLevelLow());
        assertEnergyLevel("emergency", energy.isEnergyLevelDangerouslyLow());
    }

    private void assertEnergyLevel(String warningType, boolean predicate){
        assertTrue("There should be a " + warningType +  " at current energyInt level "+  energy.get(), predicate);
    }


}
