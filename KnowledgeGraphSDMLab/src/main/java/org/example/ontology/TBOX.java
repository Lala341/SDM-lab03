package org.example.ontology;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;



public class TBOX {
    public static final String BASE_URI = "http://www.bdma.upc/#";
    public static final String TBOX_MODEL_PATH = "data/publications.owl";
    public static final String ABOX_MODEL_PATH = "data/publications_data.nt";
    public static final String DATA_FILE_PATH = "data/raw/instances_data.csv";

    // https://jena.apache.org/documentation/javadoc/jena/org/apache/jena/ontology/OntModel.html
    // https://jena.apache.org/documentation/javadoc/jena/org/apache/jena/ontology/OntModelSpec.html
    // https://jena.apache.org/documentation/javadoc/jena/org/apache/jena/rdf/model/ModelFactory.html#createOntologyModel(org.apache.jena.ontology.OntModelSpec)

    OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF);
    //==================================
    // Ontology for Persons
    //==================================

    OntClass persona = model.createClass(BASE_URI.concat("Person") );
    OntClass author = model.createClass(BASE_URI.concat("Author") );
    OntClass chair = model.createClass( BASE_URI.concat("Chair") );
    OntClass editor = model.createClass(BASE_URI.concat("Editor") );
    OntClass reviewer = model.createClass( BASE_URI.concat("Reviewer") );

    persona.addSubClass( author );
    persona.addSubClass( chair );
        persona.addSubClass( editor );
        persona.addSubClass( reviewer );

    //==================================
    // Ontology for Papers
    //==================================

    OntClass paper = model.createClass( constants.BASE_URI.concat("Paper") );
    OntClass fullPaper = model.createClass( constants.BASE_URI.concat("Full_Paper") );
    OntClass shortPaper = model.createClass( constants.BASE_URI.concat("Short_Paper") );
    OntClass demoPaper = model.createClass( constants.BASE_URI.concat("Demo_Paper") );
    OntClass posterPaper = model.createClass( constants.BASE_URI.concat("Poster_Paper") );

        paper.addSubClass( fullPaper );
        paper.addSubClass( shortPaper );
        paper.addSubClass( demoPaper );
        paper.addSubClass( posterPaper ); // Only for conference papers



}
