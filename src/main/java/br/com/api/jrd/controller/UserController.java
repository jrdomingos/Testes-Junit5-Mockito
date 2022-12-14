package br.com.api.jrd.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.api.jrd.dto.UserDTO;
import br.com.api.jrd.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	private static final String ID = "/{ID}";

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserService userService;
	
	

	@GetMapping(value = ID)
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(modelMapper.map(userService.findById(id), UserDTO.class));
	}

	@GetMapping
	@ResponseBody
	public ResponseEntity<List<UserDTO>> findAll() {
		return ResponseEntity.ok().body(userService.findAll()
				.stream().map(x -> modelMapper.map(x, UserDTO.class))
				.collect(Collectors.toList()));
	}
	
	@PostMapping
	public ResponseEntity<UserDTO>create(@RequestBody UserDTO obj){
	    URI uri = ServletUriComponentsBuilder
	    		.fromCurrentRequest().path("/id")
	    		.buildAndExpand(userService.create(obj).getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO>update(@PathVariable Long id, @RequestBody UserDTO obj){
		obj.setId(id);
		return ResponseEntity.ok().body(modelMapper.map(userService.update(obj), UserDTO.class));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<UserDTO>delete(@PathVariable Long id){
		findById(id);
		userService.delete(id);
		return ResponseEntity.noContent().build();
		
	}
	
	

}









































