package com.soft.ms.customer.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Salah Abu Msameh
 */
@Entity
@Table(name = "CUSTOMERS")
@JsonInclude(Include.NON_NULL)
public class Customer {

	private long id;
	private String name;
	private String fullName;
	private String firstName;
	private String middleName;
	private String lastName;
	
	private String civilId;
	private String nationality;
	private String age;
	private String mobileNo;
	private String email;
	//private Map<String, String> customProperties;
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonProperty("full_name")
	@Column(name = "FULL_NAME")
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	@JsonProperty("first_name")
	@Column(name = "FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@JsonProperty("middle_name")
	@Column(name = "MIDDLE_NAME")
	public String getMiddleName() {
		return middleName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	@JsonProperty("last_name")
	@Column(name = "LAST_NAME")
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@JsonProperty("civil_id")
	@Column(name = "CIVIL_ID")
	public String getCivilId() {
		return civilId;
	}
	
	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}
	
	@Column(name = "NATIONALITY")
	public String getNationality() {
		return nationality;
	}
	
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	@JsonProperty("age")
	@Column(name = "AGE")
	public String getAge() {
		return age;
	}
	
	public void setAge(String age) {
		this.age = age;
	}
	
	@JsonProperty("mobile_no")
	@Column(name = "MOBILE_NO")
	public String getMobileNo() {
		return mobileNo;
	}
	
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	@JsonProperty("email")
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
