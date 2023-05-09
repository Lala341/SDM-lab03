package org.example.ontology;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.*;

public class ABOX {

    public static final String TBOX_DATA = "data/tbox.owl";
    public static final String ABOX_DATA = "data/abox.nt";
    public static final String CSV_DATA_ARTICLE = "data/csv/output_article.csv";

    public static final String BASE = "http://www.sdm.lab#";

    public static void createABOX() throws IOException {

        Model model = ModelFactory.createDefaultModel().read(TBOX_DATA);
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF, model);

        OntClass paper = ontModel.getOntClass(TBOX.BASE.concat("Paper"));
        OntClass author = ontModel.getOntClass(TBOX.BASE.concat("Author"));
        OntClass journal = ontModel.getOntClass(TBOX.BASE.concat("Journal"));

        OntProperty hasAuthor = ontModel.getOntProperty(TBOX.BASE.concat("hasauthor"));

        BufferedReader csvReader = new BufferedReader(new FileReader(CSV_DATA_ARTICLE));
        CSVParser csvParser = CSVFormat.DEFAULT.withDelimiter(';').withHeader().parse(csvReader);

        for(CSVRecord csvRecord : csvParser) {

            String paperName = csvRecord.get("title");
            Individual individualPaper = paper.createIndividual(BASE.concat(paperName));

            String journalName = csvRecord.get("journal");
            Individual individualJournal = journal.createIndividual(BASE.concat(journalName));

            String authorName = csvRecord.get("author");
            String[] authorList = authorName.split("\\|", -1);

            for (int i = 0; i < authorList.length; i++) {
                Individual individualAuthor = author.createIndividual(BASE.concat(authorList[i]));
                individualPaper.addProperty(hasAuthor, individualAuthor);
            }
        }

        FileOutputStream writerStream = new FileOutputStream(ABOX_DATA);
        model.write(writerStream, "N-TRIPLE");
        writerStream.close();
    }
}
