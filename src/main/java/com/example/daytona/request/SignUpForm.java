package com.example.daytona.request;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpForm {
	@NotBlank
    @Size(min=3, max = 50)
    private String firstName;
    
    @NotBlank
    @Size(min=3, max = 50)
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Size(max = 60)
    @Email
    private String email;
    
    private Set<String> role;
    

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;
}