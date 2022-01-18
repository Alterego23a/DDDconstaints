package validation;

import entity.tag.DomainEvent;

import java.io.IOException;

public class Validation {



    public static void main(String[] args) throws IOException {
        boolean flag=true;
       if(DomainServiceValidation.domainServiceCheck()==false)
           System.out.println("error:A domain service should not be designed as other patterns at the same time.");
        if(EntityValidation.entityCheck()==false)
            System.out.println("error:An entity has and only has one identity.");
        if(EntityValidation.entityCheck2()==false)
            System.out.println("error:An entity should has at least one domain behaviour.");
        if(EntityValidation.entityCheck3()==false)
            System.out.println("error:The identity of an entity should be designed as the composition of one or several it attributes.");
       if(ValueObjectValidation.valueObjectCheck()==false)
           System.out.println("error:A value object does not have an identity");
       if(RepositoryValidation.repositoryCheck()==false)
           System.out.println("error:A repository has no attributes");
        if(RepositoryValidation.repositoryCheck2()==false)
            System.out.println("error:A repository needs to specify the object for which it is responsible for data access. The type of the object can be entity, value object, and aggregate root.");
        if(AggregatedPartValidation.aggregatePartCheck()==false)
            System.out.println("error:The reference of an aggregate part cannot be held by the outside objects");
        if(DomainEventValidation.domainEventCheck() ==false)
            System.out.println("error:A domain event has and only has one identity.");
        if(DomainEventValidation.domainEventCheck2() ==false)
            System.out.println("error:The identiy of an entity should be designed as the composition of one or several it attributes.");


        if(AggregateValidation.aggregateCheck() ==false)
            System.out.println(" error:An aggregate’s data access has and can only be managed by a Repository.");
        if(AggregateValidation.aggregateCheck2() ==false)
            System.out.println(" error:Aggregates can only contain the aggregate root and the aggregate part.");
        if(AggregateValidation.aggregateCheck3() ==false)
            System.out.println(" error: Aggregates have one and only one aggregate root. ");
        if(AggregateValidation.aggregateCheck4() ==false)
            System.out.println(" error:The objects within an aggregate should not be crosscutting different bounded contexts. ");
        if(AggregateValidation.aggregateCheck5() ==false)
            System.out.println(" error:Association between any two aggregates is not allowed through object reference. ");




     //   System.out.println("ok");
    }
}
