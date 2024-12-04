package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Cliente;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pago;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pedido;

public interface IPagoService {
	Optional<Pago> findByPedido(Pedido pedido);
	List<Pago> findByCliente(Cliente cliente);
	List<Pago> findByMetodoPago(String metodoPago);
	List<Pago> findByEstado(String estado);
	List<Pago> findByFecha(Timestamp fecha);
	Optional<Pago> findByReferenciaPasarela(String referenciaPasarela);
	List<Pago> findAll();
	Optional<Pago> findById(Integer id);
	Pago save(Pago pago);
	String generarReferencia();
}
