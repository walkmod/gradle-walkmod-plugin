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

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Test for walkmod script integration
 *
 * @author abelsromero
 */
class WalkmodScriptsSpec extends Specification {

	private static final String EXTENSION = 'walkmod'

	private static final String WALKMOD_GROUP = 'CodeConventions'

	Project project

	def setup() {
		project = ProjectBuilder.builder().build()
	}

	def "Applies script"() {
		when:
			project.apply plugin: WalkmodPlugin

		then:
			project[EXTENSION] != null
	
			// validate check task
			Task checkTask = project.tasks.findByName('walkmodCheck')
			checkTask != null
			checkTask.group == WALKMOD_GROUP
	
			// validate check task
			Task applyTask = project.tasks.findByName('walkmodApply')
			applyTask != null
			applyTask.group == WALKMOD_GROUP
	}
}
