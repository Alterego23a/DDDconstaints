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

    public static PackagedElement repositoryCheck() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();
        Iterator<Repository> it = xmi.getRepositories().listIterator();

        while (it.hasNext()) {//遍历所有Repository
            Repository repository = it.next();
            Iterator<PackagedElement> elementIterator=xmi.getUmlModel().getPackagedElement().listIterator();
            PackagedElement packagedElementAccessing =new PackagedElement();
            while (elementIterator.hasNext())//遍历所有packagedElement
            {
                boolean hasFind=false;
                PackagedElement packagedElement1 = elementIterator.next();
                if(packagedElement1.getId().equals(repository.getAccessingDomainObject()))//找到getAccessingDomainObject()指示的packagedElement
                {
                    packagedElementAccessing=packagedElement1;//取得getAccessingDomainObject()指示的packagedElement
                    break;
                }
              /*  if(Support.isBoundedContest(packagedElement1,xmi))//搜索所有限界上下文内部，看是否是AccessingDomainObject()指示的
                {
                    Iterator<PackagedElement> elementIteratorInBoundedContest = packagedElement1.getPackagedElements().listIterator();
                    while(elementIteratorInBoundedContest.hasNext())
                    {
                        PackagedElement  packagedElementInBoundedContest =elementIteratorInBoundedContest.next();
                        if(packagedElementInBoundedContest.getId().equals(repository.getAccessingDomainObject()))
                        {
                            packagedElementAccessing=packagedElementInBoundedContest;//在内部找到了该repository
                            break;
                        }
                    }
                }*/
                if(packagedElement1.getPackagedElements()!=null)//如果有下一级的PackagedElements，也就是说是BoudedContext或者Aggregate
                {
                    Iterator<PackagedElement> elementIterator1 = packagedElement1.getPackagedElements().listIterator();//聚合内部的成员
                    while(elementIterator1.hasNext())
                    {
                        PackagedElement packagedElementTemp = elementIterator1.next();
                        if(packagedElementTemp.getId().equals(repository.getAccessingDomainObject()))
                        {
                            packagedElementAccessing=packagedElementTemp;//找到这个entity所属的packagedElement
                            hasFind=true;
                            break;
                        }
                        if(packagedElementTemp.getPackagedElements()!=null)//如果内部有其他聚合，继续搜索聚合内部
                        {
                            Iterator<PackagedElement> elementIterator2 = packagedElementTemp.getPackagedElements().listIterator();//这里主要是遍历BoudedContext里的聚合，也就是2层的包结构
                            while(elementIterator2.hasNext())
                            {
                                PackagedElement packagedElementTemp2 = elementIterator2.next();
                                if(packagedElementTemp2.getId().equals(repository.getAccessingDomainObject()))
                                {
                                    packagedElementAccessing=packagedElementTemp2;//找到这个entity所属的packagedElement
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
            elementIterator=xmi.getUmlModel().getPackagedElement().listIterator();
            PackagedElement packagedElement =new PackagedElement();
            while (elementIterator.hasNext())//遍历所有packagedElement
            {
                PackagedElement packagedElement1 = elementIterator.next();
                  if(packagedElement1.getId().equals(repository.getBaseClass()))//
                {
                    packagedElement=packagedElement1;
                    break;
                }

                if(Support.isBoundedContest(packagedElement1,xmi))//搜索所有限界上下文内部的Factory
                {
                    Iterator<PackagedElement> elementIteratorInBoundedContest = packagedElement1.getPackagedElements().listIterator();
                    while(elementIteratorInBoundedContest.hasNext())
                    {
                        PackagedElement  packagedElementInBoundedContest =elementIteratorInBoundedContest.next();
                        if(packagedElementInBoundedContest.getId().equals(repository.getBaseClass()))
                        {
                            packagedElement=packagedElementInBoundedContest;//在内部找到了该Factory对应的packagedElement
                            break;
                        }
                    }
                }
            }

            if(Support.isEntity(packagedElementAccessing,xmi)||Support.isValueObject(packagedElementAccessing,xmi)||Support.isAggregateRoot(packagedElementAccessing,xmi));
            else
                return packagedElement;




        }

        return null;

    }
    // A repository has no attributes
    public static PackagedElement repositoryCheck2() throws IOException {
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
                if(Support.isBoundedContest(packagedElement1,xmi))//搜索所有限界上下文内部的Factory
                {
                    Iterator<PackagedElement> elementIteratorInBoundedContest = packagedElement1.getPackagedElements().listIterator();
                    while(elementIteratorInBoundedContest.hasNext())
                    {
                        PackagedElement  packagedElementInBoundedContest =elementIteratorInBoundedContest.next();
                        if(packagedElementInBoundedContest.getId().equals(repository.getBaseClass()))
                        {
                            packagedElement=packagedElementInBoundedContest;//在内部找到了该repository对应的packagedElement
                            break;
                        }
                    }
                }


            }
            //  PackagedElement packagedElement=elementIterator.next();
            //assert packagedElement!=null;
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

//A repository should not be designed as other patterns at the same time.

    public static PackagedElement repositoryCheck3() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<PackagedElement> elementIterator = xmi.getUmlModel().getPackagedElement().listIterator();
        while (elementIterator.hasNext())//遍历所有packagedElement
        {
            PackagedElement packagedElement =elementIterator.next();
            if(Support.isRepository(packagedElement,xmi))
            {
                if(Support.isAggregateRoot(packagedElement,xmi)||Support.isDomainEvent(packagedElement,xmi)||Support.isDomainService(packagedElement,xmi)||Support.isAggregatePart(packagedElement,xmi)||Support.isEntity(packagedElement,xmi)||Support.isValueObject(packagedElement,xmi)||Support.isFactory(packagedElement,xmi))
                    return packagedElement;//如果同时是其他构造型 则报错
            }
            if(Support.isBoundedContest(packagedElement,xmi))
            {
                Iterator<PackagedElement> elementIteratorInBoundedContest = packagedElement.getPackagedElements().listIterator();
                while(elementIteratorInBoundedContest.hasNext())
                {
                    PackagedElement  packagedElementInBoundedContest =elementIteratorInBoundedContest.next();
                    if(Support.isRepository(packagedElementInBoundedContest,xmi))
                    {
                        if(Support.isAggregateRoot(packagedElementInBoundedContest,xmi)||Support.isDomainEvent(packagedElementInBoundedContest,xmi)||Support.isDomainService(packagedElementInBoundedContest,xmi)||Support.isAggregatePart(packagedElementInBoundedContest,xmi)||Support.isEntity(packagedElementInBoundedContest,xmi)||Support.isValueObject(packagedElementInBoundedContest,xmi)||Support.isFactory(packagedElementInBoundedContest,xmi))
                            return packagedElementInBoundedContest;//如果同时是其他构造型 则报错
                    }
                }
            }
        }


        return null;

    }
}
