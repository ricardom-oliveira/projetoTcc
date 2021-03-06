package br.com.projetoTcc.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.projetoTcc.model.enums.Gender;
import br.com.projetoTcc.model.enums.Status;

@Entity
@Table(name = "user", schema = "tccdb")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "username", unique = true, length = 30)
	private String username;

	@Column(name = "password")
	private String password;

	@Transient
	@Column(name = "password_2")
	private String password_2;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "role")
	private int role;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "birth_date")
	private LocalDate birthDate;

	@Column(name = "name")
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private Gender gender;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;

	@OneToMany(mappedBy = "user", targetEntity = Competence.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Collection<Competence> competences;
	
	@OneToMany(mappedBy = "userRequest", targetEntity = Match.class, cascade = CascadeType.ALL)  
	private Collection<Match> matchsRequest;
	
	@OneToMany(mappedBy = "userReceiver", targetEntity = Match.class,  cascade = CascadeType.ALL)
	private Collection<Match> matchsReceiver;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;
	
	
	@Column(name = "phone", length = 30)
	private String phoneNumber;
	
	@Column(name = "src_perfil_image")
	private String srcPerfilImage;

	public User() {
	}

	public User(String username, String password, String email, int role) {
		this.setUsername(username);
		this.setPassword(password);
		this.setEmail(email);
		this.setRole(role);

	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User) o;
		return id == user.id && role == user.role && Objects.equals(username, user.username)
				&& Objects.equals(password, user.password) && Objects.equals(password_2, user.password_2)
				&& Objects.equals(email, user.email) && Objects.equals(address, user.address)
				&& Objects.equals(name, user.name) && Objects.equals(gender, user.gender);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, username, password, password_2, email, role);
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword_2() {
		return password_2;
	}

	public void setPassword_2(String password_2) {
		this.password_2 = password_2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}


	public int getAge() {
		LocalDate today = LocalDate.now(); // Today's date
		LocalDate birthday = this.birthDate; // Birth date

		Period p = Period.between(birthday, today);

		return p.getYears();
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Collection<Competence> getCompetences() {
		return competences;
	}

	public void setCompetences(Collection<Competence> competences) {
		this.competences = competences;
	}

	public Collection<Match> getMatchsRequest() {
		return matchsRequest;
	}

	public void setMatchsRequest(Collection<Match> matchsRequest) {
		this.matchsRequest = matchsRequest;
	}

	public Collection<Match> getMatchsReceiver() {
		return matchsReceiver;
	}

	public void setMatchsReceiver(Collection<Match> matchsReceiver) {
		this.matchsReceiver = matchsReceiver;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSrcPerfilImage() {
		return srcPerfilImage;
	}

	public void setSrcPerfilImage(String srcPerfilImage) {
		this.srcPerfilImage = srcPerfilImage;
	}

	


}
