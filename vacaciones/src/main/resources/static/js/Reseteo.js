document.querySelectorAll(".btnReseteo").forEach(function(boton) {

    boton.addEventListener("click", function() {

        document.getElementById("idUsuario").value = this.dataset.id;

        document.getElementById("reseteo").showModal();

    });

});

let ojo = document.getElementById("imagen");
let contrasenha = document.getElementById("txtclave");

ojo.addEventListener("click", () => {
		if (contrasenha.type == "password") {
				ojo.setAttribute("src", "/imagenes/ojo_abierto.png");
				contrasenha.type = "text";
			} else {
				ojo.setAttribute("src", "/imagenes/ojo_cerrado.png");
				contrasenha.type = "password";
			}	
	})

function cerrarDialogo() {
	document.getElementById("reseteo").close();	
}

