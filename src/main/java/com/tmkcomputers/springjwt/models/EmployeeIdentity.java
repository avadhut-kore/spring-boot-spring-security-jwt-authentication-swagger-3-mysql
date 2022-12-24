package com.tmkcomputers.springjwt.models;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class EmployeeIdentity implements Serializable {
	@NotNull
	@Size(max = 20)
	private String employeeId;

	@NotNull
	@Size(max = 20)
	private String companyId;
}
