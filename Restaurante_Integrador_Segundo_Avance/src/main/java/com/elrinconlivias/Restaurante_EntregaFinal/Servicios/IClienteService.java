package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.util.List;
import java.util.Optional;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Cliente;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;


public interface IClienteService {
	
	List<Cliente> findAll();
	Optional<Cliente> findById(Integer id);
	Cliente save(Cliente usuario);
	Optional<Cliente> findByEmail(String email);
	List<Cliente> findByActivo(boolean activo);
	boolean desactivate(Integer id);
	boolean activate(Integer id);
}
