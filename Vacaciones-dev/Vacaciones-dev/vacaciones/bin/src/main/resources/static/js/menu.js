const perfil = document.getElementById("perfil");
const menu = document.getElementById("menuPerfil");

perfil.addEventListener("click", function(e) {
    e.stopPropagation();
    menu.classList.toggle("mostrar");
});

menu.addEventListener("click", function(e) {
    e.stopPropagation();
});

document.addEventListener("click", function() {
    menu.classList.remove("mostrar");
});
setTimeout(() => {
    document.getElementById("mensaje").style.display = "none";
}, 3000)