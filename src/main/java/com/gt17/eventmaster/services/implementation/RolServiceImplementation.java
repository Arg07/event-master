package com.gt17.eventmaster.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt17.eventmaster.models.entities.Rol;
import com.gt17.eventmaster.repositories.RolRepository;
import com.gt17.eventmaster.services.RolService;

@Service
public class RolServiceImplementation implements RolService {
	
	@Autowired
	private final RolRepository rolRepository;
	
	@Autowired
	public RolServiceImplementation(RolRepository rolRepository) {
		this.rolRepository = rolRepository;
	}

	@Override
	public Rol findOneById(String id) {
		return rolRepository.findRolById(id);
	}

}
