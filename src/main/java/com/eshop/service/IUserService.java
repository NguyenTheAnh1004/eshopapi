package com.eshop.service;

import java.util.List;

import com.eshop.dto.UserDTO;

public interface IUserService {
	List<UserDTO> findAll();
	UserDTO save(UserDTO user);
	UserDTO update(UserDTO user);
	String delete(Long id);
	UserDTO findByUserName(String Name);
	UserDTO changStatus(Long id);
}
