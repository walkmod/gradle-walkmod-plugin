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

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Main Plugin class
 * 
 * @author abelsromero
 */
class WalkmodPlugin implements Plugin<Project> {

	static final String EXTENSION = 'walkmod'
	
	static final String WALKMOD_GROUP = 'CodeConventions'

	void apply(Project project) {
		project.apply(plugin: 'base')
		project.extensions.create(EXTENSION, WalkmodExtension, project)

		project.task('walkmodCheck', type: WalkmodCheckTask, group: WALKMOD_GROUP) {
			description = 'Checks which classes needs to be modified according your code transformations'
		}.dependsOn "test"

		project.task('walkmodApply', type: WalkmodApplyTask, group: WALKMOD_GROUP) {
			description = 'Upgrades code to apply all code transformations'
		}.dependsOn "test"

		project.task('walkmodPatch', type: WalkmodPatchTask, group: WALKMOD_GROUP) {
			description = 'Generates a patch according your code transformations'
		}.dependsOn "test"

		// Add configuration for plugins classpath
		project.configurations {
			walkmod
		}

	}
}
