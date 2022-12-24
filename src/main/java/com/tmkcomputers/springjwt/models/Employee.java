package com.tmkcomputers.springjwt.models;

import org.hibernate.annotations.NaturalId;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "employees")
public @Data class Employee {

	@EmbeddedId
	private EmployeeIdentity employeeIdentity;

	@NotNull
	@Size(max = 60)
	private String name;

	@NaturalId
	@NotNull
	@Email
	@Size(max = 60)
	private String email;

	@Size(max = 15)
	@Column(name = "phone_number", unique = true)
	private String phoneNumber;

}
