package com.rodrigues.ecommerce.entity;

import javax.persistence.Column;
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
public class Novel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long novelId;
	@NotBlank(message = "Name is required")
	private String name;
	@NotBlank(message = "Description is required")
	@Column(columnDefinition = "TEXT", length = 255)
	private String description;
}