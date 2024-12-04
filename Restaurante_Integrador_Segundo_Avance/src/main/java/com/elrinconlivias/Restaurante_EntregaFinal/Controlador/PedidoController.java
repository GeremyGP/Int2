package com.elrinconlivias.Restaurante_EntregaFinal.Controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Cliente;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.DetallePedido;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Dia;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.MenuDia;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pago;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pedido;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IClienteService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IDetallePedidoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IPagoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IPedidoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("administrador/pedidos")
public class PedidoController {
private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	@Qualifier("pedido")
	IPedidoService pedidoService;
	
	@Autowired
	IClienteService clienteService;
	
	@Autowired
	IPagoService pagoService;
	
	@Autowired
	IDetallePedidoService detallePedidoService;
	
	@ModelAttribute("estados")
	public List<String> cargarEstados(){
		return pedidoService.obtenerEstados();
	}
	
	@GetMapping("/listar")
	public String listar(@RequestParam(value = "estado", required = false, defaultValue = "Todos") String estado,Model model) {
		List<Pedido> pedidos = pedidoService.findAll();
		model.addAttribute("pedidos",pedidos);
		if(!"Todos".equalsIgnoreCase(estado)) {
			List<Pedido> pedidosEstado = pedidos.stream()
				    .filter(pedido -> estado.equals(pedido.getEstado()))
				    .collect(Collectors.toList());
			model.addAttribute("pedidos",pedidosEstado);
			model.addAttribute("nombreEstado", estado);
		}
		model.addAttribute("titulo","Lista de los pedidos");
		model.addAttribute("Todos",true);
		model.addAttribute("Online",false);
		model.addAttribute("Local",false);
		return "Administrador/Pedidos/pedidosListar";
	}
	
	@GetMapping("/listar/ventas")
	public String listarVentas(Model model) {
		List<Pago> pagos = pagoService.findAll();
		model.addAttribute("pagos",pagos);
		model.addAttribute("titulo","Lista de las ventas");
		return "Administrador/Ventas/ventasListar";
	}
	@GetMapping("/listar/ventas/pedido/{id}")
	public String listarVentasPedidos(@PathVariable("id") Integer id,Model model) {
		Pago pago = pagoService.findById(id).get();
		Pedido pedido = pago.getPedido();
		model.addAttribute("pedidos",pedido);
		model.addAttribute("titulo","Pedido concretado");
		return "Administrador/Ventas/ventasPedido";
	}
	
	@GetMapping("/listar/ventas/pedido/detalles/{id}")
	public String listarVentasDetallesPedido(@PathVariable("id") Integer id,Model model) {
		Pedido pedido = pedidoService.findById(id).get();
		List<DetallePedido> detalles = detallePedidoService.findByPedido(pedido);
		model.addAttribute("detalles",detalles);
		model.addAttribute("titulo","Lista de detalles del pedido");
		return "Administrador/Ventas/ventasDetallePedido";
	}
	
	@GetMapping("/listar/{tipo}")
	public String listar(@RequestParam(value = "estado", required = false, defaultValue = "Todos") String estado, @PathVariable("tipo") String tipo,Model model) {
		List<Pedido> pedidos = pedidoService.findByTipo(tipo);
		model.addAttribute("pedidos",pedidos);
		if(!"Todos".equalsIgnoreCase(estado)) {
			List<Pedido> pedidosEstado = pedidos.stream()
				    .filter(pedido -> estado.equals(pedido.getEstado()))
				    .collect(Collectors.toList());
			model.addAttribute("pedidos",pedidosEstado);
			model.addAttribute("nombreEstado", estado);
		}
		model.addAttribute("titulo","Lista de los pedidos");
		model.addAttribute("Online", "Online".equalsIgnoreCase(tipo));
		model.addAttribute("Local", "Local".equalsIgnoreCase(tipo));
		model.addAttribute("Todos",false);
		return "Administrador/Pedidos/pedidosListar";
	}
	
	@GetMapping("listar/cliente/{id}")
	public String pedidosCliente(@PathVariable("id") Integer id, Model model) {
		Cliente cli = clienteService.findById(id).get();
		List<Pedido> pedidos = pedidoService.findByCliente(cli);
		model.addAttribute("pedidos", pedidos);
		model.addAttribute("titulo", "Pedidos del cliente "+cli.getNombre()+":");
		return "Administrador/Clientes/clientePedidos";
	}
	
	@RequestMapping("listar/cliente/{idCli}/detalles/{idPe}")
	public String detalles (@PathVariable("idCli") Integer idCli,@PathVariable("idPe") Integer idPe, Model model) {
		Cliente cli = clienteService.findById(idCli).get();
		List<DetallePedido> detalles = new ArrayList<DetallePedido>();
		Optional<Pedido> optionalPedido = pedidoService.findById(idPe);
		detalles = optionalPedido.get().getDetalles();
		LOGGER.info("Detalles del pedido buscado: {}", detalles);
		model.addAttribute("detalles", detalles);
		model.addAttribute("cliente", cli);
		return "Administrador/Clientes/clienteDetalles";
	}
	
	@GetMapping("detalles/{id}")
	public String detallesCamarero (@PathVariable("id") Integer id,Model model) {
		List<DetallePedido> detalles = new ArrayList<DetallePedido>();
		Optional<Pedido> optionalPedido = pedidoService.findById(id);
		detalles = optionalPedido.get().getDetalles();
		LOGGER.info("Detalles del pedido buscado: {}", detalles);
		model.addAttribute("detalles", detalles);
		return "Administrador/Pedidos/pedidosDetalles";
	}
	
	
	
	@RequestMapping("/form")
	public String formulario (Model model) {
		//Plato plato = new Plato();
		//model.put("plato",plato);
		model.addAttribute("btn", "Guardar Pedido");
		return "Pedidos/pedidoInsertar";
	}
	
	@RequestMapping("/form/{id}")
	public String actualizar (@PathVariable("id") Integer id,Model model) {
		Pedido pedido = new Pedido();
		Optional<Pedido> optionalPedido = pedidoService.findById(id);
		pedido = optionalPedido.get();
		LOGGER.info("Pedido buscado: {}", pedido);
		model.addAttribute("pedido", pedido);
		model.addAttribute("btn","Actualiza Registro");
		return "Pedidos/pedidoEditar";
	}
	
	@PostMapping("/insertar")
    public String guardar(Pedido pedido){
        // Guardar el objeto Plato
        pedidoService.save(pedido);

        return "redirect:/pedidos/listar";
    }
	
	@PostMapping("/actualizar")
	public String actualizar(Pedido pedido, @RequestParam String estado){
		pedido.setEstado(estado);
		pedidoService.save(pedido);
		return "redirect:/pedidos/listar";
	}
}
