/*
 * Copyright (c) 2018 CA. All rights reserved.
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package com.ca.apim.gateway.cagatewayexport.tasks.explode.linker;

import com.ca.apim.gateway.cagatewayconfig.beans.*;
import com.ca.apim.gateway.cagatewayconfig.beans.EnvironmentProperty.Type;
import com.ca.apim.gateway.cagatewayexport.util.TestUtils;
import com.ca.apim.gateway.cagatewayconfig.util.xml.DocumentTools;
import com.ca.apim.gateway.cagatewayexport.tasks.explode.writer.WriteException;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;

import static com.ca.apim.gateway.cagatewayconfig.util.gateway.BundleElementNames.SERVICE_DETAIL;
import static org.junit.jupiter.api.Assertions.*;

class ServiceLinkerTest {

    private ServiceLinker linker = new ServiceLinker(DocumentTools.INSTANCE);

    @Test
    void linkInvalidPolicy() {
        Service service = new Service();
        service.setPolicy("policy");
        assertThrows(WriteException.class, () -> linker.link(service, null, null));
    }

    @Test
    void linkNoServiceProperties() {
        Bundle bundle = new Bundle();
        link(bundle, false);
        assertTrue(bundle.getEnvironmentProperties().isEmpty());
    }

    @Test
    void linkWithServiceProperties() {
        Bundle bundle = new Bundle();
        link(bundle, true);
        assertFalse(bundle.getEnvironmentProperties().isEmpty());
        assertEquals(1, bundle.getEnvironmentProperties().size());
        EnvironmentProperty property = bundle.getEnvironmentProperties().get("SERVICE:prop");
        assertNotNull(property);
        assertEquals("value2", property.getValue());
        assertEquals("service.property.prop", property.getKey());
        assertEquals(Type.SERVICE, property.getType());
    }

    private void link(Bundle bundle, boolean serviceProperties) {
        Folder folder = new Folder();
        folder.setName("folder");
        folder.setId("folder");
        folder.setParentFolder(Folder.ROOT_FOLDER);
        bundle.getFolders().put("folder", folder);
        bundle.getFolders().put(Folder.ROOT_FOLDER_ID, Folder.ROOT_FOLDER);
        FolderTree tree = new FolderTree(bundle.getFolders().values());
        bundle.setFolderTree(tree);

        Service service = new Service();
        service.setPolicy("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<wsp:Policy xmlns:L7p=\"http://www.layer7tech.com/ws/policy\" xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2002/12/policy\">\n" +
                "    <wsp:All wsp:Usage=\"Required\">\n" +
                "        <L7p:CommentAssertion>\n" +
                "            <L7p:Comment stringValue=\"Policy Fragment: includedPolicy\"/>\n" +
                "        </L7p:CommentAssertion>\n" +
                "    </wsp:All>\n" +
                "</wsp:Policy>");
        service.setParentFolder(folder);
        service.setName("service");
        final Element serviceXml = TestUtils.createServiceXml(DocumentTools.INSTANCE.getDocumentBuilder().newDocument(), serviceProperties);
        service.setServiceDetailsElement((Element) serviceXml.getElementsByTagName(SERVICE_DETAIL).item(0));

        linker.link(service, bundle, bundle);

        assertNotNull(service.getPath());
        assertEquals("folder/service", service.getPath());
        assertNotNull(service.getPolicyXML());
    }

}