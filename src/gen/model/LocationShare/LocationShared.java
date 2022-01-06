package gen.model.LocationShare;


import gen.annotation.ValueObject;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;


@ValueObject

public class LocationShared{

	 
	
	//ֵ����������������
	@DomainAttribute
	private String portCode;


	@DomainAttribute
	private String name;
	
	 
		
		
	//����ֵ����ĺ��ι��캯��
	public LocationShared(String portCode){
	 
		this.portCode=portCode;
	
	}
	

	//����ֵ������޲ι��캯��
	public LocationShared(){
		this.name = "LocationShared";
	}
	
	



	//��ֵ������������Ӧ��getter��setter����
	 
	 
	public String getPortCode(){
	
		return this.portCode;
		
	}
	
	public void setPortCode(String cur){
	
		this.portCode=portCode;
	
	}
		
}

