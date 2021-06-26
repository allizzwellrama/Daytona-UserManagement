package com.example.daytona.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.daytona.model.User;
import com.example.daytona.repository.UserRepository;
import com.example.daytona.request.SignUpForm;
import com.example.daytona.request.UpdatePasswordRequest;

@RestController
public class UserActivites {

	@Autowired
	UserRepository userRepository;
	
	  @Autowired
	  PasswordEncoder encoder;

	@GetMapping("/api/user/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<User> getUserByUserName(@PathVariable(value = "username") String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException(username + " not found  "));
		return ResponseEntity.ok().body(user);
	}
	

	@GetMapping("/api/users")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> getUserallUsers() {
		List<User> users = (List<User>) userRepository.findAll();		
		return ResponseEntity.ok().body(users);
	}
	
	@PostMapping("/api/update/password")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> updateUserData(@Valid @RequestBody SignUpForm signUpForm) {

		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
		User user = userRepository.findByUsername(signUpForm.getUsername())
				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User not found."));
		

		user.setEmail(signUpForm.getEmail());
		user.setFirstName(signUpForm.getFirstName());
		user.setLastName(signUpForm.getLastName());
		user.setDate(signUpForm.getDate());
		userRepository.save(user);
		return ResponseEntity.ok().body("user updated successfully!");

	} 

	@PostMapping("/api/update/user")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> updateUserPassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {

		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
		String username = updatePasswordRequest.getUserName();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User not found."));
		if (!(user.getPassword().equals(updatePasswordRequest.getOldPassword()))) {
			throw new RuntimeException("Fail! -> Cause: User not found.");
		}

		String password = updatePasswordRequest.getNewPassword();
		if (!(updatePasswordRequest.getConfirmPassword().equals(password))) {
			throw new RuntimeException("Fail! -> Cause: wrong Credentials.");
		} else if (!isValidPassword(updatePasswordRequest.getConfirmPassword(), regex)) {
			throw new RuntimeException("Fail! -> Cause: did not match the criteria.");
		}
		user.setPassword(encoder.encode(updatePasswordRequest.getNewPassword()));
		userRepository.save(user);
		return ResponseEntity.ok().body("password updated successfully!");

	}

	public boolean isValidPassword(String password, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

}
