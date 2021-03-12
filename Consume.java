package com.bharat.rest;



import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//
//import org.json.simple.parser.JSONParser;

public class Consume {

	public static String readFileAsString(String fileName) throws Exception
	{
		String data="";
		data=new String(Files.readAllBytes(Paths.get(fileName)));
		return data;
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		 
		ArrayList<Double> precision = new ArrayList<Double>();
		ArrayList<Double> recall = new ArrayList<Double>();
		
		Dataset ds = new Dataset();
		String pathToTestData="H:\\test file\\test.txt";
		String data=readFileAsString(pathToTestData);
		
		Client client = ClientBuilder.newClient();
		
		//Using confidence 0 trying to gather the surface forms and their DBpedia URIs
		WebTarget target=client.target("https://api.dbpedia-spotlight.org/en/annotate")
				   .queryParam("text", data)
			       .queryParam("confidence","0.0")
			       .queryParam("support", "20");

		String json=target.request(MediaType.APPLICATION_JSON).get(String.class);
		
//		System.out.println(json);
		Scanner sc = new Scanner(System.in);
		int a;
		System.out.println("Select the option");
		System.out.println("1: Create the raw dataset for correction");
		System.out.println("2: Calculate Precision and Recall");
		a=sc.nextInt();
		switch(a)
		{
		case 1:
			ds.createDataset(json);
			break;
		case 2:
			ds.setSizeDataset(json);
			
			Map<String,String> map=ds.createCorrectDatasetMap();
		
			for(double i=0;i<=1;i=i+0.1)
			{
				System.out.println("--------------------------Confidence="+i+"----------------------------");
				target=client.target("https://api.dbpedia-spotlight.org/en/annotate")
						   .queryParam("text", data)
					       .queryParam("confidence",String.valueOf(i))
					       .queryParam("support", "20");
				json=target.request(MediaType.APPLICATION_JSON).get(String.class);
				
				precision.add(ds.calculatePrecision(json,map));
				recall.add(ds.calculateRecall(json,map));
				
//				System.out.println(json);
			}

			System.out.println("------Precision------");
			for(double d:precision)
			{
				System.out.println(d);
			}
			
			
			System.out.println("------Recall------");
			
			for(double d:recall)
			{
				System.out.println(d);
			}
			break;
		default:
			System.out.println("Wrong input");
		}
        
	}

}
