package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Comprobante;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pago;

public interface IComprobanteService{
	Optional<Comprobante> findFirstByTipoOrderByIdComprobanteDesc(String tipo);
	Optional<Comprobante> findByPago(Pago pago);
	List<Comprobante> findByTipo(String tipo);
	Optional<Comprobante> findByNumero(String numero);
	List<Comprobante> findAll();
	Optional<Comprobante> findById(Integer id);
	Comprobante save(Comprobante comprobante);
	String generarNumeroComprobante(Comprobante ultimoComprobante);
	BigDecimal calcularIGV(BigDecimal total);
}
