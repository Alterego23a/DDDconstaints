package validation;

import entity.OwnedAttribute;
import entity.OwnedOperation;
import entity.PackagedElement;
import entity.XMI;
import entity.tag.Entity;
import entity.tag.ValueObject;
import parser.XMLParserUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

public class    EntityValidation {
    //C1. An entity has and only has one identity.
    public static PackagedElement entityCheck() throws IOException {

        // String filePath = "src/main/resources/parser-test.xml";
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<Entity> it = xmi.getEntities().listIterator();

        while (it.hasNext()) {
            Entity e = it.next();
//遍历所有是entity的packagedElement





            Iterator<PackagedElement> elementIterator=xmi.getUmlModel().getPackagedElement().listIterator();

            PackagedElement packagedElement =new PackagedElement();

            while (elementIterator.hasNext())
            {
                boolean hasFind =false; //控制找到后跳出循环
                PackagedElement packagedElement1 = elementIterator.next();
                if(packagedElement1.getId().equals(e.getBaseClass()))
                {
                    packagedElement=packagedElement1;
                    break;                        //找到这个entity所属的packagedElement
                }

                if(packagedElement1.getPackagedElements()!=null)//如果有下一级的PackagedElements，也就是说是BoudedContext或者Aggregate
                {
                    Iterator<PackagedElement> elementIterator1 = packagedElement1.getPackagedElements().listIterator();//聚合内部的成员
                    while(elementIterator1.hasNext())
                    {
                        PackagedElement packagedElementTemp = elementIterator1.next();
                        if(packagedElementTemp.getId().equals(e.getBaseClass()))
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
                                if(packagedElementTemp2.getId().equals(e.getBaseClass()))
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
          /*  if(packagedElement==null)//如果没有
            {


            }*/

            if (e.getIdentifier()==null)
            {

                return packagedElement;//如果至少有一个Identifier

            }

            //  PackagedElement packagedElement=elementIterator.next();
            //assert packagedElement!=null;

            Iterator<OwnedAttribute> ownedAttributeIterator=packagedElement.getOwnedAttributes().listIterator();


            while (ownedAttributeIterator.hasNext())
            {
                OwnedAttribute ownedAttribute= ownedAttributeIterator.next();
                if(ownedAttribute.getName().indexOf("identity")!=-1||ownedAttribute.getName().indexOf("Identity")!=-1||ownedAttribute.getName().indexOf("Identifier")!=-1||ownedAttribute.getName().indexOf("identifier")!=-1)

                {


                    return packagedElement;
                    //属性中不能有其他identity  保证有且只有一个
                }
            }
        }



        return null;

    }


    //The identity of an entity should be designed as the composition of one or several it attributes.
    public static PackagedElement entityCheck2() throws IOException {

        // String filePath = "src/main/resources/parser-test.xml";
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<Entity> it = xmi.getEntities().listIterator();
        //HashSet<String> entitySet = new HashSet<String>();
        while (it.hasNext()) {
            Entity e = it.next();
            Iterator<PackagedElement> elementIterator = xmi.getUmlModel().getPackagedElement().listIterator();

            PackagedElement packagedElement = new PackagedElement();//packagedElement 用来保存element

            while (elementIterator.hasNext()) {
                boolean hasFind=false;//控制找到后跳出循环
                PackagedElement packagedElement1 = elementIterator.next();
                if (packagedElement1.getId().equals(e.getBaseClass())) {
                    packagedElement = packagedElement1;//packagedElement1为当前访问的element
                    break;
                }


                if(packagedElement1.getPackagedElements()!=null)//如果有下一级的PackagedElements，也就是说是BoudedContext或者Aggregate
                {
                    Iterator<PackagedElement> elementIterator1 = packagedElement1.getPackagedElements().listIterator();//聚合内部的成员
                    while(elementIterator1.hasNext())
                    {
                        PackagedElement packagedElementTemp = elementIterator1.next();
                        if(packagedElementTemp.getId().equals(e.getBaseClass()))
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
                                if(packagedElementTemp2.getId().equals(e.getBaseClass()))
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

            String identifier=e.getIdentifier();
            if (identifier==null)
                return packagedElement;
            int begin=0;
            boolean finish=true;//标记是否读到最后一个逗号后面的子串,false为读到，结束循环
            if(identifier.indexOf(' ')==-1) {
                boolean flag = false;
                {
                    Iterator<OwnedAttribute> attributeIterator =packagedElement.getOwnedAttributes().listIterator();//C1. An entity has and only has one identity.
                    while (attributeIterator.hasNext()) {
                        OwnedAttribute attribute = attributeIterator.next();
                        //System.out.println("dwdw"+""+attribute);
                        if (identifier.equals(attribute.getName()))   // /the identity of an entity should be one of the attributes of the entity itself
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
                      //  System.out.println("dwdw"+""+attribute);
                        if (temp.equals(attribute.getName()))   // /the identity of an entity should be one of the attributes of the entity itself
                            flag=true;
                    }

                    if(flag==false) return packagedElement;


                }
            }
        }

        return null;
    }




    //An entity should has at least one domain behavior
  public static PackagedElement entityCheck3() throws IOException {
        // String filePath = "src/main/resources/parser-test.xml";
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<Entity> it = xmi.getEntities().listIterator();
        HashSet<String> entitySet = new HashSet<String>();
        while (it.hasNext()) {
            Entity e = it.next();

            Iterator<PackagedElement> elementIterator=xmi.getUmlModel().getPackagedElement().listIterator();

            PackagedElement packagedElement =new PackagedElement();

            while (elementIterator.hasNext())
            {

                boolean hasFind=false;
                PackagedElement packagedElement1 = elementIterator.next();
                if(packagedElement1.getId().equals(e.getBaseClass()))
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
                        if(packagedElementTemp.getId().equals(e.getBaseClass()))
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
                                if(packagedElementTemp2.getId().equals(e.getBaseClass()))
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



            if( packagedElement.getOwnedOperations().isEmpty())
                return packagedElement;
        }

        return null;
    }



}
