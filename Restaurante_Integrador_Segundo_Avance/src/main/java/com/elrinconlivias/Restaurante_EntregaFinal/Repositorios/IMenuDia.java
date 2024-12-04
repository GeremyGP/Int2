package com.elrinconlivias.Restaurante_EntregaFinal.Repositorios;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Dia;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.MenuDia;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Producto;

@Repository
public interface IMenuDia extends JpaRepository<MenuDia, Serializable>{
	@Query("SELECT md FROM MenuDia md WHERE md.dia.nombre = :nombreDia")
	List<MenuDia> findByDiaNombre(@Param("nombreDia") String nombreDia);
	List<MenuDia> findByDia(Dia dia);
}
