package GeneradorLexers;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

/**
 * La presente clase tiene como objetivo ingresar un documento de COCOL/R y analizarlo
 * @author Eric Mendoza
 * @version 3.0
 * @since 23/07/2017
 */
public class Main {
    public static void main(String[] args) throws IOException {
        // Ingreso del nombre del archivo con la descripcion lexica
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del archivo con la especificación léxica en Cocol (sin .txt): ");
        //String archivoEspecificacionLexica = scanner.next();
        String archivoEspecificacionLexica = "CocolR";

        CocolRReader cocolRReader = new CocolRReader();

        // Verificar que el archivo este bien estructurado
        System.out.println("Cargando...");
        if (cocolRReader.analizeCocolRSyntax(archivoEspecificacionLexica + ".txt")){
            if (cocolRReader.generateLexer()){
                cocolRReader.generateLexerJavaFile();
                System.out.println("Se generó el lexer. \nSe generó grmática.");
            } else {
                System.err.println("Error en la especificación léxica.\nNo se generó el lexer");
            }

        } else {
            System.err.println("Error en la especificacion lexica. \nNo se generó el lexer.");
        }

        // Entrega final
        Grammar grammar = cocolRReader.getGrammar();

        // Serializar gramativa y automata
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("grammar.ser"));
        out.writeObject(grammar);
        out.flush();
        out.close();
    }

}
