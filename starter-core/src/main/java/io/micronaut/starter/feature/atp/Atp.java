/*
 * Copyright 2017-2021 original authors
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
package io.micronaut.starter.feature.atp;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.starter.application.ApplicationType;
import io.micronaut.starter.application.generator.GeneratorContext;
import io.micronaut.starter.build.dependencies.Dependency;
import io.micronaut.starter.feature.Category;
import io.micronaut.starter.feature.Feature;
import io.micronaut.starter.feature.FeatureContext;
import io.micronaut.starter.feature.FeaturePhase;
import io.micronaut.starter.feature.config.ApplicationConfiguration;
import io.micronaut.starter.feature.database.jdbc.JdbcFeature;
import io.micronaut.starter.feature.oraclecloud.OracleCloudSdk;
import jakarta.inject.Singleton;

@Singleton
public class Atp implements Feature {
    private final OracleCloudSdk oracleCloudSdkFeature;

    public Atp(OracleCloudSdk oracleCloudSdkFeature) {
        this.oracleCloudSdkFeature = oracleCloudSdkFeature;
    }

    @NonNull
    @Override
    public String getName() {
        return "oracle-cloud-atp";
    }

    @Override
    public String getTitle() {
        return "Oracle Cloud ATP";
    }

    @Override
    public String getDescription() {
        return "Provides integration with Oracle Cloud Autonomous Database";
    }

    @Override
    public String getMicronautDocumentation() {
        return "https://micronaut-projects.github.io/micronaut-oracle-cloud/latest/guide/#_micronaut_oraclecloud_atp";
    }

    @Override
    public String getThirdPartyDocumentation() {
        return "https://www.oracle.com/autonomous-database/autonomous-transaction-processing/";
    }

    @Override
    public boolean supports(ApplicationType applicationType) {
        return true;
    }

    @Override
    public int getOrder() {
        // need to run after the jdbc feature
        return FeaturePhase.DEFAULT.getOrder();
    }

    @Override
    public String getCategory() {
        return Category.CLOUD;
    }

    @Override
    public void processSelectedFeatures(FeatureContext featureContext) {
        if (!featureContext.isPresent(OracleCloudSdk.class)) {
            featureContext.addFeature(oracleCloudSdkFeature);
        }
    }

    @Override
    public void apply(GeneratorContext generatorContext) {
        generatorContext.addDependency(Dependency.builder()
                .compile()
                .groupId("io.micronaut.oraclecloud").artifactId("micronaut-oraclecloud-atp")
                .build());

        ApplicationConfiguration cfg = generatorContext.getConfiguration();

        // remove old jdbc config
        generatorContext.getFeature(JdbcFeature.class).ifPresent(jdbc -> {
            cfg.remove(jdbc.getDriverKey());
            cfg.remove(jdbc.getUrlKey());
            cfg.remove(jdbc.getUsernameKey());
            cfg.remove(jdbc.getPasswordKey());
        });

        cfg.put("datasources.default.ocid", "");
        cfg.put("datasources.default.walletPassword", "");
        cfg.put("datasources.default.username", "");
        cfg.put("datasources.default.password", "");
    }
}
