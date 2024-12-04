package com.elrinconlivias.Restaurante_EntregaFinal.Controlador;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Carrito;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Cliente;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Comentario;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Comprobante;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.DetallePedido;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pago;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pedido;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Producto;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Rol;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IDetallePedidoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IEmpleadoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IMenuDiaService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IPagoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IPedidoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IProductoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IRolService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.QRCodeService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.ICarritoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IClienteService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IComentarioService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IComprobanteService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/inicio")
public class InicioController {
	
	
	
	private final Logger log = LoggerFactory.getLogger(InicioController.class);
	
	@Autowired
	IProductoService productoService;
	
	@Autowired
	IPedidoService pedidoService;
	
	@Autowired
	IDetallePedidoService detallePedidoService;
	
	@Autowired
	IClienteService clienteService;
	
	@Autowired
	IEmpleadoService empleadoService;
	
	@Autowired
	IRolService rolService;
	
	@Autowired
	IMenuDiaService menuDiaService;
	
	@Autowired
	ICarritoService carritoService;
	
	@Autowired
	IPagoService pagoService;

	@Autowired
	IComentarioService comentarioService;
	
	@Autowired
	IComprobanteService comprobanteService;
	
	@Autowired
	QRCodeService qrCodeService;
	
	@GetMapping
	public String Home(Model model, HttpSession session) {
		log.info("Sesion del usuario: {}", session.getAttribute("idCliente"));
		List<Producto> allPlatos = productoService.findByActivo(true);
        List<Producto> limitedPlatos = allPlatos.stream()
                                             .limit(10) // Limita a los primeros 10 elementos
                                             .collect(Collectors.toList());

        model.addAttribute("productos", limitedPlatos);
		model.addAttribute("sesion", session.getAttribute("idCliente"));
		return "Cliente/index";
	}
	
	@GetMapping("menu/{dia}")
	public String menu(@PathVariable("dia") String dia, Model model, HttpSession session) {
		
		log.info("Sesion del usuario: {}", session.getAttribute("idCliente"));
		model.addAttribute("menuDia",menuDiaService.findByDiaNombre(dia));
		model.addAttribute("sesion", session.getAttribute("idCliente"));
		if(session.getAttribute("idCliente")!=null) {
			Cliente cliente =clienteService.findById( Integer.parseInt(session.getAttribute("idCliente").toString())).get();
			model.addAttribute("carrito", carritoService.findByCliente(cliente));
			List<Carrito> car = carritoService.findByCliente(cliente);
			if(car!=null) {
				int cantidad = car.stream()
		                .mapToInt(item -> item.getCantidad())  // Obtenemos la cantidad de cada item
		                .sum();  // Sumamos las cantidades
				model.addAttribute("cantidad", cantidad);
			}
		}
		model.addAttribute("total", session.getAttribute("total"));
		return "Cliente/menu";
	}
	
	@GetMapping("detalleProducto/{id}")
	public String detalleProducto(@PathVariable("id") Integer id, Model model, HttpSession session) {
		Object sesion = session.getAttribute("idCliente");
		Producto producto = productoService.findById(id).get();
		if(sesion!=null) {
			Cliente cliente =clienteService.findById( Integer.parseInt(session.getAttribute("idCliente").toString())).get();
			Optional<Comentario> com = comentarioService.findByProductoAndCliente(producto, cliente);
			model.addAttribute("comentarioPrincipal", com.orElse(null));
		}
		model.addAttribute("producto", producto);
		model.addAttribute("imagenProducto", producto.getImagen());
		model.addAttribute("comentarios",comentarioService.findByProducto(producto));
		model.addAttribute("sesion", session.getAttribute("idCliente"));
		return "Cliente/detalleProducto";
	}
	
