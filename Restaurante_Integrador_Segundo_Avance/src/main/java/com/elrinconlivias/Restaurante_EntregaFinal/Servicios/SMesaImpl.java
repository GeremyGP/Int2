package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Mesa;
import com.elrinconlivias.Restaurante_EntregaFinal.Repositorios.IMesa;

@Service("mesa")
public class SMesaImpl implements IMesaService{
	
	@Autowired
	IMesa iMesaRepository;

	@Override
	public List<Mesa> findAll() {
		return iMesaRepository.findAll();
	}

	@Override
	public Optional<Mesa> findById(Integer id) {
		return iMesaRepository.findById(id);
	}

	@Override
	public Mesa save(Mesa mesa) {
		return iMesaRepository.save(mesa);
	}
	
	@Override
	public Optional<Mesa> findByNumero(int numero) {
		return iMesaRepository.findByNumero(numero);
	}

	@Override
	public List<Mesa> findByCapacidad(int capacidad) {
		return iMesaRepository.findByCapacidad(capacidad);
	}
	
	@Override
	public List<Mesa> findByEstado(String estado) {
		return iMesaRepository.findByEstado(estado);
	}
	
	@Override
	public boolean delete(Integer id) {
		try {
			Optional<Mesa> mesa= iMesaRepository.findById(id);
			if(mesa.isPresent()) {
				Mesa m = mesa.get();
				iMesaRepository.delete(m);
				return true;
			}
			return false;
		}catch(Exception e) {
			return false;
		}
	}

	@Override
	public boolean desactivate(Integer id) {
		try {
			Optional<Mesa> mesa= iMesaRepository.findById(id);
			if(mesa.isPresent()) {
				Mesa m = mesa.get();
				m.setActivo(false);
				return true;
			}
			return false;
		}catch(Exception e) {
			return false;
		}
	}

	@Override
	public boolean activate(Integer id) {
		try {
			Optional<Mesa> mesa= iMesaRepository.findById(id);
			if(mesa.isPresent()) {
				Mesa m = mesa.get();
				m.setActivo(true);
				return true;
			}
			return false;
		}catch(Exception e) {
			return false;
		}
	}

	@Override
	public List<Mesa> findByPiso(int piso) {
		return iMesaRepository.findByPiso(piso);
	}

	@Override
	public List<Mesa> findByActivo(boolean activo) {
		return iMesaRepository.findByActivo(activo);
	}
	
}
