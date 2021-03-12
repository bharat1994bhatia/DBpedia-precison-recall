package com.bharat.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Dataset {
	
	public double size=0;
	
	public void setSizeDataset(String json) throws Exception
	{
		Object obj = new JSONParser().parse(json);
		JSONObject jo = (JSONObject) obj; 
	    JSONArray ja = (JSONArray) jo.get("Resources");

	    size=ja.size();
	}
	
	public void createDataset(String json) throws Exception
	{
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("entities");
		int rownum=0;
		//Json extraction
		Object obj = new JSONParser().parse(json);
		JSONObject jo = (JSONObject) obj; 
	    JSONArray ja = (JSONArray) jo.get("Resources");
	    Iterator itr= ja.iterator();
		
	    
        while(itr.hasNext())
        {
        	Row row = sheet.createRow(rownum++);
        	Cell cell = row.createCell(0);
        	Cell cell1 = row.createCell(1);
        	
        	
        	String tempArr[]=((Map) itr.next()).entrySet().toString().split(",");
        	
        	cell.setCellValue(tempArr[3].substring(tempArr[3].indexOf('=')+1));
        	cell1.setCellValue(tempArr[1].substring(tempArr[1].indexOf('=')+1));

        }	
        	try { 
                // this Writes the workbook 
        		String pathToDatasetCreated="H:\\dataset.xlsx";
                FileOutputStream out = new FileOutputStream(new File(pathToDatasetCreated)); 
                workbook.write(out); 
                out.close(); 
                System.out.println("dataset.xlsx written successfully on disk."); 
            } 
            catch (Exception e) { 
                e.printStackTrace(); 
            } 
        
	}
	public Map<String,String> createCorrectDatasetMap() {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		
		try
        {
			String pathToDatasetCreated="H:\\dataset.xlsx";
            FileInputStream file = new FileInputStream(new File(pathToDatasetCreated));
 
            XSSFWorkbook workbook = new XSSFWorkbook(file);
 
            XSSFSheet sheet = workbook.getSheetAt(0);
 
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) 
            {
                Row row = rowIterator.next();
                Cell cell = row.getCell(0);
            	Cell cell1 = row.getCell(1);
            	System.out.println(cell.toString()+" "+cell1.toString());
                map.put(cell.toString(), cell1.toString());
            }
        }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	return map;
	}
	
//	Precision = TruePositives / (TruePositives + FalsePositives)
//			correct predicts/(correct predicts + wrong predicts)  (Total predictions made)

	public double calculatePrecision(String json,Map<String,String> map) throws Exception
	{
		//Json extraction
		Object obj = new JSONParser().parse(json);
		JSONObject jo = (JSONObject) obj; 
	    JSONArray ja = (JSONArray) jo.get("Resources");
	    Iterator itr= ja.iterator();
		
		double truePositive=0;
		double falsePositive=0; 
		double precision=0;
		while(itr.hasNext())
        {
        	
        	String tempArr[]=((Map) itr.next()).entrySet().toString().split(",");
        	
        	String key=tempArr[3].substring(tempArr[3].indexOf('=')+1);
            String value=tempArr[1].substring(tempArr[1].indexOf('=')+1);
            
        	if(map.containsKey(key))
        	{
        		if(map.get(key).equals(value))
        			truePositive++;
        		else
        			falsePositive++;
        	}
        }
		precision=truePositive/(truePositive+falsePositive);
        return precision;
	}

	
//	Recall = TruePositives / (TruePositives + FalseNegatives)
//	correct predicts/(correct predicts + (missed entities))
	public double calculateRecall(String json,Map<String,String> map) throws Exception
	{
		//Json extraction
		Object obj = new JSONParser().parse(json);
		JSONObject jo = (JSONObject) obj; 
	    JSONArray ja = (JSONArray) jo.get("Resources");
	    Iterator itr= ja.iterator();
		
		
	    double truePositive=0;
		double falseNegative=0; 
		double recall=0;
		while(itr.hasNext())
        {
        	
        	String tempArr[]=((Map) itr.next()).entrySet().toString().split(",");
        	
        	String key=tempArr[3].substring(tempArr[3].indexOf('=')+1);
            String value=tempArr[1].substring(tempArr[1].indexOf('=')+1);
            
        	if(map.containsKey(key))
        	{
        		if(map.get(key).equals(value))
        			truePositive++;
        	}
        }
		
		System.out.println(size+" "+ja.size()+" "+truePositive+" "+falseNegative);
		falseNegative=size-ja.size();
		recall=truePositive/(truePositive+falseNegative);
		return recall;
	}

}