	@PostMapping("comentar/{idProducto}")
	public String comentar(@PathVariable("idProducto") Integer idProducto, Comentario co, HttpSession session) {
		Cliente cliente =clienteService.findById( Integer.parseInt(session.getAttribute("idCliente").toString())).get();
		Producto producto = productoService.findById(idProducto).get();
		Comentario com = co;
		com.setCliente(cliente);
		com.setProducto(producto);
		// Obtener la fecha y hora actuales
        Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());
        com.setFechaCreacion(fechaCreacion);
		comentarioService.save(com);
		productoService.actualizarCalificacion(comentarioService.findByProducto(producto), producto);
		return "redirect:/inicio/detalleProducto/"+idProducto;
	}
	
	@GetMapping("pedidos")
	public String listar(Model model, HttpSession session) {
		
		Cliente cliente =clienteService.findById( Integer.parseInt(session.getAttribute("idCliente").toString())).get();
		List<Pedido> pedidos = pedidoService.findByCliente(cliente);
		
		model.addAttribute("pedidos",pedidos);
		model.addAttribute("titulo","Lista de los pedidos");
		return "Cliente/pedidos";
	}
	
	@RequestMapping("detalles/{id}")
	public String detalles (@PathVariable("id") Integer id,Model model) {
		List<DetallePedido> detalles = new ArrayList<DetallePedido>();
		Optional<Pedido> optionalPedido = pedidoService.findById(id);
		detalles = optionalPedido.get().getDetalles();
		log.info("Detalles del pedido buscado: {}", detalles);
		model.addAttribute("detalles", detalles);
		return "Cliente/detallePedidos";
	}
	
	@GetMapping("resumenPedido")
	public String detalles(HttpSession session, Model model) {
		Cliente cliente =clienteService.findById( Integer.parseInt(session.getAttribute("idCliente").toString())).get();
		
		List<DetallePedido> detalles = new ArrayList<>();
		
		List<Carrito> car = carritoService.findByCliente(cliente);
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
		
		if(car!=null) {
			int cantidad = car.stream()
	                .mapToInt(item -> item.getCantidad())  // Obtenemos la cantidad de cada item
	                .sum();  // Sumamos las cantidades
			model.addAttribute("cantidad", cantidad);
			
			// Calcular la cantidad total de productos de tipo "Entrada"
			int totalEntradas = car.stream()
			    .filter(item -> "Entrada".equals(item.getProducto().getCategoria().getNombre()))
			    .mapToInt(item -> item.getCantidad()) // Obtener la cantidad de cada producto
			    .sum(); // Sumar todas las cantidades
			model.addAttribute("entradas", totalEntradas);
			
			// Calcular la cantidad total de productos de tipo "Plato Principal"
			int totalPlatosPrincipales = car.stream()
			    .filter(item -> "Plato Principal".equals(item.getProducto().getCategoria().getNombre()))
			    .mapToInt(item -> item.getCantidad())
			    .sum();
			model.addAttribute("platosPrincipales", totalPlatosPrincipales);

			// Calcular la cantidad total de productos de tipo "Bebida"
			int totalBebidas = car.stream()
			    .filter(item -> "Bebida".equals(item.getProducto().getCategoria().getNombre()))
			    .mapToInt(item -> item.getCantidad())
			    .sum();
			model.addAttribute("bebidas", totalBebidas);

			// Calcular la cantidad total de productos de tipo "Postre"
			int totalPostres = car.stream()
			    .filter(item -> "Postre".equals(item.getProducto().getCategoria().getNombre()))
			    .mapToInt(item -> item.getCantidad())
			    .sum();
			model.addAttribute("postres", totalPostres);
		}
		session.setAttribute("resumen", detalles);
        model.addAttribute("resumen", detalles);
        model.addAttribute("total",sumaTotal);
        model.addAttribute("cliEmail",cliente.getEmail());
        model.addAttribute("cliNombre",cliente.getNombre());
        model.addAttribute("cliApellido",cliente.getApellido());
        model.addAttribute("cliTelefono",cliente.getTelefono());
		
		return "Cliente/metodoPago";
	}
	
	
	/*
	@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET})
	@RequestMapping("/carrito")
	public String addCarrito(@RequestBody Map<String, Object> data, Model model) {
		
		// Obtener los datos enviados en el JSON
	    List<Integer> ids = (List<Integer>) data.get("listId");
	    List<Integer> cantidades = (List<Integer>) data.get("listCantidad");
        
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < ids.size(); i++) {
            map.put(ids.get(i), cantidades.get(i));
        }
        
     // Recorrer el mapa usando keySet()
        for (Integer codigo : map.keySet()) {
            Integer cant = map.get(codigo);
            DetallePedido detalleOrden = new DetallePedido();
    		Plato plato = new Plato();
    		double sumaTotal = 0;
    		Optional<Plato> optionalProducto = platoService.findById(codigo);
    		log.info("Producto añadido: {}", optionalProducto.get());
    		log.info("Cantidad: {}", cant);
    		plato = optionalProducto.get();
    		detalleOrden.setCantidad(cant);
    		detalleOrden.setPrecioUnitario(plato.getPrecio());
    		detalleOrden.setNombre(plato.getNombre());
    		// Convertir el Integer a BigDecimal
            BigDecimal integerAsBigDecimal = new BigDecimal(cant);
    		detalleOrden.setSubTotal(plato.getPrecio().multiply(integerAsBigDecimal));
    		detalleOrden.setPlato(plato);
    		detalles.add(detalleOrden);
        }
        model.addAttribute("carrito", detalles);
		return "Cliente/metodoPago";
	}
	*/
	
	@PostMapping("pagar")
	public String pagarPedido(@RequestParam(name="ra", required = false) String ras ,@RequestParam(name="ru", required = false) String ru,Pago pag,Comprobante comprobante, HttpSession session, Model model) throws ServletException, IOException {
		Pago pago = pag;
		pago.setEstado("Terminado");
		Timestamp fechaPago = new Timestamp(System.currentTimeMillis());
		pago.setFecha(fechaPago);
		pago.setMetodoPago("Tarjeta");
		pago.setReferenciaPasarela(pagoService.generarReferencia());
		
		
		Pago pagado = pagoService.save(pago);
		
		if(pagado !=null) {
			List<DetallePedido> detalles = (List<DetallePedido>) session.getAttribute("resumen");
			Pedido pedido = new Pedido();
			Cliente cliente =clienteService.findById( Integer.parseInt(session.getAttribute("idCliente").toString())).get();
			pedido.setCliente(cliente);
			// Calcular la suma total usando BigDecimal
	        BigDecimal sumaTotal = detalles.stream()
	                .map(DetallePedido::getSubTotal) // Asumimos que getSubTotal devuelve BigDecimal
	                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sumar todos los valores BigDecimal
	        pedido.setTotal(sumaTotal);
	        pedido.setTipo("Online");

	        pedido.setEstado("Pendiente");
	     // Obtener la fecha y hora actuales
	        Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());
	        pedido.setFechaCreacion(fechaCreacion);
			pedidoService.save(pedido);
			
			
			for (DetallePedido dt : detalles) {
				dt.setPedido(pedido);
				dt.setEstado(false);
				detallePedidoService.save(dt);
			}
			
			pagado.setPedido(pedido);
			pagado.setCliente(cliente);
			pagoService.save(pagado);
			
			carritoService.deleteByCliente(clienteService.findById( Integer.parseInt(session.getAttribute("idCliente").toString())).get());
			session.removeAttribute("carrito");
			session.removeAttribute("total");
			session.removeAttribute("cantidad");
			
			
			//Creamos el comprobante
			
			Comprobante comp = comprobante;
			comp.setTotal(pagado.getMontoTotal());
			comp.setPago(pagado);
			if(ru!=null && ras!=null) {
				comp.setRuc(ru);
				comp.setRazonSocial(ras);
			}
			comp.setIgv(comprobanteService.calcularIGV(pagado.getMontoTotal()));
			comp.setMetodoPago(pagado.getMetodoPago());
			Timestamp fechaEmision = new Timestamp(System.currentTimeMillis());
			comp.setFechaEmision(fechaEmision);
			comp.setEstado("Pagado");
			
			comprobanteService.save(comp);
			model.addAttribute("comprobante", comp);
			
			return "Cliente/pago";
		}else {
			return "redirect: /inicio/menu/lunes";
		}
	}
	
	@PostMapping("cancelarPedido/{id}")
	public String cancelar(@PathVariable("id") Integer id) {
		Pedido ped = pedidoService.findById(id).get();
		if("Pendiente".equals(ped.getEstado())) {
			ped.setEstado("Cancelado");
			pedidoService.save(ped);
		}else {
			
		}
		return "redirect:/inicio/pedidos";
	}

	@GetMapping("mostrarComprobante/{id}")
	public String comprobante(@PathVariable("id") Integer id, Model modelo) {
		Comprobante comp = comprobanteService.findById(id).get();
		modelo.addAttribute("comprobante", comp);
		modelo.addAttribute("productos", comp.getPago().getPedido().getDetalles());
		
		// Generar el QR con el servicio
		String qrData = "http://localhost:8080/inicio/imprimirComprobante/" + id;
        String qrBase64 = qrCodeService.generarQR(qrData, 200, 200);
        modelo.addAttribute("qrBase64", qrBase64);
        
		return "Cliente/comprobante";
	}
	
	@GetMapping("imprimirComprobante/{id}")
	public String impComprobante(@PathVariable("id") Integer id, Model modelo) {
		Comprobante comp = comprobanteService.findById(id).get();
		modelo.addAttribute("comprobante", comp);
		modelo.addAttribute("productos", comp.getPago().getPedido().getDetalles());
		
		// Generar el QR con el servicio
		String qrData = "http://localhost:8080/inicio/imprimirComprobante/" + id;
        String qrBase64 = qrCodeService.generarQR(qrData, 200, 200);
        modelo.addAttribute("qrBase64", qrBase64);
        
		return "Cliente/imprimirComprobante";
	}
	
	@GetMapping("loginForm")
	public String form(Model modelo) {
		modelo.addAttribute("usuario",new Cliente());
		return "Cliente/login";
	}
	
	@PostMapping("registrar")
	public String registrar(Cliente cliente, HttpSession session) {
		Date fechaCreacion = new Date();
		cliente.setFechaCreacion(fechaCreacion);
		clienteService.save(cliente);
		session.setAttribute("idCliente", cliente.getIdCliente());
		return "redirect:/inicio";
	}
	
	@PostMapping("login")
	public String login(@RequestParam String emailLog, @RequestParam String claveLog,HttpSession session,  Model modelo) {
		if(!emailLog.isEmpty() && !claveLog.isEmpty()) {
			Optional<Cliente> cliente = clienteService.findByEmail(emailLog);
			Optional<Empleado> empleado = empleadoService.findByEmail(emailLog);
			
			if (empleado.isPresent() && empleado.get().isActivo()) {
				if(empleado.get().getClave().equals(claveLog)) {
					session.setAttribute("idEmpleado", empleado.get().getIdEmpleado());
					List<Rol> roles = rolService.findByEmpleado(empleado.get());
					for(Rol rol: roles) {
						if("Administrador".equals(rol.getRolNombre())) {
							return "redirect:/administrador/productos/listar/activos";
						}
					}
					for(Rol rol: roles) {
						if("Camarero".equals(rol.getRolNombre())){
							return "redirect:/carrito/listar";
						}else if("Cocinero".equals(rol.getRolNombre())) {
							return "redirect:/puestos/cocinero/pedidos/Pendiente";
						}else {
							return "redirect:/inicio";
						}
					}
				}else {
					return "redirect:/inicio";
				}
			} else if(cliente.isPresent()){
				if(cliente.get().getClave().equals(claveLog)) {
					session.setAttribute("idCliente", cliente.get().getIdCliente());
				}
				return "redirect:/inicio";
			}else {
				log.info("Usuario no existe");
			}
		}
		return "redirect:/inicio";
	}
	
	@PostMapping("cerrar")
	public String cerrarSesion(HttpSession session) {
		carritoService.deleteByCliente(clienteService.findById( Integer.parseInt(session.getAttribute("idCliente").toString())).get());
		session.removeAttribute("carrito");
		session.removeAttribute("total");
		session.removeAttribute("cantidad");
		session.removeAttribute("idCliente");
		return "redirect:/inicio";
	}
}
