package Day3;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="simp")
public class SimpleObject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column
	String key1;
	
	@Column
	Long value;
	
	public SimpleObject(){
		
	}

	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getKey() {
		return key1;
	}

	public void setKey(String key) {
		this.key1 = key;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((key1 == null) ? 0 : key1.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}


//	@Override
//	public boolean equals(Object obj) {
//		SimpleObject o = (SimpleObject)obj;
//		if(this.key1.equals(o.getKey())){
//			System.out.println("They are same");
//			return true;
//		} 
//		return false;
//	}

    
	@Override
	public String toString() {
		return "SimpleObject [id=" + id + ", key1=" + key1 + ", value=" + value + "]";
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleObject other = (SimpleObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (key1 == null) {
			if (other.key1 != null)
				return false;
		} else if (!key1.equals(other.key1))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
}