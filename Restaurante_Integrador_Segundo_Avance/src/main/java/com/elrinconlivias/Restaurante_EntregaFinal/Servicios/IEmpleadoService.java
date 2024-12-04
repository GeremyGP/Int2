package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.util.List;
import java.util.Optional;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;

public interface IEmpleadoService {
	List<Empleado> findAll();
	Optional<Empleado> findById(Integer id);
	Empleado save(Empleado empleado);
	Optional<Empleado> findByEmail(String email);
	Optional<Empleado> findByNombre(String nombre);
	List<Empleado> findByActivo(boolean activo);
	boolean desactivate(Integer id);
	boolean activate(Integer id);
}
