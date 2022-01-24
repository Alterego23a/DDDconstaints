package validation;

import entity.OwnedAttribute;
import entity.PackagedElement;
import entity.XMI;
import entity.tag.DomainService;
import entity.tag.Repository;
import entity.tag.ValueObject;
import parser.XMLParserUtil;

import java.io.IOException;
import java.util.Iterator;

public class RepositoryValidation {



    // A repository needs to specify the object for which it is responsible for data access. The type of the object can be entity, value object, and aggregate root.

    public static boolean repositoryCheck() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();
        Iterator<Repository> it = xmi.getRepositories().listIterator();

        while (it.hasNext()) {//遍历所有Repository
            Repository repository = it.next();
            Iterator<PackagedElement> elementIterator=xmi.getUmlModel().getPackagedElement().listIterator();
            PackagedElement packagedElement =new PackagedElement();
            while (elementIterator.hasNext())//遍历所有packagedElement
            {
                PackagedElement packagedElement1 = elementIterator.next();
                if(packagedElement1.getId().equals(repository.getAccessingDomainObject()))//找到getAccessingDomainObject()指示的packagedElement
                {
                    packagedElement=packagedElement1;//取得getAccessingDomainObject()指示的packagedElement
                    break;
                }
            }

            if(Support.isEntity(packagedElement,xmi)||Support.isValueObject(packagedElement,xmi)||Support.isAggregateRoot(packagedElement,xmi));
            else
                return false;




        }

        return true;

    }
    // A repository has no attributes
    public static boolean repositoryCheck2() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();
        Iterator<Repository> it = xmi.getRepositories().listIterator();

        while (it.hasNext())
        {
            Repository repository=it.next();
            Iterator<PackagedElement> elementIterator=xmi.getUmlModel().getPackagedElement().listIterator();
            PackagedElement packagedElement =new PackagedElement();
            while (elementIterator.hasNext())
            {
                PackagedElement packagedElement1 = elementIterator.next();
                if(packagedElement1.getId().equals(repository.getBaseClass()))//该packagedElement是repository
                {
                    packagedElement=packagedElement1;
                    break;
                }
            }
            //  PackagedElement packagedElement=elementIterator.next();
            //assert packagedElement!=null;
            if(!packagedElement.getOwnedAttributes().isEmpty())
                return false;
        }
        return true;
    }

//A repository should not be designed as other patterns at the same time.

    public static boolean repositoryCheck3() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<PackagedElement> elementIterator = xmi.getUmlModel().getPackagedElement().listIterator();
        while (elementIterator.hasNext())//遍历所有packagedElement
        {
            PackagedElement packagedElement =elementIterator.next();
            if(Support.isRepository(packagedElement,xmi))
            {
                if(Support.isAggregateRoot(packagedElement,xmi)||Support.isDomainEvent(packagedElement,xmi)||Support.isDomainService(packagedElement,xmi)||Support.isAggregatePart(packagedElement,xmi)||Support.isEntity(packagedElement,xmi)||Support.isValueObject(packagedElement,xmi)||Support.isFactory(packagedElement,xmi))
                    return false;//如果同时是其他构造型 则报错
            }
        }


        return true;

    }
}
