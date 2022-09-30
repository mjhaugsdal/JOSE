package types;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "M9_6", propOrder = { "dokument" })
public class M9_6 implements java.io.Serializable {

	@XmlElement(required = true, name = "dokument", nillable = false, namespace = "http://rekvirent.webservices.reseptformidleren.ergo.no/types/")
	protected Object dokument;

	public M9_6() {
	}

	public Object getDokument() {
		return dokument;
	}

	public void setDokument(Object dokument) {
		this.dokument = dokument;
	}
}