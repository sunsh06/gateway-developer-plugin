/*
 * Copyright (c) 2018 CA. All rights reserved.
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package com.ca.apim.gateway.cagatewayexport.tasks.explode.writer;

import com.ca.apim.gateway.cagatewayexport.util.file.DocumentFileUtils;
import com.ca.apim.gateway.cagatewayexport.util.json.JsonTools;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class EntityWriterRegistry {

    private final Collection<EntityWriter> entityLoaders;

    public EntityWriterRegistry(final DocumentFileUtils documentFileUtils, JsonTools jsonTools) {
        final Collection<EntityWriter> loadersCollection = new HashSet<>();
        loadersCollection.add(new PolicyWriter(documentFileUtils));
        loadersCollection.add(new ServiceWriter(documentFileUtils, jsonTools));
        loadersCollection.add(new EncassWriter(documentFileUtils, jsonTools));
        loadersCollection.add(new StaticPropertiesWriter(documentFileUtils));
        loadersCollection.add(new EnvironmentPropertiesWriter(documentFileUtils));
        loadersCollection.add(new PolicyBackedServiceWriter(documentFileUtils, jsonTools));
        loadersCollection.add(new ListenPortWriter(documentFileUtils, jsonTools));
        loadersCollection.add(new IdentityProviderWriter(documentFileUtils, jsonTools));

        this.entityLoaders = Collections.unmodifiableCollection(loadersCollection);
    }

    public Collection<EntityWriter> getEntityWriters() {
        return entityLoaders;
    }
}
