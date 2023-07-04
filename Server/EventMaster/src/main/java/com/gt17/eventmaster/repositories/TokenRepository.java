package com.gt17.eventmaster.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.gt17.eventmaster.models.entities.Token;
import com.gt17.eventmaster.models.entities.User;

public interface TokenRepository 
extends ListCrudRepository<Token, UUID>{ 

List<Token> findByUserAndActive(User user, Boolean active);

}