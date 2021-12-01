package com.mitocode.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mitocode.dto.PersonaDTO;
import com.mitocode.exception.ModeloNotFountException;
import com.mitocode.model.Persona;
import com.mitocode.service.IPersonaService;

@RestController
@RequestMapping("/personas")
public class PersonaController {
	
	@Autowired
	private IPersonaService service;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping
	public ResponseEntity<List<PersonaDTO>> listar() throws Exception{
		List<PersonaDTO> lista = service.listar().stream().map(p -> mapper.map(p, PersonaDTO.class)).collect(Collectors.toList());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PersonaDTO> listarPorId(@PathVariable("id") Integer id)throws Exception{
		Persona obj = service.listarPorId(id);
		if(obj == null) {
			throw new ModeloNotFountException("ID NO ENCONTRADO: " + id);
		}
		PersonaDTO dtoResponse = mapper.map(obj, PersonaDTO.class);
		return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Void> registar(@Valid @RequestBody PersonaDTO dtoRequest)throws Exception{
		Persona obj = service.registrar(mapper.map(dtoRequest, Persona.class));
		PersonaDTO dtoResponse = mapper.map(obj, PersonaDTO.class);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dtoResponse.getIdPersona()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Void> modificar(@Valid @RequestBody PersonaDTO dtoRequest)throws Exception{
		Persona pac = service.listarPorId(dtoRequest.getIdPersona());
		if(pac == null) {
			throw new ModeloNotFountException("ID NO ENCONTRADO " + dtoRequest.getIdPersona());
		}
		Persona p = mapper.map(dtoRequest, Persona.class);
		Persona obj = service.modificar(p);
		PersonaDTO dtoResponse = mapper.map(obj, PersonaDTO.class);
		//return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dtoResponse.getIdPersona()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id)throws Exception{
		service.eliminar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	

}
