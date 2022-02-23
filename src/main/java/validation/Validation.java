package validation;

import entity.PackagedElement;

import java.io.IOException;

public class Validation {



    public static void main(String[] args) throws IOException {

     /*   try {
            EntityValidation.entityCheck1();
        } catch (PkException pkE) {
            System.out.println(pkE.packagedElement.getName());
        } catch (Exception e) {
            //
        }*/

        PackagedElement packagedElement=new PackagedElement();
//----------------------------------Entity-------------------------------------
      packagedElement=EntityValidation.entityCheck();
        if(packagedElement!=null)
            System.out.printf("%s   :error:An entity has and only has one identity.\n",packagedElement.getName());
        packagedElement=EntityValidation.entityCheck2();
        if(packagedElement!=null)
           System.out.printf("%s   :error:The identity of an entity should be designed as the composition of one or several it attributes.\n",packagedElement.getName());
        packagedElement=EntityValidation.entityCheck3();
        if(packagedElement!=null)
            System.out.printf("%s   :error:An entity should has at least one domain behavior.\n",packagedElement.getName());
//----------------------------------Value Object------------------------------------------------
        packagedElement=ValueObjectValidation.valueObjectCheck();
       if(packagedElement!=null)
           System.out.printf("%s   :error:A value object does not have an identity.\n",packagedElement.getName());



//---------------------------------DomainEvent------------------------------------------------
        packagedElement=DomainEventValidation.domainEventCheck();
        if(packagedElement!=null)
            System.out.printf("%s   :error:A domain event has and only has one identity.\n",packagedElement.getName());
        packagedElement=DomainEventValidation.domainEventCheck2();
        if(packagedElement!=null)
            System.out.printf("%s   :error:The identity of a domainevent should be designed as the composition of one or several it attributes.\n",packagedElement.getName());
        packagedElement=DomainEventValidation.domainEventCheck3();
        if(packagedElement!=null)
            System.out.printf("%s   :error:A domain event needs a timestamp that records the time when the event happens.\n",packagedElement.getName());
        packagedElement=DomainEventValidation.domainEventCheck4();
        if(packagedElement!=null)
            System.out.printf("%s   :error:A domain event needs to specify the publisher and subscriber of the event.\n",packagedElement.getName());
      //  System.out.println("ok2");




 //---------------------------------DomainService------------------------------------------------
        packagedElement=DomainServiceValidation.domainServiceCheck();
        if(packagedElement!=null)
            System.out.printf("%s   :error:A domain service is stateless.\n",packagedElement.getName());
        packagedElement=DomainServiceValidation.domainServiceCheck2();
        if(packagedElement!=null)
            System.out.printf("%s   :error:A domain service should not be designed as other patterns at the same time.\n",packagedElement.getName());
    //    System.out.println("ok3");

//---------------------------------Repository------------------------------------------------
        packagedElement=RepositoryValidation.repositoryCheck();
        if(packagedElement!=null)
            System.out.printf("%s   :error:A repository needs to specify the object for which it is responsible for data access. The type of the object can be entity, value object, and aggregate root.\n",packagedElement.getName());
        packagedElement=(RepositoryValidation.repositoryCheck2());
       if(packagedElement!=null)
           System.out.printf("%s   :error:A repository has no attributes.\n",packagedElement.getName());
        packagedElement=RepositoryValidation.repositoryCheck3();
        if(packagedElement!=null)
            System.out.printf("%s   :error:A factory should not be designed as other patterns at the same time.\n",packagedElement.getName());
    //    System.out.println("ok4");


//---------------------------------Factory------------------------------------------------

        packagedElement=FactoryValidation.factoryCheck();
        if(packagedElement!=null)
            System.out.printf("%s   :error:A factory needs to specify the object that it is responsible for creating. The type of the object can be entity, value object, and aggregate.\n",packagedElement.getName());
        packagedElement=FactoryValidation.FactoryCheck2();
        if(packagedElement!=null)
            System.out.printf("%s   :error:A factory should not be designed as other patterns at the same time.\n",packagedElement.getName());

     //   System.out.println("ok5");



//---------------------------------AggregateRoot------------------------------------------------
        packagedElement=AggregateRootValidation.aggregateRootCheck();
        if(packagedElement!=null)
            System.out.printf("%s   :error:The root of an aggregate can only be designed as an entity.\n",packagedElement.getName());


    //    System.out.println("ok6");

//---------------------------------AggregatePart------------------------------------------------
       packagedElement=AggregatePartValidation.aggregatePartCheck();
        if(packagedElement!=null)
            System.out.printf("%s   :error:The aggregate part can only be designed as an entity or a value object.\n",packagedElement.getName());
        packagedElement=AggregatePartValidation.aggregatePartCheck2();
        if(packagedElement!=null)
            System.out.printf("%s   :error:The reference of an aggregate part cannot be held by the outside objects.\n",packagedElement.getName());
   //     System.out.println("ok7");
//---------------------------------Aggregate------------------------------------------------
        packagedElement=AggregateValidation.aggregateCheck();
        if(packagedElement !=null)
            System.out.printf("%s   :error:An aggregate has one and only one aggregate root.\n",packagedElement.getName());
        packagedElement=AggregateValidation.aggregateCheck2();
        if(packagedElement !=null)
            System.out.printf("%s   :error:Except the aggregate root, an aggregate can only contain aggregate parts.\n",packagedElement.getName());
        packagedElement=AggregateValidation.aggregateCheck3();
        if(packagedElement !=null)
            System.out.printf("%s   :error: The creation of an aggregate should be done by a factory.\n ",packagedElement.getName());
        packagedElement=AggregateValidation.aggregateCheck4();
        if(packagedElement !=null)
            System.out.printf("%s   :error:The accessing of an aggregate should be done by a repository.\n ",packagedElement.getName());
        packagedElement=AggregateValidation.aggregateCheck5();
        if(packagedElement !=null)
            System.out.printf("%s   :error:The objects within an aggregate should not be crosscutting different bounded contexts.\n ",packagedElement.getName());




     //  System.out.println("ok8");
    }
}
