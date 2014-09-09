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

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

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

		Configuration configuration = project.configurations.maybeCreate(EXTENSION)

		project.afterEvaluate {
			project.tasks.findAll {it instanceof WalkmodAbstractTask && !it.classpath}.each {
				it.classpath = configuration
			}
			project.dependencies {
				//                walkmod("org.walkmod:walkmod-core:${extension.version}")
			}
		}

		project.task('walkmodCheck', type: WalkmodCheckTask, group: WALKMOD_GROUP) {
			description = 'Checks'
			classpath 	= configuration
		}

		project.task('walkmodApply', type: WalkmodApplyTask, group: WALKMOD_GROUP) {
			description = 'Applies'
			classpath 	= configuration
		}

		/*
		 TODO: review if needed
		project.task("walkmodInstall", type: WalkmodInstallTask, group: WALKMOD_GROUP) { 
			description = 'Per ara no fa res, veure si te sentit '
			classpath 	= configuration
		}
		*/
	}
}
