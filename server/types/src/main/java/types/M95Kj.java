package types;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "M95Kj", propOrder = { "dokument" })
public class M95Kj implements java.io.Serializable {

	@XmlElement(required = true, name = "dokument", nillable = false, namespace = "http://rekvirent.webservices.reseptformidleren.ergo.no/types/")
	protected Object dokument;

	public M95Kj() {
	}

	public Object getDokument() {
		return dokument;
	}

	public void setDokument(Object dokument) {
		this.dokument = dokument;
	}
}
