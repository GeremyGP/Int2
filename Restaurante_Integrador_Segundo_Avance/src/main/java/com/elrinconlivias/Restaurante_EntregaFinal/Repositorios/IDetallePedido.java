package com.elrinconlivias.Restaurante_EntregaFinal.Repositorios;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.DetallePedido;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pedido;

@Repository
public interface IDetallePedido extends JpaRepository<DetallePedido, Serializable>{
	public abstract List<DetallePedido> findByPedido(Pedido pedido); //Buscar detalle pedido por pedido
	//public abstract DetallePedido findByNombre(String empleado);
}
