package validation;

import entity.OwnedAttribute;
import entity.PackagedElement;
import entity.XMI;
import entity.tag.*;
import parser.XMLParserUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

public class AggregateValidation {





    // An aggregate has one and only one aggregate root
    public static PackagedElement aggregateCheck() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<Aggregate> it = xmi.getAggregates().listIterator();

        while (it.hasNext()) {
            int num = 0;
            Aggregate aggregate = it.next();
            Iterator<PackagedElement> elementIterator = xmi.getUmlModel().getPackagedElement().listIterator();
            PackagedElement packagedElement = new PackagedElement();
            while (elementIterator.hasNext()) {
                PackagedElement packagedElement1 = elementIterator.next();
                if (packagedElement1.getId().equals( aggregate.getBasePackage()))//该element是aggregate
                {
                    packagedElement = packagedElement1;
                    break;
                }
            }
            Iterator<PackagedElement> elementIterator1 = packagedElement.getPackagedElements().listIterator();//聚合内部的成员

            while (elementIterator1.hasNext()) {
                PackagedElement packagedElement1 = elementIterator1.next();
                if (Support.isAggregateRoot(packagedElement1, xmi))
                    num++;
            }
            if(num!=1) return packagedElement;
        }
        return null;


    }

    //Except the aggregate root, an aggregate can only contain aggregate parts.
    public static PackagedElement aggregateCheck2() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<Aggregate> it = xmi.getAggregates().listIterator();

        while (it.hasNext()) {
            Aggregate aggregate = it.next();
            Iterator<PackagedElement> elementIterator = xmi.getUmlModel().getPackagedElement().listIterator();
            PackagedElement packagedElement = new PackagedElement();

            while (elementIterator.hasNext()) {
                PackagedElement packagedElement1 = elementIterator.next();
                if (packagedElement1.getId() .equals( aggregate.getBasePackage()))//该element是aggregate
                {
                    packagedElement = packagedElement1;
                    break;
                }
            }


            Iterator<PackagedElement> elementIterator1 = packagedElement.getPackagedElements().listIterator();//聚合内部的成员
            int num = 0;
            while (elementIterator1.hasNext()) {
                PackagedElement packagedElement1 = elementIterator1.next();
                if (Support.isAggregatePart(packagedElement1, xmi) || Support.isAggregateRoot(packagedElement1, xmi)) ;
                else return packagedElement;
            }
        }

        return null;
    }



    // The creation of an aggregate should be done by a factory.
    public static PackagedElement aggregateCheck3() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();
        Iterator<PackagedElement> it = xmi.getUmlModel().getPackagedElement().listIterator();

        while (it.hasNext()) {//遍历所有元素
            PackagedElement packagedElement = it.next();
            if (Support.isAggregate(packagedElement, xmi)) {//如果是聚合
                Iterator<Factory> factoryIterator = xmi.getFactories().listIterator();
                boolean temp =false;
                while(factoryIterator.hasNext())
                {
                    Factory factory =factoryIterator.next();
                    if(factory.getCreatingDomainObject()==null)
                        return packagedElement;//getAccessingDomainObject不能为空
                    if(factory.getCreatingDomainObject().equals(packagedElement.getId()))//必须有一个factory负责该聚合的创建
                        temp=true;
                }
                if(!temp) return  packagedElement;


            }


        }
        return null;
    }
    // The accessing of an aggregate should be done by a repository.

        public static PackagedElement aggregateCheck4() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();
        Iterator<PackagedElement> it = xmi.getUmlModel().getPackagedElement().listIterator();

        while (it.hasNext()) {//遍历所有元素
            PackagedElement packagedElement = it.next();
            if (Support.isAggregate(packagedElement, xmi)) {//如果是聚合
                Iterator<Repository> repositoryIterator = xmi.getRepositories().listIterator();
                boolean temp =false;
                while(repositoryIterator.hasNext())
                {
                    Repository repository =repositoryIterator.next();
                    if(repository.getAccessingDomainObject()==null)
                        return packagedElement;//getAccessingDomainObject不能为空
                    if(repository.getAccessingDomainObject().equals(packagedElement.getId()))//必须有一个资源库访问该聚合
                        temp=true;

                }
                if(!temp) return  packagedElement;


            }


        }
        return null;
    }












    //Association between any two aggregates is not allowed through object reference.   该条约束 貌似删除了
  /*  public static boolean aggregateCheck4() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<PackagedElement> it = xmi.getUmlModel().getPackagedElement().listIterator();

        HashSet<String> aggregateRootSet = new HashSet<String>();
        while (it.hasNext()) {//遍历所有元素
            PackagedElement packagedElement = it.next();
            if (Support.isAggregateRoot(packagedElement, xmi)) {//如果是聚合根
                aggregateRootSet.add(packagedElement.getId());//将所有聚合根的id存入set
                }
            }

        it=xmi.getUmlModel().getPackagedElement().listIterator();
        while (it.hasNext()) {//遍历所有元素
            PackagedElement packagedElement = it.next();
            if (Support.isAggregateRoot(packagedElement, xmi)) {//如果是聚合根
                Iterator<OwnedAttribute> attributeIterator = packagedElement.getOwnedAttributes().listIterator();

                while (attributeIterator.hasNext()) {   //遍历聚合根的所有属性
                    OwnedAttribute attribute = attributeIterator.next();
                    if(aggregateRootSet.contains(attribute.getType())) //属性中有另一聚合根
                        return  false;
                }
            }
        }
            return true;

    }
*/ //The objects within an aggregate should not be crosscutting different bounded contexts.
    public static PackagedElement aggregateCheck5() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();


        Iterator<Aggregate> it = xmi.getAggregates().listIterator();
        //  C17. Except the aggregate root, an aggregate can only contain aggregate parts
        HashSet<String> aggregateMemberSet = new HashSet<String>();
        aggregateMemberSet.add("uml:Property");//把基本数据类型加进去。
        while (it.hasNext()) {
            Aggregate aggregate = it.next();
            Iterator<PackagedElement> elementIterator = xmi.getUmlModel().getPackagedElement().listIterator();
            PackagedElement packagedElement = new PackagedElement();

            while (elementIterator.hasNext()) {
                PackagedElement packagedElement1 = elementIterator.next();
                if (packagedElement1.getId().equals( aggregate.getBasePackage()))//该element是aggregate
                {
                    packagedElement = packagedElement1;
                    break;
                }
            }

            Iterator<PackagedElement> elementIteratorMember = packagedElement.getPackagedElements().listIterator();//聚合内部的所有成员
     //       int num = 0;
            while (elementIteratorMember.hasNext()) {
                PackagedElement packagedElementMember = elementIteratorMember.next();
                aggregateMemberSet.add(packagedElementMember.getId());//把聚合内部所有领域对象加到set里
            }

            elementIteratorMember = packagedElement.getPackagedElements().listIterator();//聚合内部的所有成员
            while (elementIteratorMember.hasNext()) {
                PackagedElement packagedElement1 = elementIteratorMember.next();
                Iterator<OwnedAttribute> attributeIterator = packagedElement1.getOwnedAttributes().listIterator();//聚合内部的成员的属性
//
                while (attributeIterator.hasNext()) {//遍历所有属性
                    OwnedAttribute attribute = attributeIterator.next();
                    if (!aggregateMemberSet.contains(attribute.getType())) //如果该属性引用的type不是内部的，则错误
                        return packagedElement;
                }
            }
        }

        return null;

    }


}
