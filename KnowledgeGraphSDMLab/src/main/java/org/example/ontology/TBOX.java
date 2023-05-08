package org.example.ontology;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class TBOX {
    public static final String BASE = "http://www.sdm.lab#";
    public static final String TBOX_DATA = "data/tbox.owl";

    public static void createTBOX() throws IOException {

        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF);
       
        OntClass person = model.createClass(BASE.concat("Person") );
        OntClass author = model.createClass(BASE.concat("Author") );
        OntClass chair = model.createClass( BASE.concat("Chair") );
        OntClass editor = model.createClass(BASE.concat("Editor") );
        OntClass reviewer = model.createClass( BASE.concat("Reviewer") );

        person.addSubClass(author);
        person.addSubClass(chair);
        person.addSubClass(editor);
        person.addSubClass(reviewer);
        OntClass area = model.createClass(BASE.concat("Area") );
        OntClass conference = model.createClass(BASE.concat("Conference") );
        OntClass journal = model.createClass(BASE.concat("Journal") );
        OntClass publication = model.createClass(BASE.concat("Publication") );
        OntClass decision = model.createClass(BASE.concat("Decision") );
        OntClass review = model.createClass(BASE.concat("Review") );
        OntClass reviewText = model.createClass(BASE.concat("ReviewText") );

        OntClass paper = model.createClass( BASE.concat("Paper") );
        OntClass fullPaper = model.createClass( BASE.concat("Full_Paper") );
        OntClass shortPaper = model.createClass( BASE.concat("Short_Paper") );
        OntClass demoPaper = model.createClass( BASE.concat("Demo_Paper") );
        OntClass posterPaper = model.createClass( BASE.concat("Poster_Paper") );

        paper.addSubClass(fullPaper);
        paper.addSubClass(shortPaper);
        paper.addSubClass(demoPaper);
        paper.addSubClass(posterPaper);

        OntProperty hasArea = model.createOntProperty(BASE.concat("hasarea"));
        hasArea.addDomain(paper);
        hasArea.addRange(area);
        hasArea.addLabel("hasArea", "en");

        OntProperty hasAuthor = model.createOntProperty(BASE.concat("hasauthor"));
        hasAuthor.addDomain(paper);
        hasAuthor.addRange(author);
        hasAuthor.addLabel("hasAuthor", "en");

        OntProperty hasChair = model.createOntProperty(BASE.concat("haschair"));
        hasChair.addDomain(conference);
        hasChair.addRange(chair);
        hasChair.addLabel("hasChair", "en");

        OntProperty hasDecision = model.createOntProperty(BASE.concat("hasdecision"));
        hasDecision.addDomain(review);
        hasDecision.addRange(decision);
        hasDecision.addLabel("hasDecision", "en");

        OntProperty hasEditor = model.createOntProperty(BASE.concat("haseditor"));
        hasEditor.addDomain(journal);
        hasEditor.addRange(editor);
        hasEditor.addLabel("hasEditor", "en");

        OntProperty hasPaper = model.createOntProperty(BASE.concat("haspaper"));
        hasPaper.addDomain(publication);
        hasPaper.addRange(paper);
        hasPaper.addLabel("hasPaper", "en");

        OntProperty hasReview = model.createOntProperty(BASE.concat("hasreview"));
        hasReview.addDomain(paper);
        hasReview.addRange(review);
        hasReview.addLabel("hasReview", "en");

        OntProperty hasReviewer = model.createOntProperty(BASE.concat("hasreviewer"));
        hasReviewer.addDomain(review);
        hasReviewer.addRange(reviewer);
        hasReviewer.addLabel("hasReviewer", "en");

        OntProperty hasReviewText = model.createOntProperty("http://www.sdm.lab#hasreviewtext");
        hasReviewText.addDomain(review);
        hasReviewText.addRange(reviewText);
        hasReviewText.addLabel("hasReviewText", "en");

        FileOutputStream writerStream = new FileOutputStream(TBOX_DATA);
        model.write(writerStream, "RDF/XML");
        writerStream.close();
    }
}
