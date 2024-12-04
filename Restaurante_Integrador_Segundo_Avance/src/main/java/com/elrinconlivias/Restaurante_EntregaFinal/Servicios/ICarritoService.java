package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.util.List;
import java.util.Optional;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Carrito;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Cliente;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Producto;

public interface ICarritoService {
	List<Carrito> findByCliente(Cliente cliente);
	List<Carrito> findByEmpleado(Empleado empleado);
	Optional<Carrito> findByClienteAndProducto(Cliente cliente, Producto producto);
	Optional<Carrito> findByEmpleadoAndProducto(Empleado empleado, Producto producto);
	boolean deleteByCliente(Cliente cliente);
	boolean deleteByEmpleado(Empleado empleado);
	Carrito save(Carrito carrito);
	boolean delete (Integer id);
}
