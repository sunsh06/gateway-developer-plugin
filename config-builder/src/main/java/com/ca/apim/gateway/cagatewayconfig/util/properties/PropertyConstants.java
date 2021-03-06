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
    public static final String PROPERTY_PASSWORD = "password";
    public static final String PROPERTY_MIN_POOL_SIZE = "minimumPoolSize";
    public static final String PROPERTY_MAX_POOL_SIZE = "maximumPoolSize";
    public static final String PROPERTY_TAG = "tag";
    public static final String PROPERTY_SUBTAG = "subtag";

    public static final String KEY_VALUE_SOAP = "soap";
    public static final String KEY_VALUE_SOAP_VERSION = "soapVersion";
    public static final String KEY_VALUE_WSS_PROCESSING_ENABLED = "wssProcessingEnabled";
    public static final String TAG_VALUE_POLICY = "policy";
    public static final String TAG_VALUE_WSDL = "wsdl";

    private PropertyConstants() { }
}
