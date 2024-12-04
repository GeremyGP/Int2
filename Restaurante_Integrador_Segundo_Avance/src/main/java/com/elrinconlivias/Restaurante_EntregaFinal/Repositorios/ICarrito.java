package com.elrinconlivias.Restaurante_EntregaFinal.Repositorios;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Carrito;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Cliente;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Producto;

@Repository
public interface ICarrito extends JpaRepository<Carrito, Serializable>{
	public abstract List<Carrito> findByCliente(Cliente cliente);
	public abstract List<Carrito> findByEmpleado(Empleado empleado);
	public abstract Optional<Carrito> findByClienteAndProducto(Cliente cliente, Producto producto);
	public abstract Optional<Carrito> findByEmpleadoAndProducto(Empleado empleado, Producto producto);
	public abstract Integer deleteByCliente(Cliente cliente);
	public abstract Integer deleteByEmpleado(Empleado empleado);
}
