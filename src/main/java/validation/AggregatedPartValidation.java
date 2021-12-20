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
import java.util.Iterator;
public class AggregatedPartValidation {
    public static boolean aggregatePartCheck() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<AggregatePart> it = xmi.getAggregateParts().listIterator();

        while (it.hasNext()) {
            AggregatePart aggregatePart = it.next();
            Iterator<PackagedElement> elementIterator = xmi.getUmlModel().getPackagedElement().listIterator();  //遍历每个element，对每个是聚合的element进行操作
            PackagedElement packagedElement = new PackagedElement();
            while (elementIterator.hasNext()) {
                PackagedElement packagedElement1 = elementIterator.next();
                if (Support.isAggregate(packagedElement1, xmi)) {

                    packagedElement = packagedElement1;

                    Iterator<PackagedElement> elementIterator1 = packagedElement.getPackagedElements().listIterator();//聚合内部的成员
                    while (elementIterator1.hasNext()) {
                        PackagedElement packagedElement2 = elementIterator1.next();
                        if (packagedElement2.getId() == aggregatePart.getId())
                            return false;//内部成员不能是该聚合部分
                    }
                }
            }


        }
        return true;
    }

}