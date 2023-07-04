package com.gt17.eventmaster.models.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RolXUsuarioDTO {
	private String user_identifier;
	private String id_rol;
}

