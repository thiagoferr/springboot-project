package com.educandoweb.course.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.services.UserService;

@RestController // controlador rest com o caminho abaixo
@RequestMapping(value = "/users") // caminho
public class UserResource {

	@Autowired
	private UserService service;

	@GetMapping
	public ResponseEntity<List<User>> findAll() { // retorna todos os usuarios
		List<User> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}") // retorna usuario a partir do id
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	// faz um pré processamento na compilação do controlador que define que este
	// metodo ira receber o metodo POST do HTTP
	@PostMapping
	public ResponseEntity<User> insert(@RequestBody User obj) {
		// endpoint recebe um objeto do tipo User
		// Anotation '@RequestBody' serve para desserializar o objeto Json para User na
		// hora de fazer a requisição
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	//Obs: é complicado mas é a forma adequada de inserir um recurso no banco de dados
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
