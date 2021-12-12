package entity.tag;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;

/**
 * @author CZK
 * @since 1.0.0
 **/
public class Entity extends TagTemplate implements Serializable {

    private static final long serialVersionUID = 550261141909134026L;

    @JacksonXmlProperty(isAttribute = true,localName = "Identity")
    private String identity;

    public Entity() {

    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }


}
