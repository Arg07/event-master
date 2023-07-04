package com.gt17.eventmaster.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
	@NotEmpty
	private String name;
	@NotEmpty
    private String username;
	@NotEmpty
    private String email;
	@NotEmpty
    private String password;
}
