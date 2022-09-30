package im.haugsdal.webservice.rest.context;

import java.security.Principal;
import java.security.cert.X509Certificate;

import org.apache.cxf.security.SecurityContext;

/***
 * Implementerer Security context interface for å få med Jws signeringssertifikat.
 */
public class CustomSecurityContext implements SecurityContext {
    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    public X509Certificate getRequestSigningCertificate() {
        return null;
    }

}
