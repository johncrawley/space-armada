package com.jacstuff.spacearmada.managers;


import java.util.List;

import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.controls.TouchPoint;
import com.jacstuff.spacearmada.actors.ships.ControllableShip;
import com.jacstuff.spacearmada.commands.Command;
import com.jacstuff.spacearmada.commands.FireCommand;
import com.jacstuff.spacearmada.commands.MoveCommand;
import com.jacstuff.spacearmada.controls.CircularControl;
import com.jacstuff.spacearmada.controls.ControlButton;
import com.jacstuff.spacearmada.controls.DPad;

/**
 * Created by John on 29/08/2017.
 * Responsible for connecting the player's spaceship to the controls
 */
public class InputControlsManager {
    private DPad dpad;
    private ControlButton fireButton;
    private final ControllableShip spaceship;

    public InputControlsManager(int viewWidth, int viewHeight, ControllableShip spaceship){
        this.spaceship = spaceship;
        setupActionButton(viewWidth, viewHeight);
        assignSpaceShipCommands();
    }


    public void setupDpad(int centreX, int centreY, int radius){
        dpad = new DPad(centreX, centreY, radius);
        for(Direction direction : Direction.values()){
            assignDPadCommand(direction);
        }
    }


    private void setupActionButton(int viewWidth, int viewHeight){
        int border = 50;
        final int actionButtonRadius = 70;
        int actionButtonXPosition = viewWidth - ((actionButtonRadius * 2) + border);
        int yPosition = viewHeight/2 - (actionButtonRadius);
        fireButton = new ControlButton(actionButtonXPosition, yPosition, actionButtonRadius);
    }


    private void assignSpaceShipCommands(){
       assignActionCommands();
    }


    public void setDpadPosition(int centreX, int centreY, int radius){
        dpad.setCentrePosition(centreX,centreY, radius);
    }


    private void assignActionCommands(){
        Command fireCommand = new FireCommand(spaceship);
        fireButton.assignCommand(fireCommand);
    }


    private void assignDPadCommand(Direction d){
        MoveCommand command = new MoveCommand(spaceship);
        command.assignDirection(d);
        dpad.assignCommand(d, command);
    }


    public void process(List<TouchPoint> touchPoints){
        dpad.process(touchPoints);
        fireButton.process(touchPoints);
    }


    public CircularControl getDPad(){return this.dpad;}


    public CircularControl getFireButton(){return this.fireButton;}

}
