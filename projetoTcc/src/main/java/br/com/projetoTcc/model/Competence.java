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

	@Enumerated(EnumType.STRING)
	@Column(name = "categorie", nullable = true)
	private Categories categorie;
	
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

	public Categories getCategorie() {
		return this.categorie;
	}

	public void setCategorie(Categories categorie) {
		this.categorie = categorie;
	} 
	    
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        	Competence competence = (Competence) o;
        	return id == competence.id &&
                user == competence.user &&
                		  Objects.equals(description, competence.description) &&
                		  Objects.equals(categorie, competence.categorie); 

      }

	    @Override
	    public int hashCode() {
	        return Objects.hash(id, description, categorie, user);
	    }
}
