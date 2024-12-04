// Modal para las imágenes

// Asegurar que el DOM esté completamente cargado antes de ejecutar el código
document.addEventListener('DOMContentLoaded', () => {
    let imagen = document.getElementsByClassName("imagen-tabla");
    for(let k = 0; k < imagen.length; k++) {
        imagen[k].addEventListener('click', () => openImageModal(imagen[k].src));
    }
});

// Función para abrir el modal
function openImageModal(src) {
    const modal = document.getElementById('myModal'); // El div que contiene la imagen y el botón de cierre
    const modalImage = document.getElementById('modalImage'); // La imagen dentro del modal
    modal.style.display = 'block';
    modalImage.src = src;
}

// Función para cerrar el modal
function closeModal() { 
    const modal = document.getElementById('myModal'); // El div que contiene la imagen y el botón de cierre
    modal.style.display = 'none';
}
