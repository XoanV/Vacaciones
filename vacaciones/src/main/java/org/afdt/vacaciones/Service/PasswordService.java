package org.afdt.vacaciones.Service;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    public String generarContraseña() {

        String mayusculas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String minusculas = "abcdefghijklmnopqrstuvwxyz";
        String numeros = "0123456789";
        String especiales = "!@#$%&*+-_=.?";

        String todos = mayusculas + minusculas + numeros + especiales;

        SecureRandom random = new SecureRandom();

        StringBuilder contraseña = new StringBuilder();

        contraseña.append(
            mayusculas.charAt(random.nextInt(mayusculas.length()))
        );

        contraseña.append(
            minusculas.charAt(random.nextInt(minusculas.length()))
        );

        contraseña.append(
            numeros.charAt(random.nextInt(numeros.length()))
        );

        contraseña.append(
            especiales.charAt(random.nextInt(especiales.length()))
        );

        while (contraseña.length() < 12) {
            contraseña.append(
                todos.charAt(random.nextInt(todos.length()))
            );
        }

        char[] caracteres = contraseña.toString().toCharArray();

        for (int i = caracteres.length - 1; i > 0; i--) {

            int posicion = random.nextInt(i + 1);

            char temporal = caracteres[i];
            caracteres[i] = caracteres[posicion];
            caracteres[posicion] = temporal;
        }

        return new String(caracteres);
    }
}