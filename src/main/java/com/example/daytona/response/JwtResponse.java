package com.example.daytona.response;

import java.util.Set;

import com.example.daytona.model.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Set<Role> role ;
	public JwtResponse(String token, Set<Role> usrrole) {
		super();
		this.token = token;
		this.role = usrrole;
	}

    

    
}
