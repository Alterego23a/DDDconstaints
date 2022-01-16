package entity.tag;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;

/**
 * @author CZK
 * @since 1.0.0
 **/
public class DomainEvent extends TagTemplate implements Serializable {

    private static final long serialVersionUID = -8281616205562160675L;

    @JacksonXmlProperty(isAttribute = true,localName = "identifier")
    private String identifier;

    public DomainEvent() {

    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

}
