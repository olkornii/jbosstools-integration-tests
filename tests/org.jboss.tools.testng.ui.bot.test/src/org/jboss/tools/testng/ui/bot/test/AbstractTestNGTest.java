/******************************************************************************* 
 * Copyright (c) 2020 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.tools.testng.ui.bot.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.eclipse.ui.perspectives.AbstractPerspective;
import org.eclipse.reddeer.eclipse.ui.problems.Problem;
import org.eclipse.reddeer.eclipse.ui.views.markers.ProblemsView;
import org.eclipse.reddeer.eclipse.ui.views.markers.ProblemsView.ProblemType;
import org.eclipse.reddeer.jface.condition.WindowIsAvailable;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.eclipse.reddeer.workbench.handler.WorkbenchShellHandler;
import org.eclipse.reddeer.workbench.impl.editor.TextEditor;
import org.jboss.tools.quarkus.reddeer.perspective.QuarkusPerspective;
//import org.jboss.tools.maven.reddeer.wizards.MavenImportWizardFirstPage;
import org.junit.After;

/**
 * 
 * @author olkornii@redhat.com
 *
 */
public abstract class AbstractTestNGTest {

	private static String jdkVersion11 = "11";
	private static String propertyName = "java.specification.version";

	private static String pomFile = "pom.xml";

	private static String mavenCompilerSourceStart = "<maven.compiler.source>";
	private static String mavenCompilerSourceEnd = "</maven.compiler.source>";
	private static String mavenCompilerTargetStart = "<maven.compiler.target>";
	private static String mavenCompilerTargetEnd = "</maven.compiler.target>";

	public static void checkJdkVersion(String projectName) {
		String javaVersion = System.getProperty(propertyName);
		boolean isFound = javaVersion.contains(jdkVersion11);

		String newSource;
		String newTarget;
		String actualSource;
		String actualTarget;
		String openFile;

		if (!isFound) {
			newSource = mavenCompilerSourceStart + javaVersion + mavenCompilerSourceEnd;
			newTarget = mavenCompilerTargetStart + javaVersion + mavenCompilerTargetEnd;
			actualSource = mavenCompilerSourceStart + jdkVersion11 + mavenCompilerSourceEnd;
			actualTarget = mavenCompilerTargetStart + jdkVersion11 + mavenCompilerTargetEnd;
			openFile = pomFile;
			changeVersion(projectName, openFile, actualSource, actualTarget, newSource, newTarget);
		}
	}

	private static void changeVersion(String projectName, String openFile, String actualSource, String actualTarget,
			String newSource, String newTarget) {
		new ProjectExplorer().getProject(projectName).getProjectItem(openFile).select();
		new ContextMenuItem("Open With", "Text Editor").select();

		TextEditor ed = new TextEditor(openFile);

		changeLine(ed, actualSource, newSource);
		changeLine(ed, actualTarget, newTarget);

		ed.save();
		ed.close();

		new ProjectExplorer().selectProjects(projectName);

		new ContextMenuItem("Maven", "Update Project...").select();
		new OkButton().click();

		new WaitWhile(new JobIsRunning(), TimePeriod.VERY_LONG);
	}

	private static void changeLine(TextEditor ed, String myActual, String myNew) {
		ed.selectText(myActual);
		int textPos = ed.getPositionOfText(myActual);
		new ContextMenuItem("Cut").select();
		ed.insertText(textPos, myNew);
	}

	public static void checkProblemsView() {
		ProblemsView problemsView = new ProblemsView();
		problemsView.open();
		List<Problem> problems = problemsView.getProblems(ProblemType.ERROR);
		assertEquals("There should be no errors in imported project", 0, problems.size());

	}

	public static void createQuarkusProject(String projectName) {
		openQuarkusPerspective();
		openQuarkusWizard();

		new LabeledText("Project name:").setText(projectName);
		new PushButton("Next >").click();
		new PushButton("Finish").click();
		new WaitWhile(new JobIsRunning(), TimePeriod.VERY_LONG);
	}

	public static void openQuarkusPerspective() {
		AbstractPerspective qp = new QuarkusPerspective();
		qp.open();
		assertTrue(qp.isOpened());
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
	}

	public static void openQuarkusWizard() {
		new ShellMenuItem("File", "New", "Quarkus Project").select();
		new WaitUntil(new WindowIsAvailable("New Quarkus project"), TimePeriod.LONG);
	}

	@After
	public void deleteProject() {
		WorkbenchShellHandler.getInstance().closeAllNonWorbenchShells();
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		pe.deleteAllProjects(true);
		new WaitWhile(new JobIsRunning(), TimePeriod.VERY_LONG);
	}
}
