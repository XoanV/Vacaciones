let ojo = document.getElementById("imagen");

let ojo1 = document.getElementById("imagen1");

let contrasenha = document.getElementById("txtclave");

let contrasenharepe = document.getElementById("txtclave1");

ojo.addEventListener("click", () => {
		if (contrasenha.type == "password") {
				ojo.setAttribute("src", "/imagenes/ojo_abierto.png");
				contrasenha.type = "text";
			} else {
				ojo.setAttribute("src", "/imagenes/ojo_cerrado.png");
				contrasenha.type = "password";
			}	
	})
	
ojo1.addEventListener("click", () => {
				if (contrasenharepe.type == "password") {
					ojo1.setAttribute("src", "/imagenes/ojo_abierto.png");
					contrasenharepe.type = "text";
				} else {
					ojo1.setAttribute("src", "/imagenes/ojo_cerrado.png");
					contrasenharepe.type = "password";
				}		
		})