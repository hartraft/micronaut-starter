/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.starter.feature.function.oraclefunction;

import com.fizzed.rocker.RockerModel;
import io.micronaut.context.annotation.Primary;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.starter.application.ApplicationType;
import io.micronaut.starter.application.Project;
import io.micronaut.starter.application.generator.GeneratorContext;
import io.micronaut.starter.feature.FeatureContext;
import io.micronaut.starter.feature.function.AbstractFunctionFeature;
import io.micronaut.starter.feature.function.Cloud;
import io.micronaut.starter.feature.function.CloudFeature;
import io.micronaut.starter.feature.function.oraclefunction.template.projectFnFunc;
import io.micronaut.starter.feature.logging.Logback;
import io.micronaut.starter.feature.logging.SimpleLogging;
import io.micronaut.starter.feature.server.ServerFeature;
import io.micronaut.starter.feature.server.template.groovyJunit;
import io.micronaut.starter.feature.server.template.javaJunit;
import io.micronaut.starter.feature.server.template.koTest;
import io.micronaut.starter.feature.server.template.kotlinJunit;
import io.micronaut.starter.feature.server.template.spock;
import io.micronaut.starter.options.BuildTool;
import io.micronaut.starter.template.RockerTemplate;

import jakarta.inject.Singleton;

@Singleton
@Primary
public class OracleFunction extends AbstractFunctionFeature implements CloudFeature {

    private final SimpleLogging simpleLogging;

    public OracleFunction(SimpleLogging simpleLogging) {
        this.simpleLogging = simpleLogging;
    }

    @Override
    public void processSelectedFeatures(FeatureContext featureContext) {
        if (!featureContext.isPresent(SimpleLogging.class)) {
            featureContext.addFeature(simpleLogging);
            featureContext.exclude(feature -> feature instanceof Logback);
        }

        if (featureContext.isPresent(ServerFeature.class)) {
            featureContext.exclude(feature -> feature instanceof ServerFeature);
        }
    }

    @Override
    public void apply(GeneratorContext generatorContext) {
        generatorContext.addTemplate(
                "func.yml", new RockerTemplate(
                        "func.yml",
                        projectFnFunc.template(generatorContext.getProject()
                ))
        );

        applyFunction(generatorContext, generatorContext.getApplicationType());
    }

    @Override
    public String getName() {
        return "oracle-function-http";
    }

    @Override
    public String getTitle() {
        return "Oracle Function Support";
    }

    @Override
    public String getDescription() {
        return "Adds support for Oracle Functions.";
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    protected String getRunCommand(BuildTool buildTool) {
        if (buildTool == BuildTool.MAVEN) {
            return "mvnw mn:run";
        } else {
            return "gradlew run";
        }
    }

    @Override
    protected String getBuildCommand(BuildTool buildTool) {
        if (buildTool == BuildTool.MAVEN) {
            return "mvnw clean package";
        } else if (buildTool.isGradle()) {
            return "gradlew clean assemble";
        } else {
            throw new IllegalStateException("Unsupported build tool");
        }
    }

    @Override
    protected String getTestSuffix(ApplicationType type) {
        if (type == ApplicationType.FUNCTION) {
            return "Function";
        }
        return "Controller";
    }

    @Override
    protected RockerModel javaJUnitTemplate(Project project) {
        return javaJunit.template(project);
    }

    @Override
    protected RockerModel kotlinJUnitTemplate(Project project) {
        return kotlinJunit.template(project);
    }

    @Override
    protected RockerModel groovyJUnitTemplate(Project project) {
        return groovyJunit.template(project);
    }

    @Override
    protected RockerModel koTestTemplate(Project project) {
        return koTest.template(project);
    }

    @Override
    public RockerModel spockTemplate(Project project) {
        return spock.template(project);
    }

    @Override
    public Cloud getCloud() {
        return Cloud.ORACLE;
    }

    @Nullable
    @Override
    public String getMicronautDocumentation() {
        return "https://micronaut-projects.github.io/micronaut-oracle-cloud/latest/guide/#httpFunctions";
    }
}
