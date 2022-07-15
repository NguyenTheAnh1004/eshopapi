package com.eshop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eshop.dto.UserDTO;
import com.eshop.dto.responses.MessageResponse;
import com.eshop.entity.RoleEntity;
import com.eshop.entity.UserEntity;
import com.eshop.repository.RoleRepository;
import com.eshop.repository.UserRepository;
import com.eshop.service.IUserService;

@Service
public class UserService implements IUserService {
	@Autowired
    PasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<UserDTO> findAll() {
		List<UserEntity> entity = new ArrayList<UserEntity>();
		entity = userRepository.findAll();
		List<UserDTO> results = new ArrayList<UserDTO>();
		for(UserEntity items: entity) {
			modelMapper.typeMap(UserEntity.class, UserDTO.class).addMappings(mapper -> mapper.skip(UserDTO::setRoles));
			UserDTO dto = modelMapper.map(items, UserDTO.class);
			items.getRoles().forEach(item -> {
				dto.getRoles().add(item.getName());  
			});
			results.add(dto);
		}
		return results;
	}

	@Override
	public UserDTO save(UserDTO dto) {
		UserEntity entity = new UserEntity();
		modelMapper.typeMap(UserDTO.class, UserEntity.class).addMappings(mapper -> mapper.skip(UserEntity::setRoles));
		entity = modelMapper.map(dto, UserEntity.class);   

		for(String items: dto.getRoles()) {
			RoleEntity roles = roleRepository.findOneByName(items);
			entity.getRoles().add(roles);
		}
		entity.setPassword(encoder.encode(entity.getPassword()));
		entity = userRepository.save(entity);
		modelMapper.typeMap(UserEntity.class, UserDTO.class).addMappings(mapper -> mapper.skip(UserDTO::setRoles));
		UserDTO result = new UserDTO();
		UserDTO results = modelMapper.map(entity, UserDTO.class);
		entity.getRoles().forEach(items -> {
			results.getRoles().add(items.getName());  
		});
        return results;
	}

	@Override
	public UserDTO update(UserDTO dto) {
		UserEntity entity = userRepository.findOneById(dto.getId());
		modelMapper.typeMap(UserDTO.class, UserEntity.class).addMappings(mapper -> mapper.skip(UserEntity::setRoles));
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setCreatedDate(entity.getCreatedDate());
		entity = modelMapper.map(dto, UserEntity.class); 
		entity.getRoles().clear();
		for(String items: dto.getRoles()) {
			RoleEntity roles = roleRepository.findOneByName(items);
			entity.getRoles().add(roles);
		}
		entity.setPassword(encoder.encode(entity.getPassword()));
		entity = userRepository.save(entity);
		modelMapper.typeMap(UserEntity.class, UserDTO.class).addMappings(mapper -> mapper.skip(UserDTO::setRoles));
		UserDTO result = new UserDTO();
		UserDTO results = modelMapper.map(entity, UserDTO.class);
		entity.getRoles().forEach(items -> {
			results.getRoles().add(items.getName());  
		});
		return results;
	}

	@Override
	public String delete(Long id) {
		Optional<UserEntity> users = userRepository.findById(id);
		if(users.isPresent()) {
			UserEntity user = userRepository.findOneById(id);
			user.getRoles().clear();
			userRepository.deleteById(id);
			return "deleted user " +id+ " successfully";
		}
		return "delete fail";
	}

	@Override
	public UserDTO findByUserName(String Name) {
		UserEntity entity = new UserEntity();
		entity = userRepository.findOneByUsername(Name);
		modelMapper.typeMap(UserEntity.class, UserDTO.class).addMappings(mapper -> mapper.skip(UserDTO::setRoles));
		UserDTO dto = modelMapper.map(entity, UserDTO.class);
		entity.getRoles().forEach(item -> {
			dto.getRoles().add(item.getName());  
		});
		return dto;
	}

	@Override
	public UserDTO changStatus(Long id) {
		UserEntity entity = userRepository.findOneById(id);
		entity.setStatus(!entity.isStatus());
		entity = userRepository.save(entity);
		modelMapper.typeMap(UserEntity.class, UserDTO.class).addMappings(mapper -> mapper.skip(UserDTO::setRoles));
		UserDTO result = new UserDTO();
		UserDTO results = modelMapper.map(entity, UserDTO.class);
		entity.getRoles().forEach(items -> {
			results.getRoles().add(items.getName());  
		});
		return results;
	}
	

}
