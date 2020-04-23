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
package io.micronaut.starter.feature.function.azure;

import com.fizzed.rocker.RockerModel;
import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.starter.application.Project;
import io.micronaut.starter.feature.Feature;
import io.micronaut.starter.feature.function.azure.template.azureFunctionGroovyJunit;
import io.micronaut.starter.feature.function.azure.template.azureFunctionJavaJunit;
import io.micronaut.starter.feature.function.azure.template.azureFunctionTriggerKotlin;

import javax.inject.Singleton;

@Singleton
public class AzureHttpFunction extends AbstractAzureFunction implements Feature {

    @NonNull
    @Override
    public String getName() {
        return "azure-function-http";
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    protected RockerModel javaJUnitTemplate(Project project) {
        return azureFunctionJavaJunit.template(project);
    }

    @Override
    protected RockerModel kotlinJUnitTemplate(Project project) {
        return azureFunctionTriggerKotlin.template(project);
    }

    @Override
    protected RockerModel groovyJUnitTemplate(Project project) {
        return azureFunctionGroovyJunit.template(project);
    }

    @Override
    protected RockerModel kotlinTestTemplate(Project project) {
        return azureFunctionTriggerKotlin.template(project);
    }

    @Override
    public RockerModel spockTemplate(Project project) {
        return azureFunctionTriggerKotlin.template(project);
    }
}