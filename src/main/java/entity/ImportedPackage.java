package entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;

/**
 * @author SunWarriorZLX
 * @since 1.0.0
 **/

@JacksonXmlRootElement(localName = "importedPackage")
public class ImportedPackage implements Serializable {

    private static final long serialVersionUID = 1533900509339825374L;

    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlProperty(isAttribute = true)
    private String href;

}
