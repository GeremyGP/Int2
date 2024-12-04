package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Cliente;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pago;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pedido;


public interface IPedidoService {
	List<Pedido> findAll();
	Optional<Pedido> findById(Integer id);
	Pedido save(Pedido orden);
	//String generarNumeroOrden();
	List<Pedido> findByCliente(Cliente cliente);
	List<Pedido> findByEmpleado(Empleado empleado);
	List<Pedido> findByTipo(String tipo);
	List<Pedido> findByEstado(String estado);
	List<Pedido> findByFechaCreacion(Timestamp fechaCreacion);
	List<String> obtenerEstados();
}
