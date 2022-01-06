package gen.model.Cargo;

import gen.annotation.AggregateRoot;
import gen.annotation.Entity;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;

@AggregateRoot
@Entity(identifier = "itineraryNumber")

public class Itinerary{


	 
	
	//�����ɵ�ʵ������
	@DomainAttribute
	private String itineraryNumber;
	
	 
		
		
	//���ι��캯��
	public Itinerary(String itineraryNumber){
	 
		this.itineraryNumber=itineraryNumber;
	
	}
	
	//�����ι��캯��
	public Itinerary(){
	
	}
	



	//���Ե�getter��setter����
	 
	 
	public String getItineraryNumber(){
	
		return this.itineraryNumber;
		
	}
	
	public void setItineraryNumber(String cur){
	
		this.itineraryNumber=itineraryNumber;
	
	}
		
}
