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

                boolean hasFind=false;
                PackagedElement packagedElement1 = elementIterator.next();
                if(packagedElement1.getId().equals(domainService.getBaseClass()))//该element是domainservice
                {

                    packagedElement=packagedElement1;
                    break;

                }
                if(packagedElement1.getPackagedElements()!=null)//如果有下一级的PackagedElements，也就是说是BoudedContext或者Aggregate
                {
                    Iterator<PackagedElement> elementIterator1 = packagedElement1.getPackagedElements().listIterator();//聚合内部的成员
                    while(elementIterator1.hasNext())
                    {
                        PackagedElement packagedElementTemp = elementIterator1.next();
                        if(packagedElementTemp.getId().equals(domainService.getBaseClass()))
                        {
                            packagedElement=packagedElementTemp;//找到这个entity所属的packagedElement
                            hasFind=true;
                            break;
                        }
                        if(packagedElementTemp.getPackagedElements()!=null)//如果内部有其他聚合，继续搜索聚合内部
                        {
                            Iterator<PackagedElement> elementIterator2 = packagedElementTemp.getPackagedElements().listIterator();//这里主要是遍历BoudedContext里的聚合，也就是2层的包结构
                            while(elementIterator2.hasNext())
                            {
                                PackagedElement packagedElementTemp2 = elementIterator2.next();
                                if(packagedElementTemp2.getId().equals(domainService.getBaseClass()))
                                {
                                    packagedElement=packagedElementTemp2;//找到这个entity所属的packagedElement
                                    hasFind=true;
                                    break;
                                }
                            }
                        }
                        if(hasFind) break;
                    }


                    // if(hasFind) break;
                }
            }
            //  PackagedElement packagedElement=elementIterator.next();
            //assert packagedElement!=null;
       //     if(packagedElement==null) return true;
          if(!packagedElement.getOwnedAttributes().isEmpty())
          {

              Iterator<OwnedAttribute> attributeIterator= packagedElement.getOwnedAttributes().listIterator();

              while(attributeIterator.hasNext())
              {
                  OwnedAttribute attribute =attributeIterator.next();
                  if(attribute.getAssociation()==null)
                      return packagedElement;
              }

          }

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
            if(Support.isBoundedContest(packagedElement,xmi))
            {
                Iterator<PackagedElement> elementIteratorInBoundedContest = packagedElement.getPackagedElements().listIterator();
                while(elementIteratorInBoundedContest.hasNext())
                {
                    PackagedElement  packagedElementInBoundedContest =elementIteratorInBoundedContest.next();
                    if(Support.isDomainService(packagedElement,xmi)) //如果是DomainService
                    {
                        if(Support.isAggregateRoot(packagedElement,xmi)||Support.isDomainEvent(packagedElement,xmi)||Support.isFactory(packagedElement,xmi)||Support.isAggregatePart(packagedElement,xmi)||Support.isEntity(packagedElement,xmi)||Support.isValueObject(packagedElement,xmi)||Support.isRepository(packagedElement,xmi))
                            return packagedElement;//如果同时是其他构造型 则报错
                    }
                }
            }
        }


        return null;

    }
}
