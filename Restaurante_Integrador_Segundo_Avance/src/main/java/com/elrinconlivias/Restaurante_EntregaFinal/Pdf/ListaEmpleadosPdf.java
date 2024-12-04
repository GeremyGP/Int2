package com.elrinconlivias.Restaurante_EntregaFinal.Pdf;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;


import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Empleado;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component("Administrador/Empleados/empleadoPdf")
public class ListaEmpleadosPdf extends AbstractPdfView  {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		@SuppressWarnings("unchecked")
		List<Empleado> empleados = (List<Empleado>) model.get("empleado");
		
		document.setPageSize(PageSize.LETTER.rotate()); 
		document.setMargins(20,20,10,10);
	    document.open();    
		   
	    Image logo = Image.getInstance("Imagenes/Logo-negro.png");  
	    logo.scaleToFit(100, 50); 
	    logo.setAlignment(Image.ALIGN_CENTER); 
	    document.add(logo); 
	        
	    PdfPTable tablatitulo = new PdfPTable(1);
		PdfPCell celda = null;
		
		Font fuentetitulo = FontFactory.getFont("Helvetica",16,Color.BLACK);
	    
		celda = new PdfPCell(new Phrase("Listado de empleados", fuentetitulo));
        celda.setBorder(0);
        celda.setBackgroundColor(new Color(250,71,18));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(30);
        
        tablatitulo.addCell(celda);
        tablatitulo.setSpacingAfter(30);
        
        
		PdfPTable tablaEmpleado = new PdfPTable(8);
		   
		empleados.forEach(empleado -> {
		   
			   tablaEmpleado.addCell(empleado.getIdEmpleado().toString());
			   tablaEmpleado.addCell(empleado.getNombre());
			   tablaEmpleado.addCell(empleado.getApellido());
			   tablaEmpleado.addCell(empleado.getEmail());
			   tablaEmpleado.addCell(empleado.getTelefono());
			   tablaEmpleado.addCell(empleado.getSalario().toString());
			   tablaEmpleado.addCell(empleado.getClave());
			   tablaEmpleado.addCell(empleado.getFechaContratacion().toString());
			   
		   });
		   document.add(tablaEmpleado);
		   document.add(tablatitulo);
	}

	
}