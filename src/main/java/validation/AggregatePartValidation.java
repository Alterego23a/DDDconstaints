package validation;

import entity.OwnedAttribute;
import entity.PackagedElement;
import entity.XMI;
import entity.tag.*;
import parser.XMLParserUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
public class AggregatePartValidation {

    //The aggregate part can only be designed as an entity or a value object.

   /* public void aggregatePartCheckFun(PackagedElement packagedElement,XMI xmi){
       // XMI xmi = XMLParserUtil.parserXML();
        if(Support.isAggregatePart(packagedElement,xmi)){//对于AggregatePart
            if(Support.isFactory(packagedElement,xmi)||Support.isDomainService(packagedElement,xmi)||Support.isDomainEvent(packagedElement,xmi)||Support.isAggregateRoot(packagedElement,xmi)||Support.isRepository(packagedElement,xmi))
                return packagedElement; //不能是除entity或value object之外的类型
            if(Support.isEntity(packagedElement,xmi)&&Support.isValueObject(packagedElement,xmi)) //不能同时是entity或value object
                return packagedElementTemp;
            if(!Support.isEntity(packagedElementTemp,xmi)&&!Support.isValueObject(packagedElementTemp,xmi))//都不是
                return packagedElementTemp;
        }
    }
    */

    public static PackagedElement aggregatePartCheck() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<PackagedElement> packagedElementIterator= xmi.getUmlModel().getPackagedElement().listIterator();
        while (packagedElementIterator.hasNext()) {
            boolean hasFind=false;
            PackagedElement packagedElement = packagedElementIterator.next();
            if(Support.isAggregatePart(packagedElement,xmi)){//对于AggregatePart
                if(Support.isFactory(packagedElement,xmi)||Support.isDomainService(packagedElement,xmi)||Support.isDomainEvent(packagedElement,xmi)||Support.isAggregateRoot(packagedElement,xmi)||Support.isRepository(packagedElement,xmi))
                    return packagedElement; //不能是除entity或value object之外的类型
                if(Support.isEntity(packagedElement,xmi)&&Support.isValueObject(packagedElement,xmi)) //不能同时是entity或value object
                    return packagedElement;
                if(!Support.isEntity(packagedElement,xmi)&&!Support.isValueObject(packagedElement,xmi))//都不是
                    return packagedElement;
            }


            if(packagedElement.getPackagedElements()!=null)//如果有下一级的PackagedElements，也就是说是BoudedContext或者Aggregate
            {
                Iterator<PackagedElement> elementIterator1 = packagedElement.getPackagedElements().listIterator();//聚合内部的成员
                while(elementIterator1.hasNext())
                {
                    PackagedElement packagedElementTemp = elementIterator1.next();
                    if(Support.isAggregatePart(packagedElementTemp,xmi)){//对于AggregatePart
                        if(Support.isFactory(packagedElementTemp,xmi)||Support.isDomainService(packagedElementTemp,xmi)||Support.isDomainEvent(packagedElementTemp,xmi)||Support.isAggregateRoot(packagedElementTemp,xmi)||Support.isRepository(packagedElementTemp,xmi))
                            return packagedElementTemp; //不能是除entity或value object之外的类型
                        if(Support.isEntity(packagedElementTemp,xmi)&&Support.isValueObject(packagedElementTemp,xmi)) //不能同时是entity或value object
                            return packagedElementTemp;
                        if(!Support.isEntity(packagedElementTemp,xmi)&&!Support.isValueObject(packagedElementTemp,xmi))//都不是
                            return packagedElementTemp;


                    }
                //    System.out.println("s");
                    if(packagedElementTemp.getPackagedElements()!=null)//如果内部有其他聚合，继续搜索聚合内部
                    {
                       // System.out.println("s");
                        Iterator<PackagedElement> elementIterator2 = packagedElementTemp.getPackagedElements().listIterator();//这里主要是遍历BoudedContext里的聚合，也就是2层的包结构
                        while(elementIterator2.hasNext())
                        {
                            PackagedElement packagedElementTemp2 = elementIterator2.next();
                            if(Support.isAggregatePart(packagedElementTemp2,xmi)){//对于AggregatePart
                                if(Support.isFactory(packagedElementTemp2,xmi)||Support.isDomainService(packagedElementTemp2,xmi)||Support.isDomainEvent(packagedElementTemp2,xmi)||Support.isAggregateRoot(packagedElementTemp2,xmi)||Support.isRepository(packagedElementTemp2,xmi))
                                    return packagedElementTemp2; //不能是除entity或value object之外的类型
                                if(Support.isEntity(packagedElementTemp2,xmi)&&Support.isValueObject(packagedElementTemp2,xmi)) //不能同时是entity或value object
                                    return packagedElementTemp2;
                                if(!Support.isEntity(packagedElementTemp2,xmi)&&!Support.isValueObject(packagedElementTemp2,xmi))//都不是
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


    public static PackagedElement aggregatePartCheck2() throws IOException {// C15. The reference of an aggregate part cannot be held by the outside objects
        XMI xmi = XMLParserUtil.parserXML();

      /*  Iterator<PackagedElement> itAggregate = xmi.getUmlModel().getPackagedElement().listIterator();

        while (itAggregate.hasNext()) {//遍历所有element，找到是聚合的

            HashSet<String> aggregatePartSet = new HashSet<String>();//把该聚合的所有aggregatePart的id到set里

            PackagedElement packagedElement = itAggregate.next();

            if (Support.isAggregate(packagedElement, xmi)) {

                Iterator<PackagedElement> itAggregatePart = packagedElement.getPackagedElements().listIterator();
                while (itAggregatePart.hasNext()) {
                    PackagedElement aggregatePart = itAggregatePart.next();

                    ;//把该聚合的所有aggregatePart的id到set里
                /*    if (Support.isAggregatePart(aggregatePart, xmi)) {
                        aggregatePartSet.add(aggregatePart.getId());

                    }

                   if (Support.isAggregatePart(aggregatePart, xmi)) {


                    Iterator<PackagedElement> itOtherAggregate = xmi.getUmlModel().getPackagedElement().listIterator();
                    while (itOtherAggregate.hasNext()) {//遍历其他element

                        PackagedElement otherAggregate = itOtherAggregate.next();
                        if (Support.isAggregate(otherAggregate, xmi) && (!otherAggregate.getId().equals(packagedElement.getId())))//其他聚合???????//
                        {
                            Iterator<PackagedElement> itOtherAggregateMember = otherAggregate.getPackagedElements().listIterator();//遍历其他聚合内部的聚合部分
                            while (itOtherAggregateMember.hasNext()) {
                                PackagedElement otherAggregateMember = itOtherAggregateMember.next();

                                Iterator<OwnedAttribute> attributeIterator = otherAggregateMember.getOwnedAttributes().listIterator();

                                while (attributeIterator.hasNext()) {
                                    OwnedAttribute attribute = attributeIterator.next();          //属性中引用了其他的聚合部分
                                    if (attribute.getType().equals(aggregatePart.getId())) {
                                        return aggregatePart;
                                    }
                                }
                            }
                        }
                        if (Support.isBoundedContest(otherAggregate, xmi)) {
                            Iterator<PackagedElement> boundedContestIterator = otherAggregate.getPackagedElements().listIterator();//内部的成员
                            while (boundedContestIterator.hasNext()) {
                                PackagedElement packagedElementInBoundedContest = boundedContestIterator.next();

                                Iterator<OwnedAttribute> elementAttributeInBoundedContest = packagedElementInBoundedContest.getOwnedAttributes().listIterator();//遍历其他聚合内部的聚合部分
                                while (elementAttributeInBoundedContest.hasNext()) {
                                    OwnedAttribute attribute = elementAttributeInBoundedContest.next();          //属性中引用了其他的聚合部分
                                    if (attribute.getType().equals(aggregatePart.getId())) {
                                        return aggregatePart;
                                    }
                                }

                                if (Support.isAggregate(packagedElementInBoundedContest, xmi) && (!packagedElementInBoundedContest.getId().equals(packagedElement.getId())))//其他聚合???????//
                                {
                                    Iterator<PackagedElement> itOtherAggregateMember = packagedElementInBoundedContest.getPackagedElements().listIterator();//遍历其他聚合内部的聚合部分
                                    while (itOtherAggregateMember.hasNext()) {
                                        PackagedElement otherAggregatePartMember = itOtherAggregateMember.next();

                                        Iterator<OwnedAttribute> attributeIterator = otherAggregatePartMember.getOwnedAttributes().listIterator();

                                        while (attributeIterator.hasNext()) {
                                            OwnedAttribute attribute = attributeIterator.next();          //属性中引用了其他的聚合部分
                                            if (attribute.getType().equals(aggregatePart.getId())) {
                                                return aggregatePart;
                                            }
                                        }
                                    }
                                }


                            }


                        } else {
                            Iterator<PackagedElement> ItOtherElement = otherAggregate.getPackagedElements().listIterator();//遍历其他模型元素（除限界上下文和聚合外）
                            while (ItOtherElement.hasNext()) {
                                PackagedElement otherElement = ItOtherElement.next();

                                Iterator<OwnedAttribute> attributeIterator = otherElement.getOwnedAttributes().listIterator();

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
            if(Support.isBoundedContest(packagedElement, xmi)){//搜索限界上下文内部

                Iterator<PackagedElement> elementIterator1 = packagedElement.getPackagedElements().listIterator();//内部的成员
                while(elementIterator1.hasNext())
                {
                    PackagedElement packagedElementTemp = elementIterator1.next();
                    if (Support.isAggregate(packagedElementTemp, xmi)) {

                        Iterator<PackagedElement> itAggregatePart = packagedElementTemp.getPackagedElements().listIterator();
                        while (itAggregatePart.hasNext()) {
                            PackagedElement aggregatePart = itAggregatePart.next();

                            ;//把该聚合的所有aggregatePart的id到set里
                /*    if (Support.isAggregatePart(aggregatePart, xmi)) {
                        aggregatePartSet.add(aggregatePart.getId());

                    }*/


                 /*           Iterator<PackagedElement> itOtherAggregate = xmi.getUmlModel().getPackagedElement().listIterator();
                            while (itOtherAggregate.hasNext()) {//遍历其他element

                                PackagedElement otherAggregate = itOtherAggregate.next();
                                if (Support.isAggregate(otherAggregate, xmi) && (!otherAggregate.getId().equals(packagedElement.getId())))//其他聚合???????//
                                {
                                    Iterator<PackagedElement> itOtherAggregateMember= otherAggregate.getPackagedElements().listIterator();//遍历其他聚合内部的聚合部分
                                    while (itOtherAggregateMember.hasNext()) {
                                        PackagedElement otherAggregateMember = itOtherAggregateMember.next();

                                        Iterator<OwnedAttribute> attributeIterator = otherAggregateMember.getOwnedAttributes().listIterator();

                                        while (attributeIterator.hasNext()) {
                                            OwnedAttribute attribute = attributeIterator.next();          //属性中引用了其他的聚合部分
                                            if (attribute.getType().equals(aggregatePart.getId())) {
                                                return aggregatePart;
                                            }
                                        }
                                    }
                                }

                                if(Support.isBoundedContest(otherAggregate,xmi))
                                {
                                    Iterator<PackagedElement> boundedContestIterator = otherAggregate.getPackagedElements().listIterator();//内部的成员
                                    while(boundedContestIterator.hasNext())
                                    {
                                        PackagedElement packagedElementInBoundedContest =boundedContestIterator.next();

                                        Iterator<OwnedAttribute>  elementAttributeInBoundedContest= packagedElementInBoundedContest.getOwnedAttributes().listIterator();//遍历其他聚合内部的聚合部分
                                        while (elementAttributeInBoundedContest.hasNext()) {
                                            OwnedAttribute attribute = elementAttributeInBoundedContest.next();          //属性中引用了其他的聚合部分
                                            if (attribute.getType().equals(aggregatePart.getId())) {
                                                return aggregatePart;
                                            }
                                        }

                                        if (Support.isAggregate(packagedElementInBoundedContest, xmi) && (!packagedElementInBoundedContest.getId().equals(packagedElement.getId())))//其他聚合???????//
                                        {
                                            Iterator<PackagedElement> itOtherAggregateMember = packagedElementInBoundedContest.getPackagedElements().listIterator();//遍历其他聚合内部的聚合部分
                                            while (itOtherAggregateMember.hasNext()) {
                                                PackagedElement otherAggregatePartMember = itOtherAggregateMember.next();

                                                Iterator<OwnedAttribute> attributeIterator = otherAggregatePartMember.getOwnedAttributes().listIterator();

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
                                else{

                                }

                            }


                        }

                    }

                }


                // if(hasFind) break;
            }
        }*/
   //     XMI xmi = XMLParserUtil.parserXML();

        Iterator<AggregatePart> it = xmi.getAggregateParts().listIterator();
        HashSet<String> entitySet = new HashSet<String>();
        while (it.hasNext()) {
            AggregatePart aggregatePart = it.next();

            Iterator<PackagedElement> elementIterator=xmi.getUmlModel().getPackagedElement().listIterator();

            PackagedElement packagedElement =new PackagedElement();
            PackagedElement packaged =new PackagedElement();
            while (elementIterator.hasNext())
            {

                boolean hasFind=false;
                PackagedElement packagedElement1 = elementIterator.next();
                if(packagedElement1.getId().equals(aggregatePart.getBaseClass()))
                {
                    packagedElement=packagedElement1;         //找到是entity的packagedElement
                    break;
                }




                if(packagedElement1.getPackagedElements()!=null)//如果有下一级的PackagedElements，也就是说是BoudedContext或者Aggregate
                {
                    Iterator<PackagedElement> elementIterator1 = packagedElement1.getPackagedElements().listIterator();//聚合内部的成员
                    while(elementIterator1.hasNext())
                    {
                        PackagedElement packagedElementTemp = elementIterator1.next();
                        if(packagedElementTemp.getId().equals(aggregatePart.getBaseClass()))
                        {
                            packaged=packagedElement1;
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
                                if(packagedElementTemp2.getId().equals(aggregatePart.getBaseClass()))
                                {
                                    packaged=packagedElementTemp;
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
            //第二遍遍历所有模型元素的属性
            elementIterator=xmi.getUmlModel().getPackagedElement().listIterator();
            while (elementIterator.hasNext())
            {

                boolean hasFind=false;
                PackagedElement packagedElement1 = elementIterator.next();
                Iterator<OwnedAttribute>  elementAttributeIterator= packagedElement1.getOwnedAttributes().listIterator();//遍历其他聚合内部的聚合部分
              while(elementAttributeIterator.hasNext())
              {
                  OwnedAttribute attribute =elementAttributeIterator.next();
                  if(attribute.getType().equals(packagedElement.getId()))
                      return packagedElement;
              }




                if(packagedElement1.getPackagedElements()!=null)//如果有下一级的PackagedElements，也就是说是BoudedContext或者Aggregate
                {
                    Iterator<PackagedElement> elementIterator1 = packagedElement1.getPackagedElements().listIterator();//聚合或BoundedContext内部的成员
                    while(elementIterator1.hasNext())
                    {
                        PackagedElement packagedElementTemp = elementIterator1.next();
                        Iterator<OwnedAttribute>  elementAttributeIterator2= packagedElementTemp.getOwnedAttributes().listIterator();//
                        while(elementAttributeIterator2.hasNext())
                        {
                            OwnedAttribute attribute =elementAttributeIterator2.next();
                            if(attribute.getType().equals(packagedElement.getId())&&!(packagedElement1.getId().equals(packaged.getId())))
                                return packagedElement;
                        }
                        if(packagedElementTemp.getPackagedElements()!=null)//如果内部有其他聚合，继续搜索聚合内部
                        {
                            Iterator<PackagedElement> elementIterator2 = packagedElementTemp.getPackagedElements().listIterator();//这里主要是遍历BoudedContext里的聚合，也就是2层的包结构
                            while(elementIterator2.hasNext())
                            {
                                PackagedElement packagedElementTemp2 = elementIterator2.next();
                                Iterator<OwnedAttribute>  elementAttributeIterator3= packagedElementTemp2.getOwnedAttributes().listIterator();//
                                while(elementAttributeIterator3.hasNext())
                                {
                                    OwnedAttribute attribute =elementAttributeIterator3.next();
                                    if(attribute.getType().equals(packagedElement.getId())&&!(packagedElementTemp.getId().equals(packaged.getId())))
                                        return packagedElement;
                                }
                            }
                        }
                       // if(hasFind) break;
                    }


                    // if(hasFind) break;
                }
            }



            //  PackagedElement packagedElement=elementIterator.next();
            //assert packagedElement!=null;


        }

        return null;
    }






}

