let imagen = document.getElementsByClassName("imagenojo");

let contrasenha = document.getElementsByClassName("visible");

let contrasenharepe = document.getElementsByClassName("visible1");

for (let img of imagen) {
	img.addEventListener("click", () => {
		for (let con of contrasenha) {
			if (con.type == "password") {
				img.setAttribute("src", "/imagenes/ojo_abierto.png");
				con.type = "text";
			} else {
				img.setAttribute("src", "/imagenes/ojo_cerrado.png");
				con.type = "password";
			}
		}		
	})
	
	for (let img of imagen) {
		img.addEventListener("click", () => {
			for (let con of contrasenharepe) {
				if (con.type == "password") {
					img.setAttribute("src", "/imagenes/ojo_abierto.png");
					con.type = "text";
				} else {
					img.setAttribute("src", "/imagenes/ojo_cerrado.png");
					con.type = "password";
				}
			}		
		})
	}
}