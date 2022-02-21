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
public class AggregatePartValidation {

    //The aggregate part can only be designed as an entity or a value object.
    public static PackagedElement aggregatePartCheck() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<PackagedElement> packagedElementIterator= xmi.getUmlModel().getPackagedElement().listIterator();
        while (packagedElementIterator.hasNext()) {
            PackagedElement packagedElement = packagedElementIterator.next();
            if(Support.isAggregatePart(packagedElement,xmi)){//对于AggregatePart
                if(Support.isFactory(packagedElement,xmi)||Support.isDomainService(packagedElement,xmi)||Support.isDomainEvent(packagedElement,xmi)||Support.isAggregateRoot(packagedElement,xmi)||Support.isRepository(packagedElement,xmi))
                    return packagedElement; //不能是除entity或value object之外的类型
                if(Support.isEntity(packagedElement,xmi)&&Support.isValueObject(packagedElement,xmi)) //不能同时是entity或value object
                    return packagedElement;
                if(!Support.isEntity(packagedElement,xmi)&&!Support.isValueObject(packagedElement,xmi))//都不是
                    return packagedElement;
            }
        }

        return null;

    }


    public static PackagedElement aggregatePartCheck2() throws IOException {// C15. The reference of an aggregate part cannot be held by the outside objects
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<PackagedElement> itAggregate = xmi.getUmlModel().getPackagedElement().listIterator();

        while (itAggregate.hasNext()) {//遍历所有element，找到是聚合的

            HashSet<String> aggregatePartSet = new HashSet<String>();//把该聚合的所有aggregatePart的id到set里

            PackagedElement aggregate = itAggregate.next();

            if (Support.isAggregate(aggregate, xmi)) {

                Iterator<PackagedElement> itAggregatePart = aggregate.getPackagedElements().listIterator();
                while (itAggregatePart.hasNext()) {
                    PackagedElement aggregatePart = itAggregatePart.next();

                    ;//把该聚合的所有aggregatePart的id到set里
                /*    if (Support.isAggregatePart(aggregatePart, xmi)) {
                        aggregatePartSet.add(aggregatePart.getId());

                    }*/


                    Iterator<PackagedElement> itOtherAggregate = xmi.getUmlModel().getPackagedElement().listIterator();
                    while (itOtherAggregate.hasNext()) {//遍历其他element，找到是聚合的

                        PackagedElement otherAggregate = itOtherAggregate.next();
                        if (Support.isAggregate(otherAggregate, xmi) && (!otherAggregate.getId().equals(aggregate.getId())))//其他聚合???????//
                        {
                            Iterator<PackagedElement> itOtherAggregatePart = otherAggregate.getPackagedElements().listIterator();//遍历其他聚合内部的聚合部分
                            while (itOtherAggregatePart.hasNext()) {
                                PackagedElement otherAggregatePart = itOtherAggregatePart.next();
                                if (Support.isAggregatePart(otherAggregatePart, xmi)) {
                                    Iterator<OwnedAttribute> attributeIterator = otherAggregatePart.getOwnedAttributes().listIterator();

                                    while (attributeIterator.hasNext()) {
                                        OwnedAttribute attribute = attributeIterator.next();          //属性中引用了其他的聚合部分
                                        if (attribute.getType().equals(aggregatePart.getId())) {
                                            return aggregatePart;
                                        }
                                    }
                                }
                            }
                        }

                    }


                }

            }
        }

        return null;
    }

}

