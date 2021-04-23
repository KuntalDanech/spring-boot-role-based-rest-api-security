package com.danech.model;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

import lombok.Data;

/**
 * DTO class used to give UserResponse for token
 * @author dev77
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	private String message;
	private String token;
}
