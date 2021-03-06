package validation;

import entity.OwnedAttribute;
import entity.PackagedElement;
import entity.XMI;
import entity.tag.DomainEvent;
import entity.tag.Entity;
import parser.XMLParserUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

public class    DomainEventValidation {


//The identity of a domain event should be one of the attributes of the event itself.
public static PackagedElement domainEventCheck() throws IOException {

    // String filePath = "src/main/resources/parser-test.xml";
    XMI xmi = XMLParserUtil.parserXML();

    Iterator<DomainEvent> it = xmi.getDomainEvents().listIterator();

    while (it.hasNext()) {
        DomainEvent domainEvent = it.next();
//遍历所有是entity的packagedElement





        Iterator<PackagedElement> elementIterator=xmi.getUmlModel().getPackagedElement().listIterator();

        PackagedElement packagedElement =new PackagedElement();

        while (elementIterator.hasNext())
        {
            boolean hasFind =false;
            PackagedElement packagedElement1 = elementIterator.next();
            if(packagedElement1.getId().equals(domainEvent.getBaseClass()))
            {
                packagedElement=packagedElement1;
                break;                        //找到这个domainEvent所属的packagedElement
            }
            if(packagedElement1.getPackagedElements()!=null)//如果有下一级的PackagedElements，也就是说是BoundedContext或者Aggregate
            {
                Iterator<PackagedElement> elementIterator1 = packagedElement1.getPackagedElements().listIterator();//聚合内部的成员
                while(elementIterator1.hasNext())
                {
                    PackagedElement packagedElementTemp = elementIterator1.next();
                    if(packagedElementTemp.getId().equals(domainEvent.getBaseClass()))
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
                            if(packagedElementTemp2.getId().equals(domainEvent.getBaseClass()))
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
        if (domainEvent.getIdentifier()==null)
            return packagedElement;//如果至少有一个Identifier

        //  PackagedElement packagedElement=elementIterator.next();
        //assert packagedElement!=null;

        Iterator<OwnedAttribute> ownedAttributeIterator=packagedElement.getOwnedAttributes().listIterator();


        while (ownedAttributeIterator.hasNext())
        {
            OwnedAttribute ownedAttribute= ownedAttributeIterator.next();
            if(ownedAttribute.getName().indexOf("identity")!=-1||ownedAttribute.getName().indexOf("Identity")!=-1||ownedAttribute.getName().indexOf("Identifier")!=-1||ownedAttribute.getName().indexOf("identifier")!=-1)
                return packagedElement;//属性中不能有其他identity  保证有且只有一个
        }
    }



    return null;

}
  /*  public static boolean domainEventCheck2() throws IOException {

        XMI xmi = XMLParserUtil.parserXML();
        Iterator<DomainEvent> it = xmi.getDomainEvents().listIterator();
        while (it.hasNext()) {
            DomainEvent domainEvent = it.next();
            if(domainEvent.getTimesStamp())
        }
        }
*/


//The identiy of an domainEvent should be designed as the composition of one or several it attributes.
    public static PackagedElement domainEventCheck2() throws IOException {

        // String filePath = "src/main/resources/parser-test.xml";
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<DomainEvent> it = xmi.getDomainEvents().listIterator();

        while (it.hasNext()) {
            DomainEvent domainEvent = it.next();
            Iterator<PackagedElement> elementIterator = xmi.getUmlModel().getPackagedElement().listIterator();

            PackagedElement packagedElement = new PackagedElement();

            while (elementIterator.hasNext()) {
                boolean hasFind=false;
                PackagedElement packagedElement1 = elementIterator.next();
                if (packagedElement1.getId().equals(domainEvent.getBaseClass())) {
                    packagedElement = packagedElement1;
                    break;
                }
                if(packagedElement1.getPackagedElements()!=null)//如果有下一级的PackagedElements，也就是说是BoudedContext或者Aggregate
                {
                    Iterator<PackagedElement> elementIterator1 = packagedElement1.getPackagedElements().listIterator();//聚合内部的成员
                    while(elementIterator1.hasNext())
                    {
                        PackagedElement packagedElementTemp = elementIterator1.next();
                        if(packagedElementTemp.getId().equals(domainEvent.getBaseClass()))
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
                                if(packagedElementTemp2.getId().equals(domainEvent.getBaseClass()))
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

            String identifier=domainEvent.getIdentifier();
            if(identifier==null)
                return packagedElement;
            int begin=0;
            boolean finish=true;//标记是否读到最后一个逗号后面的子串,false为读到，结束循环
            if(identifier.indexOf(' ')==-1) {
                boolean flag = false;
                {
                    Iterator<OwnedAttribute> attributeIterator =packagedElement.getOwnedAttributes().listIterator();
                    while (attributeIterator.hasNext()) {
                        OwnedAttribute attribute = attributeIterator.next();

                        if (identifier.equals(attribute.getName()))
                            flag = true;

                    }
                    if (flag == false) return packagedElement;

                }
            }
            else{
                while(finish) {
                    String temp = new String();
                    if(identifier.indexOf(' ',begin)!=-1)
                    {
                        temp = identifier.substring(begin, identifier.indexOf(' ', begin));//截取字符串
                        begin = identifier.indexOf(' ', begin) + 1;     //把起点重定位到下个逗号+1的位置

                    }
                    else
                    {
                        temp=identifier.substring(begin);
                        finish=false; //循环可以结束
                    }

                    Iterator<OwnedAttribute> attributeIterator = packagedElement.getOwnedAttributes().listIterator();
                    boolean flag=false;  //遍历属性的名字，含有temp则flag为true
                    while(attributeIterator.hasNext()) {
                        OwnedAttribute attribute = attributeIterator.next();

                        if (temp.equals(attribute.getName()))   // /the identity of an entity should be one of the attributes of the entity itself
                            flag=true;
                    }

                    if(flag==false) return packagedElement;


                }
            }
        }

        return null;
    }

    //9. A domain event needs a timestamp that records the time when the event happens.
    public static PackagedElement domainEventCheck3() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();
        Iterator<DomainEvent> it = xmi.getDomainEvents().listIterator();
        while (it.hasNext()) {
            DomainEvent domainEvent = it.next();

            Iterator<PackagedElement> elementIterator = xmi.getUmlModel().getPackagedElement().listIterator();

            PackagedElement packagedElement = new PackagedElement();

            while (elementIterator.hasNext()) {
                boolean hasFind =false;
                PackagedElement packagedElement1 = elementIterator.next();
                if (packagedElement1.getId().equals(domainEvent.getBaseClass())) {
                    packagedElement = packagedElement1;
                    break;
                }
                if(packagedElement1.getPackagedElements()!=null)//如果有下一级的PackagedElements，也就是说是BoudedContext或者Aggregate
                {
                    Iterator<PackagedElement> elementIterator1 = packagedElement1.getPackagedElements().listIterator();//聚合内部的成员
                    while(elementIterator1.hasNext())
                    {
                        PackagedElement packagedElementTemp = elementIterator1.next();
                        if(packagedElementTemp.getId().equals(domainEvent.getBaseClass()))
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
                                if(packagedElementTemp2.getId().equals(domainEvent.getBaseClass()))
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

            if (domainEvent.getTimesStamp() == null) {
                return packagedElement;
            }

        }
        return null;
    }

    //11. A domain event needs to specify the publisher and subscriber of the event.
    public static PackagedElement domainEventCheck4() throws IOException{
        XMI xmi = XMLParserUtil.parserXML();
        Iterator<DomainEvent> it = xmi.getDomainEvents().listIterator();
        while (it.hasNext()) {
            DomainEvent domainEvent = it.next();

            Iterator<PackagedElement> elementIterator = xmi.getUmlModel().getPackagedElement().listIterator();

            PackagedElement packagedElement = new PackagedElement();

            while (elementIterator.hasNext()) {
                boolean hasFind=false;
                PackagedElement packagedElement1 = elementIterator.next();
                if (packagedElement1.getId().equals(domainEvent.getBaseClass())) {
                    packagedElement = packagedElement1;
                    break;
                }
                if(packagedElement1.getPackagedElements()!=null)//如果有下一级的PackagedElements，也就是说是BoudedContext或者Aggregate
                {
                    Iterator<PackagedElement> elementIterator1 = packagedElement1.getPackagedElements().listIterator();//聚合内部的成员
                    while(elementIterator1.hasNext())
                    {
                        PackagedElement packagedElementTemp = elementIterator1.next();
                        if(packagedElementTemp.getId().equals(domainEvent.getBaseClass()))
                        {
                            packagedElement=packagedElementTemp;//找到这个entity所属的packagedElement
                            hasFind=true;
                            break;
                        }
                        if(packagedElementTemp.getPackagedElements()!=null)//如果内部有其他聚合，继续搜索聚合内部          //
                                                                                                                    //  其实这里是多余的 ，因为聚合内部只会有root和Part 不会有其他的构造型。
                        {
                            Iterator<PackagedElement> elementIterator2 = packagedElementTemp.getPackagedElements().listIterator();//这里主要是遍历BoudedContext里的聚合，也就是2层的包结构
                            while(elementIterator2.hasNext())
                            {
                                PackagedElement packagedElementTemp2 = elementIterator2.next();
                                if(packagedElementTemp2.getId().equals(domainEvent.getBaseClass()))
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


            if(domainEvent.getPublisher()==null||domainEvent.getSubscriber()==null)
                return packagedElement;
        }
        return null;
    }

}
