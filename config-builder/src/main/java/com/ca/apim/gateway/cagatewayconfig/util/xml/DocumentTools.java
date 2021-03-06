/*
 * Copyright (c) 2018 CA. All rights reserved.
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package com.ca.apim.gateway.cagatewayconfig.util.xml;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Tools used to parse and process XML documents
 */
public class DocumentTools {
    public static final DocumentTools INSTANCE = new DocumentTools();

    private final DocumentBuilder builder;
    private final XPathFactory xPathFactory;
    private final TransformerFactory transformerFactory;

    public DocumentTools() {
        final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            builder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new DocumentToolsException("Unexpected exception creating DocumentBuilder", e);
        }

        xPathFactory = XPathFactory.newInstance();

        try {
            transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            transformerFactory.setAttribute("indent-number", 4);
        } catch (TransformerConfigurationException e) {
            throw new DocumentToolsException("Unexpected exception creating TransformerFactory", e);
        }
    }

    public Transformer getTransformer() {
        try {
            return configureTransformer(transformerFactory.newTransformer());
        } catch (TransformerConfigurationException e) {
            throw new DocumentToolsException("Exception loading stylesheet.", e);
        }
    }

    public Transformer getTransformer(final StreamSource stylesheet) {
        try {
            return configureTransformer(transformerFactory.newTransformer(stylesheet));
        } catch (TransformerConfigurationException e) {
            throw new DocumentToolsException("Exception loading stylesheet.", e);
        }
    }

    private Transformer configureTransformer(final Transformer transformer) {
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        return transformer;
    }

    public DocumentBuilder getDocumentBuilder() {
        return builder;
    }

    public Document parse(final File file) throws DocumentParseException {
        try {
            return parse(FileUtils.openInputStream(file));
        } catch (IOException e) {
            throw new DocumentParseException("Exception reading file: " + file, e);
        }
    }

    public Document parse(String string) throws DocumentParseException {
        try {
            return parse(IOUtils.toInputStream(string, "UTF-8"));
        } catch (IOException e) {
            throw new DocumentParseException("Exception creating stream from a String", e);
        }
    }

    /**
     * Parses an input stream into a document object
     *
     * @param inputStream The input stream to parse into a document
     * @return The parsed document
     * @throws DocumentParseException Thrown if there is an exception while parsing the document
     */
    private synchronized Document parse(final InputStream inputStream) throws DocumentParseException {
        try {
            return builder.parse(inputStream);
        } catch (SAXException | IOException e) {
            throw new DocumentParseException("Exception parsing document from input stream", e);
        }
    }

    /**
     * Returns a new xPath that can be used to query a document
     *
     * @return an xPath that can be used to query a document
     */
    private XPath newXPath() {
        return xPathFactory.newXPath();
    }

    public void cleanup(final Document bundleDocument) {
        bundleDocument.normalize();
        final XPath xPath = newXPath();
        final NodeList nodeList;
        try {
            nodeList = (NodeList) xPath.evaluate("//text()[normalize-space()='']",
                    bundleDocument,
                    XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new DocumentToolsException("Unexpected error evaluating xpath.", e);
        }

        for (int i = 0; i < nodeList.getLength(); ++i) {
            final Node node = nodeList.item(i);
            node.getParentNode().removeChild(node);
        }
    }

}
