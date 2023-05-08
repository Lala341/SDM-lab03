// Import necessary packages
package org.example.ontology;

import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;

import org.example.common.constants;
import org.example.common.utils;

public class TBOX {

    public static void createAndSaveTBOX() {

        // create ontology model
        OntModel model = ModelFactory.createOntologyModel( OntModelSpec.RDFS_MEM_RDFS_INF );


        // define classes

        //==================================
        // Ontology for Persons
        //==================================
        
        OntClass person = model.createClass( constants.BASE_URI.concat("Person") );
        OntClass author = model.createClass( constants.BASE_URI.concat("Author") );
        OntClass chair = model.createClass( constants.BASE_URI.concat("Chair") );
        OntClass editor = model.createClass( constants.BASE_URI.concat("Editor") );
        OntClass reviewer = model.createClass( constants.BASE_URI.concat("Reviewer") );
        
        person.addSubClass( author );
        person.addSubClass( chair );
        person.addSubClass( editor );
        person.addSubClass( reviewer );
        
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
        
        //==================================
        // Ontology for Paper's Year_Check
        //==================================
        
        OntClass year = model.createClass( constants.BASE_URI.concat("Year") );

        //==================================
        // Ontology for Venue
        //==================================
        
        OntClass venue = model.createClass( constants.BASE_URI.concat("Venue") );
        OntClass conference = model.createClass( constants.BASE_URI.concat("Conference") );
        OntClass journal = model.createClass( constants.BASE_URI.concat("Journal") );
        
        venue.addSubClass( conference );
        venue.addSubClass( journal );
        
        //==================================
        // Ontology for Conference Types
        //==================================

        OntClass workshop = model.createClass( constants.BASE_URI.concat("Workshop") );
        OntClass symposium = model.createClass( constants.BASE_URI.concat("Symposium") );
        OntClass expertGroup = model.createClass( constants.BASE_URI.concat("Expert_Group") );
        OntClass regularConference = model.createClass( constants.BASE_URI.concat("Regular_Conference") );
        
        conference.addSubClass( workshop );
        conference.addSubClass( symposium );
        conference.addSubClass( expertGroup );
        conference.addSubClass( regularConference );

        //==================================
        // Ontology for Review
        //==================================

        OntClass review = model.createClass( constants.BASE_URI.concat("Review") );
        OntClass reviewText = model.createClass( constants.BASE_URI.concat("Review_Text") );
        OntClass decision = model.createClass( constants.BASE_URI.concat("Decision") );

        reviewer.addSubClass( paper );
        paper.addSubClass( review );
        review.addSubClass( decision );
        review.addSubClass( reviewText );
        
        //==================================
        // Ontology for Decision
        //==================================

        OntClass accepted = model.createClass( constants.BASE_URI.concat("Accepted") );
        OntClass rejected = model.createClass( constants.BASE_URI.concat("Rejected") );

        decision.addSubClass( accepted );
        decision.addSubClass( rejected );

        //==================================
        // Ontology for Area
        //==================================

        // We will soft-codedly create the subclasses for area during the creation of ABOX
        // This allows consistency with the publication's data

        OntClass area = model.createClass( constants.BASE_URI.concat("Area") );

        //==================================
        // Ontology for Publications
        //==================================

        OntClass publications = model.createClass( constants.BASE_URI.concat("Publications") );



        // define object properties

        //==================================
        // Ontology Object Properties
        //==================================

        OntProperty hasAuthor = model.createOntProperty( constants.BASE_URI.concat("hasAuthor") );
        hasAuthor.addDomain( paper );
        hasAuthor.addRange( author );
        hasAuthor.addLabel("Author has paper", "en");

        OntProperty hasPaper = model.createOntProperty( constants.BASE_URI.concat("has_paper"));
        hasPaper.addDomain( publications );
        hasPaper.addRange( paper );
        hasPaper.addLabel( "Paper is published","en");

        OntProperty hasVenue = model.createOntProperty( constants.BASE_URI.concat("published_to") );
        hasVenue.addDomain( publications );
        hasVenue.addRange( venue );
        hasVenue.addLabel("Publications has venue", "en");
        
        OntProperty hasChair = model.createOntProperty( constants.BASE_URI.concat("handles_conference") );
        hasChair.addDomain( conference );
        hasChair.addRange( chair );
        hasChair.addLabel("Chair(s) handles a conference", "en");

        OntProperty hasEditor = model.createOntProperty( constants.BASE_URI.concat("handles_journal") );
        hasEditor.addDomain( journal );
        hasEditor.addRange( editor );
        hasEditor.addLabel("Editor(s) handles a journal", "en");

        OntProperty assignedByChairs = model.createOntProperty( constants.BASE_URI.concat("assigned_by_chairs") );
        assignedByChairs.addDomain( chair );
        assignedByChairs.addRange( reviewer );
        assignedByChairs.addLabel("Chairs assign reviewers", "en");

        OntProperty assignedByEditors = model.createOntProperty( constants.BASE_URI.concat("assigned_by_editors") );
        assignedByEditors.addDomain( editor );
        assignedByEditors.addRange( reviewer );
        assignedByEditors.addLabel("Editors assign reviewers", "en");

        OntProperty hasReviewer = model.createOntProperty( constants.BASE_URI.concat("Paper_is_reviewed_by") );
        hasReviewer.addDomain( paper );
        hasReviewer.addRange( reviewer );
        hasReviewer.addLabel("Reviewers are assigned to a paper", "en");

        OntProperty hasReview = model.createOntProperty( constants.BASE_URI.concat("paper_has_review"));
        hasReview.addDomain( paper );
        hasReview.addRange( review );
        hasReview.addLabel("Reviewer takes a decision","en");

        OntProperty hasDecision = model.createOntProperty( constants.BASE_URI.concat("has_decision"));
        hasDecision.addDomain( review );
        hasDecision.addRange( decision );
        hasDecision.addLabel("Review has a decision","en");

        OntProperty DecisionIsGiven = model.createOntProperty( constants.BASE_URI.concat("is_paper_accepted_or_rejected?"));
        DecisionIsGiven.addDomain( decision );
        DecisionIsGiven.addRange( accepted );
        DecisionIsGiven.addLabel( "Paper is accepted","en");
        DecisionIsGiven.addRange( rejected );
        DecisionIsGiven.addLabel( "Paper is rejected","en");

        OntProperty hasReviewText = model.createOntProperty( constants.BASE_URI.concat("has_review_comments"));
        hasReviewText.addDomain( decision );
        hasReviewText.addRange( reviewText );
        hasReviewText.addLabel( "Comments added by the Reviewer","en");

        OntProperty hasArea = model.createOntProperty( constants.BASE_URI.concat("has_area"));
        hasArea.addDomain( venue );
        hasArea.addRange( area );
        hasArea.addLabel( "Venue has area","en");

        OntProperty hasYear = model.createOntProperty( constants.BASE_URI.concat("published_paper_year"));
        hasYear.addDomain( paper );
        hasYear.addRange( year );
        hasYear.addLabel( "Paper published in year ","en");


        try {
            
            utils.line_separator();
            utils.log("Saving ontology model to '" + constants.TBOX_MODEL_PATH + "'");
            
            FileOutputStream writerStream = new FileOutputStream( constants.TBOX_MODEL_PATH );
            model.write(writerStream, "RDF/XML");
            writerStream.close();
            
            utils.log("Ontology model has been created!");
            utils.line_separator();

        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
}
