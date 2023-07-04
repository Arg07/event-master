package com.gt17.eventmaster.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.gt17.eventmaster.models.dtos.UserDTO;
import com.gt17.eventmaster.models.entities.Token;
import com.gt17.eventmaster.models.entities.User;

@Service
public interface UserService {

	User createUser(UserDTO userDTO);
	User findOneByIdentifier(String username, String email);
    Boolean comparePassword(String toCompare, String current);
	Token registerToken(User user) throws Exception;
	Boolean isTokenValid(User user, String token);
	void cleanTokens(User user) throws Exception;
	User findUserAuthenticated();
}
