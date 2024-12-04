package com.elrinconlivias.Restaurante_EntregaFinal.Pdf;

import java.awt.Color;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Cliente;
//import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.DetallePedido;
//import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Pedido;
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

@Component("Administrador/Clientes/clientePdf")
public class ListaClientesPdf extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		@SuppressWarnings("unchecked")
		List<Cliente> clientes = (List<Cliente>) model.get("clientes");
		
	    document.setPageSize(PageSize.LETTER.rotate()); 
	    document.setMargins(20,20,10,10);
        document.open();     
                
        
        Image logo = Image.getInstance("Imagenes/Logo-negro.png");  
        logo.scaleToFit(100, 50); 
        logo.setAlignment(Image.ALIGN_CENTER); 
        document.add(logo); 
        
        
        
        PdfPTable tablaTitular = new PdfPTable(1);
		PdfPCell celda = null;
		
		Font fuenteTitular = FontFactory.getFont("Helvetica",16,Color.BLACK);
		
		
        celda = new PdfPCell(new Phrase("Listado de clientes", fuenteTitular));
        celda.setBorder(0);
        celda.setBackgroundColor(new Color(250,71,18));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(30);
        
        tablaTitular.addCell(celda);
        tablaTitular.setSpacingAfter(30);
        
        
		PdfPTable tablaCliente = new PdfPTable(7);
		
	      clientes.forEach(cliente -> {
			tablaCliente.addCell(cliente.getIdCliente().toString());
			tablaCliente.addCell(cliente.getNombre());
			tablaCliente.addCell(cliente.getApellido());
			tablaCliente.addCell(cliente.getEmail());
			tablaCliente.addCell(cliente.getTelefono());
			tablaCliente.addCell(cliente.getDireccion());
			tablaCliente.addCell(cliente.getFechaCreacion().toString());
			
			
			// List<Pedido> pedidos = cliente.getPedidos();
			 
			// Itera sobre cada pedido 
			  //  for (Pedido pedido : pedidos) {
			    //    tablaCliente.addCell(pedido.getTipo()); // Accediendo a tipo de Pedido

			      //  List<DetallePedido> detalles = pedido.getDetalles();
			        
			        // Itera sobre cada detalle
			        //for (DetallePedido detalle : detalles) {
			          //  tablaCliente.addCell(detalle.getNombreProducto()); // Accediendo a nombreProducto
			        //}
			    //}
			
			
	});
	
	      document.add(tablaCliente);
	      document.add(tablaTitular);
	}

}
