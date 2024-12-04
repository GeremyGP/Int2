package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Dia;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.MenuDia;

public interface IMenuDiaService{
	List<MenuDia> findAll();
	Optional<MenuDia> findById(Integer id);
	List<MenuDia> findByDiaNombre(@Param("nombreDia") String nombreDia);
	List<MenuDia> findByDia(Dia dia);
	MenuDia save(MenuDia menuDia);
	boolean delete(Integer id);
}
