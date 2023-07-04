package com.gt17.eventmaster.services;

import org.springframework.stereotype.Service;

import com.gt17.eventmaster.models.entities.Rol;

@Service
public interface RolService {
	Rol findOneById(String id);
}
