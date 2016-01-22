package edu.pitt.dbmi.nlp.nmvb.extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FileUtils {
	public static List<List<String>> parseCSVFile(File selectedFile, String delimiter) throws Exception{
			List<List<String>> lines = new ArrayList<List<String>>();
			
			if(selectedFile!=null && selectedFile.exists()){
				
				FileReader fr = new FileReader(selectedFile);
				BufferedReader br = new BufferedReader(fr);
				String strLine = "";
				StringTokenizer st = null;
				//read comma separated file line by line
				while( (strLine = br.readLine()) != null)
				{
					List<String> fields = new ArrayList<String>();
					
					//break comma separated line using ","
					
					String[]split = strLine.split(delimiter);
					
					for(String s:split)
					{
						if(s.length() == 0)
							s = " ";
						fields.add(s);
					}
					lines.add(fields);

				}
				
				br.close();
				fr.close();
				
				return lines;
				
	
			}
			throw new Exception("File does not exist");
		}
	
	public static void writeToFile(File f, String data) throws IOException{
		FileWriter fw = new FileWriter(f);
		fw.write(data);
		fw.close();
	}
		
}

