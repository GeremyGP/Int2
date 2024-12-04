package com.elrinconlivias.Restaurante_EntregaFinal.Controlador;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pedido;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Producto;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IEmpleadoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IPedidoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IProductoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IClienteService;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	@Autowired
	IProductoService productoService;
	
	@Autowired
	IClienteService clienteService;
	
	@Autowired
	IEmpleadoService empleadoService;
	
	@Autowired
	IPedidoService pedidoService;
	
	
	private Logger logg = LoggerFactory.getLogger(AdministradorController.class);
	
	@GetMapping("/inicio")
	public String home(Model model) {
		List<Producto> productos = productoService.findAll();
		model.addAttribute("platos", productos);
		return "layout/templateAdmin";
	}

	@GetMapping("/usuarios")
	public String usuarios(Model model) {
		model.addAttribute("usuarios", clienteService.findAll());
		return "administrador/usuarios";
	}
	
	@GetMapping("/empleados")
	public String empleados(Model model) {
		model.addAttribute("empleados", empleadoService.findAll());
		return "administrador/empleados";
	}

	@GetMapping("/ordenes")
	public String ordenes(Model model) {
		model.addAttribute("ordenes", pedidoService.findAll());
		return "administrador/ordenes";
	}

	@GetMapping("/detalle/{id}")
	public String detalle(Model model, @PathVariable Integer id) {
		logg.info("Id de la orden {}", id);
		Pedido pedido = pedidoService.findById(id).get();
		model.addAttribute("detalles", pedido.getDetalles());
		return "administrador/detalleorden";
	}
}
