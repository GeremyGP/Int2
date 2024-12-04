package com.elrinconlivias.Restaurante_EntregaFinal.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Categoria;
import com.elrinconlivias.Restaurante_EntregaFinal.Repositorios.ICategoria;

@Service("categoria")
public class SCategoriaImpl implements ICategoriaService{
	
	@Autowired
	ICategoria iCategoriaRepository;
	
	//Método para guardar el categoria
		@Override
		public Categoria save(Categoria categoria) {		
			return iCategoriaRepository.save(categoria);
		}
		
		//Método para listar las categorias
		@Override
		public List<Categoria> findAll(){		
			return iCategoriaRepository.findAll();
		}
		
		//Método para buscar la categoria por su ID
		@Override
		public Optional<Categoria> findById(Integer id) {
			return iCategoriaRepository.findById(id);
		}
		
		//Método para buscar el categoria por su ID
		@Override
		public Optional<Categoria> findByNombre(String nombre) {
			return iCategoriaRepository.findByNombre(nombre);
		}
		
		
		@Override
		public boolean delete(Integer id) {
			try {
				Optional<Categoria> categoria = iCategoriaRepository.findById(id);
				//Verificar que el usuario existe
				if(categoria.isPresent()) {
					Categoria cat = categoria.get();
					iCategoriaRepository.delete(cat); //Eliminar usuario
					return true;
				}
				return false;
			} catch (Exception e) {
				return false;
			}
		}
}
