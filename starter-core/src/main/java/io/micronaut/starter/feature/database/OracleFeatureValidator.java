/*
 * Copyright 2021 original authors
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
package io.micronaut.starter.feature.database;

import io.micronaut.starter.application.ApplicationType;
import io.micronaut.starter.feature.Feature;
import io.micronaut.starter.feature.database.r2dbc.R2dbcFeature;
import io.micronaut.starter.feature.validation.FeatureValidator;
import io.micronaut.starter.options.Options;

import javax.inject.Singleton;
import java.util.Set;

@Singleton
public class OracleFeatureValidator implements FeatureValidator {
    @Override
    public void validatePreProcessing(Options options, ApplicationType applicationType, Set<Feature> features) {
        if (features.stream().anyMatch(f -> f instanceof Oracle)) {
            if (features.stream().anyMatch(f -> f instanceof R2dbcFeature)) {
                throw new IllegalArgumentException("R2DBC is not yet supported by Oracle");
            }
        }
    }

    @Override
    public void validatePostProcessing(Options options, ApplicationType applicationType, Set<Feature> features) {

    }
}