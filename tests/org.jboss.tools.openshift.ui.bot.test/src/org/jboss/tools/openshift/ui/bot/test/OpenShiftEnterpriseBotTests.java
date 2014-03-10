/*******************************************************************************
 * Copyright (c) 2007-20013 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.openshift.ui.bot.test;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.tools.openshift.ui.bot.test.app.CreateAdapterFromServerView;
import org.jboss.tools.openshift.ui.bot.test.app.CreateAppUsingWizard;
import org.jboss.tools.openshift.ui.bot.test.app.CreateApplicationFromGithubEnterprise;
import org.jboss.tools.openshift.ui.bot.test.app.CreateDeleteEWSApp;
import org.jboss.tools.openshift.ui.bot.test.app.CreateDeleteJBossApp;
import org.jboss.tools.openshift.ui.bot.test.app.CreateDeleteJenkinsApp;
import org.jboss.tools.openshift.ui.bot.test.app.CreateDeletePHPApp;
import org.jboss.tools.openshift.ui.bot.test.app.CreateDeletePerlApp;
import org.jboss.tools.openshift.ui.bot.test.app.CreateDeletePythonApp;
import org.jboss.tools.openshift.ui.bot.test.app.CreateDeleteRubyApp;
import org.jboss.tools.openshift.ui.bot.test.app.CreateDeleteScalableEWSApp;
import org.jboss.tools.openshift.ui.bot.test.app.CreateDeleteScalablePHPApp;
import org.jboss.tools.openshift.ui.bot.test.app.CreateDeleteScalablePerlApp;
import org.jboss.tools.openshift.ui.bot.test.app.CreateDeleteScalablePythonApp;
import org.jboss.tools.openshift.ui.bot.test.app.CreateDeleteScaledJBossApp;
import org.jboss.tools.openshift.ui.bot.test.app.CreateDeleteScaledRubyApp;
import org.jboss.tools.openshift.ui.bot.test.app.ImportAndDeployGitHubProjectEnterprise;
import org.jboss.tools.openshift.ui.bot.test.app.ImportApplicationFromOpenShift;
import org.jboss.tools.openshift.ui.bot.test.app.RepublishApp;
import org.jboss.tools.openshift.ui.bot.test.app.RestartApplication;
import org.jboss.tools.openshift.ui.bot.test.cartridge.EmbedCartridges;
import org.jboss.tools.openshift.ui.bot.test.domain.CreateDomain;
import org.jboss.tools.openshift.ui.bot.test.domain.DeleteDomain;
import org.jboss.tools.openshift.ui.bot.test.domain.RenameDomain;
import org.jboss.tools.openshift.ui.bot.test.explorer.Connection;
import org.jboss.tools.openshift.ui.bot.test.explorer.CreateAdapter;
import org.jboss.tools.openshift.ui.bot.test.explorer.ManageSSH;
import org.jboss.tools.openshift.ui.bot.test.explorer.OpenShiftEnterpriseDebugFeatures;
import org.jboss.tools.openshift.ui.bot.util.CleanUp;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * <b>OpenShift RedDeer TestSuite</b>
 * <br>
 * Test for OpenShift Enterprise private cloud. 
 * <b>
 * Please do not change the order of tests - relationship between automated tests and TCMS
 * <b/>
 * 
 * @author mlabuda
 */
@RunWith(RedDeerSuite.class)
@SuiteClasses({
	/* Connection stuff */
	Connection.class, 
	ManageSSH.class, 
	
	/* Domain*/
	CreateDomain.class,
 	DeleteDomain.class,
 	RenameDomain.class,

	/* Application creation*/
 	ImportApplicationFromOpenShift.class,
 	CreateAppUsingWizard.class,
	ImportAndDeployGitHubProjectEnterprise.class,
	// Create app from github is temporarily down bcs. of broken example and enterprise 
//	CreateApplicationFromGithubEnterprise.class,
 	CreateAdapter.class,
	CreateAdapterFromServerView.class,
	EmbedCartridges.class, 
	// TODO Conflict cartridge 
	RepublishApp.class,
	OpenShiftEnterpriseDebugFeatures.class,
 	RestartApplication.class, 
 	// TODO maven profile
 	// TODO multimaven app
 	
	/* Applications*/ 
	CreateDeleteJBossApp.class,
	CreateDeleteEWSApp.class, 
	CreateDeletePHPApp.class,
	CreateDeletePythonApp.class,
	CreateDeleteRubyApp.class, 
	CreateDeleteJenkinsApp.class, 
	CreateDeletePerlApp.class, 
	
	/* Scalable applications */
	CreateDeleteScalableEWSApp.class, 
	CreateDeleteScaledJBossApp.class, 
	CreateDeleteScalablePHPApp.class,
	CreateDeleteScalablePythonApp.class, 
	CreateDeleteScalablePerlApp.class, 
	CreateDeleteScaledRubyApp.class, 
	
	CleanUp.class
})
public class OpenShiftEnterpriseBotTests {
	
}