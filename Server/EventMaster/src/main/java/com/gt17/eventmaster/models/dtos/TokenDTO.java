package com.gt17.eventmaster.models.dtos;

import com.gt17.eventmaster.models.dtos.TokenDTO;
import com.gt17.eventmaster.models.entities.Token;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDTO {
private String token;
	
	public TokenDTO(Token token) {
		this.token = token.getContent();
	}
}
