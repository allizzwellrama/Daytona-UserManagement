package com.example.daytona.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class UpdatePasswordRequest {
	 @NotBlank
	 @Size(min=3, max = 60)
	private String userName;
	 @NotBlank
	 @Size(min=3, max = 60)
	private String oldPassword;
	 @NotBlank
	 @Size(min=3, max = 60)
	private String newPassword;
	 @NotBlank
     @Size(min=3, max = 60)
	private String confirmPassword;
	

}
