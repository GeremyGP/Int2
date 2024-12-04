/*
function toggleSections() {
    const tipoComprobante = document.getElementById('selector-tipo-comprobante').value;
    const boletaSection = document.getElementById('datos-boleta');
    const facturaSection = document.getElementById('datos-factura');

    // Ocultar ambas secciones por defecto
    boletaSection.style.display = 'none';
    document.getElementById("dni").value = "";
    facturaSection.style.display = 'none';
    document.getElementById("ruc").value = "";
    document.getElementById("razon").value = "";

    // Mostrar la sección correspondiente
    if (tipoComprobante === 'Boleta') {
        boletaSection.style.display = 'flex';
    } else if (tipoComprobante === 'Factura') {
        facturaSection.style.display = 'flex';
    }
}*/
function toggleSections() {
    const tipoComprobante = document.getElementById("selector-tipo-comprobante").value;
    const contenedorDatos = document.getElementById("boleta-factura");

    // Limpia el contenedor antes de agregar nuevos campos
    contenedorDatos.innerHTML = '';

    if (tipoComprobante === "Boleta") {
        // Crear campos para Boleta
        const dniLabel = document.createElement("label");
        dniLabel.setAttribute("for", "dni");
        dniLabel.innerHTML = '<i class="ti ti-id"></i>Número de documento de identidad (DNI)';
        const dniInput = document.createElement("input");
        dniInput.setAttribute("id", "dni");
        dniInput.setAttribute("type", "number");
        dniInput.setAttribute("placeholder", "12345678");
        dniInput.setAttribute("maxlength", "8");
        dniInput.setAttribute("pattern", "\\d{8}");
        dniInput.setAttribute("required", "");

        // Agregar campos al contenedor
        contenedorDatos.appendChild(dniLabel);
        contenedorDatos.appendChild(dniInput);
    } else if (tipoComprobante === "Factura") {
        // Crear campos para Factura
        const rucLabel = document.createElement("label");
        rucLabel.setAttribute("for", "r");
        rucLabel.innerHTML = '<i class="ti ti-id-badge-2"></i>RUC';
        const rucInput = document.createElement("input");
        rucInput.setAttribute("id", "r");
        rucInput.setAttribute("name", "ru");
        rucInput.setAttribute("type", "text");
        rucInput.setAttribute("placeholder", "12345678");
        rucInput.setAttribute("maxlength", "11");
        rucInput.setAttribute("pattern", "\\d{11}");
        rucInput.setAttribute("required", "");

        const razonLabel = document.createElement("label");
        razonLabel.setAttribute("for", "razon");
        razonLabel.innerHTML = '<i class="ti ti-id"></i>Nombre o Razón Social';
        const razonInput = document.createElement("input");
        razonInput.setAttribute("id", "razon");
        razonInput.setAttribute("name", "ras");
        razonInput.setAttribute("type", "text");
        razonInput.setAttribute("placeholder", "Nombre o Razón Social");
        razonInput.setAttribute("required", "");

        // Agregar campos al contenedor
        contenedorDatos.appendChild(rucLabel);
        contenedorDatos.appendChild(rucInput);
        contenedorDatos.appendChild(razonLabel);
        contenedorDatos.appendChild(razonInput);
    }
}

// Inicializa la visibilidad de las secciones
toggleSections();

function identificarTarjeta() {
    const numeroTarjeta = document.getElementById("numeroTarjeta").value;
    const tarjetaIcono = document.getElementById("tarjetaIcono");

    // Cambia la imagen según el tipo de tarjeta detectado
    if (/^4/.test(numeroTarjeta)) {
      tarjetaIcono.src = "/Multimedia/Img/Logos/Visa.png"; // Cambia a la imagen de Visa
      tarjetaIcono.alt = "Visa";
    } else if (/^5[1-5]/.test(numeroTarjeta)) {
      tarjetaIcono.src = "/Multimedia/Img/Logos/Mastercard.png"; // Cambia a la imagen de Mastercard
      tarjetaIcono.alt = "Mastercard";
    } else if (/^3[47]/.test(numeroTarjeta)) {
      tarjetaIcono.src = "/Multimedia/Img/Logos/AmericanExpress.png"; // Cambia a la imagen de American Express
      tarjetaIcono.alt = "American Express";
    } else {
      tarjetaIcono.src = "/Multimedia/Img/Logos/Card_default.png"; // Imagen predeterminada
      tarjetaIcono.alt = "Tipo de tarjeta";
    }
  }

// Llama a la función al cargar la página para configurar el estado inicial
window.onload = toggleSections;