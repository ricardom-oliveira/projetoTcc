package br.com.projetoTcc.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.projetoTcc.model.enums.Categories;

@Entity
@Table(name = "competence", schema = "tccdb")
public class Competence {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true)
	private int id;	

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "categorie", nullable = true)
	private String categorie;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
 
	    
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        	Competence competence = (Competence) o;
        	return id == competence.id &&
                user == competence.user &&
                		  Objects.equals(description, competence.description) &&
                		  Objects.equals(getCategorie(), competence.getCategorie()); 

      }

	    @Override
	    public int hashCode() {
	        return Objects.hash(id, description, getCategorie(), user);
	    }

		public String getCategorie() {
			return categorie;
		}

		public void setCategorie(String categorie) {
			this.categorie = categorie;
		}
}
