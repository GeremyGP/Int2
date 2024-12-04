package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Dia;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.MenuDia;
import com.elrinconlivias.Restaurante_EntregaFinal.Repositorios.IMenuDia;

@Service("menudia")
public class SMenuDiaImpl implements IMenuDiaService{
	
	@Autowired
	IMenuDia iMenuDiaRepository;

	@Override
	public List<MenuDia> findAll() {
		return iMenuDiaRepository.findAll();
	}

	@Override
	public Optional<MenuDia> findById(Integer id) {
		return iMenuDiaRepository.findById(id);
	}
	
	@Override
	public List<MenuDia> findByDiaNombre(String nombreDia) {
		return iMenuDiaRepository.findByDiaNombre(nombreDia);
	}
	
	@Override
	public List<MenuDia> findByDia(Dia dia) {
		return iMenuDiaRepository.findByDia(dia);
	}

	@Override
	public MenuDia save(MenuDia menuDia) {
		return iMenuDiaRepository.save(menuDia);
	}

	@Override
	public boolean delete(Integer id) {
		try {
			Optional<MenuDia> menuDia = iMenuDiaRepository.findById(id);
			//Verificar que el rol existe
			if(menuDia.isPresent()) {
				MenuDia m = menuDia.get();
				iMenuDiaRepository.delete(m); //Eliminar rol al empleado
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
}
