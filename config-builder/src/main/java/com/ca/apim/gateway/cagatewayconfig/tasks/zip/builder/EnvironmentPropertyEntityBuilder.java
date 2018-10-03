/*
 * Copyright (c) 2018 CA. All rights reserved.
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package com.ca.apim.gateway.cagatewayconfig.tasks.zip.builder;

import com.ca.apim.gateway.cagatewayconfig.tasks.zip.beans.Bundle;
import com.ca.apim.gateway.cagatewayconfig.util.IdGenerator;
import org.w3c.dom.Document;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.ca.apim.gateway.cagatewayconfig.tasks.zip.builder.ClusterPropertyEntityBuilder.buildClusterPropertyElement;
import static com.ca.apim.gateway.cagatewayconfig.util.entity.EntityTypes.CLUSTER_PROPERTY_TYPE;
import static com.ca.apim.gateway.cagatewayconfig.util.gateway.MappingProperties.MAP_BY;
import static com.ca.apim.gateway.cagatewayconfig.util.gateway.MappingProperties.NAME;
import static com.ca.apim.gateway.cagatewayconfig.util.properties.PropertyConstants.PREFIX_ENV;
import static com.ca.apim.gateway.cagatewayconfig.util.properties.PropertyConstants.PREFIX_GATEWAY;

@Singleton
public class EnvironmentPropertyEntityBuilder {

    private static final String ENV = "env";

    private final IdGenerator idGenerator;

    @Inject
    EnvironmentPropertyEntityBuilder(final IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public List<Entity> build(Bundle bundle, Document document) {
        return bundle.getEnvironmentProperties().entrySet().stream()
                .filter(propertyEntry -> propertyEntry.getKey().startsWith(PREFIX_GATEWAY))
                .map(propertyEntry ->
                        buildEnvironmentPropertyEntity(propertyEntry.getKey().substring(8), document)
                ).collect(Collectors.toList());
    }

    private Entity buildEnvironmentPropertyEntity(String name, Document document) {
        String id = idGenerator.generate();
        HashMap<String, String> valueAttributes = new HashMap<>();
        valueAttributes.put(ENV, Boolean.TRUE.toString());
        Entity entity = new Entity(CLUSTER_PROPERTY_TYPE, PREFIX_ENV + name, id, buildClusterPropertyElement(PREFIX_ENV + name, id, PREFIX_ENV + PREFIX_GATEWAY + name, document, valueAttributes));
        entity.setMappingProperty(MAP_BY, NAME);
        return entity;
    }
}
