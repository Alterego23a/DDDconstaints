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



    public static boolean domainEventCheck() throws IOException {

        // String filePath = "src/main/resources/parser-test.xml";
        XMI xmi = XMLParserUtil.parserXML();


        Iterator<DomainEvent> it = xmi.getDomainEvents().listIterator();
        HashSet<String> domainEventSet = new HashSet<String>();
        while (it.hasNext()) {
            DomainEvent domainEvent = it.next();
//遍历所有是entity的packagedElement
            Iterator<PackagedElement> elementIterator = xmi.getUmlModel().getPackagedElement().listIterator();

            PackagedElement packagedElement = new PackagedElement();

            while (elementIterator.hasNext()) {//从所有packagedElement中找到是entity的
                PackagedElement packagedElement1 = elementIterator.next();
                if (packagedElement1.getId().equals(domainEvent.getBaseClass())) {//从所有packagedElement中找到是entity的
                    packagedElement = packagedElement1;
                    break;
                }
            }


            Iterator<OwnedAttribute> attributeIterator = packagedElement.getOwnedAttributes().listIterator();
            while (attributeIterator.hasNext()) {
                OwnedAttribute attribute = attributeIterator.next();

             if (domainEventSet.contains(domainEvent.getIdentifier()) == false)
                    domainEventSet.add(domainEvent.getIdentifier());
                else
                    return false;

            }
        }

        return true;


    }

    public static boolean domainEventCheck2() throws IOException {

        // String filePath = "src/main/resources/parser-test.xml";
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<DomainEvent> it = xmi.getDomainEvents().listIterator();

        while (it.hasNext()) {
            DomainEvent domainEvent = it.next();
            Iterator<PackagedElement> elementIterator = xmi.getUmlModel().getPackagedElement().listIterator();

            PackagedElement packagedElement = new PackagedElement();

            while (elementIterator.hasNext()) {
                PackagedElement packagedElement1 = elementIterator.next();
                if (packagedElement1.getId().equals(domainEvent.getBaseClass())) {
                    packagedElement = packagedElement1;
                    break;
                }
            }

            String identifier=domainEvent.getIdentifier();
            int begin=0;
            boolean finish=true;//标记是否读到最后一个逗号后面的子串,false为读到，结束循环
            if(identifier.indexOf(',')==-1) {
                boolean flag = false;
                {
                    Iterator<OwnedAttribute> attributeIterator =packagedElement.getOwnedAttributes().listIterator();
                    while (attributeIterator.hasNext()) {
                        OwnedAttribute attribute = attributeIterator.next();

                        if (identifier.equals(attribute.getName()))
                            flag = true;

                    }
                    if (flag == false) return false;

                }
            }
            else{
                while(finish) {
                    String temp = new String();
                    if(identifier.indexOf(',',begin)!=-1)
                    {
                        temp = identifier.substring(begin, identifier.indexOf(',', begin));//截取字符串
                        begin = identifier.indexOf(',', begin) + 1;     //把起点重定位到下个逗号+1的位置

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

                    if(flag==false) return false;


                }
            }
        }

        return true;
    }
}
