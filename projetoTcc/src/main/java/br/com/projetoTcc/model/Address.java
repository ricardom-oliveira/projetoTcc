package br.com.projetoTcc.model;

import javax.persistence.*;

@Entity
@Table(name = "address", schema = "tccdb")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "zipcode", nullable = true)
	private String zipcode;

	@Column(name = "district", nullable = true)
	private String district;

	@Column(name = "street")
	private String street;

	@Column(name = "number")
	private String number;

	@Column(name = "city")
	private String city;
	
	@Column(name = "country")
	private String country;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	

}
