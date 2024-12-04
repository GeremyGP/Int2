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

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Producto;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.ICategoriaService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.IProductoService;
import com.elrinconlivias.Restaurante_EntregaFinal.Servicios.UploadFilePlatoService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/administrador/productos")

public class ProductoController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	@Qualifier("producto")
	IProductoService productoService;
	
	@Autowired
	@Qualifier("categoria")
	ICategoriaService categoriaService;
	
	@Autowired
	private UploadFilePlatoService upload;
	
	@GetMapping("/")
	public String listarTodos(Model model) {
	    List<Producto> productos = productoService.findAll();
	    model.addAttribute("productos", productos);
	    model.addAttribute("titulo", "Lista de todos los productos");
	    return "Administrador/Productos/productoPdf";
	}
	
	@GetMapping("/listar/{estado}")
	public String listar(@PathVariable("estado") String estado,  Model model, HttpServletRequest request) {
		if("activos".equals(estado)) {
			List<Producto> productos = productoService.findByActivo(true);
			model.addAttribute("productos",productos);
		}else if("inactivos".equals(estado)) {
			List<Producto> productos = productoService.findByActivo(false);
			model.addAttribute("productos",productos);
			model.addAttribute("estado", false);
		}
		model.addAttribute("titulo","Lista de los productos");
		return "Administrador/Productos/productoListar";
	}
	
	@RequestMapping("/form")
	public String formulario (Model model) {
		model.addAttribute("btn", "Guardar Producto");
		model.addAttribute("categorias", categoriaService.findAll());
		model.addAttribute("titulo","Insertar nuevo producto");
		return "Administrador/Productos/productoInsertar";
	}
	
	@RequestMapping("/form/{id}")
	public String actualizar (@PathVariable("id") Integer id,Model model) {
		Producto producto = new Producto();
		Optional<Producto> optionalPlato = productoService.findById(id);
		producto = optionalPlato.get();
		LOGGER.info("Producto buscado: {}", producto);
		model.addAttribute("producto", producto);
		model.addAttribute("btn", "Actualizar Producto");
		model.addAttribute("categorias", categoriaService.findAll());
		model.addAttribute("titulo","Editar producto");
		return "Administrador/Productos/productoEditar";
	}
	
	@PostMapping("/insertar")
    public String guardar(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
        
        // Verificar si se ha proporcionado un archivo
        if (producto.getIdProducto() == null){ //Cuando creamos un nuevo producto
            String nombreImagen = upload.saveImage(file); // Guarda la imagen y obtiene el nombre
            producto.setImagen(nombreImagen); // Asigna el nombre de la imagen al campo 'imagen'
        }

        // Guardar el objeto Producto
        producto.setActivo(true);
        productoService.save(producto);

        return "redirect:/administrador/productos/listar/activos";
    }
	
	@PostMapping("/actualizar")
	public String actualizar(Producto producto, @RequestParam("img") MultipartFile file) throws IOException{
		if(file.isEmpty()) { // Editamos el plato pero no cambiamos la imagen
			Producto prod = new Producto();
			prod = productoService.findById(producto.getIdProducto()).get();
    		producto.setImagen(prod.getImagen());
    	}else {  //Editar el plato y la imagen
    		Producto prod = new Producto();
    		prod = productoService.findById(producto.getIdProducto()).get();
    		
    		if(!prod.getImagen().equals("DefaultPlato.jpeg")) {
    			upload.deleteImage(prod.getImagen());
    		}
    		
    		String nombreImagen = upload.saveImage(file); // Guarda la imagen y obtiene el nombre
            producto.setImagen(nombreImagen);// Asigna el nombre de la imagen al campo 'imagen'
    	}
		productoService.save(producto);
		return "redirect:/administrador/productos/listar/activos";
	}

	
	@RequestMapping("/eliminar/{id}")
	public String eliminar(@PathVariable("id") Integer id) {
		Producto prod = new Producto();
		prod = productoService.findById(id).get();
		if(!prod.getImagen().equals("DefaultPlato.jpeg")) {
			upload.deleteImage(prod.getImagen());
		}
		productoService.desactivate(id);
		return "redirect:/administrador/productos/listar/activos";
	}
	
}
