package main.commons.events.ui;

import main.commons.events.BaseEvent;
import main.model.person.ReadOnlyPerson;

/**
 * Represents a selection change in the Person List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyTask newSelection;

    public TaskPanelSelectionChangedEvent(ReadOnlyTask newSelection){
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyTask getNewSelection() {
        return newSelection;
    }
}
