package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.util.List;
import java.util.Optional;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Categoria;

public interface ICategoriaService {
	List<Categoria> findAll();
	Optional<Categoria> findById(Integer id);
	Categoria save(Categoria categoria);
	Optional<Categoria> findByNombre(String nombre);
	boolean delete(Integer id);
}
