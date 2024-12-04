package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Dia;
import com.elrinconlivias.Restaurante_EntregaFinal.Repositorios.IDia;

@Service("dia")
public class SDiaImpl implements IDiaService{
	
	@Autowired
	IDia iDiaRepository;

	@Override
	public List<Dia> findAll() {
		return iDiaRepository.findAll();
	}

	@Override
	public Optional<Dia> findById(Integer id) {
		return iDiaRepository.findById(id);
	}

	@Override
	public Dia save(Dia dia) {
		return iDiaRepository.save(dia);
	}

	@Override
	public Optional<Dia> findByNombre(String nombre) {
		return iDiaRepository.findByNombre(nombre);
	}

	@Override
	public boolean delete(Integer id) {
		try {
			Optional<Dia> dia = iDiaRepository.findById(id);
			//Verificar que el dia existe
			if(dia.isPresent()) {
				Dia d = dia.get();
				iDiaRepository.delete(d); //Eliminar dia
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
}
