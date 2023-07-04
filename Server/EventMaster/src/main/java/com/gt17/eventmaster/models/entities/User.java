package com.gt17.eventmaster.models.entities;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gt17.eventmaster.models.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@ToString(exclude = {"tokens"})
@Table(name = "usuario")
public class User implements UserDetails {
	
	private static final long serialVersionUID = 1460435087476558985L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@OneToMany(mappedBy = "user" , fetch = FetchType.LAZY)
	private List<Rol> roles;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Token> tokens;
	
	@Column(name="username")
	@NotEmpty
	@Size(min = 4, max = 15)
	private String username;
	
	@Column(name="name")
	@NotEmpty
	@Size(min = 2, max = 15)
	private String name;
	
	@Column(name="password")
	@NotEmpty
	@JsonIgnore
	@Pattern(regexp = "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$")
	private String password;
	
	@Column(name="email")
	@NotEmpty
	@Email
	private String email;
	
	@Column(name= "active", insertable = false)
	private Boolean active;
	
	
	public User(@NotEmpty @Size(min = 2, max = 15) String name, @NotEmpty @Size(min = 4, max = 15) String username, @Email String email,
			@NotEmpty @Size(min = 8, max = 16) String password) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	public String getPassword1() {
		return password;
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
