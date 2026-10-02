package org.afdt.vacaciones;

import org.afdt.vacaciones.model.EstadoPeticion;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class DatosGlobales {

    @ModelAttribute
    public void cargarDatos(Model modelo) {
        modelo.addAttribute("estados", EstadoPeticion.values());
    }
}
