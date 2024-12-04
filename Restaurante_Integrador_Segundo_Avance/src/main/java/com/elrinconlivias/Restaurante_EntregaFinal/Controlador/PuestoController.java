package com.elrinconlivias.Restaurante_EntregaFinal.Controlador;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Carrito;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.DetallePedido;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Mesa;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pago;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pedido;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Producto;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.ICarritoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IDetallePedidoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IEmpleadoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IMenuDiaService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IMesaService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IPagoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IPedidoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IProductoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/puestos")
public class PuestoController {
	
	@Autowired
	IProductoService productoService;
	
	@Autowired
	IEmpleadoService empleadoService;
	
	@Autowired
	IDetallePedidoService detallePedidoService;
	
	@Autowired
	IPedidoService pedidoService;
	
	@Autowired
	IMenuDiaService menuDiaService;
	
	@Autowired
	ICarritoService carritoService;
	
	@Autowired
	IMesaService mesaService;
	
	@Autowired
	IPagoService pagoService;
	
	private final Logger log = LoggerFactory.getLogger(InicioController.class);
	
	@RequestMapping("cocinero/pedidos/{estado}")
	public String HomeCocinero (@PathVariable("estado") String estado,Model model, HttpSession session) {
		List<Pedido> pedidos = pedidoService.findByEstado(estado);
		model.addAttribute("pedidos", pedidos);
		model.addAttribute("estado", estado);
		model.addAttribute("sesion", session.getAttribute("idEmpleado"));
		return "Trabajador/cocinero/pedidos";
	}
	
	@RequestMapping("cocinero/detalles/{id}")
	public String detallesCocinero (@PathVariable("id") Integer id, Model model) {
		List<DetallePedido> detalles = new ArrayList<DetallePedido>();
		Optional<Pedido> optionalPedido = pedidoService.findById(id);
		detalles = optionalPedido.get().getDetalles();
		log.info("Detalles del pedido buscado: {}", detalles);
		model.addAttribute("detalles", detalles);
		model.addAttribute("pedido", optionalPedido.get());
		boolean terminado = detalles.stream().anyMatch(item -> !item.isEstado());
		model.addAttribute("terminado", terminado);
		return "Trabajador/cocinero/detallePedidos";
	}
	
	@PostMapping("cocinero/activarDetalle/{id}")
	public String activar(@PathVariable("id") Integer id){
		Pedido ped = detallePedidoService.findById(id).get().getPedido();
		detallePedidoService.activate(id);
		return "redirect:/puestos/cocinero/detalles/"+ped.getIdPedido();
	}
	@PostMapping("cocinero/desactivarDetalle/{id}")
	public String desactivar(@PathVariable("id") Integer id){
		Pedido ped = detallePedidoService.findById(id).get().getPedido();
		detallePedidoService.desactivate(id);
		return "redirect:/puestos/cocinero/detalles/"+ped.getIdPedido();
	}
	
	
	@PostMapping("cocinero/actualizarPedido")
	public String actualizarPedido(Pedido pedido){
		Pedido ped = pedidoService.findById(pedido.getIdPedido()).get();
		ped.setEstado(pedido.getEstado());
		pedidoService.save(ped);
		if("En proceso".equals(ped.getEstado())) {
			return "redirect:/puestos/cocinero/detalles/"+ped.getIdPedido();
		}
		return "redirect:/puestos/cocinero/pedidos/"+ped.getEstado();
	}
	
	
	@GetMapping("camarero/menu/{dia}")
	public String HomeCamarero(@PathVariable("dia") String dia, Model model, HttpSession session) {
		log.info("Sesion del empleado: {}", session.getAttribute("idEmpleado"));
		model.addAttribute("menuDia",menuDiaService.findByDiaNombre(dia));
		model.addAttribute("sesion", session.getAttribute("idEmpleado"));
		model.addAttribute("carrito", session.getAttribute("carrito"));
		List<Carrito> car = (List<Carrito>) session.getAttribute("carrito");
		if(car!=null) {
			int cantidad = car.stream()
	                .mapToInt(item -> item.getCantidad())  // Obtenemos la cantidad de cada item
	                .sum();  // Sumamos las cantidades
			model.addAttribute("cantidad", cantidad);
		}
		model.addAttribute("total", session.getAttribute("total"));
		return "Trabajador/camarero/menu";
	}
	
