package com.proyectofinal17.datos;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proyectofinal17.modelo.Contrasena;
import com.proyectofinal17.seguridad.EncriptadorAES;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * GestorArchivos: guarda y carga lista de contraseñas en un archivo cifrado.
 * - Serializa a JSON con Gson.
 * - Cifra el JSON completo con EncriptadorAES y lo escribe en archivo.
 */
public class GestorArchivos {

    private final File archivo;
    private final Gson gson = new Gson();

    public GestorArchivos(String ruta) {
        this.archivo = new File(ruta);
    }

    public void guardar(List<Contrasena> lista, EncriptadorAES cifrador) throws Exception {
        String json = gson.toJson(lista);
        String cifrado = cifrador.cifrar(json);
        try (FileWriter fw = new FileWriter(archivo, false)) {
            fw.write(cifrado);
        }
    }

    public List<Contrasena> cargar(EncriptadorAES cifrador) throws Exception {
        if (!archivo.exists()) return new ArrayList<>();
        byte[] data = Files.readAllBytes(archivo.toPath());
        String cifrado = new String(data, "UTF-8");
        String json = cifrador.descifrar(cifrado);
        Type t = new TypeToken<List<Contrasena>>(){}.getType();
        List<Contrasena> lista = gson.fromJson(json, t);
        if (lista == null) return new ArrayList<>();
        return lista;
    }
}
