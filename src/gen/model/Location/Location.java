package gen.model.Location;

import gen.annotation.Entity;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;


@Entity(identifier = "portCode")

public class Location{


	 
	
	//�����ɵ�ʵ������
	@DomainAttribute
	private String portCode;
	
	 
		
		
	//���ι��캯��
	public Location(String portCode){
	 
		this.portCode=portCode;
	
	}
	
	//�����ι��캯��
	public Location(){
	
	}
	



	//���Ե�getter��setter����
	 
	 
	public String getPortCode(){
	
		return this.portCode;
		
	}
	
	public void setPortCode(String cur){
	
		this.portCode=portCode;
	
	}
		
}
