package Day4;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ComputerBook extends BookSuperClass{
	
	@Column
	String language;

	public ComputerBook() {
		super();
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}