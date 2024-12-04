package com.elrinconlivias.Restaurante_EntregaFinal.Repositorios;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Categoria;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Producto;

@Repository
public interface IProducto extends JpaRepository<Producto, Serializable>{
	public abstract Optional<Producto> findByNombre(String nombre);
	public abstract List<Producto> findByCategoria(Categoria categoria);
	public abstract List<Producto> findByActivo(boolean activo);
}
