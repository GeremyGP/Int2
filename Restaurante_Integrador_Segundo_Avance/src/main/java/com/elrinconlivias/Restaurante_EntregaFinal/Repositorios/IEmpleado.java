package com.elrinconlivias.Restaurante_EntregaFinal.Repositorios;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;

@Repository
public interface IEmpleado extends JpaRepository<Empleado, Serializable>{
	public abstract Optional<Empleado> findByNombre(String nombre);
	public abstract Optional<Empleado> findByEmail(String email);
	public abstract List<Empleado> findByActivo(boolean activo);
}
