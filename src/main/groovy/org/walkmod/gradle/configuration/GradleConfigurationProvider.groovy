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
package org.walkmod.gradle.configuration

import org.apache.tools.ant.taskdefs.Classloader
import org.gradle.api.artifacts.PublishArtifactSet
import org.walkmod.conf.ConfigurationException
import org.walkmod.conf.ConfigurationProvider
import org.walkmod.conf.entities.Configuration

/**
 * Created by abelsromero on 21/02/16.
 */
class GradleConfigurationProvider implements ConfigurationProvider{

    Configuration configuration

    Classloader defaultClassloader
    PublishArtifactSet artifacts


    @Override
    void init(Configuration configuration) {
        this.configuration = configuration
    }

    @Override
    void load() throws ConfigurationException {
        if (configuration && artifacts) {
            def urls = artifacts.files.collect { it.toURI().toURL() }
            ClassLoader loader = new URLClassLoader(urls as URL[], Thread.currentThread().contextClassLoader)
            configuration.parameters.put('classLoader', loader)
        }
    }
}
