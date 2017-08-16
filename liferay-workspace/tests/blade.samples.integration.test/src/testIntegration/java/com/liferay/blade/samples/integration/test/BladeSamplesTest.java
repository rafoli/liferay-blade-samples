/**
 * Copyright 2000-present Liferay, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liferay.blade.samples.integration.test;

import aQute.bnd.osgi.Jar;

import aQute.lib.io.IO;

import com.liferay.blade.samples.integration.test.utils.BladeCLIUtil;
import com.liferay.blade.samples.integration.test.utils.GradleRunnerUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;

import java.nio.file.Files;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.Request.Builder;

import org.gradle.testkit.runner.BuildTask;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Lawrence Lee
 */
public class BladeSamplesTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		OkHttpClient client = new OkHttpClient();
		Request request = new Builder().url("http://localhost:8080").build();

		boolean pingSucceeded = false;

		while (!pingSucceeded) {
			try {
				client.newCall(request).execute();
				pingSucceeded = true;
				break;
			}
			catch (Exception e) {
			}

			Thread.sleep(100);
		}
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		if (BladeCLIUtil.bladeJar.exists()) {
			IO.delete(BladeCLIUtil.bladeJar);
			Assert.assertFalse(BladeCLIUtil.bladeJar.exists());
		}
	}

	@Before
	public void setUp() throws Exception {
		_testDir = Files.createTempDirectory("bladetest").toFile();

		_testDir.deleteOnExit();
	}

	@After
	public void tearDown() throws Exception {
		if (_testDir.exists()) {
			IO.delete(_testDir);
			Assert.assertFalse(_testDir.exists());
		}
	}

	@Test
	public void testAllBladeSamples() throws Exception {
		List<String> bladeSampleOutputFiles = new ArrayList<>();
		Map<String, String> bundleIDAllMap = new HashMap<>();
		Map<String, String> bundleIDStartMap = new HashMap<>();

		String[] sampleOutputFiles = System.getProperty(
			"moduleOutputPaths").split(",");

		for (String file : sampleOutputFiles) {
			bladeSampleOutputFiles.add(file);
		}

		for (String sampleBundleFile : bladeSampleOutputFiles) {
			String printFileName;
			String installBundleOutput;

			if (sampleBundleFile.endsWith(".war")) {
				printFileName = new File(sampleBundleFile).getName();

				printFileName = printFileName.substring(
					0, printFileName.lastIndexOf('.'));

				String output = BladeCLIUtil.execute(
					"sh", "install",
					"webbundle:file://" + sampleBundleFile +
						"?Web-ContextPath=/" + printFileName);

				installBundleOutput = output.substring(
					output.indexOf("bundle id:") + 11,
					output.indexOf("\n", output.indexOf("bundle id:")));

				bundleIDAllMap.put(installBundleOutput, printFileName);
				bundleIDStartMap.put(installBundleOutput, printFileName);
			}
			else {
				File file = new File(sampleBundleFile);

				if (file.exists()) {
					printFileName = new File(sampleBundleFile).getName();

					printFileName = printFileName.substring(
						0, printFileName.lastIndexOf('.'));

					installBundleOutput = BladeCLIUtil.installBundle(
						new File(sampleBundleFile));

					bundleIDAllMap.put(installBundleOutput, printFileName);

					try (Jar jar =
							new Jar(sampleBundleFile, sampleBundleFile)) {

						Manifest manifest = jar.getManifest();

						Attributes mainAttributes =
							manifest.getMainAttributes();

						if (mainAttributes.getValue("Fragment-Host") == null) {
							bundleIDStartMap.put(
								installBundleOutput, printFileName);
						}
					}
				}
			}
		}

		for (String startBundleID : bundleIDStartMap.keySet()) {
			BladeCLIUtil.startBundle(startBundleID);
		}

		for (String allBundleID : bundleIDAllMap.keySet()) {
			BladeCLIUtil.uninstallBundle(allBundleID);
		}
	}

	@Test
	public void testControlMenuEntryGradleTemplates() throws Exception {
		File projectPath = BladeCLIUtil.createProject(
			_testDir, "control-menu-entry", "helloworld");

		BuildTask buildtask = GradleRunnerUtil.executeGradleRunner(
			projectPath, "build");

		GradleRunnerUtil.verifyGradleRunnerOutput(buildtask);

		File buildOutput = new File(
			projectPath + "/build/libs/helloworld-1.0.0.jar");

		Assert.assertTrue(buildOutput.exists());

		String bundleID = BladeCLIUtil.installBundle(buildOutput);

		BladeCLIUtil.startBundle(bundleID);

		BladeCLIUtil.uninstallBundle(bundleID);
	}

	@Test
	public void testMVCPortletGradleTemplates() throws Exception {
		File projectPath = BladeCLIUtil.createProject(
			_testDir, "mvc-portlet", "helloworld");

		BuildTask buildtask = GradleRunnerUtil.executeGradleRunner(
			projectPath, "build");

		GradleRunnerUtil.verifyGradleRunnerOutput(buildtask);

		File buildOutput = new File(
			projectPath + "/build/libs/helloworld-1.0.0.jar");

		Assert.assertTrue(buildOutput.exists());

		String bundleID = BladeCLIUtil.installBundle(buildOutput);

		BladeCLIUtil.startBundle(bundleID);

		BladeCLIUtil.uninstallBundle(bundleID);
	}

	@Test
	public void testPanelAppGradleTemplates() throws Exception {
		File projectPath = BladeCLIUtil.createProject(
			_testDir, "panel-app", "helloworld");

		BuildTask buildtask = GradleRunnerUtil.executeGradleRunner(
			projectPath, "build");

		GradleRunnerUtil.verifyGradleRunnerOutput(buildtask);

		File buildOutput = new File(
			projectPath + "/build/libs/helloworld-1.0.0.jar");

		Assert.assertTrue(buildOutput.exists());

		String bundleID = BladeCLIUtil.installBundle(buildOutput);

		BladeCLIUtil.startBundle(bundleID);

		BladeCLIUtil.uninstallBundle(bundleID);
	}

	@Test
	public void testPortletGradleTemplates() throws Exception {
		File projectPath = BladeCLIUtil.createProject(
			_testDir, "portlet", "helloworld");

		BuildTask buildtask = GradleRunnerUtil.executeGradleRunner(
			projectPath, "build");

		GradleRunnerUtil.verifyGradleRunnerOutput(buildtask);

		File buildOutput = new File(
			projectPath + "/build/libs/helloworld-1.0.0.jar");

		Assert.assertTrue(buildOutput.exists());

		String bundleID = BladeCLIUtil.installBundle(buildOutput);

		BladeCLIUtil.startBundle(bundleID);

		BladeCLIUtil.uninstallBundle(bundleID);
	}

	@Test
	public void testPortletProviderGradleTemplates() throws Exception {
		File projectPath = BladeCLIUtil.createProject(
			_testDir, "portlet-provider", "helloworld");

		BuildTask buildtask = GradleRunnerUtil.executeGradleRunner(
			projectPath, "build");

		GradleRunnerUtil.verifyGradleRunnerOutput(buildtask);

		File buildOutput = new File(
			projectPath + "/build/libs/helloworld-1.0.0.jar");

		Assert.assertTrue(buildOutput.exists());

		String bundleID = BladeCLIUtil.installBundle(buildOutput);

		BladeCLIUtil.startBundle(bundleID);

		BladeCLIUtil.uninstallBundle(bundleID);
	}

	@Test
	public void testServiceBuilderBladeSample() throws Exception {
		File projectPath = new File(
			System.getProperty("user.dir")).getParentFile().getParentFile();

		File serviceProperties = new File(
			projectPath,
			"apps/service-builder/foo-service/src/main/resources" +
				"/service.properties");

		File servicePropertiesBackup = new File("service.properties.bak");

		IO.copy(serviceProperties, servicePropertiesBackup);

		BuildTask buildService = GradleRunnerUtil.executeGradleRunner(
			projectPath, ":apps:service-builder:foo-service:buildService");

		GradleRunnerUtil.verifyGradleRunnerOutput(buildService);

		IO.copy(servicePropertiesBackup, serviceProperties);
		IO.delete(servicePropertiesBackup);

		BuildTask cleanTask = GradleRunnerUtil.executeGradleRunner(
			projectPath, ":apps:service-builder:foo-api:clean");

		GradleRunnerUtil.verifyGradleRunnerOutput(cleanTask);

		BuildTask buildApiTask = GradleRunnerUtil.executeGradleRunner(
			projectPath, ":apps:service-builder:foo-api:build");

		GradleRunnerUtil.verifyGradleRunnerOutput(buildApiTask);

		cleanTask = GradleRunnerUtil.executeGradleRunner(
			projectPath, ":apps:service-builder:foo-service:clean");

		GradleRunnerUtil.verifyGradleRunnerOutput(cleanTask);

		BuildTask buildServiceTask = GradleRunnerUtil.executeGradleRunner(
			projectPath, ":apps:service-builder:foo-service:assemble");

		GradleRunnerUtil.verifyGradleRunnerOutput(buildServiceTask);

		File buildApiOutput = new File(
			projectPath + "/apps/service-builder/foo-api/build/libs" +
				"/com.liferay.blade.foo.api-1.0.0.jar");
		File buildServiceOutput = new File(
			projectPath + "/apps/service-builder/foo-service/build/libs" +
				"/com.liferay.blade.foo.service-1.0.0.jar");

		Assert.assertTrue(buildApiOutput.exists());
		Assert.assertTrue(buildServiceOutput.exists());

		String bundleIDApi = BladeCLIUtil.installBundle(buildApiOutput);
		String bundleIDService = BladeCLIUtil.installBundle(buildServiceOutput);

		BladeCLIUtil.startBundle(bundleIDApi);
		BladeCLIUtil.startBundle(bundleIDService);

		BladeCLIUtil.uninstallBundle(bundleIDApi, bundleIDService);
	}

	@Test
	public void testServiceBuilderGradleTemplate() throws Exception {
		File projectPath = BladeCLIUtil.createProject(
			_testDir, "service-builder", "guestbook", "-p",
			"com.liferay.docs.guestbook");

		BuildTask buildService = GradleRunnerUtil.executeGradleRunner(
			projectPath, "buildService");

		GradleRunnerUtil.verifyGradleRunnerOutput(buildService);

		BuildTask buildtask = GradleRunnerUtil.executeGradleRunner(
			projectPath, "build");

		GradleRunnerUtil.verifyGradleRunnerOutput(buildtask);

		File buildApiOutput = new File(
			projectPath + "/guestbook-api/build/libs" +
				"/com.liferay.docs.guestbook.api-1.0.0.jar");
		File buildServiceOutput = new File(
			projectPath + "/guestbook-service/build/libs" +
				"/com.liferay.docs.guestbook.service-1.0.0.jar");

		Assert.assertTrue(buildApiOutput.exists());
		Assert.assertTrue(buildServiceOutput.exists());

		String bundleIDApi = BladeCLIUtil.installBundle(buildApiOutput);
		String bundleIDService = BladeCLIUtil.installBundle(buildServiceOutput);

		BladeCLIUtil.startBundle(bundleIDApi);
		BladeCLIUtil.startBundle(bundleIDService);

		BladeCLIUtil.uninstallBundle(bundleIDApi, bundleIDService);
	}

	@Test
	public void testServiceGradleTemplate() throws Exception {
		BladeCLIUtil.createProject(
			_testDir, "service", "helloworld", "-s",
			"com.liferay.portal.kernel.events.LifecycleAction", "-c",
			"FooAction");

		File projectPath = new File(_testDir + "/helloworld");

		File file = new File(
			projectPath + "/src/main/java/helloworld/FooAction.java");

		List<String> lines = new ArrayList<>();
		String line = null;

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			while ((line = reader.readLine()) != null) {
				lines.add(line);

				if (line.startsWith(
						"import com.liferay.portal.kernel.events." +
							"LifecycleAction;")) {

					lines.add(
						"import com.liferay.portal.kernel.events." +
							"LifecycleEvent;");
					lines.add(
						"import com.liferay.portal.kernel.events." +
							"ActionException;");
				}

				if (line.equals(
						"public class FooAction implements LifecycleAction " +
							"{")) {

					StringBuilder sb = new StringBuilder();

					sb.append(
						"@Override\n"
					).append(
						"public void processLifecycleEvent"
					).append(
						"(LifecycleEvent lifecycleEvent)\n"
					).append(
						"throws ActionException {\n"
					).append(
						"System.out.println"
					).append(
						"(\"login.event.pre=\" + lifecycleEvent);\n"
					).append(
						"}\n"
					);

					lines.add(sb.toString());
				}
			}
		}

		try (Writer writer = new FileWriter(file)) {
			for (String string : lines) {
				writer.write(string + "\n");
			}
		}

		BuildTask buildtask = GradleRunnerUtil.executeGradleRunner(
			projectPath, "build");

		GradleRunnerUtil.verifyGradleRunnerOutput(buildtask);

		File buildOutput = new File(
			projectPath + "/build/libs/helloworld-1.0.0.jar");

		Assert.assertTrue(buildOutput.exists());

		String bundleID = BladeCLIUtil.installBundle(buildOutput);

		BladeCLIUtil.startBundle(bundleID);

		BladeCLIUtil.uninstallBundle(bundleID);
	}

	@Test
	public void testServiceWrapperGradleTemplate() throws Exception {
		File projectPath = BladeCLIUtil.createProject(
			_testDir, "service-wrapper", "serviceoverride", "-s",
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper");

		BuildTask buildtask = GradleRunnerUtil.executeGradleRunner(
			projectPath, "build");

		GradleRunnerUtil.verifyGradleRunnerOutput(buildtask);

		File buildOutput = new File(
			projectPath + "/build/libs/serviceoverride-1.0.0.jar");

		Assert.assertTrue(buildOutput.exists());

		String bundleID = BladeCLIUtil.installBundle(buildOutput);

		BladeCLIUtil.startBundle(bundleID);

		BladeCLIUtil.uninstallBundle(bundleID);
	}

	private File _testDir;

}