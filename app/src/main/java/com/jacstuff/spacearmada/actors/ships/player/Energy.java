package com.jacstuff.spacearmada.actors.ships.player;

public class Energy {

    private int value;
    private int warningEnergyLevel;
    private int emergencyEnergyLevel;


    public Energy(int initialEnergy){
        this(initialEnergy, initialEnergy/2, initialEnergy/3);
    }


    public Energy(int initialEnergy, int warningEnergyLevel, int emergencyEnergyLevel){
        this.value = initialEnergy;
        this.warningEnergyLevel = warningEnergyLevel;
        this.emergencyEnergyLevel = emergencyEnergyLevel;
    }


    public int get(){
        return this.value;
    }


    public void drain(int amount){
        value -= amount;
        if(value < 0){
            value = 0;
        }
    }


    public boolean isDepleted(){
        return this.value <= 0;
    }


    public void deplete(){
        this.value = 0;
    }


    public void collideWith(Energy otherEnergy){
        boolean isOtherEnergyGreater = otherEnergy.get() > this.value;
        Energy e1 = isOtherEnergyGreater ? otherEnergy : this;
        Energy e2 = isOtherEnergyGreater ? this : otherEnergy;

        drainFirstDepleteSecond(e1, e2);
    }


    private void drainFirstDepleteSecond(Energy e1, Energy e2){
        e1.drain(e2.get());
        e2.deplete();
    }


    public boolean isEnergyLevelLow(){
        return this.value < warningEnergyLevel;
    }


    public boolean isEnergyLevelDangerouslyLow(){
        return this.value < emergencyEnergyLevel;
    }

}
