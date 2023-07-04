package com.gt17.eventmaster.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gt17.eventmaster.models.entities.Rol;
import com.gt17.eventmaster.models.entities.RolXUsuario;
import com.gt17.eventmaster.models.entities.User;

@Service
public interface RolXUsuarioService {
	
	List<RolXUsuario> findAll();
	List<RolXUsuario> findByUser(User user);
	void save(Rol rol, User user) throws Exception;
}
