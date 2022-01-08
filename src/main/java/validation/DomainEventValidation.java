package validation;

import entity.OwnedAttribute;
import entity.XMI;
import entity.tag.DomainEvent;
import entity.tag.Entity;
import parser.XMLParserUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class    DomainEventValidation {
    public static boolean domainEventCheck() throws IOException {//C5. A domain event has and only has one identity.

        // String filePath = "src/main/resources/parser-test.xml";
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<DomainEvent> it = xmi.getDomainEvents().listIterator();
        HashSet<String> domainEventSet = new HashSet<String>();
        while (it.hasNext()) {
            DomainEvent domainEvent = it.next();
            Iterator<OwnedAttribute> attributeIterator=domainEvent.getOwnedAttributes().listIterator();
            while(attributeIterator.hasNext()) {
                OwnedAttribute attribute= attributeIterator.next();

                if (domainEvent.getIdentity() != attribute.getType())//C. The identity of a domain event should be one of the attributes of the event itself
                    return false;

                else if (domainEventSet.contains(domainEvent.getIdentity()) == false)
                    domainEventSet.add(domainEvent.getIdentity());
                else
                    return false;

            }
        }

        return true;
    }
}
