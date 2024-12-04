package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Comprobante;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pago;
import com.elrinconlivias.Restaurante_EntregaFinal.Repositorios.IComprobante;

@Service("comprobante")
public class SComprobanteImpl implements IComprobanteService{
	
	@Autowired
	IComprobante iComprobanteRepository;

	@Override
	public Optional<Comprobante> findFirstByTipoOrderByIdComprobanteDesc(String tipo) {
		return iComprobanteRepository.findFirstByTipoOrderByIdComprobanteDesc(tipo);
	}

	@Override
	public Optional<Comprobante> findByPago(Pago pago) {
		return iComprobanteRepository.findByPago(pago);
	}

	@Override
	public List<Comprobante> findByTipo(String tipo) {
		return iComprobanteRepository.findByTipo(tipo);
	}

	@Override
	public Optional<Comprobante> findByNumero(String numero) {
		return iComprobanteRepository.findByNumero(numero);
	}

	@Override
	public List<Comprobante> findAll() {
		return iComprobanteRepository.findAll();
	}

	@Override
	public Optional<Comprobante> findById(Integer id) {
		return iComprobanteRepository.findById(id);
	}

	@Override
	public Comprobante save(Comprobante comprobante) {
	    Optional<Comprobante> ultimoComprobante = iComprobanteRepository.findFirstByTipoOrderByIdComprobanteDesc(comprobante.getTipo());

	    // Verificar si el Optional tiene un valor antes de obtenerlo
	    if (ultimoComprobante.isPresent()) {
	        String numero = generarNumeroComprobante(ultimoComprobante.get());
	        comprobante.setNumero(numero);
	    } else {
	        // Si no se encuentra un comprobante previo, puedes asignar un número inicial
	        // Por ejemplo, asignar un número de serie por defecto
	        String numero = generarNumeroComprobante(new Comprobante()); // Pasar un objeto vacío si no hay último comprobante
	        comprobante.setNumero(numero);
	    }

	    return iComprobanteRepository.save(comprobante);
	}

	@Override
	public String generarNumeroComprobante(Comprobante ultimoComprobante) {
	    String serie = "";
	    int numero = 1;

	    // Comprobar si el último comprobante no es nulo antes de intentar acceder a sus propiedades
	    if (ultimoComprobante != null && "Boleta".equals(ultimoComprobante.getTipo())) {
	        serie = "B001";
	    } else if (ultimoComprobante != null && "Factura".equals(ultimoComprobante.getTipo())) {
	        serie = "F001";
	    }

	    // Verificar si el comprobante tiene un número y sumarle 1 si es necesario
	    if (ultimoComprobante != null && ultimoComprobante.getNumero() != null) {
	        String[] partes = ultimoComprobante.getNumero().split("-");
	        numero = Integer.parseInt(partes[1]) + 1;
	    }

	    // Formatear el número con ceros a la izquierda
	    String numeroFormateado = String.format("%06d", numero);
	    return serie + "-" + numeroFormateado;
	}


	@Override
	public BigDecimal calcularIGV(BigDecimal total) {
		// El IGV es el 18% del monto total
		BigDecimal igv = total.subtract(total.divide(new BigDecimal(1.18), RoundingMode.HALF_UP));
		return igv;
	}

}
