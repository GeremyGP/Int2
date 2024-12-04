package com.elrinconlivias.Restaurante_EntregaFinal.Controlador;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Carrito;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Cliente;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Producto;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.ICarritoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IClienteService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IEmpleadoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IProductoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/carrito")
public class CarritoController {
	@Autowired
	ICarritoService carritoService;
	
	@Autowired
	IEmpleadoService empleadoService;
	
	@Autowired
	IClienteService clienteService;
	
	@Autowired
	IProductoService productoService;
	
	private Logger logg = LoggerFactory.getLogger(CarritoController.class);
	
	@GetMapping("listar")
	public String obtenerCarrito(HttpSession session) {
		if(session.getAttribute("idEmpleado")!=null) {
			Empleado empleado = empleadoService.findById(Integer.parseInt(session.getAttribute("idEmpleado").toString())).get();
			List<Carrito> carrito= carritoService.findByEmpleado(empleado);
			session.setAttribute("carrito", carrito);
			BigDecimal total = carrito.stream()
			                          .map(item -> item.getSubTotal())  // Obtenemos el subtotal de cada item
			                          .reduce(BigDecimal.ZERO, BigDecimal::add); // Sumamos los subtotales
			session.setAttribute("total", total);
			return "redirect:/puestos/camarero/menu/Lunes";
		}else if(session.getAttribute("idCliente")!=null) {
			Cliente cliente =clienteService.findById( Integer.parseInt(session.getAttribute("idCliente").toString())).get();
			List<Carrito> carrito= carritoService.findByCliente(cliente);
			session.setAttribute("carrito", carrito);
			BigDecimal total = carrito.stream()
                    .map(item -> item.getSubTotal())  // Obtenemos el subtotal de cada item
                    .reduce(BigDecimal.ZERO, BigDecimal::add); // Sumamos los subtotales
			session.setAttribute("total", total);
			return "redirect:/inicio/menu/Lunes";
		}else {
			return "redirect:/inicio/menu/Lunes";
		}
	}
	
	@PostMapping("add/{id}")
	public String addItem(@RequestParam(value = "cantidad", required = false, defaultValue = "1") Integer cant, @PathVariable("id") Integer id, HttpSession session) {
		if(session.getAttribute("idEmpleado")!=null) {
			Empleado empleado = empleadoService.findById(Integer.parseInt(session.getAttribute("idEmpleado").toString())).get();
			Optional<Producto> producto = productoService.findById(id);
			if(producto.isPresent()) {
				Optional<Carrito> carrito = carritoService.findByEmpleadoAndProducto(empleado, producto.get());
				if(carrito.isPresent()) {
					Carrito encontrado = carrito.get();
					int cantidad = encontrado.getCantidad()+cant;
					encontrado.setCantidad(cantidad);
					BigDecimal integerAsBigDecimal = new BigDecimal(cantidad);
					encontrado.setSubTotal(producto.get().getPrecio().multiply(integerAsBigDecimal));
					carritoService.save(encontrado);
				}else {
					Carrito car = new Carrito();
					car.setCantidad(cant);
					car.setEmpleado	(empleado);
					car.setProducto(producto.get());
					BigDecimal integerAsBigDecimal = new BigDecimal(cant);
					car.setSubTotal(producto.get().getPrecio().multiply(integerAsBigDecimal));
					carritoService.save(car);
				}
			}
			return "redirect:/carrito/listar";
		}else if(session.getAttribute("idCliente")!=null) {
			Cliente cliente =clienteService.findById( Integer.parseInt(session.getAttribute("idCliente").toString())).get();
			Optional<Producto> producto = productoService.findById(id);
			if(producto.isPresent()) {
				Optional<Carrito> carrito = carritoService.findByClienteAndProducto(cliente, producto.get());
				if(carrito.isPresent()) {
					Carrito encontrado = carrito.get();
					int cantidad = encontrado.getCantidad()+cant;
					encontrado.setCantidad(cantidad);
					BigDecimal integerAsBigDecimal = new BigDecimal(cantidad);
					encontrado.setSubTotal(producto.get().getPrecio().multiply(integerAsBigDecimal));
					carritoService.save(encontrado);
				}else {
					Carrito car = new Carrito();
					car.setCantidad(cant);
					car.setCliente(cliente);
					car.setProducto(producto.get());
					BigDecimal integerAsBigDecimal = new BigDecimal(cant);
					car.setSubTotal(producto.get().getPrecio().multiply(integerAsBigDecimal));
					carritoService.save(car);
				}
			}
			return "redirect:/carrito/listar";
		}else {
			return "redirect:/carrito/listar";
		}
	}
	
	@PostMapping("remove/{id}")
	public String removeItem(@PathVariable("id") Integer id, HttpSession session) {
		if(session.getAttribute("idEmpleado")!=null) {
			Empleado empleado = empleadoService.findById(Integer.parseInt(session.getAttribute("idEmpleado").toString())).get();
			Optional<Producto> producto = productoService.findById(id);
			if(producto.isPresent()) {
				Optional<Carrito> carrito = carritoService.findByEmpleadoAndProducto(empleado, producto.get());
				if(carrito.isPresent()) {
					Carrito encontrado = carrito.get();
					int cantidad = encontrado.getCantidad()-1;
					encontrado.setCantidad(cantidad);
					if(encontrado.getCantidad()<1) {
						carritoService.delete(encontrado.getIdCarrito());
					}else {
						BigDecimal integerAsBigDecimal = new BigDecimal(cantidad);
						encontrado.setSubTotal(producto.get().getPrecio().multiply(integerAsBigDecimal));
						carritoService.save(encontrado);
					}
				}
			}
			return "redirect:/carrito/listar";
		}else if(session.getAttribute("idCliente")!=null) {
			Cliente cliente =clienteService.findById( Integer.parseInt(session.getAttribute("idCliente").toString())).get();
			Optional<Producto> producto = productoService.findById(id);
			if(producto.isPresent()) {
				Optional<Carrito> carrito = carritoService.findByClienteAndProducto(cliente, producto.get());
				if(carrito.isPresent()) {
					Carrito encontrado = carrito.get();
					int cantidad = encontrado.getCantidad()-1;
					encontrado.setCantidad(cantidad);
					if(encontrado.getCantidad()<1) {
						carritoService.delete(encontrado.getIdCarrito());
					}else {
						BigDecimal integerAsBigDecimal = new BigDecimal(cantidad);
						encontrado.setSubTotal(producto.get().getPrecio().multiply(integerAsBigDecimal));
						carritoService.save(encontrado);
					}
				}
			}
			return "redirect:/carrito/listar";
		}else {
			return "redirect:/carrito/listar";
		}
	}
	
	@PostMapping("clean")
	public String limpiarCarrito(HttpSession session) {
		if(session.getAttribute("idEmpleado")!=null) {
			Empleado empleado = empleadoService.findById(Integer.parseInt(session.getAttribute("idEmpleado").toString())).get();
			carritoService.deleteByEmpleado(empleado);
		}else if(session.getAttribute("idCliente")!=null){
			Cliente cliente =clienteService.findById(Integer.parseInt(session.getAttribute("idCliente").toString())).get();
			carritoService.deleteByCliente(cliente);
		}
		return "redirect:/carrito/listar";
	}
	
}
