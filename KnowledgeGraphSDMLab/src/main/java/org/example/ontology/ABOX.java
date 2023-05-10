package org.example.ontology;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;

import java.io.*;
import java.net.URLEncoder;
import java.util.Iterator;

public class ABOX {

    public static final String TBOX_DATA = "data/tbox.owl";
    public static final String ABOX_DATA = "data/abox.nt";
    public static final String CSV_DATA_ARTICLE = "data/csv/merged_file.csv";

    public static final String BASE = "http://www.sdm.lab#";

    public static void createABOX() throws IOException {

        Model model = ModelFactory.createDefaultModel().read(TBOX_DATA);
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF, model);

        OntClass paper = ontModel.getOntClass(BASE.concat("Paper"));
        OntClass fullPaper = ontModel.getOntClass(BASE.concat("FullPaper"));
        OntClass shortPaper = ontModel.getOntClass(BASE.concat("ShortPaper"));
        OntClass demoPaper = ontModel.getOntClass(BASE.concat("DemoPaper"));
        OntClass poster = ontModel.getOntClass(BASE.concat("Poster"));

        OntClass person = ontModel.getOntClass(BASE.concat("Person"));
        OntClass author = ontModel.getOntClass(BASE.concat("Author"));
        OntClass reviewer = ontModel.getOntClass(BASE.concat("Reviewer"));
        OntClass editor = ontModel.getOntClass(BASE.concat("Editor"));
        OntClass chair = ontModel.getOntClass(BASE.concat("Chair"));

        OntClass venue = ontModel.getOntClass(BASE.concat("Venue"));
        OntClass journal = ontModel.getOntClass(BASE.concat("Journal"));
        OntClass conference = ontModel.getOntClass(BASE.concat("Conference"));
        OntClass workshop = ontModel.getOntClass(BASE.concat("Workshop"));
        OntClass symposium = ontModel.getOntClass(BASE.concat("Symposium"));
        OntClass expertGroup = ontModel.getOntClass(BASE.concat("ExpertGroup"));
        OntClass regularConference = ontModel.getOntClass(BASE.concat("RegularConference"));

        OntClass review = ontModel.getOntClass(BASE.concat("Review"));
        OntClass publication = ontModel.getOntClass(BASE.concat("Publication"));
        OntClass year = ontModel.getOntClass(BASE.concat("Year"));

        OntProperty hasAuthor = ontModel.getOntProperty(BASE.concat("hasauthor"));
        OntProperty hasReviewer = ontModel.getOntProperty(BASE.concat("hasreviewer"));
        OntProperty hasReview = ontModel.getOntProperty(BASE.concat("hasreview"));
        OntProperty hasEditor = ontModel.getOntProperty(BASE.concat("haseditor"));
        OntProperty hasChair = ontModel.getOntProperty(BASE.concat("haschair"));
        OntProperty hasPaper = ontModel.getOntProperty(BASE.concat("haspaper"));
        OntProperty hasVenue = ontModel.getOntProperty(BASE.concat("hasvenue"));
        OntProperty hasArea = ontModel.getOntProperty(BASE.concat("hasarea"));
        OntProperty hasResearchArea = ontModel.getOntProperty(BASE.concat("hasresearcharea"));
        OntProperty hasYear = ontModel.getOntProperty(BASE.concat("hasyear"));

        BufferedReader csvReader = new BufferedReader(new FileReader(CSV_DATA_ARTICLE));
        CSVParser csvParser = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(csvReader);

        for(CSVRecord csvRecord : csvParser) {

            //creating papers
            String paperID = csvRecord.get("DOI");
            String paperName = csvRecord.get("title_x");
            String paperAbstract = csvRecord.get("abstract");
            String paperType = csvRecord.get("edition_venue");

            OntClass paperClass;

            if(paperType.equals("FullPaper")) {
                paperClass = fullPaper;
            }
            else if(paperType.equals("ShortPaper")) {
                paperClass = shortPaper;
            }
            else if(paperType.equals("DemoPaper")) {
                paperClass = demoPaper;
            }
            else {
                paperClass = poster;
            }

            Individual individualPaper = paperClass.createIndividual(BASE.concat(paperID));

            //creating authors
            String authorName = csvRecord.get("author_x");
            String[] authorList = authorName.split("\\|", -1);

            for (int i = 0; i < authorList.length; i++) {
                Individual individualAuthor = author.createIndividual(BASE.concat(URLEncoder.encode(authorList[i])));
                individualPaper.addProperty(hasAuthor, individualAuthor);
            }

            //creating reviews
            String reviewID = csvRecord.get("reviewID");
            Individual individualReview = review.createIndividual(BASE.concat(reviewID));
            individualPaper.addProperty(hasReview, individualReview);

            //creating reviewers
            String reviewerName = csvRecord.get("reviewer_name");
            Individual individualReviewer = reviewer.createIndividual(BASE.concat(URLEncoder.encode(reviewerName)));
            individualReview.addProperty(hasReviewer, individualReviewer);

            //creating journals/venues
            String venueType = csvRecord.get("venue");

            if(venueType.equals("J")) {
                //creating journals
                String journalName = csvRecord.get("journal");
                Individual individualJournal = journal.createIndividual(BASE.concat(URLEncoder.encode(journalName)));

                //creating editors
                String editorName = csvRecord.get("editor");
                Individual individualEditor = editor.createIndividual(BASE.concat(URLEncoder.encode(editorName)));
                individualJournal.addProperty(hasEditor, individualEditor);

            }
            else if (venueType.equals("W")) {
                //creating workshops
                //String workshopName = csvRecord.get("");
                //Individual individualWorkshop = workshop.createIndividual(BASE.concat(URLEncoder.encode(workshopName)));

                //creating chairs
                //String chairName = csvRecord.get("chair");
                //Individual individualChair = chair.createIndividual(BASE.concat(URLEncoder.encode(chairName)));
                //individualWorkshop.addProperty(hasChair, individualChair);
            }
            else if (venueType.equals("C")) {

            }
            else {

            }
        }

        FileOutputStream writerStream = new FileOutputStream(ABOX_DATA);
        model.write(writerStream, "N-TRIPLE");
        writerStream.close();
    }
}
