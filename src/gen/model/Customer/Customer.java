package gen.model.Customer;

import gen.annotation.Entity;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;


@Entity(identifier = "customerId")

public class Customer{


	 
	
	//�����ɵ�ʵ������
	@DomainAttribute
	private String name;
	 
	
	//�����ɵ�ʵ������
	@DomainAttribute
	private String customerId;
	
	 
	 
		
		
	//���ι��캯��
	public Customer(String name,String customerId){
	 
		this.name=name;
	 
		this.customerId=customerId;
	
	}
	
	//�����ι��캯��
	public Customer(){
	
	}
	



	//���Ե�getter��setter����
	 
	 
	public String getName(){
	
		return this.name;
		
	}
	
	public void setName(String cur){
	
		this.name=name;
	
	}
		
	 
	 
	public String getCustomerId(){
	
		return this.customerId;
		
	}
	
	public void setCustomerId(String cur){
	
		this.customerId=customerId;
	
	}
		
}
