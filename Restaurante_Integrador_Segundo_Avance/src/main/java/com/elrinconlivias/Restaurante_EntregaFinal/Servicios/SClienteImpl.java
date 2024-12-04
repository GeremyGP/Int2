package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Cliente;
import com.elrinconlivias.Restaurante_EntregaFinal.Repositorios.ICliente;

@Service("cliente")
public class SClienteImpl implements IClienteService{
	
	@Autowired
	ICliente iClienteRepository;
	
	//Método para guardar el usuario
	@Override
	public Cliente save(Cliente cliente) {
		// Obtener la fecha y hora actuales
        Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());
        cliente.setFechaCreacion(fechaCreacion);
		return iClienteRepository.save(cliente);
	}
	
	//Método para listar los usuarios
	@Override
	public List<Cliente> findAll(){		
		return iClienteRepository.findAll();
	}
	
	//Método para buscar el usuario por su ID
	@Override
	public Optional<Cliente> findById(Integer id) {
		return iClienteRepository.findById(id);
	}
	
	//Método para buscar el usuario por su nombre
	@Override
		public Optional<Cliente> findByEmail(String email) {
			return iClienteRepository.findByEmail(email);
		}
	
	@Override
	public List<Cliente> findByActivo(boolean activo) {
		return iClienteRepository.findByActivo(activo);
	}
	
	@Override
	public boolean desactivate(Integer id) {
		try {
			Optional<Cliente> cliente = iClienteRepository.findById(id);
			//Verificar que el cliente existe
			if(cliente.isPresent()) {
				Cliente cli = cliente.get();
				cli.setActivo(false);
				iClienteRepository.save(cli);
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
			Optional<Cliente> cliente = iClienteRepository.findById(id);
			//Verificar que el cliente existe
			if(cliente.isPresent()) {
				Cliente cli = cliente.get();
				cli.setActivo(true);
				iClienteRepository.save(cli);
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

}
