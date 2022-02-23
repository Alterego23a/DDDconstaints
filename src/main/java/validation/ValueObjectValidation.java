package validation;

import entity.OwnedAttribute;
import entity.PackagedElement;
import entity.XMI;
import entity.tag.ValueObject;
import parser.XMLParserUtil;

import java.io.IOException;
import java.util.Iterator;

public class ValueObjectValidation {          // C3. A value object does not have an identity
    public static PackagedElement valueObjectCheck() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();
        Iterator<ValueObject> it = xmi.getValueObjects().listIterator();

        while (it.hasNext())

        {
            ValueObject valueObject=it.next();

            Iterator<PackagedElement> elementIterator=xmi.getUmlModel().getPackagedElement().listIterator();

            PackagedElement packagedElement =new PackagedElement();

            while (elementIterator.hasNext())
            {

                boolean hasFind =false;
                PackagedElement packagedElement1 = elementIterator.next();
                if(packagedElement1.getId().equals(valueObject.getBaseClass()))
                {
                    packagedElement=packagedElement1;
                    break;
                }
                if(packagedElement1.getPackagedElements()!=null)//如果有下一级的PackagedElements，也就是说是BoudedContext或者Aggregate
                {
                    Iterator<PackagedElement> elementIterator1 = packagedElement1.getPackagedElements().listIterator();//聚合内部的成员
                    while (elementIterator1.hasNext()) {
                        PackagedElement packagedElementTemp = elementIterator1.next();
                        if (packagedElementTemp.getId().equals(valueObject.getBaseClass())) {
                            packagedElement = packagedElementTemp;//找到这个entity所属的packagedElement
                            hasFind = true;
                            break;
                        }
                        if (packagedElementTemp.getPackagedElements() != null)//如果内部有其他聚合，继续搜索聚合内部
                        {
                            Iterator<PackagedElement> elementIterator2 = packagedElementTemp.getPackagedElements().listIterator();//这里主要是遍历BoudedContext里的聚合，也就是2层的包结构
                            while (elementIterator2.hasNext()) {
                                PackagedElement packagedElementTemp2 = elementIterator2.next();
                                if (packagedElementTemp2.getId().equals(valueObject.getBaseClass())) {
                                    packagedElement = packagedElementTemp2;//找到这个value object所属的packagedElement
                                    hasFind = true;
                                    break;
                                }
                            }
                        }
                        if (hasFind) break;
                    }
                }





            }


            //  PackagedElement packagedElement=elementIterator.next();
            //assert packagedElement!=null;

            Iterator<OwnedAttribute> ownedAttributeIterator=packagedElement.getOwnedAttributes().listIterator();


            while (ownedAttributeIterator.hasNext())
            {
                OwnedAttribute ownedAttribute= ownedAttributeIterator.next();
                if(ownedAttribute.getName().indexOf("identity")!=-1||ownedAttribute.getName().indexOf("Identity")!=-1||ownedAttribute.getName().indexOf("Identifier")!=-1||ownedAttribute.getName().indexOf("identifier")!=-1)
                    return packagedElement;
            }
        }


        return null;
    }
}