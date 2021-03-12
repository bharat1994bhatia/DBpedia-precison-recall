# DBpedia-precison-recall
--> Calculation of precision and recall of DBpedia spotlight tool by calling its REST API at different confidence levels and matching the results with the result at confidence 0 which has maximum recommendations possible by the tool.

Step 1:Setup

1) Create a maven java project 

2) Add these dependencies in pom.xml for rest api consumption and client creation
	<groupId>javax.ws.rs</groupId>
	<artifactId>javax.ws.rs-api</artifactId>
	<version>2.1.1</version>
	
	<groupId>org.jboss.resteasy</groupId>
	<artifactId>resteasy-client</artifactId>
	<version>3.0.2.Final</version>
	
	<groupId>javax</groupId>
   	<artifactId>javaee-api</artifactId>
   	<version>7.0</version>
	
	<groupId>com.googlecode.json-simple</groupId>
	<artifactId>json-simple</artifactId>
	<version>1.1.1</version>
	
	<groupId>org.apache.poi</groupId>
        <artifactId>poi-ooxml</artifactId>
        <version>3.9</version>

Step 2: Creation of dataset

1) Set path to the text file that has to be used for annotation using dbpedia rest api . Change this in main() of 'Consume' class (pathToTestData="H:\\test file\\test.txt").
2) Set path to the location for dataset creation and name of .xlsc file . Change this in createDataset() of 'Dataset' class (pathToDatasetCreated="H:\\dataset.xlsx")
3) Manually corrected the entries in .xlsc file after matching with dbpedia spotlight tool and create correct dataset.
4) Set path to the location of .xlsc file for map creation for corect dataset . Change this in createCorrectDatasetMap() of 'Dataset' class (pathToDatasetCreated="H:\\dataset.xlsx")

Step 3: Execution for calculating precision and recall.

1) After correcting the dataset only calculate precision and recall for diferent confidence values. Do this using option 2 in menu driven program.

--> For simplicity, attaching the 'test.txt' containing test resume lines and corresponding corrected dataset 'dataset.xlsc' files.
Just change the path of locations where these files are stored on your system as explained in step 2: Creation of Dataset above.

--> Algorithm for Precision and Recall:
--> Precision:-
Precision = TruePositives / (TruePositives + FalsePositives)
TruePositives=correct predicts by dbpedia spotlight
FalsePositives=wrong predicts

1) While checking for response on a particular confidence value, checking each surface form if present in the correct map for dataset.
2) If prresent and values for that surface form are same then truePositive++
3) If values are not same then FalsePositives++

--> Recall:-
Recall = TruePositives / (TruePositives + FalseNegatives)
TruePositives=correct predicts by dbpedia spotlight
FalseNegatives=missed predictions at a particular confidence value

1) While checking for response on a particular confidence value, checking each surface form if present in the correct map for dataset.
2) If prresent and values for that surface form are same then truePositive++
3) FalseNegatives is the difference between correct dataset(all the entities predicted) and the number of predictions for particular confidence value.
