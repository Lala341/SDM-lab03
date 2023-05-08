package org.example.ontology;

import java.io.FileOutputStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import org.example.common.constants;
import org.example.common.utils;

public class ABOX {
    
    public static void createAndSaveABOX() {
        
        try {

            //===============================================
            // Reading & building Ontology model from TBOX
            //===============================================

            utils.log("Reading TBOX from '" + constants.TBOX_MODEL_PATH + "'");
            
            Model m = ModelFactory.createDefaultModel().read(constants.TBOX_MODEL_PATH);
            OntModel model = ModelFactory.createOntologyModel( OntModelSpec.RDFS_MEM_RDFS_INF, m );
            
            utils.log("Ontology Model built from TBOX");
            
            //===============================================
            // Getting all OntClasses (concepts) from TBOX
            //===============================================

            utils.log("Getting all Ontology Classes (concepts) from TBOX");
            
            OntClass person = model.getOntClass( constants.BASE_URI.concat("Person") );
            OntClass author = model.getOntClass( constants.BASE_URI.concat("Author") );
            OntClass chair = model.getOntClass( constants.BASE_URI.concat("Chair"));
            OntClass editor = model.getOntClass( constants.BASE_URI.concat("Editor"));
            OntClass reviewer = model.getOntClass( constants.BASE_URI.concat("Reviewer"));
            OntClass paper = model.getOntClass( constants.BASE_URI.concat("Paper"));
            OntClass fullPaper = model.getOntClass( constants.BASE_URI.concat("Full_Paper") );
            OntClass shortPaper = model.getOntClass( constants.BASE_URI.concat("Short_Paper") );
            OntClass demoPaper = model.getOntClass( constants.BASE_URI.concat("Demo_Paper") );
            OntClass posterPaper = model.getOntClass( constants.BASE_URI.concat("Poster_Paper") );
            OntClass year = model.getOntClass( constants.BASE_URI.concat("Year"));
            OntClass venue = model.getOntClass( constants.BASE_URI.concat("Venue") );
            OntClass conference = model.getOntClass( constants.BASE_URI.concat("Conference") );
            OntClass journal = model.getOntClass( constants.BASE_URI.concat("Journal") );
            OntClass workshop = model.getOntClass( constants.BASE_URI.concat("Workshop") );
            OntClass symposium = model.getOntClass( constants.BASE_URI.concat("Symposium") );
            OntClass expertGroup = model.getOntClass( constants.BASE_URI.concat("Expert_Group") );
            OntClass regularConference = model.getOntClass( constants.BASE_URI.concat("Regular_Conference") );
            OntClass decision = model.getOntClass( constants.BASE_URI.concat("Decision") );
            OntClass acceptOrRejected = model.getOntClass( constants.BASE_URI.concat("Accepted_Or_Rejected") );
            OntClass reviewText = model.getOntClass( constants.BASE_URI.concat("Review_Text") );
            OntClass areas = model.getOntClass( constants.BASE_URI.concat("Areas") );
            OntClass publications = model.getOntClass( constants.BASE_URI.concat("Publications") );
            
            //===============================================
            // Getting all Ontology Properties from TBOX
            //===============================================
            
            utils.log("Getting all Ontology Properties from TBOX");

            OntProperty submit = model.getOntProperty( constants.BASE_URI.concat("submit") );
            OntProperty submittedToVenue = model.getOntProperty( constants.BASE_URI.concat("submitted_to") );
            OntProperty handlesConferences = model.getOntProperty( constants.BASE_URI.concat("handles_conference") );
            OntProperty handlesJournals = model.getOntProperty( constants.BASE_URI.concat("handles_journal") );
            OntProperty assignedByChairs = model.getOntProperty( constants.BASE_URI.concat("assigned_by_chairs") );
            OntProperty assignedByEditors = model.getOntProperty( constants.BASE_URI.concat("assigned_by_editors") );
            OntProperty assignedTo = model.getOntProperty( constants.BASE_URI.concat("assigned_to") );
            OntProperty takesDecision = model.getOntProperty( constants.BASE_URI.concat("takes_decision") );
            OntProperty reviewIsGiven = model.getOntProperty( constants.BASE_URI.concat("is_paper_accepted") );
            OntProperty hasReviewComments = model.getOntProperty( constants.BASE_URI.concat("has_review_comments") );
            OntProperty hasArea = model.getOntProperty( constants.BASE_URI.concat("has_area") );
            OntProperty paperHasPublication = model.getOntProperty( constants.BASE_URI.concat("has_publication") );
            OntProperty paperYear = model.getOntProperty( constants.BASE_URI.concat("published_year") );                
            
            //===============================================
            // Read & Parse the csv file 
            //===============================================
            
            utils.line_separator();
            utils.log("Parsing instances data from '" + constants.DATA_FILE_PATH + "'");
            
            BufferedReader csvReader = new BufferedReader(new FileReader(constants.DATA_FILE_PATH));
            CSVParser parser = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(csvReader);
            Integer rowCount = 0;

            for(CSVRecord record : parser) {
                rowCount += 1;
                
                //===============================================
                // Parse each record (by column name) 
                //===============================================
                
                String author_name = utils.str_clean( record.get("Author") );
                String paper_title = utils.str_clean( record.get("Paper") );
                String paper_type = utils.str_clean( record.get("Paper_Type") );
                String conference_type = utils.str_clean( record.get("Conference_Type") );
                String paper_year = utils.str_clean( record.get("Year") );
                String venue_title = utils.str_clean( record.get("Source") );
                String publication_title = utils.str_clean( record.get("Publication") );
                String document_type = utils.str_clean( record.get("Document_Type") );
                String reviewer_1 = utils.str_clean( record.get("Reviewer_1") );
                String reviewer_2 = utils.str_clean( record.get("Reviewer_2") );
                String handler = utils.str_clean( record.get("Handler") );
                String all_areas = utils.str_clean( record.get("Areas") );
                String reviewer_decision = utils.str_clean( record.get("Reviewer_Decision") );
                String reviewer_text = utils.str_clean( record.get("Reviewer_Text") );
                
                //===============================================
                // Create individuals for each concept (OntClass) 
                //===============================================

                Boolean isConference = document_type.equals("Conference");
                Boolean isAccepted = reviewer_decision.equals("Accepted");

                // Add year for paper
                Individual _year = year.createIndividual( constants.BASE_URI.concat( paper_year ) );

                // Add paper
                OntClass __paper;
                if(paper_type.equals("Full_Paper")) {
                    __paper = fullPaper;                    
                }
                else if(paper_type.equals("Short_Paper")) {
                    __paper = shortPaper;
                }
                else if(paper_type.equals("Demo_Paper")) {
                    __paper = demoPaper;
                }
                else {
                    __paper = posterPaper;
                }
                
                Individual _paper = __paper.createIndividual( constants.BASE_URI.concat( paper_title ) );
                _paper.addProperty( paperYear, _year );
                
                // If paper is accepted, then paper is published in publications (Conference Proceeding/Journal Volumes)
                if (isAccepted) {
                    Individual _publications = publications.createIndividual( constants.BASE_URI.concat( publication_title ) );
                    _paper.addProperty( paperHasPublication, _publications );
                }

                // Add author
                Individual _author = author.createIndividual( constants.BASE_URI.concat( author_name ) );
                _author.addProperty( submit, _paper );
                
                // Decision (common decision)
                Individual _acceptOrRejected = acceptOrRejected.createIndividual( constants.BASE_URI.concat( reviewer_decision ) );
                Individual _reviewText = reviewText.createIndividual( constants.BASE_URI.concat( reviewer_text ) );

                // Add decision (kinda blank node)
                Individual _decision = decision.createIndividual( );
                _decision
                    .addProperty( reviewIsGiven, _acceptOrRejected )
                    .addProperty( hasReviewComments, _reviewText );
                
                // Add both reviewers (assuming that we only store the final decision by both reviewers)
                Individual _reviewer_1 = reviewer.createIndividual( constants.BASE_URI.concat( reviewer_1 ) );
                _reviewer_1
                    .addProperty( assignedTo, _paper )
                    .addProperty( takesDecision, _decision );

                Individual _reviewer_2 = reviewer.createIndividual( constants.BASE_URI.concat( reviewer_2 ) );
                _reviewer_2
                    .addProperty( assignedTo, _paper )
                    .addProperty( takesDecision, _decision );

                Individual __venue;
                // If paper is submitted in a conference
                if(isConference) {

                    // Add conference
                    OntClass __conference;
                    if(conference_type.equals("Workshop")) {
                        __conference = workshop;
                    }
                    else if(conference_type.equals("Symposium")) {
                        __conference = symposium;
                    }
                    else if(conference_type.equals("Expert_Group")) {
                        __conference = expertGroup;
                    }
                    else {
                        __conference = regularConference;
                    }
                    Individual _conference = __conference.createIndividual( constants.BASE_URI.concat( venue_title ) );
                    __venue = _conference;
                    
                    // Add chairs
                    Individual _chair = chair.createIndividual( constants.BASE_URI.concat( handler ) );
                    _chair
                        .addProperty( handlesConferences, _conference )
                        .addProperty( assignedByChairs, _reviewer_1 )
                        .addProperty( assignedByChairs, _reviewer_2 );
                    
                }
                else {
                    // If paper is submitted in a journal
                    
                    // Add journal
                    Individual _journal = journal.createIndividual( constants.BASE_URI.concat( venue_title ) );
                    __venue = _journal;

                    // Add editors
                    Individual _editor = editor.createIndividual( constants.BASE_URI.concat( handler ) );
                    _editor
                        .addProperty( handlesJournals, _journal )
                        .addProperty( assignedByEditors, _reviewer_1 )
                        .addProperty( assignedByEditors, _reviewer_2 );

                }

                // Add property for venue & paper
                _paper.addProperty( submittedToVenue, __venue );

                // Add area if not added before
                if (all_areas.contains(";")) {
                    
                    String[] __areas = all_areas.split(";");
                    
                    for(String area: __areas) {

                        // If the area doesn't exist, create it
                        if (model.getOntClass( constants.BASE_URI.concat(area) ) == null) {
                            
                            OntClass __area = model.createClass( constants.BASE_URI.concat( area ) );
                            areas.addSubClass( __area );

                            __venue.addProperty( hasArea, __area.createIndividual() );
                            
                        }
                    }
                }
            }

            utils.line_separator();
            utils.log("Writing " + rowCount + " records to ABOX to file '" + constants.ABOX_MODEL_PATH + "'");
            
            FileOutputStream writerStream = new FileOutputStream( constants.ABOX_MODEL_PATH );
            model.write(writerStream, "N-TRIPLE");
            writerStream.close();

            utils.log("Done with ABOX creation!");
            utils.line_separator();

        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
