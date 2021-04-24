package com.danech.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AdminController used to take care of Admin's utility
 * 
 * @author dev77
 *
 */
@RestController
@RequestMapping("admin")
// only admin roles can access this module
@Secured("ROLE_ADMIN")
public class AdminController {
	
	@GetMapping("inventory")
	public ResponseEntity<String> welcome(Principal principal) {
		return ResponseEntity.ok().body("Admin ::"+principal.getName()+" has accessed the inventory API");
	}
	
	@GetMapping("sales")
	public ResponseEntity<String> products(Principal principal) {
		return ResponseEntity.ok().body("Admin ::"+principal.getName()+" has accessed the sales API");		
	}
}
