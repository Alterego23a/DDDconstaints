package validation;

import entity.OwnedAttribute;
import entity.PackagedElement;
import entity.XMI;
import entity.tag.Aggregate;
import entity.tag.AggregatePart;
import entity.tag.DomainService;
import entity.tag.ValueObject;
import parser.XMLParserUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
public class AggregateRootValidation {

    //The root of an aggregate can only be designed as an entity.
    public static PackagedElement aggregateRootCheck() throws IOException {

            XMI xmi = XMLParserUtil.parserXML();

            Iterator<PackagedElement> packagedElementIterator= xmi.getUmlModel().getPackagedElement().listIterator();
            while (packagedElementIterator.hasNext()) {
                PackagedElement packagedElement = packagedElementIterator.next();
                if(Support.isAggregateRoot(packagedElement,xmi)){//对于AggregateRoot
                    if(Support.isFactory(packagedElement,xmi)||Support.isDomainService(packagedElement,xmi)||Support.isDomainEvent(packagedElement,xmi)||Support.isAggregatePart(packagedElement,xmi)||Support.isRepository(packagedElement,xmi))
                        return packagedElement; //不能是除entity之外的类型
                    if(!Support.isEntity(packagedElement,xmi))//必须是Entity
                        return packagedElement;
                }


                if(packagedElement.getPackagedElements()!=null)//如果有下一级的PackagedElements，也就是说是BoudedContext或者Aggregate
                {
                    Iterator<PackagedElement> elementIterator1 = packagedElement.getPackagedElements().listIterator();//聚合内部的成员
                    while(elementIterator1.hasNext())
                    {
                        PackagedElement packagedElementTemp = elementIterator1.next(); //获得当前BoundedConte或Aggregate内部的模型元素
                        if(Support.isAggregateRoot(packagedElementTemp,xmi)){//对于AggregateRoot
                            if(Support.isFactory(packagedElementTemp,xmi)||Support.isDomainService(packagedElementTemp,xmi)||Support.isDomainEvent(packagedElementTemp,xmi)||Support.isAggregatePart(packagedElementTemp,xmi)||Support.isRepository(packagedElementTemp,xmi))
                                return packagedElementTemp; //不能是除entity之外的类型
                            if(!Support.isEntity(packagedElementTemp,xmi))//必须是Entity
                                return packagedElementTemp;
                        }

                        if(packagedElementTemp.getPackagedElements()!=null)//如果内部有其他聚合，继续搜索聚合内部
                        {
                            Iterator<PackagedElement> elementIterator2 = packagedElementTemp.getPackagedElements().listIterator();//这里主要是遍历BoudedContext里的聚合，也就是2层的包结构
                            while(elementIterator2.hasNext())
                            {
                                PackagedElement packagedElementTemp2 =elementIterator2.next();
                                if(Support.isAggregateRoot(packagedElementTemp2,xmi)){//对于AggregateRoot
                                    if(Support.isFactory(packagedElementTemp2,xmi)||Support.isDomainService(packagedElementTemp2,xmi)||Support.isDomainEvent(packagedElementTemp2,xmi)||Support.isAggregatePart(packagedElementTemp2,xmi)||Support.isRepository(packagedElementTemp2,xmi))
                                        return packagedElementTemp2; //不能是除entity之外的类型
                                    if(!Support.isEntity(packagedElementTemp2,xmi))//必须是Entity
                                        return packagedElementTemp2;

                                }
                            }
                        }
                    }


                    // if(hasFind) break;
                }

            }

            return null;
    }
}

