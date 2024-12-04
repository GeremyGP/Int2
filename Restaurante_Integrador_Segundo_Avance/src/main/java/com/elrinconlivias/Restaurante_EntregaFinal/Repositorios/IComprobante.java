package com.elrinconlivias.Restaurante_EntregaFinal.Repositorios;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Comprobante;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pago;

@Repository
public interface IComprobante extends JpaRepository<Comprobante, Serializable>{
	public abstract Optional<Comprobante> findFirstByTipoOrderByIdComprobanteDesc(String tipo);
	public abstract Optional<Comprobante> findByPago(Pago pago);
	public abstract List<Comprobante> findByTipo(String tipo);
	public abstract Optional<Comprobante> findByNumero(String numero);
}
