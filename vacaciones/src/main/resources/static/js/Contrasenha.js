let imagen = document.getElementsByClassName("imagenojo");

let contrasenha = document.getElementsByClassName("visible");

for (let img of imagen) {
	img.addEventListener("click", () => {
		for (let con of contrasenha) {
			if (con.type == "password") {
				img.setAttribute("src", "public/imagenes/ojo_abierto.png");
				con.type = "text";
			} else {
				img.setAttribute("src", "public/imagenes/ojo_cerrado.png");
				con.type = "password";
			}
		}		
	})
}