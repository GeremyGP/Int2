package com.elrinconlivias.Restaurante_EntregaFinal.Modelo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comprobante")
public class Comprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comprobante")
    private Integer idComprobante;

    @OneToOne
    @JoinColumn(name = "id_pago", nullable = false)
    @JsonBackReference
    private Pago pago;

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;

    @Column(name = "numero_comprobante", nullable = false, length = 50)
    private String numero;
    
    @Column(name = "igv", nullable = false, columnDefinition = "decimal(10,2)")
    private BigDecimal igv;
    
    @Column(name = "monto_total", nullable = false, columnDefinition = "decimal(10,2)")
    private BigDecimal total;
    
    @Column(name = "ruc", nullable = true, length = 11)
    private String ruc;
    
    @Column(name = "razon_social", nullable = true, length = 100)
    private String razonSocial;
    
    @Column(name = "metodo_pago", nullable = false, length = 25)
    private String metodoPago;

    @Column(name = "fecha_emision", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Timestamp fechaEmision;
    
    @Column(name = "estado", columnDefinition = "varchar(25) default 'Emitido'")
    private String estado;
    
    // Getters and Setters

	public Integer getIdComprobante() {
		return idComprobante;
	}

	public void setIdComprobante(Integer idComprobante) {
		this.idComprobante = idComprobante;
	}

	public Pago getPago() {
		return pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public BigDecimal getIgv() {
		return igv;
	}

	public void setIgv(BigDecimal igv) {
		this.igv = igv;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	public Timestamp getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Timestamp fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

    
}
