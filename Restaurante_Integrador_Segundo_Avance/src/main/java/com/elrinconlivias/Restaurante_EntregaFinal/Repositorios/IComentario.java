package com.elrinconlivias.Restaurante_EntregaFinal.Repositorios;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Cliente;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Comentario;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Producto;


@Repository
public interface IComentario extends JpaRepository<Comentario, Serializable>{
	public abstract List<Comentario> findByCliente(Cliente cliente);
	public abstract List<Comentario> findByProducto(Producto producto);
	public abstract List<Comentario> findByProductoAndCalificacion(Producto producto, int calificacion);
	public abstract Optional<Comentario> findByProductoAndCliente(Producto producto, Cliente cliente);
}
