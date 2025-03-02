package co.com.nexos_software.util;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NameGenerator {

    private static final List<String> NOMBRES = List.of(
        "Juan", "María", "Carlos", "Ana", "Luis", "Laura", "Pedro", "Sofía", 
        "José", "Elena", "Miguel", "Isabel", "Jorge", "Carmen", "Ricardo", "Lucía"
    );

    private static final List<String> APELLIDOS = List.of(
        "Gómez", "Rodríguez", "Fernández", "López", "Martínez", "Pérez", 
        "González", "Sánchez", "Romero", "Torres", "Díaz", "Vargas", 
        "Jiménez", "Ruiz", "Hernández", "Ortega"
    );

    public static String generarNombreTitular() {
        int indiceNombre = ThreadLocalRandom.current().nextInt(NOMBRES.size());
        int indiceApellido = ThreadLocalRandom.current().nextInt(APELLIDOS.size());
        return NOMBRES.get(indiceNombre) + " " + APELLIDOS.get(indiceApellido);
    }
}