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
    @JacksonXmlProperty(isAttribute = true,localName = "timesStamp")
    private String timesStamp;

    @JacksonXmlProperty(isAttribute = true,localName = "publisher")
    private String publisher;

    @JacksonXmlProperty(isAttribute = true,localName = "subscriber")
    private String subscriber;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public String getTimesStamp() {
        return timesStamp;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public void setTimesStamp(String timesStamp) {
        this.timesStamp = timesStamp;
    }
}

