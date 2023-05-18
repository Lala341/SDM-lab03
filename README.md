A. Overview of Implementation

The creation of TBOX was done with gra.fo tool, the resulting owl can be found in the file (KnowledgeGraphSDMLab\data\tbox.owl). The KnowledgeGraphSDMLab folder contains the Java project  for creating the ABOX (KnowledgeGraphSDMLab\src\main\java\org\example\ontology\ABOX.java) and the nt file of the resulting graph is located at (KnowledgeGraphSDMLab\data\abox.nt). Additionally, for part B4 the queries are found in the file (Team12E_B4_OGBUCHI_FORERO_ZIVKOVIC.sparql). 

Below is an overview of the implementation:

1. Technology

1.1 GraphDB

GraphDB is a graph database compliant with RDF and SPARQL specifications. It supports open APIs based on the RDF4J (ex-Sesame) project and enables fast publishing of linked data on the web. The Workbench is used for searching, exploring and managing GraphDB semantic repositories.

1.2 Apache Jena 
Apache Jena is an open source Java framework for building Semantic Web and Linked Data applications. It is quite powerful as it can be used to create ontologies, query and add constraints (via SHACL) in a semantic web world. For the purpose of this lab, we have used Apache Jena API to create TBOX and ABOX (and their links) for our publications' data.
1.3 Ontology
The TBOX (Terminology Box) represents the metadata or schema of the knowledge graph, defining concepts (Classes) and their relationships (Properties).
The ABOX (Assertion Box) represents the data instances and their relationships. By combining the TBOX and ABOX, we create an ontology that enables various possibilities for querying and analyzing the data.
2. Dataset
We used the output_article data generated from DBLP data (2023) (which was converted from XML to csv). The final data is sliced and stored in datafinal folder due to its size and then preprocessed for the creation of the ABOX data.
3. Preprocess
In order to create the correct topology (TBOX and ABOX), we pre-process our data first. We wrote a python script which you can use to get the preprocessed data. Run the following to obtain the instance_data.csv file:
$ git clone https://github.com/Lala341/SDM-lab03.git or unzip the Code file
$ cd SDM-lab03/KnowledgeGraphSDMLab/data/csv
Execute preprocess_dblp.ipynb
<!—Notebook can be converted to py script using in case of absence of notebook compiler like jupyter--!>
$ jupyter nbconvert --to script preprocess_dblp.ipynb
python preprocess_dblp.py
4. Generate TBOX
Obtained from gra.fo saved into
$ cd SDM-lab03\KnowledgeGraphSDMLab\data
Confirm tbox.owl and the previously generated csv exists.
5. Generate ABOX
Execute the Java main class (i.e source file name ‘App’)to generate and save the ABOX
After running the above mentioned commands, you should have these files under cd SDM-lab03\KnowledgeGraphSDMLab\data directory: Tbox.owl, Abox.nt, csv/ instance_data.csv
Now, you can load Tbox.owl and Abox.nt in GraphDB and start querying the data.


-- GraphDB is a compliant graph database that supports RDF and SPARQL specifications. It uses open APIs based on the RDF4J (formerly Sesame) project and allows for the efficient publication of linked data on the web. The GraphDB Workbench is a tool used for searching, exploring, and managing semantic repositories in GraphDB.

-- Apache Jena, on the other hand, is an open-source Java framework used for building applications in the Semantic Web and Linked Data domain. It provides powerful capabilities for creating ontologies, querying data, and adding constraints using SHACL.

-- In the context of this lab, Apache Jena API was used to create the TBOX (Terminology Box) and ABOX (Assertion Box) for publications' data. The TBOX represents the metadata or schema of the knowledge graph, defining atomic concepts (Classes) and their relationships (Properties). The ABOX, on the other hand, represents the data instances and their relationships. By combining the TBOX and ABOX, we create an ontology that enables various possibilities for querying and analyzing the data.

-- The dataset used in this lab is generated from the DBLP data in XML format and converted to CSV format. To ensure correct topology in the knowledge graph, the data needs to be preprocessed. A Python script is provided in the lab, located in the "preprocess_data" folder, to perform the necessary preprocessing. Running the script will generate an "instance_data.csv" file.

-- To generate the TBOX, the preprocessed data is obtained from the saved grafo and the "tbox.owl" file is created. The generated CSV file should be placed in the "csv" directory within the "data" folder.

-- To generate the ABOX, you need to execute the main Java class named "App." Running this class will generate and save the ABOX in the form of an "Abox.nt" file.

-- After completing the above steps, you should have the following files in the "data" directory: "tbox.owl," "Abox.nt," and "csv/instance_data.csv." These files can be loaded into GraphDB, where you can start querying and exploring the data.

-- In summary, the lab involves using GraphDB and Apache Jena to create a knowledge graph from publications' data. The data is preprocessed, the TBOX is generated to define the schema, and the ABOX is generated to represent the data instances. Finally, the generated files are loaded into GraphDB for further analysis.