	@GetMapping("camarero/resumenPedido")
    public String resumenPedido(Model model,HttpSession session) {
		List<Mesa> mesas = mesaService.findByEstado("Disponible");
		List<DetallePedido> detalles = new ArrayList<>();
		
		List<Carrito> car = (List<Carrito>) session.getAttribute("carrito");
		for (Carrito carrito : car) {
			DetallePedido detalleOrden = new DetallePedido();
            Optional<Producto> optionalProducto = productoService.findById(carrito.getProducto().getIdProducto());
            if (optionalProducto.isPresent()) {
            	Producto plato = optionalProducto.get();
                detalleOrden.setCantidad(carrito.getCantidad());
                detalleOrden.setPrecioUnitario(plato.getPrecio());
                detalleOrden.setNombreProducto(plato.getNombre());
                BigDecimal integerAsBigDecimal = new BigDecimal(carrito.getCantidad());
                detalleOrden.setSubTotal(plato.getPrecio().multiply(integerAsBigDecimal));
                detalleOrden.setProducto(plato);
                detalles.add(detalleOrden);
            } else {
                // Manejar el caso en que el plato no se encuentra
            }
		}
		BigDecimal sumaTotal = detalles.stream()
	            .map(dt -> dt.getSubTotal())  // Asegúrate de que getSubTotal() devuelva BigDecimal
	            .reduce(BigDecimal.ZERO, BigDecimal::add);
		session.setAttribute("resumen", detalles);
        model.addAttribute("resumen", detalles);
        model.addAttribute("total",sumaTotal);
        model.addAttribute("mesas", mesas);
        return "Trabajador/camarero/resumenPedido"; // El nombre de la vista (sin la extensión .html)
    }
	
	
	@PostMapping("camarero/guardarPedido")
	public String pagar(HttpSession session, @RequestParam("mesa") Integer mesaId) {
		Mesa mesa = mesaService.findById(mesaId).get();
		List<DetallePedido> detalles = (List<DetallePedido>) session.getAttribute("resumen");
		Pedido pedido = new Pedido();
		Empleado empleado =empleadoService.findById( Integer.parseInt(session.getAttribute("idEmpleado").toString())).get();
		pedido.setEmpleado(empleado);
		// Calcular la suma total usando BigDecimal
        BigDecimal sumaTotal = detalles.stream()
                .map(DetallePedido::getSubTotal) // Asumimos que getSubTotal devuelve BigDecimal
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sumar todos los valores BigDecimal
        pedido.setTotal(sumaTotal);
        pedido.setTipo("Local");
        pedido.setMesa(mesa);
        pedido.setEstado("Pendiente");
     // Obtener la fecha y hora actuales
        Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());
        pedido.setFechaCreacion(fechaCreacion);
        pedidoService.save(pedido);
        mesa.setEstado("Ocupado");
        mesaService.save(mesa);
		
		
		for (DetallePedido dt : detalles) {
			dt.setPedido(pedido);
			detallePedidoService.save(dt);
		}
		
		carritoService.deleteByEmpleado(empleadoService.findById( Integer.parseInt(session.getAttribute("idEmpleado").toString())).get());
		session.removeAttribute("carrito");
		session.removeAttribute("total");
		session.removeAttribute("cantidad");
		return "redirect:/puestos/camarero/menu/Lunes";
	}
	
	@GetMapping("camarero/pagarPedido/{id}")
	public String formPago(Model model, @PathVariable("id") Integer id) {
		Pedido pedido = pedidoService.findById(id).get();
		List<DetallePedido> detalles = pedido.getDetalles();
		BigDecimal sumaTotal = detalles.stream()
	            .map(dt -> dt.getSubTotal())  // Asegúrate de que getSubTotal() devuelva BigDecimal
	            .reduce(BigDecimal.ZERO, BigDecimal::add);
		model.addAttribute("detalles", detalles);
		model.addAttribute("total", sumaTotal);
		model.addAttribute("pedido", pedido);
		return "Trabajador/Camarero/pago";
	}
	
	@PostMapping("camarero/pago/{id}")
	public String pago(Pago pag, @PathVariable("id") Integer id) {
		Pedido pedido = pedidoService.findById(id).get();
		Pago pago = pag;
		pago.setEstado("Terminado");
		Timestamp fechaPago = new Timestamp(System.currentTimeMillis());
		pago.setFecha(fechaPago);
		pago.setReferenciaPasarela(pagoService.generarReferencia());
		pago.setPedido(pedido);
		
		pagoService.save(pago);
		Mesa mesa = pedido.getMesa();
		if(mesa!=null) {
			mesa.setEstado("Disponible");
			mesaService.save(mesa);
		}
		pedido.setEstado("Terminado");
		pedidoService.save(pedido);
		return "redirect:/puestos/camarero/pedidos";
	}
	
	@GetMapping("camarero/pedidos")
	public String listar(Model model, HttpSession session) {
		
		Empleado empleado =empleadoService.findById( Integer.parseInt(session.getAttribute("idEmpleado").toString())).get();
		List<Pedido> pedidos = pedidoService.findByEmpleado(empleado);
		
		model.addAttribute("pedidos",pedidos);
		model.addAttribute("titulo","Lista de los pedidos");
		return "Trabajador/Camarero/pedidos";
	}
	
	@RequestMapping("camarero/detalles/{id}")
	public String detallesCamarero (@PathVariable("id") Integer id,Model model) {
		List<DetallePedido> detalles = new ArrayList<DetallePedido>();
		Optional<Pedido> optionalPedido = pedidoService.findById(id);
		detalles = optionalPedido.get().getDetalles();
		log.info("Detalles del pedido buscado: {}", detalles);
		model.addAttribute("detalles", detalles);
		return "Trabajador/camarero/detallePedidos";
	}
	
	
	@PostMapping("cerrar")
	public String cerrarSesion(HttpSession session) {
		carritoService.deleteByEmpleado(empleadoService.findById( Integer.parseInt(session.getAttribute("idEmpleado").toString())).get());
		session.removeAttribute("idEmpleado");
		session.removeAttribute("carrito");
		session.removeAttribute("cantidad");
		session.removeAttribute("total");
		return "redirect:/inicio";
	}
}
