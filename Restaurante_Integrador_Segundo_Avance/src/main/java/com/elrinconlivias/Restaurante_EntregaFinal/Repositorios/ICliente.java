package com.elrinconlivias.Restaurante_EntregaFinal.Repositorios;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Cliente;

@Repository
public interface ICliente extends JpaRepository<Cliente, Serializable>{
	public abstract Optional<Cliente> findByEmail(String email);
	public abstract List<Cliente> findByActivo(boolean activo);
}
