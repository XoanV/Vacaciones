let boton = document.getElementById("act");
let btnInicio = document.getElementById("inicio");
let centro = document.getElementById("cent");
let selector = document.getElementById("Centro");

let editando = false;
let inputs = [];

boton.addEventListener("click", function() {

    if (!editando) {

        btnInicio.style.visibility = "hidden";

        let spans = document.querySelectorAll(".texto");

        inputs = [];

        spans.forEach(function(span) {

            const input = document.createElement("input");

            input.type = "text";
            input.value = span.textContent.trim();
            input.className = "texto";
			input.name = "contra";

            inputs.push(input);

            span.replaceWith(input);
        });

        selector.value = centro.textContent.trim();

        selector.style.textAlign = "center";
        selector.style.visibility = "visible";

        centro.replaceWith(selector);

        boton.value = "Guardar";

        editando = true;

    } else {

        inputs.forEach(function(input) {

            const span = document.createElement("span");

            span.className = "texto";
            span.textContent = input.value;

            input.replaceWith(span);
        });

        const nuevoCentro = document.createElement("span");

        nuevoCentro.id = "cent";
        nuevoCentro.textContent = selector.value;

        selector.style.visibility = "hidden";
        selector.replaceWith(nuevoCentro);

        centro = nuevoCentro;

        boton.value = "Actualizar datos";

        btnInicio.style.visibility = "visible";

        editando = false;
    }
});