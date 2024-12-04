package com.elrinconlivias.Restaurante_EntregaFinal.Controlador;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Carrito;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.DetallePedido;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Producto;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IDetallePedidoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IPedidoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IProductoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IClienteService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/detalles")
public class DetallesController {
	
	List<DetallePedido> detalles = new ArrayList<>();
	
	private final Logger log = LoggerFactory.getLogger(InicioController.class);
	
	@Autowired
	IProductoService productoService;
	
	@Autowired
	IPedidoService pedidoService;
	
	@Autowired
	IDetallePedidoService detallePedidoService;
	
	@Autowired
	IClienteService clienteService;
	

	@PostMapping("prueba")
    public Map<String, Object> addCarrito(@RequestBody Map<String, Object> data,HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        if (data == null || !data.containsKey("listId") || !data.containsKey("listCantidad")) {
            response.put("status", "error");
            response.put("message", "Datos de la solicitud inválidos");
            return response;
        }
        
        List<Integer> ids = (List<Integer>) data.get("listId");
        List<Integer> cantidades = (List<Integer>) data.get("listCantidad");
        
        if (ids == null || cantidades == null || ids.size() != cantidades.size()) {
            response.put("status", "error");
            response.put("message", "Listas de IDs y cantidades deben tener el mismo tamaño");
            return response;
        }

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < ids.size(); i++) {
            map.put(ids.get(i), cantidades.get(i));
        }

        List<DetallePedido> detalles = new ArrayList<>();
        for (Integer codigo : map.keySet()) {
            Integer cant = map.get(codigo);
            DetallePedido detalleOrden = new DetallePedido();
            Optional<Producto> optionalProducto = productoService.findById(codigo);
            if (optionalProducto.isPresent()) {
            	Producto plato = optionalProducto.get();
                detalleOrden.setCantidad(cant);
                detalleOrden.setPrecioUnitario(plato.getPrecio());
                detalleOrden.setNombreProducto(plato.getNombre());
                BigDecimal integerAsBigDecimal = new BigDecimal(cant);
                detalleOrden.setSubTotal(plato.getPrecio().multiply(integerAsBigDecimal));
                detalleOrden.setProducto(plato);
                detalles.add(detalleOrden);
            } else {
                // Manejar el caso en que el plato no se encuentra
            }
        }
        response.put("status", "success");
        response.put("carrito", detalles);
        session.setAttribute("carrito", detalles);
        return response;
    }
	}

