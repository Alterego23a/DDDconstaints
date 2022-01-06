package gen.model.Cargo;

import gen.annotation.AggregateRoot;
import gen.annotation.Entity;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;

@AggregateRoot
@Entity(identifier = "trackingId")

public class Cargo{
	//�����ɵ�ʵ������
	@DomainAttribute
	private String trackingId;	
	//���ι��캯��
	public Cargo(String trackingId){
	 
		this.trackingId=trackingId;
	
	}		
}
