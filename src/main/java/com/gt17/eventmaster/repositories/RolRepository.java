package com.gt17.eventmaster.repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.gt17.eventmaster.models.entities.Rol;

public interface RolRepository extends ListCrudRepository<Rol, String> {
	Rol findRolById(String id);	
}
