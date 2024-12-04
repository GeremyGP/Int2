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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Rol;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IEmpleadoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IRolService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.UploadFileEmpleadoService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/administrador/empleados")
public class EmpleadoController {
private final Logger LOGGER = LoggerFactory.getLogger(EmpleadoController.class);
	
	@Autowired
	@Qualifier("empleado")
	IEmpleadoService empleadoService;
	
	@Autowired
	IRolService rolService;
	
	@Autowired
	private UploadFileEmpleadoService upload;
	
	@ModelAttribute("roles")
	public List<String> cargarRoles(){
		return rolService.obtenerRoles();
	}
	
	@GetMapping("/")
	public String listarTodos(Model model) {
	    List<Empleado> empleados = empleadoService.findAll();
	    model.addAttribute("empleado", empleados);
	    model.addAttribute("titulo", "Lista de todos los empleados");
	    return "Administrador/Empleados/empleadoPdf"; 
	}
	
	@GetMapping("listar/{estado}")
	public String listar(@PathVariable("estado") String estado,  Model model) {
		if("activos".equals(estado)) {
			List<Empleado> empleados = empleadoService.findByActivo(true);
			model.addAttribute("empleados",empleados);
		}else if("inactivos".equals(estado)) {
			List<Empleado> empleados = empleadoService.findByActivo(false);
			model.addAttribute("empleados",empleados);
			model.addAttribute("estado", false);
		}
		model.addAttribute("titulo","Lista de los empleados");
		return "Administrador/Empleados/empleadoListar";
	}
	
	@RequestMapping("form")
	public String formulario (Model model) {
		//Plato plato = new Plato();
		//model.put("plato",plato);
		model.addAttribute("btn", "Guardar Empleado");
		model.addAttribute("titulo","Insertar nuevo empleado");
		return "Administrador/Empleados/empleadoInsertar";
	}
	
	@RequestMapping("form/{id}")
	public String actualizar (@PathVariable("id") Integer id,Model model) {
		Empleado empleado = new Empleado();
		Optional<Empleado> optionalEmpleado = empleadoService.findById(id);
		empleado = optionalEmpleado.get();
		LOGGER.info("Empleado buscado: {}", empleado);
		model.addAttribute("empleado", empleado);
		model.addAttribute("btn","Actualizar empleado");
		model.addAttribute("titulo","Editar empleado");
		return "Administrador/Empleados/empleadoEditar";
	}
	
	@PostMapping("insertar")
    public String guardar(@RequestParam("rolNombre") String rolNombre, Empleado empleado, @RequestParam("img") MultipartFile file) throws IOException {
        
        // Verificar si se ha proporcionado un archivo
        if (empleado.getIdEmpleado() == null){ //Cuando creamos un nuevo empleado
            String nombreImagen = upload.saveImage(file); // Guarda la imagen y obtiene el nombre
            empleado.setImagen(nombreImagen); // Asigna el nombre de la imagen al campo 'imagen'
        }

        // Guardar un empleado
        empleado.setActivo(true);
        empleadoService.save(empleado);
        // Guardar un nuevo rol
        Rol rol = new Rol();
        rol.setRolNombre(rolNombre);
        rol.setEmpleado(empleado);
        rolService.save(rol);

        return "redirect:/administrador/empleados/listar/activos";
    }
	
	@PostMapping("actualizar")
	public String actualizar(Empleado empleado, @RequestParam("img") MultipartFile file) throws IOException{
		if(file.isEmpty()) { // Editamos el empleado pero no cambiamos la imagen
			Empleado empl = new Empleado();
			empl = empleadoService.findById(empleado.getIdEmpleado()).get();
			empleado.setImagen(empl.getImagen());
    	}else {  //Editar el empleado y la imagen
    		Empleado empl = new Empleado();
    		empl = empleadoService.findById(empleado.getIdEmpleado()).get();
    		
    		if(!"DefaultEmpleado.jpg".equals(empl.getImagen())) {
    			upload.deleteImage(empl.getImagen());
    		}
    		
    		String nombreImagen = upload.saveImage(file); // Guarda la imagen y obtiene el nombre
    		empleado.setImagen(nombreImagen);// Asigna el nombre de la imagen al campo 'imagen'
    	}
		empleadoService.save(empleado);
		return "redirect:/administrador/empleados/listar/activos";
	}

	
	@RequestMapping("eliminar/{id}")
	public String eliminar(@PathVariable("id") Integer id) {
		Empleado empl = new Empleado();
		empl = empleadoService.findById(id).get();
		if(!empl.getImagen().equals("DefaultEmpleado.jpg")) {
			upload.deleteImage(empl.getImagen());
		}
		empleadoService.desactivate(id);
		return "redirect:/administrador/empleados/listar/activos";
	}
}
