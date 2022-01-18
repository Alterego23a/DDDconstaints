package validation;

import entity.PackagedElement;
import entity.XMI;
import entity.tag.Factory;
import entity.tag.Repository;
import parser.XMLParserUtil;

import java.io.IOException;
import java.util.Iterator;
//
public class FactoryValidation {
  //  A factory needs to specify the object that it is responsible for creating. The type of the object can be entity, value object, and aggregate.
    public static boolean factoryCheck() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();
        Iterator<Factory> it = xmi.getFactories().listIterator();

        while (it.hasNext()) {
          Factory factory = it.next();
            Iterator<PackagedElement> elementIterator=xmi.getUmlModel().getPackagedElement().listIterator();
            PackagedElement packagedElement =new PackagedElement();
            while (elementIterator.hasNext())
            {
                PackagedElement packagedElement1 = elementIterator.next();
                if(packagedElement1.getId().equals(factory.getCreatingDomainObject()))//找到getAccessingDomainObject()指示的packagedElement
                {
                    packagedElement=packagedElement1;
                    break;
                }
            }

            if(Support.isEntity(packagedElement,xmi)||Support.isValueObject(packagedElement,xmi)||Support.isAggregateRoot(packagedElement,xmi));
            else
                return false;




        }

        return true;

    }
}
