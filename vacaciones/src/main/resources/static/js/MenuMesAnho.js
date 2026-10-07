const enlacePeticiones = document.getElementById("peticionesMesRango");
const petAnho = document.getElementById("peticionesAño");

enlacePeticiones.addEventListener("click", function(e) {
    e.stopPropagation();
    petAnho.classList.toggle("visible");
});

petAnho.addEventListener("click", function(e) {
    e.stopPropagation();
});

document.addEventListener("click", function() {
    petAnho.classList.remove("visible");
});