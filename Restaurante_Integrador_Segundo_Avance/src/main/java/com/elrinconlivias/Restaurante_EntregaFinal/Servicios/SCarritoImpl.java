package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Carrito;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Cliente;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Producto;
import com.elrinconlivias.Restaurante_EntregaFinal.Repositorios.ICarrito;

@Service("carrito")
public class SCarritoImpl implements ICarritoService{
	
	@Autowired
	ICarrito iCarritoRepository;

	@Override
	public List<Carrito> findByCliente(Cliente cliente) {
		return iCarritoRepository.findByCliente(cliente);
	}

	@Override
	public List<Carrito> findByEmpleado(Empleado empleado) {
		return iCarritoRepository.findByEmpleado(empleado);
	}
	
	@Override
	public Optional<Carrito> findByClienteAndProducto(Cliente cliente, Producto producto) {
		return iCarritoRepository.findByClienteAndProducto(cliente, producto);
	}
	
	@Override
	public Optional<Carrito> findByEmpleadoAndProducto(Empleado empleado, Producto producto) {
		return iCarritoRepository.findByEmpleadoAndProducto(empleado, producto);
	}

	@Transactional
	@Override
	public boolean deleteByCliente(Cliente cliente) {
		return iCarritoRepository.deleteByCliente(cliente)>0;
	}
	
	@Transactional
	@Override
	public boolean deleteByEmpleado(Empleado empleado) {
		return iCarritoRepository.deleteByEmpleado(empleado)>0;
	}

	@Override
	public Carrito save(Carrito carrito) {
		return iCarritoRepository.save(carrito);
	}

	@Override
	public boolean delete(Integer id) {
		try {
			Optional<Carrito> carrito = iCarritoRepository.findById(id);
			//Verificar que el dia existe
			if(carrito.isPresent()) {
				Carrito c = carrito.get();
				iCarritoRepository.delete(c); //Eliminar dia
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
}
