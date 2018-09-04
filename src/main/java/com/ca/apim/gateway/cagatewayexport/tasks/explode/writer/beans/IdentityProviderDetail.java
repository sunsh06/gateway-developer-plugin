/*
 * Copyright (c) 2018 CA. All rights reserved.
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */

package com.ca.apim.gateway.cagatewayexport.tasks.explode.writer.beans;

import java.util.List;

public abstract class IdentityProviderDetail {

    private List<String> serverUrls;
    private boolean useSslClientAuthentication;

    public List<String> getServerUrls() {
        return serverUrls;
    }

    public void setServerUrls(List<String> serverUrls) {
        this.serverUrls = serverUrls;
    }

    public boolean isUseSslClientAuthentication() {
        return useSslClientAuthentication;
    }

    public void setUseSslClientAuthentication(boolean useSslClientAuthentication) {
        this.useSslClientAuthentication = useSslClientAuthentication;
    }
}
