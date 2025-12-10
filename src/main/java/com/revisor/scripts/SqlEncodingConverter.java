package com.revisor.scripts;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SqlEncodingConverter {

    private static final byte[] UTF8_BOM = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Uso: java -jar sql-converter-jar-with-dependencies.jar <ruta_directorio>");
            System.err.println("Ejemplo: java -jar sql-converter-jar-with-dependencies.jar C:\\path\\to\\scripts");
            System.exit(1);
        }

        String directoryPath = args[0];
        Path rootPath = Paths.get(directoryPath);

        if (!Files.exists(rootPath)) {
            System.err.println("Error: El directorio no existe: " + directoryPath);
            System.exit(1);
        }

        if (!Files.isDirectory(rootPath)) {
            System.err.println("Error: La ruta no es un directorio: " + directoryPath);
            System.exit(1);
        }

        try {
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║  SQL Encoding Converter - Procesando ficheros SQL      ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("Directorio: " + rootPath.toAbsolutePath());
            System.out.println();

            procesarDirectorio(rootPath);

            System.out.println();
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║  Proceso completado exitosamente                       ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");

        } catch (IOException e) {
            System.err.println("Error al procesar los ficheros: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void procesarDirectorio(Path directorio) throws IOException {
        List<Path> ficherosSql = buscarFicherosSql(directorio);

        if (ficherosSql.isEmpty()) {
            System.out.println("No se encontraron ficheros .sql en el directorio especificado.");
            return;
        }

        System.out.println("Se encontraron " + ficherosSql.size() + " fichero(s) SQL:");
        System.out.println();

        for (Path fichero : ficherosSql) {
            procesarFichero(fichero);
        }
    }

    private static List<Path> buscarFicherosSql(Path directorio) throws IOException {
        List<Path> ficheros = new ArrayList<>();
        Files.walk(directorio)
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().toLowerCase().endsWith(".sql"))
                .forEach(ficheros::add);
        return ficheros;
    }

    private static void procesarFichero(Path rutaFichero) throws IOException {
        System.out.println("─ " + rutaFichero.toAbsolutePath());

        // Obtener encoding actual
        String encodingActual = detectarEncoding(rutaFichero);
        System.out.println("  Encoding actual: " + encodingActual);

        // Leer contenido actual
        byte[] contenidoActual = Files.readAllBytes(rutaFichero);
        String contenido = new String(contenidoActual, obtenerCharset(encodingActual));

        // Normalizar saltos de línea a LF (Linux)
        String contenidoNormalizado = normalizarSaltosLinea(contenido);
        boolean cambioEncodingNecesario = !encodingActual.equals("UTF-8 BOM");
        boolean cambioSaltosNecesario = !contenido.equals(contenidoNormalizado);

        // Preparar contenido final con BOM UTF-8
        byte[] bom = UTF8_BOM;
        byte[] contenidoBytes = contenidoNormalizado.getBytes(StandardCharsets.UTF_8);
        byte[] contenidoFinal = new byte[bom.length + contenidoBytes.length];
        System.arraycopy(bom, 0, contenidoFinal, 0, bom.length);
        System.arraycopy(contenidoBytes, 0, contenidoFinal, bom.length, contenidoBytes.length);

        // Escribir fichero
        Files.write(rutaFichero, contenidoFinal);

        // Mostrar cambios realizados
        System.out.println("  Cambios realizados:");
        if (cambioEncodingNecesario) {
            System.out.println("    ✓ Encoding cambiado de " + encodingActual + " a UTF-8 con BOM");
        }
        if (cambioSaltosNecesario) {
            System.out.println("    ✓ Saltos de línea normalizados a LF (Linux)");
        }
        if (!cambioEncodingNecesario && !cambioSaltosNecesario) {
            System.out.println("    ✓ Fichero ya tiene la configuración correcta (sin cambios)");
        }
        System.out.println("  Tamaño: " + contenidoFinal.length + " bytes");
        System.out.println();
    }

    private static String detectarEncoding(Path fichero) throws IOException {
        byte[] contenidoInicial = Files.readAllBytes(fichero);

        // Detectar BOM
        if (contenidoInicial.length >= 3 &&
                contenidoInicial[0] == (byte) 0xEF &&
                contenidoInicial[1] == (byte) 0xBB &&
                contenidoInicial[2] == (byte) 0xBF) {
            return "UTF-8 BOM";
        }

        // Detectar UTF-8 sin BOM
        if (esUTF8(contenidoInicial)) {
            return "UTF-8 (sin BOM)";
        }

        // Por defecto, asumir encoding del sistema (Windows-1252)
        return "Windows-1252 (u otro)";
    }

    private static boolean esUTF8(byte[] contenido) {
        int i = 0;
        while (i < contenido.length) {
            byte b = contenido[i];

            if ((b & 0x80) == 0) {
                i += 1;
            } else if ((b & 0xE0) == 0xC0) {
                if (i + 1 >= contenido.length) return false;
                i += 2;
            } else if ((b & 0xF0) == 0xE0) {
                if (i + 2 >= contenido.length) return false;
                i += 3;
            } else if ((b & 0xF8) == 0xF0) {
                if (i + 3 >= contenido.length) return false;
                i += 4;
            } else {
                return false;
            }
        }
        return true;
    }

    private static String normalizarSaltosLinea(String contenido) {
        // Reemplazar todos los tipos de saltos de línea por LF
        return contenido
                .replace("\r\n", "\n")  // Windows CRLF a LF
                .replace("\r", "\n");   // Mac antiguo CR a LF
    }

    private static java.nio.charset.Charset obtenerCharset(String encoding) {
        switch (encoding) {
            case "UTF-8 BOM":
                return StandardCharsets.UTF_8;
            case "UTF-8 (sin BOM)":
                return StandardCharsets.UTF_8;
            default:
                return java.nio.charset.Charset.forName("Windows-1252");
        }
    }
}
