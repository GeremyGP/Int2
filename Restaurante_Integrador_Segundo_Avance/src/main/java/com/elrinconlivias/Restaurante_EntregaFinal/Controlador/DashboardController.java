package com.elrinconlivias.Restaurante_EntregaFinal.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IEmpleadoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IMesaService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IPedidoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IProductoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IPagoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Rol;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Mesa;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pedido;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Producto;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pago;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;

@Controller
@RequestMapping("/administrador/dashboard")
public class DashboardController {

    @Autowired
    private IEmpleadoService empleadoService;

    @Autowired
    private IMesaService mesaService;

    @Autowired
    private IPedidoService pedidoService;

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IPagoService pagoService;

    @GetMapping("panel")
    public String showDashboard(Model model) {
        int empleadoCount = empleadoService.findAll().size();
        int mesaCount = mesaService.findAll().size();
        int pedidoCount = pedidoService.findAll().size();
        int productoCount = productoService.findAll().size();
        int pagoCount = pagoService.findAll().size();

        BigDecimal totalMontoPagos = pagoService.findAll()
            .stream()
            .map(Pago::getMontoTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Mesa> mesas = mesaService.findAll();
        int mesasDisponibles = 0;
        int mesasOcupadas = 0;
        for (Mesa mesa : mesas) {
            if (mesa.isOcupada()) {
                mesasOcupadas++;
            } else {
                mesasDisponibles++;
            }
        }

        List<Empleado> empleados = empleadoService.findAll();
        Map<String, Integer> roleCounts = new HashMap<>();
        for (Empleado empleado : empleados) {
            for (Rol rol : empleado.getRoles()) {
                String nombreRol = rol.getRolNombre();
                roleCounts.put(nombreRol, roleCounts.getOrDefault(nombreRol, 0) + 1);
            }
        }

        List<Pedido> pedidos = pedidoService.findAll();
        Map<String, Integer> estadoCounts = new HashMap<>();
        Map<String, Integer> tipoCounts = new HashMap<>();
        for (Pedido pedido : pedidos) {
            String estado = pedido.getEstado();
            estadoCounts.put(estado, estadoCounts.getOrDefault(estado, 0) + 1);

            String tipo = pedido.getTipo();
            tipoCounts.put(tipo, tipoCounts.getOrDefault(tipo, 0) + 1);
        }

        List<Producto> productos = productoService.findAll();
        Map<String, List<String>> tipoProductoNombres = new HashMap<>();
        Map<String, Integer> tipoProductoCounts = new HashMap<>();
        for (Producto producto : productos) {
            String categoriaNombre = producto.getCategoria().getNombre();
            tipoProductoNombres
                .computeIfAbsent(categoriaNombre, k -> new ArrayList<>())
                .add(producto.getNombre());
            tipoProductoCounts.put(categoriaNombre, tipoProductoCounts.getOrDefault(categoriaNombre, 0) + 1);
        }

        // Análisis detallado de pagos
        List<Pago> pagos = pagoService.findAll();
        Map<String, Integer> metodoPagoCounts = new HashMap<>();
        Map<String, BigDecimal> metodoPagoMontos = new HashMap<>();
        String metodoMayorMonto = "";
        BigDecimal mayorMonto = BigDecimal.ZERO;

        for (Pago pago : pagos) {
            String metodo = pago.getMetodoPago();
            BigDecimal monto = pago.getMontoTotal();

            metodoPagoCounts.put(metodo, metodoPagoCounts.getOrDefault(metodo, 0) + 1);
            metodoPagoMontos.put(metodo, metodoPagoMontos.getOrDefault(metodo, BigDecimal.ZERO).add(monto));

            if (metodoPagoMontos.get(metodo).compareTo(mayorMonto) > 0) {
                mayorMonto = metodoPagoMontos.get(metodo);
                metodoMayorMonto = metodo;
            }
        }

        model.addAttribute("empleadoCount", empleadoCount);
        model.addAttribute("mesaCount", mesaCount);
        model.addAttribute("pedidoCount", pedidoCount);
        model.addAttribute("productoCount", productoCount);
        model.addAttribute("pagoCount", pagoCount);
        model.addAttribute("totalMontoPagos", totalMontoPagos);
        model.addAttribute("roles", roleCounts);
        model.addAttribute("mesasDisponibles", mesasDisponibles);
        model.addAttribute("mesasOcupadas", mesasOcupadas);
        model.addAttribute("estadoCounts", estadoCounts);
        model.addAttribute("tipoCounts", tipoCounts);
        model.addAttribute("tipoProductoNombres", tipoProductoNombres);
        model.addAttribute("tipoProductoCounts", tipoProductoCounts);
        
        model.addAttribute("metodoPagoCounts", metodoPagoCounts);
        model.addAttribute("metodoPagoMontos", metodoPagoMontos);
        model.addAttribute("metodoMayorMonto", metodoMayorMonto);
        model.addAttribute("mayorMonto", mayorMonto);

        return "Administrador/Dashboard/dashboard";
    }
}
