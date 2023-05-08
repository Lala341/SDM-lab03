package org.example;

import org.example.ontology.ABOX;
import org.example.ontology.TBOX;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        TBOX.createTBOX();
        ABOX.createABOX();
    }
}
