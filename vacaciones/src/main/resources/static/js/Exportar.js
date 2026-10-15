const Exportar = document.getElementById("btnExportar");
//const ficheros = document.getElementById("archivos");
//
//Exportar.addEventListener("click", function(e) {
//    e.stopPropagation();
//    ficheros.classList.toggle("visible");
//});
//
//ficheros.addEventListener("click", function(e) {
//    e.stopPropagation();
//});
//
//document.addEventListener("click", function() {
//    ficheros.classList.remove("visible");
//});

function exportarLista(formato) {

    const lista = document.getElementById("lista");

    const formulario = document.createElement("form");
    formulario.method = "POST";
    formulario.action = "/exportar";
    const filas = Array.from(lista.querySelectorAll("tr"));

    const contenidoTabla = filas.map(fila =>
        Array.from(fila.querySelectorAll("th, td"))
            .map(celda => celda.innerText.trim())
            .join("\t")
    ).join("\n");
    const campos = {
        contenido: contenidoTabla,
        titulo: document.title,
        formato: formato.toLowerCase()
    };

    for (const [nombre, valor] of Object.entries(campos)) {
        const input = document.createElement("input");
        input.type = "hidden";
        input.name = nombre;
        input.value = valor;
        formulario.appendChild(input);
    }

    const csrf = document.querySelector('meta[name="_csrf"]');

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