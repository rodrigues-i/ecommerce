package com.rodrigues.ecommerce.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	@NotBlank(message = "firstName is mandatory")
	private String firstName;
	@NotBlank(message = "familyName is mandatory")
	private String familyName;
	@NotBlank(message = "email is mandatory")
	private String email;
	@NotBlank(message = "password is mandatory")
	private String password;
}
