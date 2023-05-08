import pandas as pd
from os import getcwd
from os.path import join, abspath, pardir
from random import choice

def print_log(text):
    print("[ log ] {}".format(text))

def preprocess_data(df):
    
    __data__ = list()
    
    paper_types = ['Full Paper', 'Short Paper', 'Demo Paper']
    conference_types = ['Workshop', 'Symposium', 'Expert Group', 'Regular Conference']
    document_types = ['Conference', 'Journal']
    decision_types = ['Accepted', 'Rejected']
    
    author_df = pd.DataFrame(df['Authors'].str.split(', ').tolist(), index=df.index).stack().reset_index().set_index('level_0')
    author_df.drop('level_1', axis=1, inplace=True)
    persons = list(author_df[0].drop_duplicates())
    
    for index, row in df.iterrows():
        
        authors = row['Authors']
        paper = row['Title']
        areas = row['Index Keywords']
        source = row['Source title']
        volume = row['Volume']
        year = row['Year']
        abstract = row['Abstract']
        
        document_type = choice(document_types)
        isConference = document_type == 'Conference'

        paper_type = choice(list(paper_types + ['Poster'])) if isConference else choice(paper_types)
        conference_type = choice(conference_types) if isConference else None
        
        reviewer_1, reviewer_2, handler = str( choice( persons ) ), str( choice( persons ) ), str( choice( persons ) )
        
        areas = areas.replace("; ", ";") if isinstance(areas, str) else None
        decision = choice(decision_types)
        
        for i, author in enumerate( authors.split(", ") ):
            
            data = dict()
            
            data['no'] = "{}.{}".format(index, i)
            data['Author'] = str(author)
            data['Paper'] = str(paper)
            data['Paper_Type'] = str(paper_type)
            data['Conference_Type'] = str(conference_type)
            data['Year'] = str(year)
            data['Source'] = str(source)
            data['Publication'] = "{} {}".format(str(source), str(volume))
            data['Document_Type'] = str(document_type)
            data['Reviewer_1'] = reviewer_1
            data['Reviewer_2'] = reviewer_2
            data['Handler'] = handler
            data['Areas'] = str(areas)
            data['Reviewer_Decision'] = str(decision)
            data['Reviewer_Text'] = str(abstract)
            
            __data__.append(data)
            
    return __data__

def main():

    parent_dir = getcwd()
    data_dir = join(parent_dir, "data", "raw")
    data_file = join(data_dir, "publications.csv")
    output_file = join(data_dir, "instances_data.csv")

    print_log("Reading publications' data from '{}'".format(data_file))

    df = pd.read_csv(data_file)

    __cols__ = ['Title', 'Source title', 'Authors', 'Index Keywords', 'Document Type', 'Volume', 'Year', 'Abstract']
    df = df[__cols__]

    print_log("Preprocessing publications' data")
    data = preprocess_data(df)
    print_log("Preprocessed '{}' publications' records".format( len(data) ))
    
    data_df = pd.DataFrame(data)

    print_log("Saving preprocessed publications' data to '{}'".format(output_file))
    data_df.to_csv(output_file, index=False)
    print_log("All done !!!")

if __name__ == "__main__":
    main()
