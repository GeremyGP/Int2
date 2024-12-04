package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Rol;
import com.elrinconlivias.Restaurante_EntregaFinal.Repositorios.IRol;

@Service("rol")
public class SRolImpl implements IRolService{
	
	@Autowired
	IRol iRolRepository;
	
	@Override
	public List<String> obtenerRoles() {
		return Arrays.asList("Administrador", "Camarero", "Cocinero");
	}

	@Override
	public List<Rol> findAll() {
		return iRolRepository.findAll();
	}

	@Override
	public Optional<Rol> findById(Integer id) {
		return iRolRepository.findById(id);
	}

	@Override
	public Rol save(Rol rol) {
		return iRolRepository.save(rol);
	}

	@Override
	public List<Rol> findByEmpleado(Empleado empleado) {
		return iRolRepository.findByEmpleado(empleado);
	}

	@Override
	public List<Rol> findByRolNombre(String nombre) {
		return iRolRepository.findByRolNombre(nombre);
	}

	@Override
	public boolean delete(Integer id) {
		try {
			Optional<Rol> rol = iRolRepository.findById(id);
			//Verificar que el rol existe
			if(rol.isPresent()) {
				Rol r = rol.get();
				iRolRepository.delete(r); //Eliminar rol al empleado
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
}
