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

import com.mitocode.dto.ProductoDTO;
import com.mitocode.exception.ModeloNotFountException;
import com.mitocode.model.Producto;
import com.mitocode.service.IProductoService;

@RestController
@RequestMapping("/productos")
public class ProductoController {
	
	@Autowired
	private IProductoService service;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping
	public ResponseEntity<List<ProductoDTO>> listar() throws Exception{
		List<ProductoDTO> lista = service.listar().stream().map(p -> mapper.map(p, ProductoDTO.class)).collect(Collectors.toList());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductoDTO> listarPorId(@PathVariable("id") Integer id)throws Exception{
		Producto obj = service.listarPorId(id);
		if(obj == null) {
			throw new ModeloNotFountException("ID NO ENCONTRADO: " + id);
		}
		ProductoDTO dtoResponse = mapper.map(obj, ProductoDTO.class);
		return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Void> registar(@Valid @RequestBody ProductoDTO dtoRequest)throws Exception{
		Producto obj = service.registrar(mapper.map(dtoRequest, Producto.class));
		ProductoDTO dtoResponse = mapper.map(obj, ProductoDTO.class);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dtoResponse.getIdProducto()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Void> modificar(@Valid @RequestBody ProductoDTO dtoRequest)throws Exception{
		Producto pac = service.listarPorId(dtoRequest.getIdProducto());
		if(pac == null) {
			throw new ModeloNotFountException("ID NO ENCONTRADO " + dtoRequest.getIdProducto());
		}
		Producto p = mapper.map(dtoRequest, Producto.class);
		Producto obj = service.modificar(p);
		ProductoDTO dtoResponse = mapper.map(obj, ProductoDTO.class);
		//return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dtoResponse.getIdProducto()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id)throws Exception{
		service.eliminar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	

}
