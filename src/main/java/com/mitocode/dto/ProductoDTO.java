package com.mitocode.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductoDTO {
	
	private Integer idProducto;
	@NotEmpty
	@NotNull
	@Size(min = 3, message = "{nombre.size}")
	private String nombre;
	@NotEmpty
	@NotNull
	private String marca;
	@NotEmpty
	@NotNull
	private String precio;
	
	public Integer getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}	
	
}
