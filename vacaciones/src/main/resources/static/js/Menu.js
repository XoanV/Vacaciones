const perfil = document.getElementById("perfil");
const menu = document.getElementById("menuPerfil");

const enlace = document.getElementById("buscar");
const pet = document.getElementById("peticiones");

perfil.addEventListener("click", function(e) {
    e.stopPropagation();
    menu.classList.toggle("mostrar");
});

menu.addEventListener("click", function(e) {
    e.stopPropagation();
});

enlace.addEventListener("click", function(e) {
    e.stopPropagation();
    pet.classList.toggle("visible");
});

pet.addEventListener("click", function(e) {
    e.stopPropagation();
});

document.addEventListener("click", function() {
    menu.classList.remove("mostrar");
    pet.classList.remove("visible");
});