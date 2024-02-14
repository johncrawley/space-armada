package com.jacstuff.spacearmada.managers;

import android.content.Context;
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
    private final DPad dpad;
    private final ControlButton fireButton;
    private final ControllableShip spaceship;
    private final Context context;

    public InputControlsManager(Context context, int screenWidth, int screenHeight, ControllableShip spaceship){
        this.context = context;
        final int actionButtonRadius = 70;
        log("constructor() screen width,height: " + screenWidth + ","  + screenHeight);
        int border = 50;
        int dpadRadius = 200;
        int dpadXPosition = 50;
        int actionButtonXPosition = screenWidth - ((actionButtonRadius * 2) + border);
        int yPosition = screenHeight - ((dpadRadius * 2) + border);

        this.spaceship = spaceship;
        dpad = new DPad(dpadXPosition, yPosition,140);
        fireButton = new ControlButton(actionButtonXPosition, yPosition, actionButtonRadius);
        assignSpaceShipCommands();
    }


    private void log(String msg){
        System.out.println("^^^ InputControlsManager: " + msg);
    }


    private void assignSpaceShipCommands(){
       assignActionCommands();
       for(Direction direction : Direction.values()){
           assignDPadCommand(direction);
       }
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
