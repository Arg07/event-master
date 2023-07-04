package com.gt17.eventmaster.services.implementation;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gt17.eventmaster.repositories.UserRepository;
import com.gt17.eventmaster.utils.JWTTools;
import com.gt17.eventmaster.models.dtos.UserDTO;
import com.gt17.eventmaster.models.entities.Token;
import com.gt17.eventmaster.repositories.TokenRepository;
import com.gt17.eventmaster.models.entities.User;
import com.gt17.eventmaster.services.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private JWTTools jwtTools;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	public PasswordEncoder passwordEncoder;
	
	@Autowired
    private final UserRepository userRepository;
	
	@Autowired
	   public UserServiceImplementation(UserRepository userRepository) {
	       this.userRepository = userRepository;
	   }
	
	@Override
	public User findOneByIdentifier(String username, String email) {
		return userRepository.findByUsernameOrEmail(username, email);
	}
	
	@Override
	public Boolean comparePassword(String toCompare, String current) {
		return passwordEncoder.matches(toCompare, current);
	}
	
	@Override
    @Transactional(rollbackOn = Exception.class)
    public User createUser(UserDTO userDTO) {
        if (userDTO.getUsername() == null || userDTO.getUsername().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es requerido.");
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico es requerido.");
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida.");
        }
        if (userDTO.getName() == null || userDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida.");
        }

        User existingUser = userRepository.findByUsernameOrEmail(userDTO.getUsername(),userDTO.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("Ya existe un usuario con ese nombre de usuario.");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setName(userDTO.getName());

        return userRepository.save(user);
    }
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public Token registerToken(User user) throws Exception {
		cleanTokens(user);
		
		String tokenString = jwtTools.generateToken(user);
		Token token = new Token(tokenString, user);
		
		tokenRepository.save(token);
		
		return token;
	}

	@Override
	public Boolean isTokenValid(User user, String token) {
		try {
			cleanTokens(user);
			List<Token> tokens = tokenRepository.findByUserAndActive(user, true);
			
			tokens.stream()
				.filter(tk -> tk.getContent().equals(token))
				.findAny()
				.orElseThrow(() -> new Exception());
			
			return true;
		} catch (Exception e) {
			return false;
		}		
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void cleanTokens(User user) throws Exception {
		List<Token> tokens = tokenRepository.findByUserAndActive(user, true);
		
		tokens.forEach(token -> {
			if(!jwtTools.verifyToken(token.getContent())) {
				token.setActive(false);
				tokenRepository.save(token);
			}
		});
		
	}
	
	@Override
	public User findUserAuthenticated() {
		String username = SecurityContextHolder
			.getContext()
			.getAuthentication()
			.getName();
		
		return userRepository.findByUsernameOrEmail(username, username);
	}
	
}