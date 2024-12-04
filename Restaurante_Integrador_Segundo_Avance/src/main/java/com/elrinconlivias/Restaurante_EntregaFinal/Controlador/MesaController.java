package com.elrinconlivias.Restaurante_EntregaFinal.Controlador;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Mesa;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Rol;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IMesaService;

@Controller
@RequestMapping("/administrador/mesas")
public class MesaController {
	private final Logger LOGGER = LoggerFactory.getLogger(MesaController.class);
	
	@Autowired
	IMesaService mesaService;
	
	@GetMapping("listar/{estado}")
	public String listarPlatos(@PathVariable("estado") String estado, Model model) {
		if("activos".equals(estado)) {
			model.addAttribute("mesas", mesaService.findByActivo(true));
		}else if("inactivos".equals(estado)) {
			model.addAttribute("mesas", mesaService.findByActivo(false));
			model.addAttribute("estado",false);
		}
		model.addAttribute("titulo","Lista de las mesas");
		return "Administrador/Mesas/mesaListar";
	}
	
	@RequestMapping("form")
	public String formulario (Model model) {
		//Plato plato = new Plato();
		//model.put("plato",plato);
		model.addAttribute("btn", "Guardar Mesa");
		model.addAttribute("titulo","Insertar nueva mesa:");
		return "Administrador/Mesas/mesaInsertar";
	}
	
	@GetMapping("form/{id}")
	public String actualizar (@PathVariable("id") Integer id, Model model) {
		Mesa mesa = new Mesa();
		Optional<Mesa> optionalMesa = mesaService.findById(id);
		mesa = optionalMesa.get();
		LOGGER.info("Mesa buscada: {}", mesa);
		model.addAttribute("mesa", mesa);
		model.addAttribute("btn","Actualizar mesa");
		model.addAttribute("titulo","Editar mesa:");
		return "Administrador/Mesas/mesaEditar";
	}
	
	@PostMapping("insertar")
    public String guardar(Mesa mesa) throws IOException {
        // Guardar una mesa
		mesa.setActivo(true);
        mesaService.save(mesa);

        return "redirect:/administrador/mesas/listar/activos";
    }
	
	@PostMapping("actualizar")
	public String actualizar(Mesa mesa) throws IOException{
		mesaService.save(mesa);
		return "redirect:/administrador/mesas/listar/activos";
	}

	
	@RequestMapping("eliminar/{id}")
	public String eliminar(@PathVariable("id") Integer id) {
		Mesa mesa = new Mesa();
		mesa = mesaService.findById(id).get();
		mesaService.delete(id);
		return "redirect:/administrador/mesas/listar/activos";
	}
}
