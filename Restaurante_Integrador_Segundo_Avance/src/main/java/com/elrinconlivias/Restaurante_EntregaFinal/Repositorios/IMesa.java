package com.elrinconlivias.Restaurante_EntregaFinal.Repositorios;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Mesa;

@Repository
public interface IMesa extends JpaRepository<Mesa, Serializable>{
	public abstract Optional<Mesa> findByNumero(int numero);
	public abstract List<Mesa> findByPiso(int piso);
	public abstract List<Mesa> findByCapacidad(int capacidad);
	public abstract List<Mesa> findByEstado(String estado);
	public abstract List<Mesa> findByActivo(boolean activo);
}
