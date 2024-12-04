Swal.fire({
    title: "Realizando transacción",
    html: `
        <div style="display: flex; flex-direction: column; align-items: center;">
            <img src="/Multimedia/Video/Pagando.gif" alt="Imagen de tarjeta" width="120" height="120" style="margin-right: 10px;" />
            <img src="/Multimedia/Video/Pagando02_cargando.gif" alt="Imagen de tarjeta" width="30%" height="auto" />
        </div>
        <p>Por favor, no recargue la página</p>
    `,
    showConfirmButton: false,
    showCancelButton: true,
    cancelButtonColor: "#d33",
    cancelButtonText: "Cancelar",
    timer: 7000,
    allowOutsideClick: false,
}).then((result) => {
	if (result.dismiss === Swal.DismissReason.timer) {
        Swal.fire({
            icon: "success",
            title: "Pago exitoso",
            html: `
            <br><a href="/inicio" id="button1" style="margin: 20px; font-size: 1.2rem; padding: 15px 25px; background-color: #3085d6; color: white; border: none; border-radius: 5px; cursor: pointer;">
                    Ir al inicio
                </a>
                <a href="/inicio/mostrarComprobante/{idComprobante}(idComprobante=${comprobante.idComprobante})" id="button2" style="margin: 20px; font-size: 1.2rem; padding: 15px 25px; background-color: #3ab65c; color: white; border: none; border-radius: 5px; cursor: pointer;">
                    Ver comprobante
                </a><br><br>`,
            text: "Gracias por elegirnos!!",
            showConfirmButton: false,
            showCancelButton: false,
            allowOutsideClick: false
        });
    }
});

