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

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Rol;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IEmpleadoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IRolService;

@Controller
@RequestMapping("/administrador/roles")
public class RolController {
private final Logger LOGGER = LoggerFactory.getLogger(RolController.class);
	
	@Autowired
	IRolService rolService;
	
	@Autowired
	IEmpleadoService empleadoService;
	
	@ModelAttribute("roles")
	public List<String> cargarRoles(){
		return rolService.obtenerRoles();
	}
	
	
	@GetMapping("/listar")
	public String listar(Model model) {
		List<Rol> roles = rolService.findAll();
		model.addAttribute("roles",roles);
		model.addAttribute("titulo","Lista de los roles");
		return "Administrador/Empleados/Roles/rolListar";
	}
	
	@RequestMapping("/form")
	public String formulario (Model model) {
		List<Empleado> empleados = empleadoService.findByActivo(true);
		model.addAttribute("empleados", empleados);
		model.addAttribute("btn", "Guardar Rol");
		model.addAttribute("titulo","Insertar nuevo rol");
		return "Administrador/Empleados/Roles/rolInsertar";
	}
	
	@RequestMapping("/form/{id}")
	public String actualizar (@PathVariable("id") Integer id,Model model) {
		Rol rol = new Rol();
		Optional<Rol> optionalRol = rolService.findById(id);
		rol = optionalRol.get();
		LOGGER.info("Rol buscado: {}", rol);
		model.addAttribute("rol", rol);
		model.addAttribute("btn","Actualizar rol");
		model.addAttribute("titulo","Editar Rol");
		return "Administrador/Empleados/Roles/rolEditar";
	}
	
	@PostMapping("/insertar")
    public String guardar(Rol rol) throws IOException {
        // Guardar un nuevo rol
        rolService.save(rol);

        return "redirect:/administrador/roles/listar";
    }
	
	@PostMapping("/actualizar")
	public String actualizar(Rol rol) throws IOException{
		rolService.save(rol);
		return "redirect:/administrador/roles/listar";
	}

	
	@RequestMapping("/eliminar/{id}")
	public String eliminar(@PathVariable("id") Integer id) {
		Rol rol = new Rol();
		rol = rolService.findById(id).get();
		rolService.delete(id);
		return "redirect:/administrador/roles/listar";
	}
}
