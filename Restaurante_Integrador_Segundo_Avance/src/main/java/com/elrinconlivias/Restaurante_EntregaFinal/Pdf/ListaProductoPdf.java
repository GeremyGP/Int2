package com.elrinconlivias.Restaurante_EntregaFinal.Pdf;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.elrinconlivias.Restaurante_EntregaFinal.Modelo.Producto; 
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("Administrador/Productos/productoPdf") 
public class ListaProductoPdf extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
          HttpServletRequest request, HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        List<Producto> productos = (List<Producto>) model.get("productos"); 
        
        document.setPageSize(PageSize.LETTER.rotate()); 
        document.setMargins(20,20,10,10);
        document.open();      
        
        Image logo = Image.getInstance("Imagenes/Logo-negro.png"); // la ruta 
        logo.scaleToFit(100, 50); // Escala la imagen para ajustar su tamaño
        logo.setAlignment(Image.ALIGN_CENTER); 
        document.add(logo); // Añade el logo al documento
        
        
        PdfPTable tablaTitulo = new PdfPTable(1);
        PdfPCell celda = null;
        
        Font fuenteTitulo = FontFactory.getFont("Helvetica",16,Color.BLACK);
        
        celda = new PdfPCell(new Phrase("Listado de productos", fuenteTitulo));
        celda.setBorder(0);
        celda.setBackgroundColor(new Color(250,71,18));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(30);
        
        tablaTitulo.addCell(celda);
        tablaTitulo.setSpacingAfter(30);
        
        
        PdfPTable tablaProductos = new PdfPTable(6); 

        productos.forEach(producto -> {
            tablaProductos.addCell(producto.getIdProducto().toString()); 
            tablaProductos.addCell(producto.getNombre());
            tablaProductos.addCell(producto.getDescripcion()); 
            tablaProductos.addCell(producto.getPrecio().toString());
            tablaProductos.addCell(producto.getCategoria().getNombre());  
            tablaProductos.addCell(String.valueOf(producto.getCalificacion()));

        });

        document.add(tablaTitulo);
        document.add(tablaProductos);
    }
}
