package com.elrinconlivias.Restaurante_EntregaFinal.Repositorios;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Cliente;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pago;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pedido;

public interface IPago extends JpaRepository<Pago, Serializable>{
	public abstract Optional<Pago> findByPedido(Pedido pedido);
	public abstract List<Pago> findByCliente(Cliente cliente);
	public abstract List<Pago> findByMetodoPago(String metodoPago);
	public abstract List<Pago> findByEstado(String estado);
	public abstract List<Pago> findByFecha(Timestamp fecha);
	public abstract Optional<Pago> findByReferenciaPasarela(String referenciaPasarela);
}
