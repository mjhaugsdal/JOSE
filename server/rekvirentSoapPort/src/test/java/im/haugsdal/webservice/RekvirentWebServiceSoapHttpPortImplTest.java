package im.haugsdal.webservice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.common.ConfigurationConstants;
import org.testng.annotations.Test;
import types.M1;

public class RekvirentWebServiceSoapHttpPortImplTest {

    @Test
    void M1Test() throws IOException {

        new RekvirentWebServiceSoapHttpPortImpl();

        Map<String, String> pw = new HashMap<>();
        pw.put("server", "changeit");
        pw.put("client", "changeit");

        JaxWsProxyFactoryBean clientProxyFactoryBean = new JaxWsProxyFactoryBean();
        clientProxyFactoryBean.setAddress("http://localhost:8888/soap");
        clientProxyFactoryBean.setServiceClass(RekvirentWebServiceSoapHttpPort.class);

        Map<String, Object> outProps = new HashMap<>();
        Properties cryptProperties = new Properties();
        cryptProperties.load(RekvirentWebServiceSoapHttpPortImplTest.class.getResourceAsStream("/wsse/client/client_encrypt.properties"));
        Properties signProperties = new Properties();
        signProperties.load(RekvirentWebServiceSoapHttpPortImplTest.class.getResourceAsStream("/wsse/client/client_sign.properties"));

        outProps.put("cryptProperties", cryptProperties);
        outProps.put("signProperties", signProperties);
        outProps.put(ConfigurationConstants.SIG_PROP_REF_ID, "signProperties");
        outProps.put(ConfigurationConstants.ENC_PROP_REF_ID, "cryptProperties");
        outProps.put(ConfigurationConstants.ACTION, ConfigurationConstants.SIGNATURE + " " + ConfigurationConstants.ENCRYPTION);
        outProps.put(ConfigurationConstants.PW_CALLBACK_REF, new KeystorePasswordCallback(pw));
        outProps.put(ConfigurationConstants.SIGNATURE_USER, "client");
        outProps.put(ConfigurationConstants.ENCRYPTION_USER, "server");
        outProps.put(ConfigurationConstants.INCLUDE_SIGNATURE_TOKEN, "true");
        outProps.put(ConfigurationConstants.SIG_KEY_ID, "DirectReference");
        outProps.put(ConfigurationConstants.ENC_KEY_ID, "X509KeyIdentifier");

        Map<String, Object> inProps = new HashMap<>();

        inProps.put("cryptProperties", cryptProperties);
        inProps.put("signProperties", signProperties);
        inProps.put(ConfigurationConstants.SIG_PROP_REF_ID, "cryptProperties"); //Her brukes cryptProperties som truststore
        inProps.put(ConfigurationConstants.DEC_PROP_REF_ID, "signProperties");
        inProps.put(ConfigurationConstants.ACTION, ConfigurationConstants.SIGNATURE + " " + ConfigurationConstants.ENCRYPTION);
        inProps.put(ConfigurationConstants.PW_CALLBACK_REF, new KeystorePasswordCallback(pw));
        inProps.put(ConfigurationConstants.USER, "client");
        //inProps.put(ConfigurationConstants.SIGNATURE_USER, ConfigurationConstants.USE_REQ_SIG_CERT);


        clientProxyFactoryBean.getOutInterceptors().add(new WSS4JOutInterceptor(outProps));
        clientProxyFactoryBean.getInInterceptors().add(new WSS4JInInterceptor(inProps));

        RekvirentWebServiceSoapHttpPort client = (RekvirentWebServiceSoapHttpPort) clientProxyFactoryBean.create();

        M1 m1 = new M1();

        String dok = "<dok></dok>";
        m1.setDokument(dok.getBytes(StandardCharsets.UTF_8));
        client.m1(m1);

    }
}