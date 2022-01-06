package gen.model.Voyage;

import gen.annotation.AggregateRoot;
import gen.annotation.Entity;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;

@AggregateRoot
@Entity(identifier = "voyageNumber")

public class Voyage{


	 
	
	//�����ɵ�ʵ������
	@DomainAttribute
	private String voyageNumber;
	
	 
		
		
	//���ι��캯��
	public Voyage(String voyageNumber){
	 
		this.voyageNumber=voyageNumber;
	
	}
	
	//�����ι��캯��
	public Voyage(){
	
	}
	



	//���Ե�getter��setter����
	 
	 
	public String getVoyageNumber(){
	
		return this.voyageNumber;
		
	}
	
	public void setVoyageNumber(String cur){
	
		this.voyageNumber=voyageNumber;
	
	}
		
}
