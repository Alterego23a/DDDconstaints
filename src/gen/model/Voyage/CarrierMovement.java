package gen.model.Voyage;


import gen.annotation.AggregatePart;
import gen.annotation.ValueObject;
import gen.annotation.DomainAttribute;
import gen.annotation.DomainBehaviour;
import gen.model.Location.Location;

import java.util.Date;

@AggregatePart(aggregateRootType = Voyage.class)
@ValueObject

public class CarrierMovement{

	 
	
	//ֵ����������������
	@DomainAttribute
	private Location departmentLocation;
	 
	
	//ֵ����������������
	@DomainAttribute
	private Location arriveLocation;
	 
	
	//ֵ����������������
	@DomainAttribute
	private Date departureTime;
	 
	
	//ֵ����������������
	@DomainAttribute
	private Date arriveTime;

	@DomainAttribute
	private String name;
	 
	 
	 
	 
		
		
	//����ֵ����ĺ��ι��캯��
	public CarrierMovement(Location departmentLocation,Location arriveLocation,Date departureTime,Date arriveTime){
	 
		this.departmentLocation=departmentLocation;
	 
		this.arriveLocation=arriveLocation;
	 
		this.departureTime=departureTime;
	 
		this.arriveTime=arriveTime;
	
	}
	

	//����ֵ������޲ι��캯��
	public CarrierMovement(){
		this.name = "CarrierMovement";
	}
	
	



	//��ֵ������������Ӧ��getter��setter����
	 
	 
	public Location getDepartmentLocation(){
	
		return this.departmentLocation;
		
	}
	
	public void setDepartmentLocation(Location cur){
	
		this.departmentLocation=departmentLocation;
	
	}
		
	 
	 
	public Location getArriveLocation(){
	
		return this.arriveLocation;
		
	}
	
	public void setArriveLocation(Location cur){
	
		this.arriveLocation=arriveLocation;
	
	}
		
	 
	 
	public Date getDepartureTime(){
	
		return this.departureTime;
		
	}
	
	public void setDepartureTime(Date cur){
	
		this.departureTime=departureTime;
	
	}
		
	 
	 
	public Date getArriveTime(){
	
		return this.arriveTime;
		
	}
	
	public void setArriveTime(Date cur){
	
		this.arriveTime=arriveTime;
	
	}
		
}

