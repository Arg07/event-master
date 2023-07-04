package com.gt17.eventmaster.models.entities;


import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "rol")
public class Rol {
	
	@Column(name="id")
	@Id
	private String id;
	
	@Column(name="rol")
	private String rol;
	
	@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	public Rol(@NotEmpty String id, @NotEmpty String rol) {
		super();
		this.id=id;
		this.rol=rol;
	}
	
}
