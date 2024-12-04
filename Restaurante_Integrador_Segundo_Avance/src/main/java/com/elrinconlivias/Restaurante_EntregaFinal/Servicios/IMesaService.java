package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.util.List;
import java.util.Optional;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Mesa;

public interface IMesaService {
	List<Mesa> findAll();
	Optional<Mesa> findById(Integer id);
	Mesa save(Mesa mesa);
	Optional<Mesa> findByNumero(int numero);
	List<Mesa> findByEstado(String estado);
	List<Mesa> findByCapacidad(int capacidad);
	List<Mesa> findByPiso(int piso);
	List<Mesa> findByActivo(boolean activo);
	boolean delete(Integer id);
	boolean desactivate(Integer id);
	boolean activate(Integer id);
}
