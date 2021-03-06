/*
 * Copyright (c) 2018 CA. All rights reserved.
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package com.ca.apim.gateway.cagatewayexport.tasks.explode.writer;

import com.ca.apim.gateway.cagatewayconfig.beans.*;
import com.ca.apim.gateway.cagatewayconfig.config.loader.policy.PolicyConverter;
import com.ca.apim.gateway.cagatewayconfig.config.loader.policy.PolicyConverterRegistry;
import com.ca.apim.gateway.cagatewayconfig.util.file.DocumentFileUtils;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Element;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Singleton
public class PolicyWriter implements EntityWriter {
    private final DocumentFileUtils documentFileUtils;
    private PolicyConverterRegistry policyConverterRegistry;

    @Inject
    PolicyWriter(PolicyConverterRegistry policyConverterRegistry, DocumentFileUtils documentFileUtils) {
        this.policyConverterRegistry = policyConverterRegistry;
        this.documentFileUtils = documentFileUtils;
    }

    @Override
    public void write(Bundle bundle, File rootFolder) {
        File policyFolder = new File(rootFolder, "policy");
        documentFileUtils.createFolder(policyFolder.toPath());

        //create folders
        bundle.getFolderTree().stream().forEach(folder -> {
            if (folder.getParentFolder() != null) {
                Path folderFile = policyFolder.toPath().resolve(bundle.getFolderTree().getPath(folder));
                documentFileUtils.createFolder(folderFile);
            }
        });

        //create policies
        Map<String, Service> services = bundle.getEntities(Service.class);
        services.values().parallelStream().forEach(serviceEntity -> writePolicy(bundle, policyFolder, serviceEntity.getParentFolder().getId(), serviceEntity.getName(), serviceEntity.getPolicyXML()));

        Stream.of(
                bundle.getEntities(Policy.class).values().stream(),
                bundle.getEntities(GlobalPolicy.class).values().stream().map(Policy.class::cast).collect(toList()).stream(),
                bundle.getEntities(AuditPolicy.class).values().stream().map(Policy.class::cast).collect(toList()).stream()
        ).flatMap(s -> s)
                .forEach(policyEntity -> writePolicy(bundle, policyFolder, policyEntity.getParentFolder().getId(), policyEntity.getName(), policyEntity.getPolicyDocument()));
    }

    private void writePolicy(Bundle bundle, File policyFolder, String folderId, String name, Element policy) {
        Folder folder = bundle.getFolderTree().getFolderById(folderId);
        Path folderPath = policyFolder.toPath().resolve(bundle.getFolderTree().getPath(folder));
        documentFileUtils.createFolders(folderPath);

        PolicyConverter policyConverter = policyConverterRegistry.getFromPolicyElement(name, policy);
        Path policyPath = folderPath.resolve(name + policyConverter.getPolicyTypeExtension());
        try (InputStream policyStream = policyConverter.convertFromPolicyElement(policy)) {
            FileUtils.copyInputStreamToFile(policyStream, policyPath.toFile());
        } catch (IOException e) {
            throw new WriteException("Unable to write assertion js policy", e);
        }
    }
}
