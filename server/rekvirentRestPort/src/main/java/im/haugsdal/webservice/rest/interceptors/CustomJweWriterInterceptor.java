package im.haugsdal.webservice.rest.interceptors;

import java.util.Properties;

import im.haugsdal.webservice.rest.context.CustomSecurityContext;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.rs.security.jose.common.JoseConstants;
import org.apache.cxf.rs.security.jose.jaxrs.JweWriterInterceptor;
import org.apache.cxf.rs.security.jose.jwa.ContentAlgorithm;
import org.apache.cxf.rs.security.jose.jwa.KeyAlgorithm;
import org.apache.cxf.rs.security.jose.jwe.JweEncryptionProvider;
import org.apache.cxf.rs.security.jose.jwe.JweHeaders;
import org.apache.cxf.rs.security.jose.jwe.JweUtils;
import org.apache.cxf.security.SecurityContext;

/***
 * Denne setter krypteringssertifikat = sertifikatet som ble brukt til å signere Jws
 * Algoritmer i bruk settes lik tillatt i innkomne melding
 */
public class CustomJweWriterInterceptor extends JweWriterInterceptor {

    private final Properties properties;

    public CustomJweWriterInterceptor(Properties properties) {
        properties.setProperty(JoseConstants.RSSEC_ENCRYPTION_CONTENT_ALGORITHM,
            properties.getProperty(JoseConstants.RSSEC_ENCRYPTION_CONTENT_ALGORITHM, "A256GCM"));

        properties.setProperty(JoseConstants.RSSEC_ENCRYPTION_KEY_ALGORITHM,
            properties.getProperty(JoseConstants.RSSEC_ENCRYPTION_KEY_ALGORITHM, "RSA-OAEP"));

        this.properties = properties;
    }

    /**
     * Overrider getInitializedEncryptionProvider i JweWriterInterceptor
     * @return JweEncryptionProvider
     */
    @Override
    protected JweEncryptionProvider getInitializedEncryptionProvider(JweHeaders headers) {
        var context = (CustomSecurityContext) PhaseInterceptorChain.getCurrentMessage().getExchange().getInMessage().get(SecurityContext.class);
        return JweUtils.createJweEncryptionProvider(context.getRequestSigningCertificate().getPublicKey(),
            KeyAlgorithm.getAlgorithm(properties.getProperty(JoseConstants.RSSEC_ENCRYPTION_KEY_ALGORITHM)),
            ContentAlgorithm.getAlgorithm(properties.getProperty(JoseConstants.RSSEC_ENCRYPTION_CONTENT_ALGORITHM)));
    }
}
