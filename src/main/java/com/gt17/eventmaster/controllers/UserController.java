package com.gt17.eventmaster.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt17.eventmaster.models.dtos.TokenDTO;
import com.gt17.eventmaster.models.entities.Rol;
import com.gt17.eventmaster.models.entities.RolXUsuario;
import com.gt17.eventmaster.models.entities.Token;
import com.gt17.eventmaster.models.dtos.UserDTO;
import com.gt17.eventmaster.utils.RequestErrorHandler;
import com.gt17.eventmaster.models.dtos.LoginDTO;
import com.gt17.eventmaster.models.dtos.RolXUsuarioDTO;
import com.gt17.eventmaster.models.entities.User;
import com.gt17.eventmaster.services.RolService;
import com.gt17.eventmaster.services.RolXUsuarioService;
import com.gt17.eventmaster.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RolXUsuarioService rolXUsuarioService;
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private RequestErrorHandler errorHandler;
	
    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@ModelAttribute UserDTO userDTO, BindingResult validations) {
    	
    	if(validations.hasErrors()) {
    		return new ResponseEntity<> (
        			errorHandler.mapErrors(validations.getFieldErrors()),HttpStatus.BAD_REQUEST);
    	}    	
        try {
            userService.createUser(userDTO);
            return new ResponseEntity<>("Usuario creado con éxito", HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    
}
    
    @PostMapping("/add-role")
    public ResponseEntity<?> addRoleToUser(@ModelAttribute RolXUsuarioDTO rxuDTO, BindingResult validations){
    	if(validations.hasErrors()) {
    		return new ResponseEntity<> (
        			errorHandler.mapErrors(validations.getFieldErrors()),HttpStatus.BAD_REQUEST);
    	}
    	
    	User user = userService.findOneByIdentifier(rxuDTO.getUser_identifier(), rxuDTO.getUser_identifier());
    	Rol rol = rolService.findOneById(rxuDTO.getId_rol());
    	List<RolXUsuario> rolesXUsuarios = rolXUsuarioService.findAll();
    	boolean flag = true;
    	
    	for(int i = 0; i < rolesXUsuarios.size(); i++) {
    		if(rolesXUsuarios.get(i).getRol().equals(rol) && rolesXUsuarios.get(i).getUser().equals(user)) {
    			flag = false;
    			break;
    		}
    	}
    	if(!flag) {
    		return new ResponseEntity<>("Este usuario ya posee este rol", HttpStatus.BAD_REQUEST);
    	}
    	if(user != null && rol != null) {
    	try {
    		rolXUsuarioService.save(rol, user);
    		return new ResponseEntity<>("Rol asignado al usuario.", HttpStatus.CREATED);
    	}catch (Exception e){
    		e.printStackTrace();
            return new ResponseEntity<>("Internal server error.", HttpStatus.BAD_REQUEST);
        }
    	}
    	return new ResponseEntity<>("No se encontro el usuario", HttpStatus.NOT_FOUND);
    }
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@ModelAttribute @Valid LoginDTO info, BindingResult validations){
    	if(validations.hasErrors()) {
			return new ResponseEntity<>("Error inesperado", HttpStatus.BAD_REQUEST);
		}
		
		User user = userService.findOneByIdentifier(info.getIdentifier(), info.getIdentifier());
		
		if(user == null) {
			return new ResponseEntity<>("Usuario inexistente", HttpStatus.NOT_FOUND);
		}
		
		if(!userService.comparePassword(info.getPassword(), user.getPassword1())) {
			return new ResponseEntity<>("Error de credenciales", HttpStatus.UNAUTHORIZED);
		}
		try {
			Token token = userService.registerToken(user);
			return new ResponseEntity<>(new TokenDTO(token), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
