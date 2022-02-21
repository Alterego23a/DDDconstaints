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
    public static PackagedElement factoryCheck() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();
        Iterator<Factory> it = xmi.getFactories().listIterator();

        while (it.hasNext()) {
          Factory factory = it.next();
            Iterator<PackagedElement> elementIterator=xmi.getUmlModel().getPackagedElement().listIterator();


            PackagedElement packagedElementCreating =new PackagedElement();
            while (elementIterator.hasNext())//遍历所有packagedElement
            {
                PackagedElement packagedElement1 = elementIterator.next();
                if(packagedElement1.getId().equals(factory.getCreatingDomainObject()))//找到getAccessingDomainObject()指示的packagedElement
                {
                    packagedElementCreating=packagedElement1;//取得getAccessingDomainObject()指示的packagedElement
                    break;
                }
            }
            elementIterator=xmi.getUmlModel().getPackagedElement().listIterator();
            PackagedElement packagedElement =new PackagedElement();
            while (elementIterator.hasNext())//遍历所有packagedElement
            {
                PackagedElement packagedElement1 = elementIterator.next();
                if(packagedElement1.getId().equals(factory.getBaseClass()))//
                {
                    packagedElement=packagedElement1;
                    break;
                }
            }


            if(Support.isEntity(packagedElementCreating,xmi)||Support.isValueObject(packagedElementCreating,xmi)||Support.isAggregateRoot(packagedElementCreating,xmi));//如果是entity,value object或aggregateRoot则没问题
            else
                return packagedElement;




        }

        return null;

    }

//A factory should not be designed as other patterns at the same time.
    public static PackagedElement FactoryCheck2() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<PackagedElement> elementIterator = xmi.getUmlModel().getPackagedElement().listIterator();
        while (elementIterator.hasNext())//遍历所有packagedElement
        {
            PackagedElement packagedElement =elementIterator.next();
            if(Support.isFactory(packagedElement,xmi)) //如果是Factory
            {
                if(Support.isAggregateRoot(packagedElement,xmi)||Support.isDomainEvent(packagedElement,xmi)||Support.isDomainService(packagedElement,xmi)||Support.isAggregatePart(packagedElement,xmi)||Support.isEntity(packagedElement,xmi)||Support.isValueObject(packagedElement,xmi)||Support.isRepository(packagedElement,xmi))
                    return packagedElement;//如果同时是其他构造型 则报错
            }
        }


        return null;

    }
}
