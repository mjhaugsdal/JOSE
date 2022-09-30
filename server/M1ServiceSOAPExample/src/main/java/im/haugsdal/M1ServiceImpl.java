package im.haugsdal;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import types.AppRec;
import types.M1;

public class M1ServiceImpl implements M1Service {

    public static void main(String[] args) {
        JaxWsServerFactoryBean factoryBean = new JaxWsServerFactoryBean();
        factoryBean.setServiceClass(M1ServiceImpl.class);
        factoryBean.setAddress("http://localhost:8889/soap/m1");
        factoryBean.create();
    }

    @Override
    public AppRec m1(M1 m1) {
        AppRec appRec = new AppRec();
        appRec.setDokument("<ok>OK!</ok>");
        return appRec;
    }
}
