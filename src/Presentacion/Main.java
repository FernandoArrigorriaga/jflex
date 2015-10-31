/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import analizadorlexico.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.runtime.Symbol;
import parser.parser;

/**
 *
 * @author Spartan
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {

        String DirectorioTrabajo = System.getProperty("user.dir");
        String[] dir = new String[5];
        for (int i = 0; i < 5; i++) {
            dir[i] = DirectorioTrabajo + "/src/analizadorlexico/Kprogram" + (i + 1) + ".ks";
        }
        Reader reader = new BufferedReader(new FileReader(dir[0]));
        Scanner lexer = new Scanner(reader);
        while (lexer.next_token().sym != 0) {
            System.out.println(lexer.yytext());
            System.out.println(lexer.lexeme);
            
        }
        parser par = new parser(lexer);
        par.parse();
    }
}
