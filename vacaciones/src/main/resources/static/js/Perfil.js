let boton = document.getElementById("act");

let mostrar = document.getElementById("Actualizardatos");

let btnCancelar = document.getElementById("canc");

boton.addEventListener("click", () => {
	mostrar.showModal();
})

btnCancelar.addEventListener("click", () => {	
	mostrar.close();
})