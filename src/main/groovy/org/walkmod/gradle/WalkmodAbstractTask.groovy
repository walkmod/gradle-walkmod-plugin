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

import org.gradle.api.DefaultTask
import org.gradle.api.artifacts.Configuration
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.walkmod.OptionsBuilder
import org.walkmod.WalkModFacade

/**
 * Base abstract Walkmod task
 */
abstract class WalkmodAbstractTask extends DefaultTask {

    @Optional
    protected List<String> chains

    @Optional
    protected Boolean offline = false

    @Optional
    protected Boolean verbose = false

    @Optional
    protected Boolean printErrors = false

    @Optional
    protected String properties

    protected Map<String, Object> dynamicParams

    /**
     * Delegated walkmod service
     */
    WalkmodProxy walkmodProxy
    final WalkmodExtension extension

    protected WalkmodAbstractTask() {
        if (!extension) {
            extension = project.extensions.getByName(WalkmodPlugin.EXTENSION)
        }
    }

    @TaskAction
    void executeTask() {

        initWalkmod()

        executeTask(getChains())

        if (!walkmodProxy) {
            initWalkmod()
        }
    }

    abstract void executeTask(String... chains)

    void buildDynamicParams() {

        if (dynamicParams == null) {
            dynamicParams = new HashMap<String, Object>()
        }

        if (getProperties() != null) {
            String[] parts = getProperties().split('\\=| ')
            int two = 2
            if (parts.length % two == 0) {

                for (int i = 0; i < parts.length - 1; i += two) {
                    System.out.println(parts[i].trim()+ " = "+ parts[i + 1].trim())
                    dynamicParams.put(parts[i].trim(), parts[i + 1].trim())
                }
            }
        }

        dynamicParams.put("classLoader",
                new URLClassLoader(
                        (project.files(project.sourceSets.test.runtimeClasspath).getFiles())
                                .collect { file -> file.toURL() } as URL[]))
    }

    void initWalkmod() {
        if (!walkmodProxy) {

            String configName = 'walkmod'

            buildDynamicParams()

            OptionsBuilder options = OptionsBuilder.options()
                    .offline(getOffline())
                    .verbose(getVerbose())
                    .printErrors(isShowErrors())
                    .dynamicArgs(dynamicParams)

            project.configurations.getByName(configName).artifacts.each {
                println "$it"
            }

            walkmodProxy = new WalkmodProxyImpl(delegate: new WalkModFacade(options))
        }
    }

    String[] getChains() {
        List<String> aux = chains ?: extension.chains
        if (aux != null) {
            String[] res = new String[aux.size()];
            aux.toArray(res)
            return res
        }
        return null
    }

    Object getOffline() {
        extension.offline ?: offline
    }

    Object getVerbose() {
        extension.verbose ?: verbose
    }

    boolean isShowErrors() {
        extension.printErrors ?: printErrors
    }

    String getProperties(){
        extension.properties?: properties
    }

    Map<String, Object> getDynamicParams() {
        dynamicParams
    }
}
