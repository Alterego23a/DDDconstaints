package validation;

import java.io.IOException;

public class Validation {
    public static void main(String[] args) throws IOException {
        boolean flag=true;
       if(DomainServiceValidation.domainServiceCheck()==false)
           System.out.println("领域服务错误");
        if(EntityValidation.entityCheck()==false)
            System.out.println("实体错误");
//        if(ValueObjectValidation.valueObjectCheck()==false)
//            System.out.println("值对象错误");
//        if(RepositoryValidation.repositoryCheck()==false)
//            System.out.println("资源库错误");

     //   System.out.println("ok");
    }
}
