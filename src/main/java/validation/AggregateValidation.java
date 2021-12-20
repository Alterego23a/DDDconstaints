package validation;

import entity.PackagedElement;
import entity.XMI;
import entity.tag.Aggregate;
import entity.tag.DomainService;
import entity.tag.Entity;
import entity.tag.Repository;
import parser.XMLParserUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

public class AggregateValidation {
    public static boolean aggregateCheck() throws IOException {

        // String filePath = "src/main/resources/parser-test.xml";
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<Repository> it = xmi.getRepositories().listIterator();
        HashSet<String> repositorySet = new HashSet<String>();
        while (it.hasNext()) {
            Repository repository = it.next();

            if (repository.getAccessingDomainObject() == null)
                return false;

            else if (repositorySet.contains(repository.getAccessingDomainObject()) == false)
                repositorySet.add(repository.getAccessingDomainObject());
            else
                return false;


        }

        return true;
    }


    public static boolean aggregateCheck2() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<Aggregate> it = xmi.getAggregates().listIterator();

        while (it.hasNext()) {
            Aggregate aggregate = it.next();
            Iterator<PackagedElement> elementIterator = xmi.getUmlModel().getPackagedElement().listIterator();
            PackagedElement packagedElement = new PackagedElement();
            while (elementIterator.hasNext()) {
                PackagedElement packagedElement1 = elementIterator.next();
                if (packagedElement1.getId() == aggregate.getBaseClass())//该element是aggregate
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
                else return false;
            }
        }

        return true;
    }

    public static boolean aggregateCheck3() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<Aggregate> it = xmi.getAggregates().listIterator();
        int num = 0;
        while (it.hasNext()) {
            Aggregate aggregate = it.next();
            Iterator<PackagedElement> elementIterator = xmi.getUmlModel().getPackagedElement().listIterator();
            PackagedElement packagedElement = new PackagedElement();
            while (elementIterator.hasNext()) {
                PackagedElement packagedElement1 = elementIterator.next();
                if (packagedElement1.getId() == aggregate.getBaseClass())//该element是aggregate
                {
                    packagedElement = packagedElement1;
                    break;
                }
            }
            Iterator<PackagedElement> elementIterator1 = packagedElement.getPackagedElements().listIterator();//聚合内部的成员

            while (elementIterator1.hasNext()) {
                PackagedElement packagedElement1 = elementIterator1.next();
                if (Support.isAggregateRoot(packagedElement1, xmi)) ;
                num++;
            }
        }

       if(num==1) return true;
       else return false;
    }
}