let ojo = document.getElementById("imagen");

let conActual = document.getElementById("txtclave");

let ojo1 = document.getElementById("imagen1");

let contrasenha = document.getElementById("txtclave1");

let ojo2 = document.getElementById("imagen2");

let contrasenharepe = document.getElementById("txtclave2");

ojo.addEventListener("click", () => {
    if (conActual.type == "password") {
        ojo.setAttribute("src", "/imagenes/ojo_abierto.png");
        conActual.type = "text";
    } else {
        ojo.setAttribute("src", "/imagenes/ojo_cerrado.png");
        conActual.type = "password";
    }
})

ojo1.addEventListener("click", () => {
    if (contrasenha.type == "password") {
        ojo1.setAttribute("src", "/imagenes/ojo_abierto.png");
        contrasenha.type = "text";
    } else {
        ojo1.setAttribute("src", "/imagenes/ojo_cerrado.png");
        contrasenha.type = "password";
    }
})

ojo2.addEventListener("click", () => {
    if (contrasenharepe.type == "password") {
        ojo2.setAttribute("src", "/imagenes/ojo_abierto.png");
        contrasenharepe.type = "text";
    } else {
        ojo2.setAttribute("src", "/imagenes/ojo_cerrado.png");
        contrasenharepe.type = "password";
    }
})