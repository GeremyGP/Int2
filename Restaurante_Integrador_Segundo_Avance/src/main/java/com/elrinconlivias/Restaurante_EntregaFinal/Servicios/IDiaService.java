package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.util.List;
import java.util.Optional;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Dia;

public interface IDiaService {
	List<Dia> findAll();
	Optional<Dia> findById(Integer id);
	Dia save(Dia dia);
	Optional<Dia>findByNombre(String nombre);
	boolean delete(Integer id);
}
