//@@author A0142686X
package main.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import main.commons.exceptions.DataConversionException;
import main.commons.util.FileUtil;
import main.model.TaskTracker;
import main.model.task.Task;
import main.testutil.TypicalTestTasks;
import main.model.ReadOnlyTaskTracker;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlTaskTrackerStorageTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlTaskTrackerStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTaskTracker_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readTaskTracker(null);
    }

    private java.util.Optional<ReadOnlyTaskTracker> readTaskTracker(String filePath) throws Exception {
        return new XmlTaskTrackerStorage(filePath).readTaskTracker(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTaskTracker("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTaskTracker("NotXmlFormatTaskTracker.xml");
    }

    @Test
    public void readAndSaveTaskTracker_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempTaskTracker.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        TaskTracker original = td.getTypicalTaskTracker();
        XmlTaskTrackerStorage xmlTaskTrackerStorage = new XmlTaskTrackerStorage(filePath);

        //Save in new file and read back
        xmlTaskTrackerStorage.saveTaskTracker(original, filePath);
        ReadOnlyTaskTracker readBack = xmlTaskTrackerStorage.readTaskTracker(filePath).get();
        assertEquals(original, new TaskTracker(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(TypicalTestTasks.deadline3));
        original.removeTask(new Task(TypicalTestTasks.event1));
        xmlTaskTrackerStorage.saveTaskTracker(original, filePath);
        readBack = xmlTaskTrackerStorage.readTaskTracker(filePath).get();
        assertEquals(original, new TaskTracker(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(TypicalTestTasks.floating3));
        xmlTaskTrackerStorage.saveTaskTracker(original); //file path not specified
        readBack = xmlTaskTrackerStorage.readTaskTracker().get(); //file path not specified
        assertEquals(original, new TaskTracker(readBack));

    }

    @Test
    public void saveTaskTracker_nullTaskTracker_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskTracker(null, "SomeFile.xml");
    }

    private void saveTaskTracker(ReadOnlyTaskTracker taskTracker, String filePath) throws IOException {
        new XmlTaskTrackerStorage(filePath).saveTaskTracker(taskTracker, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveTaskTracker_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskTracker(new TaskTracker(), null);
    }


}
