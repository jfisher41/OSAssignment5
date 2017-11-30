package ThreadClasses;

import java.io.BufferedReader;
import java.io.FileReader;

import os_assignment5.DoubleLinkedList;
import os_assignment5.MainHelper;
import os_assignment5.PCB;


public class ReadFile implements Runnable{
	String fileName;
	MainHelper helper;
	DoubleLinkedList list;
	PCB queue[];
	
	public ReadFile(String fileName, MainHelper helper){
		this.helper = helper;
		this.fileName = fileName;

	}
	
	public void read(){
		
		String fileContents[];
		String line = null;
		try {
			
			BufferedReader buffReader = new BufferedReader(new FileReader(fileName));
			
			while((line = buffReader.readLine()) != null){
				fileContents = line.split(" +");
				
				//Send the line off to be analyzed
				try{
					helper.analyzeLine(fileContents);
				}catch (RuntimeException e){break;} 
			}
			
			buffReader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		list = helper.getList();
	}

	public void run() {	
		read();
	}

}
