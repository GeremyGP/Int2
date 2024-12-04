package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Cliente;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pago;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pedido;
import com.elrinconlivias.Restaurante_EntregaFinal.Repositorios.IPedido;

@Service("pedido")
public class SPedidoImpl implements IPedidoService{
	@Autowired
	private IPedido iPedidoRepository;

	@Override
	public Pedido save(Pedido pedido) {
		return iPedidoRepository.save(pedido);
	}

	@Override
	public List<Pedido> findAll() {
		return iPedidoRepository.findAll();
	}

	/*
	@Override
	public String generarNumeroOrden() {
		int numero = 0;
		String numeroConcatenado = "";
		List<Pedido> ordenes = findAll();
		List<Integer> numeros = new ArrayList<Integer>();
		ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero())));
		if (ordenes.isEmpty()) {
			numero = 1;
		} else {
			numero = numeros.stream().max(Integer::compare).get();
			numero++;
		}
		if (numero < 10) {
			numeroConcatenado = "000000000" + String.valueOf(numero);
		} else if (numero < 100) {
			numeroConcatenado = "00000000" + String.valueOf(numero);
		} else if (numero < 1000) {
			numeroConcatenado = "0000000" + String.valueOf(numero);
		} else if (numero < 10000) {
			numeroConcatenado = "0000000" + String.valueOf(numero);
		}
		return numeroConcatenado;
	}
	*/
	

	@Override
	public List<Pedido> findByCliente(Cliente cliente) {
		return iPedidoRepository.findByCliente(cliente);
	}
	
	@Override
	public List<Pedido> findByEmpleado(Empleado empleado) {
		return iPedidoRepository.findByEmpleado(empleado);
	}

	@Override
	public Optional<Pedido> findById(Integer id) {
		return iPedidoRepository.findById(id);
	}

	@Override
	public List<Pedido> findByEstado(String estado) {
		return iPedidoRepository.findByEstado(estado);
	}

	@Override
	public List<Pedido> findByTipo(String tipo) {
		return iPedidoRepository.findByTipo(tipo);
	}

	@Override
	public List<Pedido> findByFechaCreacion(Timestamp fechaCreacion) {
		return iPedidoRepository.findByFechaCreacion(fechaCreacion);
	}

	@Override
	public List<String> obtenerEstados() {
		return Arrays.asList("Todos","Pendiente", "En proceso", "Entregado", "Cancelado", "Terminado");
	}
}
