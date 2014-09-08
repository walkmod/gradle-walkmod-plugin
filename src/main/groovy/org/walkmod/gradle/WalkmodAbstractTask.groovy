/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.walkmod.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.artifacts.Configuration
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.walkmod.WalkModFacade


/**
 * @author abelsromero
 */
abstract class WalkmodAbstractTask extends DefaultTask {

	private static final boolean IS_WINDOWS = System.getProperty('os.name').contains('Windows')
	private static final String DOUBLE_BACKLASH = '\\\\'
	private static final String BACKLASH = '\\'

	public static final String WALKMOD_FACTORY_CLASSNAME = 'org.walkmod.WalkModFacade'

	@Optional
	protected List<String> chains = null;

	@Optional
	protected offline = false;

	@Optional
	protected verbose = true;

	@Optional
	protected boolean showErrors = true;
	
	@Optional @InputFile
	protected File configFile = new File("walkmod.xml");

	@Optional @InputFile
	protected File sources

	WalkmodProxy walkmodProxy

	Configuration classpath
	private static ClassLoader cl

	WalkmodAbstractTask() {
	}

	/**
	 * Validates input values. If an input value is not valid an exception is thrown.
	 */
	private void validateInputs() {
		setupClassLoader()
	}

	private static String normalizePath(String path) {
		if (IS_WINDOWS) {
			path = path.replace(DOUBLE_BACKLASH, BACKLASH)
			path = path.replace(BACKLASH, DOUBLE_BACKLASH)
		}
		return path
	}

	@TaskAction
	void processTask() {
		validateInputs()
		instantiateWalkmod()

		executeTask(null)
		
		if (!walkmodProxy) {
			instantiateWalkmod()
		}
	}
	
	abstract void executeTask(String... chains);

	private void instantiateWalkmod() {
		//            walkmodProxy = new WalkmodProxyImpl(delegate: loadClass(WALKMOD_FACTORY_CLASSNAME).create(null as String))
		if (!walkmodProxy) {
			logger.lifecycle("Creating facade config:$configFile o:$offline v:$verbose e:$showErrors")
			walkmodProxy = new WalkmodProxyImpl(delegate: new WalkModFacade(configFile, offline, verbose, showErrors))
		}
	}

	private static Class loadClass(String className) {
		cl.loadClass(className)
	}

	private void setupClassLoader() {
		if (classpath?.files) {
			def urls = classpath.files.collect { it.toURI().toURL() }
			cl = new URLClassLoader(urls as URL[], Thread.currentThread().contextClassLoader)
			Thread.currentThread().contextClassLoader = cl
		} else {
			cl = Thread.currentThread().contextClassLoader
		}
	}
}
