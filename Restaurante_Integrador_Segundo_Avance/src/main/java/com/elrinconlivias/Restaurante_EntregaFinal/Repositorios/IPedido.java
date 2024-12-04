package com.elrinconlivias.Restaurante_EntregaFinal.Repositorios;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Cliente;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pago;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pedido;

@Repository
public interface IPedido extends JpaRepository<Pedido, Serializable>{
	public abstract List<Pedido> findByCliente(Cliente cliente); //Buscar por Usuario
	public abstract List<Pedido> findByEmpleado(Empleado empleado); //Buscar por Empleado
	public abstract List<Pedido> findByTipo(String tipo); //Buscar por tipo
	public abstract List<Pedido> findByEstado(String estado); //Buscar por estado
	public abstract List<Pedido> findByFechaCreacion(Timestamp fechaCreacion); //Buscar por fecha
	//public abstract Pedido findByNombre(String nombre);
}
