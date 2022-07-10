package com.eshop.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.dto.UserDTO;
import com.eshop.security.jwt.AuthenticationFilter;
import com.eshop.security.jwt.JwtUtils;
import com.eshop.service.impl.UserService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserAPI {
	@Autowired
	UserService userService;
	
	@Autowired
    private JwtUtils jwtUtils;
	
	@Autowired
    private AuthenticationFilter authenticationFilter;
	
	@GetMapping(value="/User")
	public List<UserDTO> showUsers(){
		return userService.findAll();
	}
	
	@PostMapping(value="/User")
	public UserDTO createUsers(@RequestBody UserDTO user) {
		return userService.save(user);    
	}
	
	@PutMapping(value="/User")
	public UserDTO updateUsers(@RequestBody UserDTO user) {
		return userService.update(user);    
	}
	
	@DeleteMapping(value="/User")
	public String updateUsers(@RequestBody Long id) {
		return userService.delete(id);    
	}
	
	@GetMapping(value="/User/{username}")
	public UserDTO findByUserName(@PathVariable("username") String username) {
		return userService.findByUserName(username);
	}
	
	@GetMapping(value="/User/Profile")
	public UserDTO profileToken(HttpServletRequest request) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String token = authenticationFilter.getTokenFromRequest(request);
		String username = jwtUtils.getUserNameFromJwtToken(token);
		return userService.findByUserName(username);
	}
	
	@PostMapping(value="/UserStatus")
	public UserDTO changeStatus(@RequestBody Long id) {
		return userService.changStatus(id);    
	}
}
