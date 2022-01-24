package validation;

import java.io.IOException;

public class Validation {



    public static void main(String[] args) throws IOException {

//----------------------------------Entity-------------------------------------
        if(EntityValidation.entityCheck()==false)
            System.out.println("error:An entity has and only has one identity.");
       /* if(EntityValidation.entityCheck2()==false)
            System.out.println("error:An entity should has at least one domain behaviour.");*/
        if(EntityValidation.entityCheck2()==false)
            System.out.println("error:The identity of an entity should be designed as the composition of one or several it attributes.");

        if(EntityValidation.entityCheck3()==false)
            System.out.println("error:An entity should has at least one domain behavior.");
//----------------------------------Value Object------------------------------------------------
       if(ValueObjectValidation.valueObjectCheck()==false)
           System.out.println("error:A value object does not have an identity");



//---------------------------------DomainEvent------------------------------------------------

        if(DomainEventValidation.domainEventCheck() ==false)
            System.out.println("error:A domain event has and only has one identity.");
        if(DomainEventValidation.domainEventCheck2() ==false)
            System.out.println("error:The identity of an entity should be designed as the composition of one or several it attributes.");
        if(DomainEventValidation.domainEventCheck3() ==false)
            System.out.println("error:A domain event needs a timestamp that records the time when the event happens.");
        if(DomainEventValidation.domainEventCheck4() ==false)
            System.out.println("error:A domain event needs to specify the publisher and subscriber of the event.");





 //---------------------------------DomainService------------------------------------------------

        if(DomainServiceValidation.domainServiceCheck() ==false)
            System.out.println("error:A domain service is stateless.");
        if(DomainServiceValidation.domainServiceCheck2() ==false)
            System.out.println("error:A domain service should not be designed as other patterns at the same time.");


//---------------------------------Repository------------------------------------------------

        if(RepositoryValidation.repositoryCheck()==false)
            System.out.println("error:A repository needs to specify the object for which it is responsible for data access. The type of the object can be entity, value object, and aggregate root.");
       if(RepositoryValidation.repositoryCheck2()==false)
           System.out.println("error:A repository has no attributes.");
        if(RepositoryValidation.repositoryCheck3()==false)
            System.out.println("error:A factory should not be designed as other patterns at the same time.");



//---------------------------------Factory------------------------------------------------


        if(FactoryValidation.factoryCheck()==false)
            System.out.println("error:A factory needs to specify the object that it is responsible for creating. The type of the object can be entity, value object, and aggregate.");

        if(FactoryValidation.FactoryCheck2()==false)
            System.out.println("error:A factory should not be designed as other patterns at the same time.");





//---------------------------------AggregateRoot------------------------------------------------
        if(AggregateRootValidation.aggregateRootCheck()==false)
            System.out.println("error:The root of an aggregate can only be designed as an entity.");




//---------------------------------AggregatePart------------------------------------------------
        if(AggregatePartValidation.aggregatePartCheck()==false)
            System.out.println("error:The aggregate part can only be designed as an entity or a value object.");
        if(AggregatePartValidation.aggregatePartCheck2()==false)
            System.out.println("error:The reference of an aggregate part cannot be held by the outside objects");
//---------------------------------Aggregate------------------------------------------------
        if(AggregateValidation.aggregateCheck() ==false)
            System.out.println(" error:An aggregate has one and only one aggregate root.");
        if(AggregateValidation.aggregateCheck2() ==false)
            System.out.println(" error:Except the aggregate root, an aggregate can only contain aggregate parts.");
        if(AggregateValidation.aggregateCheck3() ==false)
            System.out.println(" error: The creation of an aggregate should be done by a factory. ");
        if(AggregateValidation.aggregateCheck4() ==false)
            System.out.println(" error:The accessing of an aggregate should be done by a repository. ");
        if(AggregateValidation.aggregateCheck5() ==false)
            System.out.println(" error:The objects within an aggregate should not be crosscutting different bounded contexts. ");




     //   System.out.println("ok");
    }
}
