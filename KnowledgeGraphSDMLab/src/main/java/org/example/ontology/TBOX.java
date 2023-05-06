package org.example.ontology;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;



public class TBOX {
    public static final String BASE = "http://www.sdm.upc.edu/#";

    public static void createTBOX(){

        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF);
       
        OntClass person = model.createClass(BASE.concat("Person") );
        OntClass author = model.createClass(BASE.concat("Author") );
        OntClass chair = model.createClass( BASE.concat("Chair") );
        OntClass editor = model.createClass(BASE.concat("Editor") );
        OntClass reviewer = model.createClass( BASE.concat("Reviewer") );

        person.addSubClass( author );
        person.addSubClass( chair );
        person.addSubClass( editor );
        person.addSubClass( reviewer );
        
        OntClass paper = model.createClass( BASE.concat("Paper") );
        OntClass fullPaper = model.createClass( BASE.concat("Full_Paper") );
        OntClass shortPaper = model.createClass( BASE.concat("Short_Paper") );
        OntClass demoPaper = model.createClass( BASE.concat("Demo_Paper") );
        OntClass posterPaper = model.createClass( BASE.concat("Poster_Paper") );

        paper.addSubClass( fullPaper );
        paper.addSubClass( shortPaper );
        paper.addSubClass( demoPaper );
        paper.addSubClass( posterPaper );


        
        


    }


}
