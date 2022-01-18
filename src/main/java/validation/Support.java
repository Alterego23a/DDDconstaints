package validation;

import entity.PackagedElement;
import entity.XMI;
import entity.tag.*;

import java.util.Iterator;
import java.util.ListIterator;

public class Support {

    public static boolean isAggregatePart(PackagedElement packagedElement, XMI xmi)
    {
        Iterator<AggregatePart> it=xmi.getAggregateParts().listIterator();

        while (it.hasNext())
        {
            AggregatePart aggregatePart=it.next();
            if(aggregatePart.getBaseClass().equals(packagedElement.getId()))
                return true;
        }

        return false;
    }


    public static boolean isAggregateRoot(PackagedElement packagedElement, XMI xmi)
    {
        Iterator<AggregateRoot> it=xmi.getAggregateRoots().listIterator();

        while (it.hasNext())
        {
            AggregateRoot aggregateRoot=it.next();
            if(aggregateRoot.getBaseClass().equals(packagedElement.getId()))
                return true;
        }

        return false;
    }

    public static boolean isAggregate(PackagedElement packagedElement, XMI xmi)
    {
        Iterator<Aggregate> it=xmi.getAggregates().listIterator();

        while (it.hasNext())
        {
            Aggregate aggregate=it.next();
            if(aggregate.getBaseClass().equals(packagedElement.getId()))
                return true;
        }

        return false;
    }


    public static boolean isEntity(PackagedElement packagedElement, XMI xmi)
    {
        Iterator<Entity> it=xmi.getEntities().listIterator();

        while (it.hasNext())
        {
           Entity entity = it.next();
            if(entity.getBaseClass().equals(packagedElement.getId()))
                return true;
        }

        return false;
    }

    public static boolean isValueObject(PackagedElement packagedElement, XMI xmi)
    {
        Iterator<ValueObject> it=xmi.getValueObjects().listIterator();

        while (it.hasNext())
        {
            ValueObject valueObject = it.next();
            if(valueObject.getBaseClass().equals(packagedElement.getId()))
                return true;
        }

        return false;
    }

}
