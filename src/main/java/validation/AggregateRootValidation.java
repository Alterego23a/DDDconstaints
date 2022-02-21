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
            }

            return null;
    }
}

