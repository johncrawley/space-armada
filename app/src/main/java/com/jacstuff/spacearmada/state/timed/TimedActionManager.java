package com.jacstuff.spacearmada.state.timed;

import java.util.ArrayList;
import java.util.List;

public class TimedActionManager {


    private List<TimedAction> timedActionList;
    private List<TimedAction> tempList;

    public TimedActionManager(){
        this.timedActionList = new ArrayList<>();
    }
    public void update(int amount){
        tempList = new ArrayList<>(timedActionList);
        for(TimedAction timedAction : tempList){
            timedAction.decrementTimer(amount);
            if (timedAction.isReady()) {
                timedAction.executeAction();
                timedActionList.remove(timedAction);
            }
        }
    }

    public void add(TimedAction timedAction){
        timedActionList.add(timedAction);
    }
}
