package com.danech.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * EmployeeController used to take care of Admin's utility
 * 
 * @author dev77
 *
 */
@RestController
@RequestMapping("emp")
//only admin roles can access this module
@Secured("ROLE_EMP")
public class EmployeeController {
	@GetMapping("welcome")
	public ResponseEntity<String> welcome(Principal principal) {
		return ResponseEntity.ok().body("Employee ::"+principal.getName()+" has accessed the Welcome API");
	}
	@GetMapping("products")
	public ResponseEntity<String> products(Principal principal) {
		return ResponseEntity.ok().body("Employee ::"+principal.getName()+" has accessed the Products API");		
	}
}