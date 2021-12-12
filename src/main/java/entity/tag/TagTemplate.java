package entity.tag;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;

/**
 * @author SunWarriorZLX
 * @since 1.0.0
 **/
public class TagTemplate extends BaseTag implements Serializable {
    private static final long serialVersionUID = 7756989825310760243L;

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(isAttribute = true, localName = "base_Class")
    private String baseClass;

    @JacksonXmlProperty(isAttribute = true,localName = "base_Package")
    private String basePackage;

    public TagTemplate(String id, String baseClass, String basePackage) {
        this.id = id;
        this.baseClass = baseClass;
        this.basePackage = basePackage;
    }

    public TagTemplate() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaseClass() {
        return baseClass;
    }

    public void setBaseClass(String baseClass) {
        this.baseClass = baseClass;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public String toString() {
        return "TagTemplate{" +
                "id='" + id + '\'' +
                ", baseClass='" + baseClass + '\'' +
                ", basePackage='" + basePackage + '\'' +
                "} " + super.toString();
    }
}
