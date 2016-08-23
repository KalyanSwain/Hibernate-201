package Day2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class WishList {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;


	@Column()
	long Priority;
	@ManyToOne
	UserDetails UID;
	
	public WishList(long priority,UserDetails uID, MovieDomain mID) {
		super();
		UID = uID;
		MID = mID;
		Priority =priority;
	}
	
	public long getPriority() {
		return Priority;
	}
	public void setPriority(long priority) {
		this.Priority = priority;
	}
	
	public WishList() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDetails getUID() {
		return UID;
	}

	public void setUID(UserDetails uID) {
		UID = uID;
	}

	public MovieDomain getMID() {
		return MID;
	}

	public void setMID(MovieDomain mID) {
		MID = mID;
	}

	@ManyToOne
	MovieDomain MID;

}
