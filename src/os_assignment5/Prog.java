package os_assignment5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Prog {

	public static void main(String[] args) {
		MainHelper helper = new MainHelper();
		
		/**
		//Get argv from the console
		Scanner scan = new Scanner(System.in);
		String commandInput[];
		System.out.println("Enter commands:");
		String params = scan.nextLine();
		commandInput = params.split(" ");
		

		for(String param: commandInput){
			System.out.println(param);
		}
		**/
		
		//read file
		String fileContents[];
		String line = null;
		try {
			
			BufferedReader buffReader = new BufferedReader(new FileReader("input.txt"));
			
			while((line = buffReader.readLine()) != null){
				fileContents = line.split(" +");
				helper.analyzeLine(fileContents);
			}
			buffReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		helper.printStats("prog", "FIFO");
	}

}
