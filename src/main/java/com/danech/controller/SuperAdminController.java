package com.danech.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("super")
// This controller has method level access
public class SuperAdminController {
	
	// Demonstration of @PreAuthorize annotation which will use Spring EL
	@GetMapping("welcome")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public ResponseEntity<String> welcome(Principal principal) {
		return ResponseEntity.ok().body("Employee ::"+principal.getName()+" has accessed the Welcome API");
	}
	
	// Demonstration of @PreAuthorize annotation which will use Spring EL
	@GetMapping("products")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> products(Principal principal) {
		return ResponseEntity.ok().body("Employee ::"+principal.getName()+" has accessed the Products API");		
	}
}
