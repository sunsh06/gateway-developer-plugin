/*
 * Copyright (c) 2018 CA. All rights reserved.
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package com.ca.apim.gateway.cagatewayconfig.util.properties;

/**
 * Constants related to properties.
 */
@SuppressWarnings("squid:S2068") // sonarcloud believes this is a hardcoded password
public class PropertyConstants {

    public static final String PREFIX_GATEWAY = "gateway.";
    public static final String PREFIX_ENV = "ENV.";
    public static final String PREFIX_PROPERTY = "property.";

    //Trusted Cert property constants
    public static final String VERIFY_HOSTNAME = "verifyHostname";
    public static final String TRUSTED_FOR_SSL = "trustedForSsl";
    public static final String TRUSTED_AS_SAML_ATTESTING_ENTITY = "trustedAsSamlAttestingEntity";
    public static final String TRUST_ANCHOR = "trustAnchor";
    public static final String REVOCATION_CHECKING_ENABLED = "revocationCheckingEnabled";
    public static final String TRUSTING_SIGNING_CLIENT_CERTS = "trustedForSigningClientCerts";
    public static final String TRUSTED_SIGNING_SERVER_CERTS = "trustedForSigningServerCerts";
    public static final String TRUSTED_AS_SAML_ISSUER = "trustedAsSamlIssuer";

    // Property names
    public static final String PROPERTY_USER = "user";
    public static final String PROPERTY_USERNAME = "username";
    public static final String PROPERTY_PASSWORD = "password";
    public static final String PROPERTY_MIN_POOL_SIZE = "minimumPoolSize";
    public static final String PROPERTY_MAX_POOL_SIZE = "maximumPoolSize";
    public static final String PROPERTY_TAG = "tag";
    public static final String PROPERTY_SUBTAG = "subtag";
    
    // JMS Destination property constants
    // 1.0 JMS Destination Detail property constants
    // username
    // password
    public static final String DESTINATION_TYPE = "type"; // Topic or Queue
    public static final String REPLY_TYPE = "replyType";
    public static final String REPLY_QUEUE_NAME = "replyToQueueName";
    public static final String USE_REQUEST_CORRELATION_ID = "useRequestCorrelationId";
    public static final String INBOUND_ACK_TYPE = "inbound.acknowledgementType";
    public static final String INBOUND_FAILURE_QUEUE_NAME = "inbound.failureQueueName";
    public static final String OUTBOUND_MESSAGE_TYPE = "outbound.MessageType";
    public static final String INBOUND_MAX_SIZE = "inbound.maximumSize";
    
    // 2.0 JMS Connection property constants
    public static final String JNDI_INITIAL_CONTEXT_FACTORY_CLASSNAME = "jndi.initialContextFactoryClassname";
    public static final String JNDI_PROVIDER_URL = "jndi.providerUrl";
    // username
    // password
    public static final String QUEUE_CONNECTION_FACTORY_NAME = "queue.connectionFactoryName";
    public static final String CONNECTION_FACTORY_NAME = "connectionFactoryName";
    public static final String TOPIC_CONNECTION_FACTORY_NAME = "topic.connectionFactoryName";

    // 3.0 JMS Context Properties Template property
    // From 
    
    private PropertyConstants() { }
}
