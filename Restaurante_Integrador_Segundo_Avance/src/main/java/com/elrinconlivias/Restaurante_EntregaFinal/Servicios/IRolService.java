package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.util.List;
import java.util.Optional;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Rol;


public interface IRolService {
	List<Rol> findAll();
	Optional<Rol> findById(Integer id);
	Rol save(Rol categoria);
	List<Rol> findByEmpleado(Empleado empleado);
	List<Rol> findByRolNombre(String nombre);
	public boolean delete(Integer id);
	List<String> obtenerRoles();
}
