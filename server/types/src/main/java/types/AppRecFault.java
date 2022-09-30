package types;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppRecFault",
        namespace = "http://rekvirent.webservices.reseptformidleren.ergo.no/",
        propOrder = {
        "dokument"
})
public class AppRecFault extends Exception {

    @XmlElement(required = true,name = "dokument",namespace="http://rekvirent.webservices.reseptformidleren.ergo.no/types/")
    protected Object dokument;

    public AppRecFault() {
        super();
    }

//	public AppRecFault(String message) {
    //	super(message);
//	}

    public AppRecFault(byte[] initial) {
        setDokument(initial);
    }


    public void setDokument(Object dokument) {
        this.dokument = dokument;
    }


    public Object getDokument() {
        return dokument;
    }


}
