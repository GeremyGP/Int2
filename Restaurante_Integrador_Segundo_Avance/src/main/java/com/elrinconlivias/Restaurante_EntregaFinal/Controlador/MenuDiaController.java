package com.elrinconlivias.Restaurante_EntregaFinal.Controlador;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Dia;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.MenuDia;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Producto;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Rol;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IDiaService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IEmpleadoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IMenuDiaService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IProductoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IRolService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/administrador/menuDia")
public class MenuDiaController {
private final Logger LOGGER = LoggerFactory.getLogger(MenuDiaController.class);
	
	@Autowired
	IMenuDiaService menuDiaService;
	
	@Autowired
	IDiaService diaService;
	
	@Autowired
	IProductoService productoService;
	
	
	@GetMapping("/listar")
	public String listar(@RequestParam(value = "diaNombre", required = false, defaultValue = "Lunes") String diaNombre, HttpSession session, Model model) {
		List<MenuDia> menuDia = menuDiaService.findByDiaNombre(diaNombre);
		List<Dia> dias = diaService.findAll();
		model.addAttribute("dias", dias);
		model.addAttribute("diaNombre", diaNombre);
		model.addAttribute("menuDia",menuDia);
		model.addAttribute("titulo","Lista de productos del Menú por día");
		return "Administrador/Productos/Menu/menuDiaListar";
	}
	
	@RequestMapping("/form")
	public String formulario (Model model) {
		List<Producto> productos = productoService.findByActivo(true);
		List<Dia> dias = diaService.findAll();
		model.addAttribute("productos", productos);
		model.addAttribute("dias", dias);
		model.addAttribute("btn", "Guardar Menu");
		model.addAttribute("titulo","Insertar nuevo producto al menú");
		return "Administrador/Productos/Menu/menuDiaInsertar";
	}
	
	@RequestMapping("/form/{id}")
	public String formulario (@PathVariable("id") Integer id, Model model) {
		MenuDia menuDia = new MenuDia();
		Optional<MenuDia> optionalMenuDia = menuDiaService.findById(id);
		List<Producto> productos = productoService.findByActivo(true);
		List<Dia> dias = diaService.findAll();
		menuDia = optionalMenuDia.get();
		model.addAttribute("menuDia", menuDia);
		model.addAttribute("productos", productos);
		model.addAttribute("dias", dias);
		model.addAttribute("btn", "Guardar Menu");
		model.addAttribute("titulo","Insertar nuevo producto al menú");
		return "Administrador/Productos/Menu/menuDiaEditar";
	}
	
	@PostMapping("/insertar")
    public String guardar(MenuDia menuDia) throws IOException {
        // Guardar un nuevo rol
        menuDiaService.save(menuDia);

        return "redirect:/administrador/menuDia/listar";
    }
	
	@PostMapping("/actualizar")
	public String actualizar(MenuDia menuDia) throws IOException{
		menuDiaService.save(menuDia);
		return "redirect:/administrador/menuDia/listar";
	}

	
	@RequestMapping("/eliminar/{id}")
	public String eliminar(@PathVariable("id") Integer id) {
		MenuDia menuDia = new MenuDia();
		menuDia = menuDiaService.findById(id).get();
		menuDiaService.delete(id);
		return "redirect:/administrador/menuDia/listar";
	}
}
