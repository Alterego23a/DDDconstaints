package gen.model.CustomerShare;


import gen.annotation.ValueObject;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;


@ValueObject

public class CustomerShare{

	 
	
	//ֵ����������������
	@DomainAttribute
	private String CustomerId;

	@DomainAttribute
	private String name;
	 
		
		
	//����ֵ����ĺ��ι��캯��
	public CustomerShare(String CustomerId){
	 
		this.CustomerId=CustomerId;
	
	}
	

	//����ֵ������޲ι��캯��
	public CustomerShare(){
		this.name = "CustomerShare";
	}
	
	



	//��ֵ������������Ӧ��getter��setter����
	 
	 
	public String getCustomerId(){
	
		return this.CustomerId;
		
	}
	
	public void setCustomerId(String cur){
	
		this.CustomerId=CustomerId;
	
	}
		
}

