package de.nix.dreamvator.stage;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StageChangeEvent extends Event {

    private StageManager.Stage newStage;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public StageChangeEvent(StageManager.Stage stage) {
        this.newStage = stage;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public StageManager.Stage getStage() {
        return newStage;
    }

}
