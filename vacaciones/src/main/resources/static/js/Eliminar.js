
document.getElementById("canc").addEventListener("click", () => {
        document.getElementById("Eliminar").close();
    });

function eliminarPeticion(id, estado) {
	document.getElementById("idPet").value = id;
	document.getElementById("estado").value = estado;	
	document.getElementById("Eliminar").showModal();
}