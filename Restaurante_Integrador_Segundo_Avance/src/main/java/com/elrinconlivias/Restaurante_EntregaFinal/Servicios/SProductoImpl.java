package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Categoria;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Comentario;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Producto;
import com.elrinconlivias.Restaurante_EntregaFinal.Repositorios.IProducto;


@Service("producto")
public class SProductoImpl implements IProductoService{
	
	@Autowired
	IProducto iProductoRepository;
	
	@Override
	public Producto save(Producto producto) {		
		return iProductoRepository.save(producto);
	}
	
	@Override
	public List<Producto> findAll(){		
		return iProductoRepository.findAll();
	}
	
	@Override
	public Optional<Producto> findById(Integer id) {
		return iProductoRepository.findById(id);
	}
	
	@Override
	public Optional<Producto> findByNombre(String nombre) {
		return iProductoRepository.findById(nombre);
	}
		
	@Override
	public List<Producto> findByCategoria(Categoria categoria) {
		return iProductoRepository.findByCategoria(categoria);
	}
	
	@Override
	public List<Producto> findByActivo(boolean activo) {
		return iProductoRepository.findByActivo(activo);
	}
	
	@Override
	public boolean desactivate(Integer id) {
		try {
			Optional<Producto> producto = iProductoRepository.findById(id);
			//Verificar que el plato existe
			if(producto.isPresent()) {
				Producto prod = producto.get();
				prod.setActivo(false);
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean activate(Integer id) {
		try {
			Optional<Producto> producto = iProductoRepository.findById(id);
			//Verificar que el plato existe
			if(producto.isPresent()) {
				Producto prod = producto.get();
				prod.setActivo(true);
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public boolean actualizarCalificacion(List<Comentario> comentarios, Producto producto) {
		if (comentarios == null || comentarios.isEmpty()) {
			producto.setCalificacion(1);
			iProductoRepository.save(producto);
            return false;
        }
		
		// Sumar todas las puntuaciones de los comentarios
        int suma = comentarios.stream().mapToInt(Comentario::getCalificacion).sum();
        
     // Calcular el promedio y redondear
        double promedio = (double) suma / comentarios.size();
        producto.setCalificacion((int) Math.round(promedio)); // Redondear y asignar
        iProductoRepository.save(producto);
        return true;
	}

}
