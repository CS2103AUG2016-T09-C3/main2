//@@author A0142686X
package main.storage;

import main.commons.events.model.TaskTrackerChangedEvent;
import main.commons.events.storage.FilePathChangedEvent;
import main.commons.exceptions.DataConversionException;
import main.model.ReadOnlyTaskTracker;
import main.model.UserPrefs;

import java.io.IOException;
import java.util.Optional;

/**
 * API of the Storage component
 */
public interface Storage extends TaskTrackerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTaskTrackerFilePath();

    @Override
    Optional<ReadOnlyTaskTracker> readTaskTracker() throws DataConversionException, IOException;

    @Override
    void saveTaskTracker(ReadOnlyTaskTracker taskTracker) throws IOException;

    /**
     * Saves the current version of the TaskTracker to the hard disk.
     *   Creates the data file if it is missing.
     * Raises DataSavingExceptionEvent if there was an error during saving.
     */
    void handleTaskTrackerChangedEvent(TaskTrackerChangedEvent abce);

    void handleFilePathChangedEvent(FilePathChangedEvent event);
}
