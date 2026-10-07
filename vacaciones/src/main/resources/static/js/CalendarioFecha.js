class calendar {

    constructor(nombre, idDiv, idInput, idioma, mostrar, peticiones, url, fechaInicio, fechaFin) {

        this.nombre = nombre;
        this.idDiv = idDiv;
        this.idInput = idInput;
        this.idioma = idioma;
        this.mostrar = mostrar;
        this.peticiones = peticiones || [];
        this.url = url;

        this.fechaInicio = fechaInicio
            ? this.convertirFecha(fechaInicio)
            : null;

        this.fechaFin = fechaFin
            ? this.convertirFecha(fechaFin)
            : null;

        this.creado = true;

        this.fecha = new Date();

        this.mes = this.fecha.getMonth();
        this.anho = this.fecha.getFullYear();

        if (this.fechaInicio && this.fechaFin) {

            this.mes = this.fechaInicio.getMonth();
            this.anho = this.fechaInicio.getFullYear();

        }

    }

    convertirFecha(fecha) {

        if (Array.isArray(fecha)) {

            return new Date(
                fecha[0],
                fecha[1] - 1,
                fecha[2]
            );

        }

        if (typeof fecha === "string") {

            const partes = fecha.substring(0, 10).split("-");

            return new Date(
                parseInt(partes[0]),
                parseInt(partes[1]) - 1,
                parseInt(partes[2])
            );

        }

        return new Date(fecha);

    }

    actualizaFlechas() {

        const anterior = document.getElementById(
            "flechaAnterior"
        );

        const siguiente = document.getElementById(
            "flechaSiguiente"
        );

        if (!anterior || !siguiente) {

            return;

        }

        if (!this.fechaInicio || !this.fechaFin) {

            anterior.style.display = "none";
            siguiente.style.display = "none";

            return;

        }

        const actual =
            this.anho * 12 +
            this.mes;

        const minimo =
            this.fechaInicio.getFullYear() * 12 +
            this.fechaInicio.getMonth();

        const maximo =
            this.fechaFin.getFullYear() * 12 +
            this.fechaFin.getMonth();

        anterior.style.display =
            actual > minimo
                ? "block"
                : "none";

        siguiente.style.display =
            actual < maximo
                ? "block"
                : "none";

    }

    construyeCalendario() {

        const div = document.getElementById(
            this.idDiv
        );

        if (!div) {

            this.creado = false;

            return;

        }

        div.innerHTML = "";

        const tabla = document.createElement("table");

        const cabecera = document.createElement("tr");

        const dias = [
            "Lunes",
            "Martes",
            "Miércoles",
            "Jueves",
            "Viernes",
            "Sábado",
            "Domingo"
        ];

        dias.forEach(dia => {

            const th = document.createElement("th");

            th.textContent = dia;

            cabecera.appendChild(th);

        });

        tabla.appendChild(cabecera);

        const primerDia = new Date(
            this.anho,
            this.mes,
            1
        );

        let diaSemana = primerDia.getDay();

        if (diaSemana === 0) {

            diaSemana = 7;

        }

        const diasMes = new Date(
            this.anho,
            this.mes + 1,
            0
        ).getDate();

        let fila = document.createElement("tr");

        for (let i = 1; i < diaSemana; i++) {

            fila.appendChild(
                document.createElement("td")
            );

        }

        for (let dia = 1; dia <= diasMes; dia++) {

            const celda = document.createElement("td");

            celda.textContent = dia;

            const peticionesDia =
                this.estaEnPeticion(dia);

            peticionesDia.forEach(peticion => {

                const enlace =
                    document.createElement("a");

                enlace.textContent =
                    peticion.usuario.nombre +
                    " - " +
                    peticion.estado;

                enlace.href =
                    this.url +
                    "?id=" +
                    peticion.idPeticion;

                enlace.classList.add(
                    "peticion-calendario"
                );

                enlace.style.display = "block";

                enlace.style.textDecoration = "none";

                enlace.style.color = "black";

                celda.appendChild(enlace);

            });

            celda.onclick = () => {

                this.seleccionarFecha(dia);

            };

            fila.appendChild(celda);

            if (
                (dia + diaSemana - 1) % 7 === 0
            ) {

                tabla.appendChild(fila);

                fila = document.createElement("tr");

            }

        }

        if (fila.children.length > 0) {

            tabla.appendChild(fila);

        }

        div.appendChild(tabla);

        this.actualizaTitulo();

        this.actualizaFlechas();

    }

    estaEnPeticion(dia) {

        const fecha = new Date(
            this.anho,
            this.mes,
            dia
        );

        fecha.setHours(0, 0, 0, 0);

        return this.peticiones.filter(peticion => {

            const inicio =
                this.convertirFecha(
                    peticion.fechaInicio
                );

            const fin =
                this.convertirFecha(
                    peticion.fechaFin
                );

            inicio.setHours(0, 0, 0, 0);

            fin.setHours(0, 0, 0, 0);

            return fecha >= inicio && fecha <= fin;

        });

    }

    mueveCalendar(valor) {

        let nuevoMes = this.mes + valor;

        let nuevoAnho = this.anho;

        if (nuevoMes > 11) {

            nuevoMes = 0;

            nuevoAnho++;

        }

        if (nuevoMes < 0) {

            nuevoMes = 11;

            nuevoAnho--;

        }

        const nuevoValor =
            nuevoAnho * 12 +
            nuevoMes;

        const minimo =
            this.fechaInicio.getFullYear() * 12 +
            this.fechaInicio.getMonth();

        const maximo =
            this.fechaFin.getFullYear() * 12 +
            this.fechaFin.getMonth();

        if (
            nuevoValor < minimo ||
            nuevoValor > maximo
        ) {

            return;

        }

        this.mes = nuevoMes;

        this.anho = nuevoAnho;

        this.construyeCalendario();

    }

    actualizaTitulo() {

        const titulo =
            document.getElementById(
                this.idInput
            );

        if (!titulo) {

            return;

        }

        const meses = [
            "Enero",
            "Febrero",
            "Marzo",
            "Abril",
            "Mayo",
            "Junio",
            "Julio",
            "Agosto",
            "Septiembre",
            "Octubre",
            "Noviembre",
            "Diciembre"
        ];

        titulo.textContent =
            meses[this.mes] +
            " " +
            this.anho;

    }

    ntraFecha(elemento) {

        elemento.select();

    }

    mueveMesAnyo(elemento) {

        const texto =
            elemento.value.trim();

        const partes =
            texto.split(" ");

        const meses = [
            "Enero",
            "Febrero",
            "Marzo",
            "Abril",
            "Mayo",
            "Junio",
            "Julio",
            "Agosto",
            "Septiembre",
            "Octubre",
            "Noviembre",
            "Diciembre"
        ];

        let nuevoMes;

        let nuevoAnho;

        if (partes.length === 1) {

            nuevoMes =
                meses.indexOf(partes[0]);

            nuevoAnho =
                this.anho;

        } else if (partes.length === 2) {

            nuevoMes =
                meses.indexOf(partes[0]);

            nuevoAnho =
                parseInt(partes[1]);

        } else {

            this.actualizaTitulo();

            return;

        }

        if (
            nuevoMes < 0 ||
            isNaN(nuevoAnho)
        ) {

            this.actualizaTitulo();

            return;

        }

        const nuevoValor =
            nuevoAnho * 12 +
            nuevoMes;

        const minimo =
            this.fechaInicio.getFullYear() * 12 +
            this.fechaInicio.getMonth();

        const maximo =
            this.fechaFin.getFullYear() * 12 +
            this.fechaFin.getMonth();

        if (
            nuevoValor < minimo ||
            nuevoValor > maximo
        ) {

            this.actualizaTitulo();

            return;

        }

        this.mes = nuevoMes;

        this.anho = nuevoAnho;

        this.construyeCalendario();

    }

    seleccionarFecha(dia) {

        const fecha = new Date(
            this.anho,
            this.mes,
            dia
        );

        const input =
            document.getElementById(
                "inputfecha"
            );

        if (input) {

            input.value =
                this.formatearFecha(fecha);

        }

    }

}