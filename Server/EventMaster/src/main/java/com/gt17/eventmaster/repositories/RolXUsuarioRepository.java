package com.gt17.eventmaster.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.gt17.eventmaster.models.entities.Rol;
import com.gt17.eventmaster.models.entities.RolXUsuario;
import com.gt17.eventmaster.models.entities.User;

public interface RolXUsuarioRepository extends ListCrudRepository<RolXUsuario, UUID> {

	List<RolXUsuario> findByUser(User user);
	RolXUsuario findOneRolXUsuarioByRolAndUser(Rol rol, User user);
	
}
