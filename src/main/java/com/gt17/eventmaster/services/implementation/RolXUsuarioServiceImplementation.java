package com.gt17.eventmaster.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt17.eventmaster.models.entities.Rol;
import com.gt17.eventmaster.models.entities.RolXUsuario;
import com.gt17.eventmaster.models.entities.User;
import com.gt17.eventmaster.repositories.RolXUsuarioRepository;
import com.gt17.eventmaster.services.RolXUsuarioService;

@Service
public class RolXUsuarioServiceImplementation implements RolXUsuarioService{
	
	@Autowired
	private final RolXUsuarioRepository rolXUsuarioRepository;
	
	@Autowired
	public RolXUsuarioServiceImplementation(RolXUsuarioRepository rolXUsuarioRepository) {
		this.rolXUsuarioRepository = rolXUsuarioRepository;
	}

	@Override
	public List<RolXUsuario> findAll() {
		return rolXUsuarioRepository.findAll();
	}

	@Override
	public List<RolXUsuario> findByUser(User user) {
		return rolXUsuarioRepository.findByUser(user);
	}

	@Override
	public void save(Rol rol, User user) throws Exception {
		
		RolXUsuario existingRXU = rolXUsuarioRepository.findOneRolXUsuarioByRolAndUser(rol, user);
		if(existingRXU != null) {
			throw new IllegalArgumentException("Este usuario ya tiene este rol asignado.");
		}
		RolXUsuario RXU = new RolXUsuario(
				rol,
				user
				);
				rolXUsuarioRepository.save(RXU);
	}
	
	

}
