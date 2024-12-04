package com.elrinconlivias.Restaurante_EntregaFinal.Controlador;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Cliente;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IEmpleadoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IClienteService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.UploadFileEmpleadoService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/administrador/clientes")
public class ClienteController {
private final Logger LOGGER = LoggerFactory.getLogger(ClienteController.class);
	
	@Autowired
	@Qualifier("cliente")
	IClienteService clienteService;
	
	@GetMapping("/")
	public String listarTodos(Model model) {
	    List<Cliente> clientes = clienteService.findAll();
	    model.addAttribute("clientes", clientes);
	    model.addAttribute("titulo", "Lista de todos los clientes");
	    return "Administrador/Clientes/clientePdf"; 
	}
	
	@GetMapping("/listar/{estado}")
	public String listar(@PathVariable("estado") String estado, Model model) {
		if("activos".equals(estado)) {
			List<Cliente> clientes = clienteService.findByActivo(true);
			model.addAttribute("clientes",clientes);
		}else if("inactivos".equals(estado)) {
			List<Cliente> clientes = clienteService.findByActivo(false);
			model.addAttribute("clientes",clientes);
			model.addAttribute("estado", false);
		}
		
		model.addAttribute("titulo","Lista de los clientes");
		return "Administrador/Clientes/clienteListar";
	}
	
	@RequestMapping("form")
	public String formulario (Model model) {
		//Plato plato = new Plato();
		//model.put("plato",plato);
		model.addAttribute("btn", "Guardar Usuario");
		model.addAttribute("titulo","Insertar nuevo cliente");
		return "Administrador/Clientes/clienteInsertar";
	}
	
	@RequestMapping("form/{id}")
	public String actualizar (@PathVariable("id") Integer id,Model model) {
		Cliente cliente = new Cliente();
		Optional<Cliente> optionalCliente = clienteService.findById(id);
		cliente = optionalCliente.get();
		LOGGER.info("Cliente buscado: {}", cliente);
		model.addAttribute("cliente", cliente);
		model.addAttribute("btn","Actualiza Registro");
		model.addAttribute("titulo","Editar cliente");
		return "Administrador/Clientes/clienteEditar";
	}
	
	@PostMapping("insertar")
    public String guardar(Cliente cliente){
        
		//Establecer el estado del cliente
		cliente.setActivo(true);
		
        // Guardar el objeto Cliente
        clienteService.save(cliente);

        return "redirect:/administrador/clientes/listar/activos";
    }
	
	@PostMapping("actualizar")
	public String actualizar(Cliente cliente){
		clienteService.save(cliente);
		return "redirect:/administrador/clientes/listar/activos";
	}
	
	@PostMapping("activar/{id}")
	public String activar(@PathVariable("id") Integer id){
		clienteService.activate(id);
		return "redirect:/administrador/clientes/listar/activos";
	}
	@PostMapping("desactivar/{id}")
	public String desactivar(@PathVariable("id") Integer id){
		clienteService.desactivate(id);
		return "redirect:/administrador/clientes/listar/inactivos";
	}
	
	@RequestMapping("eliminar/{id}")
	public String eliminar(@PathVariable("id") Integer id) {
		clienteService.desactivate(id);
		return "redirect:/administrador/clientes/listar/activos";
	}
}
