let ojo = document.getElementById("imagen");

let conActual = document.getElementById("txtclave");

ojo.addEventListener("click", () => {
    if (conActual.type == "password") {
        ojo.setAttribute("src", "/imagenes/ojo_abierto.png");
        conActual.type = "text";
    } else {
        ojo.setAttribute("src", "/imagenes/ojo_cerrado.png");
        conActual.type = "password";
    }
})