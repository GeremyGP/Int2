
if(document.getElementById("lista-productos-carrito")!=null){
	
	document.addEventListener('DOMContentLoaded', function() {
    // Tu código aquí se ejecutará cuando la página haya cargado
    iniciar();
  });

function iniciar(){
    restaurarCarrito();
}
	
	const listaCarrito = document.getElementById("lista-productos-carrito");
	listaCarrito.getElementsByClassName("item-producto-carrito");
	const productosExistentes = listaCarrito.getElementsByClassName("item-producto-carrito");
	
	function restaurarCarrito(){
	    const vacio = listaCarrito.querySelector("#carrito-vacio");
	    const totalCarrito = document.querySelector("#total-productos-carrito");
	    if(productosExistentes.length<=0){
	        listaCarrito.style.justifyContent = "center";
	        vacio.style.display = "block";
	        totalCarrito.style.display = "none";
	    }else{
	        listaCarrito.style.justifyContent = "flex-start";
	        vacio.style.display = "none";
	        totalCarrito.style.display = "flex";
	    }
    }



//MOSTRAR CARRITO DE COMPRAS

const contenedorCarrito = document.getElementById("contenedor-productos-carrito");
    const carrito = document.getElementById("carrito-compras");
    const btncerrar = document.getElementById("btn-cerrar-carrito");
    const fondo = document.getElementById("fondo");

function mostrarCarrito(){
    contenedorCarrito.classList.toggle("show");
    fondo.classList.toggle("show");
    document.body.style.overflow = 'hidden';
}

    carrito.addEventListener("click",mostrarCarrito);
    btncerrar.addEventListener("click",mostrarCarrito);
    fondo.addEventListener("click",mostrarCarrito);
}




// Mecanismo de las tarjetas

let iconoInfo = document.querySelectorAll(".icono-info");
let iconoRegresar = document.querySelectorAll(".icono-regresar");

iconoInfo.forEach(icono => {
icono.addEventListener('click', (event) => abrirInfo(event));
});

iconoRegresar.forEach(icon =>{
icon.addEventListener('click', (event) => abrirCarta(event));
});

function abrirInfo(event){
// Obtenemos específicamente el elemento al que se le hizo "click"
let regresar = event.target;
// Obtenemos la parte frontal
let front = regresar.parentNode;
// Obtenemos la parte trasera
let back = front.nextElementSibling;

//Iteramos la clase show en ambos lados de la carta
front.classList.toggle("show");
back.classList.toggle("show");
}

function abrirCarta(event){
 // Obtenemos específicamente el elemento al que se le hizo "click"
 let regresar = event.target;
 // Obtenemos la parte trasera
 let back = regresar.parentNode;
 // Obtenemos la parte frontal
 let front = back.previousElementSibling;

 //Iteramos la clase show en ambos lados de la carta
 front.classList.toggle("show");
 back.classList.toggle("show");
}



    
    
    
    
    