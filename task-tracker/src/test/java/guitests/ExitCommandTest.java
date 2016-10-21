package guitests;

import org.junit.Test;

import main.testutil.TestTask;
import main.testutil.TestUtil;
import static org.junit.Assert.assertTrue;

public class ExitCommandTest extends TaskTrackerGuiTest  {
	@Test
	public void exit(){
		//Make sure T-T exits properly even when HelpWindow is open.
		commandBox.runCommand("help");
		commandBox.runCommand("exit");
		assertTrue(mainGui==null);
	}
}
