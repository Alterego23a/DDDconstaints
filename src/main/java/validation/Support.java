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
            if(aggregate.getBasePackage().equals(packagedElement.getId()))
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

    public static boolean isDomainService(PackagedElement packagedElement, XMI xmi)
    {
        Iterator<DomainService> it=xmi.getDomainServices().listIterator();

        while (it.hasNext())
        {
            DomainService domainService = it.next();
            if(domainService.getBaseClass().equals(packagedElement.getId()))
                return true;
        }

        return false;
    }

    public static boolean isDomainEvent(PackagedElement packagedElement, XMI xmi)
    {
        Iterator<DomainEvent> it=xmi.getDomainEvents().listIterator();

        while (it.hasNext())
        {
            DomainEvent domainEvent = it.next();
            if(domainEvent.getBaseClass().equals(packagedElement.getId()))
                return true;
        }

        return false;
    }
    public static boolean isFactory(PackagedElement packagedElement, XMI xmi)
    {
        Iterator<Factory> it=xmi.getFactories().listIterator();

        while (it.hasNext())
        {
            Factory factory = it.next();
            if(factory.getBaseClass().equals(packagedElement.getId()))
                return true;
        }

        return false;
    }
    public static boolean isRepository(PackagedElement packagedElement, XMI xmi)
    {
        Iterator<Repository> it=xmi.getRepositories().listIterator();

        while (it.hasNext())
        {
           Repository repository = it.next();
            if(repository.getBaseClass().equals(packagedElement.getId()))
                return true;
        }

        return false;
    }


}
