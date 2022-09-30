package types;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppRec", propOrder = { "dokument" })
public class AppRec implements java.io.Serializable {

	@XmlElement(required = true, name = "dokument", nillable = false, namespace = "http://rekvirent.webservices.reseptformidleren.ergo.no/types/")
	private Object dokument;

	public AppRec() {
	}

	public AppRec(Object dokument) {
		this.dokument = dokument;
	}

	public void setDokument(Object dokument) {
		this.dokument = dokument;
	}

	public Object getDokument() {
		return dokument;
	}

}
