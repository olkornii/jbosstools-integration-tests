package org.jboss.tools.aerogear.ui.bot.test;

import static org.junit.Assert.assertTrue;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.eclipse.condition.ConsoleHasText;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.condition.ShellIsActive;
import org.eclipse.reddeer.swt.condition.TreeHasChildren;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.combo.LabeledCombo;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.jboss.tools.aerogear.reddeer.feedhenry.ui.cordova.preferences.FeedHenryPreferencesPage;
import org.jboss.tools.aerogear.reddeer.feedhenry.ui.cordova.wizards.CordovaImportWizard;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * Base class for FeedHenry integration tests
 * 
 * @author Pavol Srna
 *
 */
@RunWith(RedDeerSuite.class)
public class FeedHenryBotTest {

	private static String TARGET_URL = System.getProperty("TARGET_URL");
	private static String API_KEY = System.getProperty("API_KEY");
	private static String SSH_SECRET = System.getProperty("SSH_SECRET");
	protected static String FH_PROJECT = "jbds-integration-tests";
	protected static String FH_APP_NAME = "CordovaApp";
	protected static String WS_PATH = ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString();

	@BeforeClass
	public static void setup() {
		WorkbenchPreferenceDialog preferenceDialog = new WorkbenchPreferenceDialog();
		FeedHenryPreferencesPage fhp = new FeedHenryPreferencesPage(preferenceDialog);
		preferenceDialog.open();
		preferenceDialog.select(fhp);
		fhp.setTargetUrl(TARGET_URL);
		fhp.setApiKey(API_KEY);
		preferenceDialog.ok();
	}

	public static void importApp(String project, String appName) {
		CordovaImportWizard w = new CordovaImportWizard();
		w.open();
		new WaitUntil(new TreeHasChildren(new DefaultTree()), TimePeriod.VERY_LONG);
		new DefaultTreeItem(project, appName).setChecked(true);
		new LabeledCombo("Directory:").setText(WS_PATH);
		assertTrue(w.isFinishEnabled());
		w.finish();
		// wait for shell and enter ssh secret
		try {
			new WaitUntil(new ShellIsActive("Information"), TimePeriod.LONG);
			new DefaultText().setText(SSH_SECRET);
			new PushButton("OK").click();
		} catch (RedDeerException e) {
			// do nothing
		}
		new WaitWhile(new ShellIsActive("Import"));
	}

	public DefaultShell runWithRemoteFeedHenryServer(String projectName) {
		new ProjectExplorer().selectProjects(projectName);
	    new ContextMenuItem(new WithTextMatcher("Run As"), new RegexMatcher("(\\d+)( Run w/remote FeedHenry server)")).select();
	    new WaitUntil(new ShellIsActive("BrowserSim"), TimePeriod.LONG);
	    DefaultShell browserSimShell = new DefaultShell("BrowserSim");
	    new WaitWhile(new JobIsRunning());
	    new WaitUntil(new ConsoleHasText("!JavaScript INFO: fh cloud is ready"), TimePeriod.LONG);
	    return browserSimShell;
	  }
	
}
