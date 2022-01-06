package gen.model.Cargo;

import gen.annotation.AggregatePart;
import gen.annotation.Entity;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;

import java.util.Date;

@AggregatePart(aggregateRootType = Cargo.class)
@Entity(identifier = "handlingEventId")

public class HandlingEvent{


	 
	
	//�����ɵ�ʵ������
	@DomainAttribute
	private Date completionTime;
	 
	
	//�����ɵ�ʵ������
	@DomainAttribute
	private String handlingEventId;
	
	 
	 
		
		
	//���ι��캯��
	public HandlingEvent(Date completionTime, String handlingEventId){
	 
		this.completionTime=completionTime;
	 
		this.handlingEventId=handlingEventId;
	
	}
	
	//�����ι��캯��
	public HandlingEvent(){
	
	}
	



	//���Ե�getter��setter����
	 
	 
	public Date getCompletionTime(){
	
		return this.completionTime;
		
	}
	
	public void setCompletionTime(Date cur){
	
		this.completionTime=completionTime;
	
	}
		
	 
	 
	public String getHandlingEventId(){
	
		return this.handlingEventId;
		
	}
	
	public void setHandlingEventId(String cur){
	
		this.handlingEventId=handlingEventId;
	
	}
		
}
