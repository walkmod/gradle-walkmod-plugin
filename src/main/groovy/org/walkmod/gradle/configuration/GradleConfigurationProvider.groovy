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
            configuration.getParameters().put("classLoader", loader);
        }
    }
}
