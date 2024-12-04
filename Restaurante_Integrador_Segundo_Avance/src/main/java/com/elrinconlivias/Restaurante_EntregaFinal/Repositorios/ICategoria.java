package com.elrinconlivias.Restaurante_EntregaFinal.Repositorios;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Categoria;

@Repository
public interface ICategoria extends JpaRepository<Categoria, Serializable>{
	public abstract Optional<Categoria> findByNombre(String nombre);
}
