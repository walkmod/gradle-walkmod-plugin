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


/**
 * @author abelsromero
 */
class WalkmodExtension {

    List<String> chains = null

    String properties = null;

    Boolean offline = Boolean.FALSE

    Boolean verbose = Boolean.FALSE

    Boolean printErrors = Boolean.TRUE

    Boolean isPatchPerFile = Boolean.TRUE

    Boolean isPatchPerChange = Boolean.FALSE

    String patchFormat = "raw";

    Project project

    WalkmodExtension(Project project) {
        this.project = project
    }
}
