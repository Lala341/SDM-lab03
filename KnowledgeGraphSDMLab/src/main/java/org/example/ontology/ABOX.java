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
    public static final String CSV_DATA_ARTICLE = "data/csv/instance_data.csv";

    public static final String BASE = "http://www.sdm.lab#";

    public static void createABOX() throws IOException {

        Model model = ModelFactory.createDefaultModel().read(TBOX_DATA);
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF, model);

        //creating the classes
        OntClass fullPaper = ontModel.getOntClass(BASE.concat("FullPaper"));
        OntClass shortPaper = ontModel.getOntClass(BASE.concat("ShortPaper"));
        OntClass demoPaper = ontModel.getOntClass(BASE.concat("DemoPaper"));
        OntClass poster = ontModel.getOntClass(BASE.concat("Poster"));
        OntClass author = ontModel.getOntClass(BASE.concat("Author"));
        OntClass reviewer = ontModel.getOntClass(BASE.concat("Reviewer"));
        OntClass editor = ontModel.getOntClass(BASE.concat("Editor"));
        OntClass chair = ontModel.getOntClass(BASE.concat("Chair"));
        OntClass journal = ontModel.getOntClass(BASE.concat("Journal"));
        OntClass workshop = ontModel.getOntClass(BASE.concat("Workshop"));
        OntClass symposium = ontModel.getOntClass(BASE.concat("Symposium"));
        OntClass expertGroup = ontModel.getOntClass(BASE.concat("ExpertGroup"));
        OntClass regularConference = ontModel.getOntClass(BASE.concat("RegularConference"));
        OntClass volume = ontModel.getOntClass(BASE.concat("Volume"));
        OntClass proceeding = ontModel.getOntClass(BASE.concat("Proceeding"));
        OntClass review = ontModel.getOntClass(BASE.concat("Review"));
        OntClass publication = ontModel.getOntClass(BASE.concat("Publication"));
        OntClass submission = ontModel.getOntClass(BASE.concat("Submission"));
        OntClass area = ontModel.getOntClass(BASE.concat("Area"));
        OntClass keyword = ontModel.getOntClass(BASE.concat("Keyword"));

        //creating the properties
        OntProperty hasAuthor = ontModel.getOntProperty(BASE.concat("hasauthor"));
        OntProperty hasReviewer = ontModel.getOntProperty(BASE.concat("hasreviewer"));
        OntProperty hasReview = ontModel.getOntProperty(BASE.concat("hasreview"));
        OntProperty hasEditor = ontModel.getOntProperty(BASE.concat("haseditor"));
        OntProperty hasChair = ontModel.getOntProperty(BASE.concat("haschair"));
        OntProperty hasKeyword = ontModel.getOntProperty(BASE.concat("haskeywords"));
        OntProperty hasArea = ontModel.getOntProperty(BASE.concat("hasarea"));
        OntProperty oversees = ontModel.getOntProperty(BASE.concat("oversees"));
        OntProperty supervises = ontModel.getOntProperty(BASE.concat("supervises"));
        OntProperty isSubmitted = ontModel.getOntProperty(BASE.concat("issubmitted"));
        OntProperty isPublished = ontModel.getOntProperty(BASE.concat("ispublished"));
        OntProperty definedBy = ontModel.getOntProperty(BASE.concat("definedby"));
        OntProperty submittedTo = ontModel.getOntProperty(BASE.concat("submittedto"));
        OntProperty belongsToVolume = ontModel.getOntProperty(BASE.concat("belongstovolume"));
        OntProperty belongsToProceeding = ontModel.getOntProperty(BASE.concat("belongstoproceeding"));
        OntProperty isPartOf = ontModel.getOntProperty(BASE.concat("ispartof"));
        OntProperty partOf = ontModel.getOntProperty(BASE.concat("partof"));

        //creating the data properties
        DatatypeProperty DOI = ontModel.getDatatypeProperty(BASE.concat("doi"));
        DatatypeProperty nameauthor = ontModel.getDatatypeProperty(BASE.concat("authorname"));
        DatatypeProperty authorID = ontModel.getDatatypeProperty(BASE.concat("authorid"));
        DatatypeProperty title = ontModel.getDatatypeProperty(BASE.concat("title"));
        DatatypeProperty yearPublished = ontModel.getDatatypeProperty(BASE.concat("yearpublished"));
        DatatypeProperty paperAbstract = ontModel.getDatatypeProperty(BASE.concat("abstract"));
        DatatypeProperty editorName = ontModel.getDatatypeProperty(BASE.concat("editorname"));
        DatatypeProperty chairName = ontModel.getDatatypeProperty(BASE.concat("chairname"));
        DatatypeProperty reviewerName = ontModel.getDatatypeProperty(BASE.concat("reviewername"));
        DatatypeProperty confName = ontModel.getDatatypeProperty(BASE.concat("conferencename"));
        DatatypeProperty journalName = ontModel.getDatatypeProperty(BASE.concat("journalname"));
        DatatypeProperty reviewText = ontModel.getDatatypeProperty(BASE.concat("reviewtext"));
        DatatypeProperty reviewDecision = ontModel.getDatatypeProperty(BASE.concat("decision"));
        DatatypeProperty finalDecision = ontModel.getDatatypeProperty(BASE.concat("finaldecision"));
        DatatypeProperty volumeNr = ontModel.getDatatypeProperty(BASE.concat("volumenr"));
        DatatypeProperty indexNr = ontModel.getDatatypeProperty(BASE.concat("indexnr"));
        DatatypeProperty areaName = ontModel.getDatatypeProperty(BASE.concat("areaname"));

        BufferedReader csvReader = new BufferedReader(new FileReader(CSV_DATA_ARTICLE));
        CSVParser csvParser = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(csvReader);

        for(CSVRecord csvRecord : csvParser) {

            //creating papers
            String paperTitle = csvRecord.get("Title");
            String paperType = csvRecord.get("PaperType");

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

            Individual individualPaper = paperClass.createIndividual(BASE.concat(URLEncoder.encode(paperTitle)));
            individualPaper.addLiteral(title, URLEncoder.encode(paperTitle));
            individualPaper.addLiteral(DOI, csvRecord.get("DOI"));
            individualPaper.addLiteral(paperAbstract, URLEncoder.encode(csvRecord.get("Abstract")));

            //creating authors
            String authorName = csvRecord.get("AuthorName");
            String[] authorList = authorName.split("\\|", -1);

            for (int i = 0; i < authorList.length; i++) {
                Individual individualAuthor = author.createIndividual(BASE.concat(URLEncoder.encode(authorList[i])));
                individualAuthor.addLiteral(authorID, csvRecord.get("Author-orcid"));
                individualAuthor.addLiteral(nameauthor,URLEncoder.encode(authorList[i]));
                individualPaper.addProperty(hasAuthor, individualAuthor);
            }

            //creating keywords
            String words = csvRecord.get("Keywords");
            String[] keywordList = words.split("\\|", -1);
            for (int i = 0; i < keywordList.length; i++) {
                Individual individualKeyword = keyword.createIndividual(BASE.concat(URLEncoder.encode(keywordList[i])));
                individualPaper.addProperty(hasKeyword, individualKeyword);
            }

            //creating submissions
            String submissionName = "Submission:" + paperTitle;
            Individual individualSubmission = submission.createIndividual(BASE.concat(URLEncoder.encode(submissionName)));
            individualSubmission.addLiteral(finalDecision, URLEncoder.encode(csvRecord.get("FinalDecision")));

            //creating reviews
            String reviewName1 = "Review1:" + paperTitle;
            Individual individualReview1 = review.createIndividual(BASE.concat(URLEncoder.encode(reviewName1)));
            individualReview1.addLiteral(reviewText, URLEncoder.encode(csvRecord.get("ReviewText1")));
            individualReview1.addLiteral(reviewDecision, URLEncoder.encode(csvRecord.get("ReviewDecision1")));
            individualSubmission.addProperty(hasReview, individualReview1);

            String reviewName2 = "Review2:" + paperTitle;
            Individual individualReview2 = review.createIndividual(BASE.concat(URLEncoder.encode(reviewName2)));
            individualReview1.addLiteral(reviewText, URLEncoder.encode(csvRecord.get("ReviewText2")));
            individualReview1.addLiteral(reviewDecision, URLEncoder.encode(csvRecord.get("ReviewDecision2")));
            individualSubmission.addProperty(hasReview, individualReview1);

            //creating reviewers
            String reviewerName1 = csvRecord.get("ReviewerName1");
            Individual individualReviewer1 = reviewer.createIndividual(BASE.concat(URLEncoder.encode(reviewerName1)));
            individualReviewer1.addLiteral(reviewerName, URLEncoder.encode(reviewerName1));
            individualReview1.addProperty(hasReviewer, individualReviewer1);

            String reviewerName2 = csvRecord.get("ReviewerName2");
            Individual individualReviewer2 = reviewer.createIndividual(BASE.concat(URLEncoder.encode(reviewerName2)));
            individualReviewer2.addLiteral(reviewerName, URLEncoder.encode(reviewerName2));
            individualReview2.addProperty(hasReviewer, individualReviewer2);

            //creating publication
            String publicationLabel = "Publication:" + paperTitle;
            Individual individualPublication = publication.createIndividual(BASE.concat(URLEncoder.encode(publicationLabel)));
            individualPublication.addLiteral(yearPublished, URLEncoder.encode(csvRecord.get("YearPublished")));
            if(csvRecord.get("FinalDecision").equals("A"))
            {
                individualSubmission.addProperty(isPublished, individualPublication);
            }

            //creating journals/conferences
            String venueType = csvRecord.get("VenueType");
            String confType = csvRecord.get("ConfType");

            if(venueType.equals("J")) {
                //creating journal
                String journalLabel = csvRecord.get("JournalName");
                Individual individualJournal = journal.createIndividual(BASE.concat(URLEncoder.encode(journalLabel)));
                individualJournal.addLiteral(journalName, URLEncoder.encode(journalLabel));

                //creating volume
                if (individualPublication!= null) {
                    String volumeLabel = csvRecord.get("VolumeNr");
                    Individual individualVolume = volume.createIndividual(BASE.concat(URLEncoder.encode(volumeLabel)));
                    individualVolume.addLiteral(volumeNr, URLEncoder.encode(volumeLabel));
                    individualPublication.addProperty(belongsToVolume, individualVolume);
                    individualVolume.addProperty(partOf, individualJournal);
                }

                //creating editor
                String editorLabel = csvRecord.get("EditorName");
                Individual individualEditor = editor.createIndividual(BASE.concat(URLEncoder.encode(editorLabel)));
                individualEditor.addLiteral(editorName, URLEncoder.encode(editorLabel));
                individualJournal.addProperty(hasEditor, individualEditor);

                //creating properties
                individualEditor.addProperty(oversees, individualSubmission);
                individualSubmission.addProperty(submittedTo, individualJournal);
            }
            else {
                //creating conference
                String confLabel = csvRecord.get("ConfName");
                OntClass conferenceClass;

                if (confType.equals("W")) {
                    conferenceClass = workshop;
                }
                else if (confType.equals("R")) {
                    conferenceClass = regularConference;
                }
                else if (confType.equals("S")) {
                    conferenceClass = symposium;
                }
                else {
                    conferenceClass = expertGroup;
                }

                Individual individualConference = conferenceClass.createIndividual(BASE.concat(URLEncoder.encode(confLabel)));
                individualConference.addLiteral(confName, URLEncoder.encode(confLabel));

                //creating proceeding
                if (individualPublication!= null) {
                    String proceedingLabel = csvRecord.get("IndexNr");
                    Individual individualProceeding = proceeding.createIndividual(BASE.concat(URLEncoder.encode(proceedingLabel)));
                    individualProceeding.addLiteral(indexNr, URLEncoder.encode(proceedingLabel));
                    individualPublication.addProperty(belongsToProceeding, individualProceeding);
                    individualProceeding.addProperty(isPartOf, individualConference);
                }

                //creating chair
                String chairLabel = csvRecord.get("ChairName");
                Individual individualChair = chair.createIndividual(BASE.concat(URLEncoder.encode(chairLabel)));
                individualChair.addLiteral(chairName, URLEncoder.encode(chairLabel));
                individualConference.addProperty(hasChair, individualChair);

                //creating properties
                individualChair.addProperty(supervises, individualSubmission);
                individualSubmission.addProperty(submittedTo, individualConference);
            }
        }

        FileOutputStream writerStream = new FileOutputStream(ABOX_DATA);
        model.write(writerStream, "N-TRIPLE");
        writerStream.close();
    }
}
