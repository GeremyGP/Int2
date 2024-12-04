package com.elrinconlivias.Restaurante_EntregaFinal.Repositorios;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Dia;

@Repository
public interface IDia extends JpaRepository<Dia, Serializable>{
	public abstract Optional<Dia>findByNombre(String nombre);
}
