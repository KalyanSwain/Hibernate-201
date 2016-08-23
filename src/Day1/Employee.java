package Day1;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee {
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	Long id;
	
	@Column
	String name;
	
	public Employee(){
		
	}
	
	public Employee(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}
	

}