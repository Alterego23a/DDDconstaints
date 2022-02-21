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
    public static PackagedElement domainServiceCheck() throws IOException {//C9. A domain service is stateless.
        XMI xmi = XMLParserUtil.parserXML();
        Iterator<DomainService> it = xmi.getDomainServices().listIterator();

        while (it.hasNext())
        {
            DomainService domainService=it.next();
            Iterator<PackagedElement> elementIterator=xmi.getUmlModel().getPackagedElement().listIterator();
           PackagedElement packagedElement= new PackagedElement();

            while (elementIterator.hasNext())
            {

                PackagedElement packagedElement1 = elementIterator.next();
                if(packagedElement1.getId().equals(domainService.getBaseClass()))//该element是domainservice
                {

                    packagedElement=packagedElement1;
                    break;

                }
            }
            //  PackagedElement packagedElement=elementIterator.next();
            //assert packagedElement!=null;
       //     if(packagedElement==null) return true;
          if(!packagedElement.getOwnedAttributes().isEmpty())
                return packagedElement;
        }
        return null;
    }

//    A domain service should not be designed as other patterns at the same time.
    public static PackagedElement domainServiceCheck2() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<PackagedElement> elementIterator = xmi.getUmlModel().getPackagedElement().listIterator();
        while (elementIterator.hasNext())//遍历所有packagedElement
        {
            PackagedElement packagedElement =elementIterator.next();
            if(Support.isDomainService(packagedElement,xmi)) //如果是DomainService
            {
                if(Support.isAggregateRoot(packagedElement,xmi)||Support.isDomainEvent(packagedElement,xmi)||Support.isFactory(packagedElement,xmi)||Support.isAggregatePart(packagedElement,xmi)||Support.isEntity(packagedElement,xmi)||Support.isValueObject(packagedElement,xmi)||Support.isRepository(packagedElement,xmi))
                    return packagedElement;//如果同时是其他构造型 则报错
            }
        }


        return null;

    }
}
