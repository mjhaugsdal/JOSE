/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package im.haugsdal.webservice.rest.filters;

import java.security.Principal;
import java.security.cert.X509Certificate;
import javax.ws.rs.container.ContainerRequestFilter;

import im.haugsdal.webservice.rest.context.CustomSecurityContext;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.rs.security.jose.jaxrs.JwsContainerRequestFilter;
import org.apache.cxf.rs.security.jose.jws.JwsSignatureVerifier;
import org.apache.cxf.rs.security.jose.jws.PublicKeyJwsSignatureVerifier;

/***
 * Custom filter for å override configureSecurityContext
 */
public class CustomJwsContainerRequestFilter extends JwsContainerRequestFilter implements ContainerRequestFilter {

    /***
     * Overrider denne for å få med sertifikatet som ble brukt til å signere Jws
     * @param sigVerifier objektet som ble benyttet til å validere signaturen (Som verifiserte OK)
     * @return CustomSecurityContext
     */
    @Override
    protected CustomSecurityContext configureSecurityContext(JwsSignatureVerifier sigVerifier) {
        if (sigVerifier instanceof PublicKeyJwsSignatureVerifier publicKeyJwsSignatureVerifier
            && (publicKeyJwsSignatureVerifier).getX509Certificate() != null) {
            final Principal principal =
                (publicKeyJwsSignatureVerifier).getX509Certificate().getSubjectX500Principal();
            return new CustomSecurityContext() {

                @Override
                public Principal getUserPrincipal() {
                    return principal;
                }

                @Override
                public boolean isUserInRole(String arg0) {
                    return false;
                }

                @Override
                public X509Certificate getRequestSigningCertificate() {
                    return (publicKeyJwsSignatureVerifier).getX509Certificate();
                }
            };
        }
        return null;
    }
}
