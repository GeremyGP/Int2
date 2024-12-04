package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.util.List;
import java.util.Optional;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Cliente;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Comentario;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Producto;

public interface IComentarioService {
	Comentario save(Comentario comentario);
	List<Comentario> findAll();
	List<Comentario> findByCliente(Cliente cliente);
	List<Comentario> findByProducto(Producto producto);
	List<Comentario> findByProductoAndCalificacion(Producto producto, int calificacion);
	Optional<Comentario> findByProductoAndCliente(Producto producto, Cliente cliente);
}
