const Exportar = document.getElementById("btnExportar");
const ficheros = document.getElementById("archivos");

Exportar.addEventListener("click", function(e) {
    e.stopPropagation();
    ficheros.classList.toggle("visible");
});

ficheros.addEventListener("click", function(e) {
    e.stopPropagation();
});

document.addEventListener("click", function() {
    ficheros.classList.remove("visible");
});

function exportarLista(formato) {
	
		    const lista = document.getElementById("lista");
		    const titulo = document.title;
	
		    const formulario = document.createElement("form");
	
		    formulario.method = "POST";
		    formulario.action = "/exportar";
	
		    const contenido = document.createElement("input");
		    contenido.type = "hidden";
		    contenido.name = "contenido";
		    contenido.value = lista.innerText;
	
		    const tituloInput = document.createElement("input");
		    tituloInput.type = "hidden";
		    tituloInput.name = "titulo";
		    tituloInput.value = titulo;
	
		    const formatoInput = document.createElement("input");
		    formatoInput.type = "hidden";
		    formatoInput.name = "formato";
		    formatoInput.value = formato;
	
		    formulario.appendChild(contenido);
		    formulario.appendChild(tituloInput);
		    formulario.appendChild(formatoInput);
		    
		    const csrf = document.querySelector(
		            'meta[name="_csrf"]'
		        );
		    if (csrf) {

		        const csrfInput = document.createElement("input");

		        csrfInput.type = "hidden";
		        csrfInput.name = "_csrf";
		        csrfInput.value = csrf.content;

		        formulario.appendChild(csrfInput);
		    }
	
		    document.body.appendChild(formulario);
		    formulario.submit();
		}