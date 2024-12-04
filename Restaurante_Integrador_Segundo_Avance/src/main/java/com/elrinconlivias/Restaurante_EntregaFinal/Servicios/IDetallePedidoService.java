package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.util.List;
import java.util.Optional;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.DetallePedido;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pedido;

public interface IDetallePedidoService {
	List<DetallePedido> findAll();
	List<DetallePedido> findByPedido(Pedido pedido);
	Optional<DetallePedido> findById(Integer id);
	DetallePedido save (DetallePedido detallePedido);
	boolean desactivate(Integer id);
	boolean activate(Integer id);
}
