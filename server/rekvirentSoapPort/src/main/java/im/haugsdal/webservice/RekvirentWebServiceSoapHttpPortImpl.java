package im.haugsdal.webservice;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import im.haugsdal.M1Service;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.common.ConfigurationConstants;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoFactory;
import org.apache.wss4j.common.ext.WSSecurityException;
import types.AppRec;
import types.AppRecFault;
import types.M1;
import types.M24_1;
import types.M24_2;
import types.M25_1;
import types.M27_1;
import types.M27_2;
import types.M4_1;
import types.M4_2;
import types.M5;
import types.M95Kj;
import types.M96Kj;
import types.M9_11;
import types.M9_12;
import types.M9_21;
import types.M9_22;
import types.M9_5;
import types.M9_6;
import types.M9_7;
import types.M9_8;
import types.MV;

public class RekvirentWebServiceSoapHttpPortImpl implements RekvirentWebServiceSoapHttpPort {

    

    static M1Service m1Service;

    static {
        var cryptoProperties = new Properties();
        org.apache.xml.security.Init.init();

        cryptoProperties.put(
            "org.apache.ws.security.crypto.provider",
            "org.apache.ws.security.components.crypto.Merlin"
        );

        cryptoProperties.put("org.apache.ws.security.crypto.merlin.keystore.type", "jks");
        cryptoProperties.put("org.apache.ws.security.crypto.merlin.keystore.password", "changeit");
        cryptoProperties.put("org.apache.ws.security.crypto.merlin.keystore.alias", "server");
        cryptoProperties.put("org.apache.ws.security.crypto.merlin.keystore.file", "wsse/server/server-keystore.jks");

        Crypto crypto = null;
        try {
            crypto = CryptoFactory.getInstance(cryptoProperties);
        } catch (WSSecurityException e) {
            e.printStackTrace();
        }

        Map<String, String> pw = new HashMap<>();
        pw.put("server", "changeit");
        pw.put("client", "");

        Map<String, Object> inProps = new HashMap<>();
        inProps.put("crypto", crypto);
        inProps.put(
            ConfigurationConstants.ACTION,
            ConfigurationConstants.SIGNATURE + " " + ConfigurationConstants.ENCRYPTION
        );
        inProps.put(ConfigurationConstants.PW_CALLBACK_REF, new KeystorePasswordCallback(pw));

        inProps.put(ConfigurationConstants.SIG_PROP_REF_ID, "crypto");
        inProps.put(ConfigurationConstants.DEC_PROP_REF_ID, "crypto");
        inProps.put(
            ConfigurationConstants.SIG_SUBJECT_CERT_CONSTRAINTS,
            "(.)*"
        ); //This disables warning about lack of subject constraints.
        inProps.put(ConfigurationConstants.ALLOW_RSA15_KEY_TRANSPORT_ALGORITHM, "true");  // For old systems

        Map<String, Object> outProps = new HashMap<>();
        outProps.put(ConfigurationConstants.PW_CALLBACK_REF, new KeystorePasswordCallback(pw));
        outProps.put("crypto", crypto);

        outProps.put(
            ConfigurationConstants.ACTION,
            ConfigurationConstants.SIGNATURE + " " + ConfigurationConstants.ENCRYPTION
        );
        outProps.put(ConfigurationConstants.ENCRYPTION_USER, ConfigurationConstants.USE_REQ_SIG_CERT);
        outProps.put(ConfigurationConstants.SIG_PROP_REF_ID, "crypto");
        outProps.put(ConfigurationConstants.INCLUDE_SIGNATURE_TOKEN, "true");
        outProps.put(ConfigurationConstants.SIG_KEY_ID, "DirectReference");
        outProps.put(ConfigurationConstants.ENC_KEY_ID, "SKIKeyIdentifier");
        outProps.put(ConfigurationConstants.SIGNATURE_USER, "server");

        var wss4JInInterceptor = new WSS4JInInterceptor(inProps);
        var wss4JOutInterceptor = new WSS4JOutInterceptor(outProps);


        var serverFactoryBean = new JaxWsServerFactoryBean();
        serverFactoryBean.setServiceClass(RekvirentWebServiceSoapHttpPortImpl.class);
        serverFactoryBean.setAddress("http://localhost:8888/soap");

        var loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);
        serverFactoryBean.getFeatures().add(loggingFeature);


        serverFactoryBean.getInInterceptors().add(wss4JInInterceptor);
        serverFactoryBean.getOutInterceptors().add(wss4JOutInterceptor);
        serverFactoryBean.create();

        //M1 Service
        var clientProxyFactoryBean = new JaxWsProxyFactoryBean();
        clientProxyFactoryBean.setAddress("http://localhost:8889/soap/m1");
        clientProxyFactoryBean.setServiceClass(M1Service.class);
        m1Service = (M1Service) clientProxyFactoryBean.create();

    }

    @Override
    public AppRec m1(M1 m1) {
        return m1Service.m1(m1);
    }

    @Override
    public AppRec m5(M5 m5) {
        return null;
    }

    @Override
    public M9_6 m9_5(M9_5 m9_5) {
        return null;
    }

    @Override
    public M96Kj M95kj(M95Kj m95Kj) throws AppRecFault {
        return null;
    }

    @Override
    public M4_2 m4_1(M4_1 m4_1) {
        return null;
    }

    @Override
    public M24_2 m24_1(M24_1 m24_1) {
        return null;
    }

    @Override
    public AppRec m25_1(M25_1 m25_1) {
        return null;
    }

    @Override
    public M27_2 m27_1(M27_1 m271) throws AppRecFault {
        return null;
    }

    @Override
    public M9_12 m9_11(M9_11 m911) throws AppRecFault {
        return null;
    }

    @Override
    public M9_8 m9_7(M9_7 m9_7) throws AppRecFault {
        return null;
    }

    @Override
    public AppRec verify(MV mv) {
        return null;
    }

    @Override
    public M9_22 m9_21(M9_21 m921) throws AppRecFault {
        return null;
    }

}
