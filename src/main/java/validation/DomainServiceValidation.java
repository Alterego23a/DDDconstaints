package validation;

import entity.OwnedAttribute;
import entity.PackagedElement;
import entity.XMI;
import entity.tag.DomainService;
import entity.tag.ValueObject;
import parser.XMLParserUtil;

import java.io.IOException;
import java.util.Iterator;

public class DomainServiceValidation {
    public static boolean valueObjectCheck() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();
        Iterator<DomainService> it = xmi.getDomainServices().listIterator();

        while (it.hasNext())
        {
            DomainService domainService=it.next();
            Iterator<PackagedElement> elementIterator=xmi.getUmlModel().getPackagedElement().listIterator();
            PackagedElement packagedElement = null;
            while (elementIterator.hasNext())
            {
                PackagedElement packagedElement1 = elementIterator.next();
                if(packagedElement1.getId()==domainService.getBaseClass())
                {
                    packagedElement=packagedElement1;
                    break;
                }
            }
            //  PackagedElement packagedElement=elementIterator.next();
            //assert packagedElement!=null;
            if(packagedElement.getOwnedAttributes().size()>0)
                return false;
        }
        return true;
    }
}
