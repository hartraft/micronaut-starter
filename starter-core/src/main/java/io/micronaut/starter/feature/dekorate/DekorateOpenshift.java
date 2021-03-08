/*
 * Copyright 2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.starter.feature.dekorate;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.starter.application.generator.GeneratorContext;
import io.micronaut.starter.build.dependencies.Dependency;
import io.micronaut.starter.feature.other.Management;

import javax.inject.Singleton;

/**
 * Adds Dekorate Openshift support.
 *
 * @author Pavol Gressa
 * @since 2.1
 */
@Singleton
public class DekorateOpenshift extends AbstractDekoratePlatformFeature {

    public DekorateOpenshift(Management management) {
        super(management);
    }

    @NonNull
    @Override
    public String getName() {
        return "dekorate-openshift";
    }

    @Override
    public String getTitle() {
        return "Dekorate Openshift Support";
    }

    @Override
    public String getDescription() {
        return "Generates OpenShift deployment manifest using Dekorate OpenShift Support.";
    }

    @Nullable
    @Override
    public String getThirdPartyDocumentation() {
        return "https://github.com/dekorateio/dekorate#kubernetes";
    }

    @Override
    public void apply(GeneratorContext generatorContext) {
        Dependency.Builder openshift = Dependency.builder()
                .groupId("io.dekorate")
                .artifactId("openshift-annotations")
                .template();

        generatorContext.addDependency(openshift.version("${dekorate.version}").annotationProcessor());
        generatorContext.addDependency(openshift.compile());
    }
}