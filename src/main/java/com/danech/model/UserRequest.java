package com.danech.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class used to prepare UserRequest
 * @author dev77
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
	private String userName;
	private String password;
}
