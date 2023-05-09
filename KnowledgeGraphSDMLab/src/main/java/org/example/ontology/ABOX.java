package org.example.ontology;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.*;
import java.util.Iterator;

public class ABOX {

    public static final String TBOX_DATA = "data/tbox.owl";
    public static final String ABOX_DATA = "data/abox.nt";
    public static final String CSV_DATA_ARTICLE = "data/csv/articles.csv";

    public static final String CSV_DATA_REVIEW = "data/csv/reviews.csv";

    public static final String BASE = "http://www.sdm.lab#";

    public static void createABOX() throws IOException {

        Model model = ModelFactory.createDefaultModel().read(TBOX_DATA);
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF, model);

        OntClass paper = ontModel.getOntClass(BASE.concat("Paper"));
        OntClass fullPaper = ontModel.getOntClass(BASE.concat("FullPaper"));
        OntClass shortPaper = ontModel.createClass(BASE.concat("ShortPaper"));
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

            //creating a paper

            String paperID = csvRecord.get("DOI");
            String paperName = csvRecord.get("title");
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

            String authorName = csvRecord.get("author");
            String[] authorList = authorName.split("\\|", -1);

            for (int i = 0; i < authorList.length; i++) {
                Individual individualAuthor = author.createIndividual(BASE.concat(authorList[i]));
                individualPaper.addProperty(hasAuthor, individualAuthor);
            }
        }

        BufferedReader csvReviewsReader = new BufferedReader(new FileReader(CSV_DATA_REVIEW));
        CSVParser csvReviewParser = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(csvReviewsReader);

        for(CSVRecord csvRecord : csvReviewParser) {

            String reviewerName = csvRecord.get("reviewer_name");
            Individual individualReviewer = reviewer.createIndividual(BASE.concat(reviewerName));
        }


        FileOutputStream writerStream = new FileOutputStream(ABOX_DATA);
        model.write(writerStream, "N-TRIPLE");
        writerStream.close();
    }
}
