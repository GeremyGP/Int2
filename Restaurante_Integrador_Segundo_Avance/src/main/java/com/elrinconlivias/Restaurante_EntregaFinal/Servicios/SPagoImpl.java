package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Cliente;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pago;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pedido;
import com.elrinconlivias.Restaurante_EntregaFinal.Repositorios.IPago;

@Service("pago")
public class SPagoImpl implements IPagoService{
	
	@Autowired
	IPago iPagoRepository;

	@Override
	public Optional<Pago> findByPedido(Pedido pedido) {
		return iPagoRepository.findByPedido(pedido);
	}

	@Override
	public List<Pago> findByCliente(Cliente cliente) {
		return iPagoRepository.findByCliente(cliente);
	}

	@Override
	public List<Pago> findByMetodoPago(String metodoPago) {
		return iPagoRepository.findByMetodoPago(metodoPago);
	}

	@Override
	public List<Pago> findByEstado(String estado) {
		return iPagoRepository.findByEstado(estado);
	}

	@Override
	public List<Pago> findByFecha(Timestamp fecha) {
		return iPagoRepository.findByFecha(fecha);
	}

	@Override
	public Optional<Pago> findByReferenciaPasarela(String referenciaPasarela) {
		return iPagoRepository.findByReferenciaPasarela(referenciaPasarela);
	}

	@Override
	public List<Pago> findAll() {
		return iPagoRepository.findAll();
	}

	@Override
	public Optional<Pago> findById(Integer id) {
		return iPagoRepository.findById(id);
	}

	@Override
	public Pago save(Pago pago) {
		
		
		
		return iPagoRepository.save(pago);
	}

	@Override
	public String generarReferencia() {
		String uuid = UUID.randomUUID().toString();
        return "REF-" + uuid.substring(0, 10); // Limitar a 8 caracteres si es necesario
	}

}
