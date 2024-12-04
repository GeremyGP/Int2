package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.elrinconlivias.Restaurante_EntregaFinal.Repositorios.IEmpleado;

@Service("empleado")
public class SEmpleadoImpl implements IEmpleadoService{
	
	@Autowired
	IEmpleado iEmpleadoRepository;
	
	//Método para guardar el empleado
		@Override
		public Empleado save(Empleado empleado) {		
			return iEmpleadoRepository.save(empleado);
		}
		
		//Método para listar los empleados
		@Override
		public List<Empleado> findAll(){		
			return iEmpleadoRepository.findAll();
		}
		
		//Método para buscar el empleado por su ID
		@Override
		public Optional<Empleado> findById(Integer id) {
			return iEmpleadoRepository.findById(id);
		}
		
		//Método para buscar el empleado por su nombre
		@Override
			public Optional<Empleado> findByEmail(String email) {
				return iEmpleadoRepository.findByEmail(email);
			}
		//Método para buscar el empleado por su nombre
		
		@Override
		public Optional<Empleado> findByNombre(String nombre) {
			return iEmpleadoRepository.findByEmail(nombre);
		}
		
		@Override
		public List<Empleado> findByActivo(boolean activo) {
			return iEmpleadoRepository.findByActivo(activo);
		}
		
		@Override
		public boolean desactivate(Integer id) {
			try {
				Optional<Empleado> empleado = iEmpleadoRepository.findById(id);
				//Verificar que el empleado existe
				if(empleado.isPresent()) {
					Empleado emp = empleado.get();
					emp.setActivo(false);
					return true;
				}
				return false;
			} catch (Exception e) {
				return false;
			}
		}

		@Override
		public boolean activate(Integer id) {
			try {
				Optional<Empleado> empleado = iEmpleadoRepository.findById(id);
				//Verificar que el empleado existe
				if(empleado.isPresent()) {
					Empleado emp = empleado.get();
					emp.setActivo(true);
					return true;
				}
				return false;
			} catch (Exception e) {
				return false;
			}
		}
		
}
