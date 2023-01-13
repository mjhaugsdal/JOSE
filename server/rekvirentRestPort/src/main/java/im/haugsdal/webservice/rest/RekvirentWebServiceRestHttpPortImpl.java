package im.haugsdal.webservice.rest;

import im.haugsdal.M1Service;
import im.haugsdal.webservice.rest.filters.CustomJwsContainerRequestFilter;
import im.haugsdal.webservice.rest.interceptors.CustomJweWriterInterceptor;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.rs.security.jose.jaxrs.JweContainerRequestFilter;
import org.apache.cxf.rs.security.jose.jaxrs.JwsWriterInterceptor;
import types.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class RekvirentWebServiceRestHttpPortImpl implements RekvirentWebServiceRestHttpPort {

    static M1Service m1Service;

    public static void main(String[] args) {
        System.out.println("Started!");
    }

    static {
        var serverFactoryBean = new JAXRSServerFactoryBean();
        serverFactoryBean.setServiceClass(RekvirentWebServiceRestHttpPortImpl.class);
        serverFactoryBean.setAddress("http://localhost:8890/");
        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);
        serverFactoryBean.getFeatures().add(loggingFeature);

        List<Object> providers = new LinkedList<>();
        providers.add("com.fasterxml.jackson.jaxrs.xml.JacksonJaxbXMLProvider");

        var jweContainerRequestFilter = new JweContainerRequestFilter();

        Properties properties = new Properties();
        try {
            properties.load(RekvirentWebServiceRestHttpPortImpl.class.getClassLoader().getResourceAsStream("jwe/server/server.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        CustomJwsContainerRequestFilter jwsContainerRequestFilter = new CustomJwsContainerRequestFilter();
        CustomJweWriterInterceptor jweWriterInterceptor = new CustomJweWriterInterceptor(properties); //This combined with CustomJwsWriterInterceptor implements encryption using request signing certificate
        //JweWriterInterceptor jweWriterInterceptor = new JweWriterInterceptor();
        //jweWriterInterceptor.setUseJweOutputStream(true);
        JwsWriterInterceptor jwsWriterInterceptor = new JwsWriterInterceptor();
        jwsWriterInterceptor.setUseJwsOutputStream(true);

        providers.add(jweContainerRequestFilter);
        providers.add(jwsContainerRequestFilter);
        providers.add(jweWriterInterceptor);
        providers.add(jwsWriterInterceptor);

        serverFactoryBean.setProviders(providers);

        //ENCRYPTION properties
        serverFactoryBean.getProperties(true).put("rs.security.encryption.in.properties",
            "jwe/server/server.properties");

        serverFactoryBean.getProperties(true).put("rs.security.encryption.out.properties",
            "jwe/server/server-out.properties");

        //SIGNATURE IN
        serverFactoryBean.getProperties(true).put("rs.security.signature.in.properties",
            "jwe/server/server-sign.properties");

        //SIGNATURE OUT
        serverFactoryBean.getProperties(true).put("rs.security.signature.out.properties",
            "jwe/server/server-out-sign.properties");

        serverFactoryBean.getProperties(true).put("jose.debug", true);

        serverFactoryBean.create();

        //M1 Service er fremdeles SOAP
        /*JaxWsProxyFactoryBean clientProxyFactoryBean = new JaxWsProxyFactoryBean();
        clientProxyFactoryBean.setAddress("http://localhost:8889/soap/m1");
        clientProxyFactoryBean.setServiceClass(M1Service.class);
        m1Service = (M1Service) clientProxyFactoryBean.create();*/
    }


    @Override
    public AppRec m1(M1 m1) {
        var dokument = m1.getDokument();
        var strDok = new String((byte[]) dokument, StandardCharsets.UTF_8);
        System.out.println(strDok);
        String answer = "Hello world!";
        AppRec appRec = new AppRec();
        appRec.setDokument(answer.getBytes(StandardCharsets.UTF_8));
        return new AppRec();
    }

    @Override
    public AppRec m5(M5 m5) {
        return new AppRec();
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
