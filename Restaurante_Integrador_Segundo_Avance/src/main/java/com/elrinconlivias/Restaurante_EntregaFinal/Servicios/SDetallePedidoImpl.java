package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Cliente;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.DetallePedido;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pedido;
import com.elrinconlivias.Restaurante_EntregaFinal.Repositorios.IDetallePedido;

@Service("detalle")
public class SDetallePedidoImpl implements IDetallePedidoService{
	
	@Autowired
	IDetallePedido iDetallePedidoRepository;

	@Override
	public List<DetallePedido> findAll() {
		return iDetallePedidoRepository.findAll();
	}

	@Override
	public List<DetallePedido> findByPedido(Pedido pedido) {
		return iDetallePedidoRepository.findByPedido(pedido);
	}

	@Override
	public DetallePedido save(DetallePedido detallePedido) {
		return iDetallePedidoRepository.save(detallePedido);
	}
	
	@Override
	public boolean desactivate(Integer id) {
		try {
			Optional<DetallePedido> detalle = iDetallePedidoRepository.findById(id);
			//Verificar que el cliente existe
			if(detalle.isPresent()) {
				DetallePedido det = detalle.get();
				det.setEstado(false);
				iDetallePedidoRepository.save(det);
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
			Optional<DetallePedido> detalle = iDetallePedidoRepository.findById(id);
			//Verificar que el cliente existe
			if(detalle.isPresent()) {
				DetallePedido det = detalle.get();
				det.setEstado(true);
				iDetallePedidoRepository.save(det);
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Optional<DetallePedido> findById(Integer id) {
		return iDetallePedidoRepository.findById(id);
	}
	
}
