
document.getElementById("cerrar").addEventListener("click", () => {
        document.getElementById("Actualizarcom").close();
    });

function modificarComentario(id, estado) {
	document.getElementById("idPet").value = id;
	document.getElementById("estado").value = estado;
	document.getElementById("Actualizarcom").showModal();
}