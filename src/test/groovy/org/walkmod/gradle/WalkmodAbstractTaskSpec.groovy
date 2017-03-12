/*
 * Copyright 2017 walkmod.
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

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

import spock.lang.Specification

/**
 * Abstract task specification.
 *
 * @author abelsromero
 */
class WalkmodAbstractTaskSpec extends Specification {

	private static final String EXTENSION = 'walkmod'

	private static final String CHECK_TASK = 'walkmodCheck'

	Project project

	def setup() {
		project = ProjectBuilder.builder().build()
	}

	def "Extension default values"() {
		when:
			project.apply plugin: WalkmodPlugin

		then:
			WalkmodExtension extension = project.walkmod
			extension.chains == null
			!extension.offline
			extension.verbose
			extension.printErrors
			extension.configFile == new File("walkmod.xml")
	}
	
	def 'Tasks default values'() {
		when:
			project.apply plugin: WalkmodPlugin

		then:
			WalkmodAbstractTask task = project.tasks.findByName(CHECK_TASK)
			task != null
			task.@chains  == null
			task.@offline == null
			task.@verbose == null
			task.@printErrors == null
			task.@configFile == null
	}
	
	def 'Task should overwrite extension values when null'() {
		given: 'A standard Walkmod project'
			project.apply plugin: WalkmodPlugin

		when: 'no properties are modified'		
			WalkmodAbstractTask task = project.tasks.findByName(CHECK_TASK)
			task != null
			task.@chains  == null
			task.@offline == null
			task.@verbose == null
			task.@printErrors == null
			task.@configFile == null

		then: 'returned values are the ones of the the extension'
			task != null
			// extension values
			WalkmodExtension extension = project.walkmod
			extension.chains == null
			!extension.offline
			extension.verbose
			extension.printErrors
			extension.configFile == new File("walkmod.xml")
			// values comparision
			task.chains  == extension.chains
			task.offline == extension.offline
			task.verbose == extension.verbose
			task.showErrors == extension.printErrors
			task.configFile == extension.configFile
	}
}
