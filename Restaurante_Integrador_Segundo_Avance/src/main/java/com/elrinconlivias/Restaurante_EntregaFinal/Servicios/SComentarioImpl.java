package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Cliente;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Comentario;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Producto;
import com.elrinconlivias.Restaurante_EntregaFinal.Repositorios.IComentario;

@Service("comentario")
public class SComentarioImpl implements IComentarioService{
	
	@Autowired
	IComentario iComentarioRepository;

	@Override
	public Comentario save(Comentario comentario) {
		return iComentarioRepository.save(comentario);
	}

	@Override
	public List<Comentario> findAll() {
		return iComentarioRepository.findAll();
	}
	
	@Override
	public List<Comentario> findByCliente(Cliente cliente) {
		return iComentarioRepository.findByCliente(cliente);
	}

	@Override
	public List<Comentario> findByProducto(Producto producto) {
		return iComentarioRepository.findByProducto(producto);
	}

	@Override
	public List<Comentario> findByProductoAndCalificacion(Producto producto, int calificacion) {
		return iComentarioRepository.findByProductoAndCalificacion(producto, calificacion);
	}

	@Override
	public Optional<Comentario> findByProductoAndCliente(Producto producto, Cliente cliente) {
		return iComentarioRepository.findByProductoAndCliente(producto, cliente);
	}

}
