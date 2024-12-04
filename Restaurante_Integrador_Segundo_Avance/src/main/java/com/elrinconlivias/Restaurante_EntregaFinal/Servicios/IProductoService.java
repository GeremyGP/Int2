package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.util.List;
import java.util.Optional;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Categoria;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Comentario;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Producto;


public interface IProductoService {
	
	List<Producto> findAll();
	Producto save( Producto producto);
	Optional<Producto> findById(Integer id);
	Optional<Producto> findByNombre(String nombre);
	List<Producto> findByCategoria(Categoria categoria);
	List<Producto> findByActivo(boolean activo);
	boolean desactivate(Integer id);
	boolean activate(Integer id);
	boolean actualizarCalificacion(List<Comentario> comentarios, Producto producto);
}
