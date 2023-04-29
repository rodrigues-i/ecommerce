package com.rodrigues.ecommerce.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Novel {

	private Long novelId;
	@NotBlank(message = "Name is required")
	private String name;
	@NotBlank(message = "Description is required")
	private String description;
}