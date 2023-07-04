package com.gt17.eventmaster.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gt17.eventmaster.models.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
	
	User findByUsernameOrEmail(String username, String email);
	
}
