package com.elrinconlivias.Restaurante_EntregaFinal.Repositorios;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Rol;

@Repository
public interface IRol extends JpaRepository<Rol, Serializable>{
	public abstract List<Rol> findByEmpleado(Empleado empleado);
	public abstract List<Rol> findByRolNombre(String nombre);
}
