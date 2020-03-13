package testdata;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class WriteTest {
	
	
	
	

	public static void writeInJava (String s) throws IOException {
		
		String path = "C://Users/LE40044430/eclipse-workspace/test4.txt";
		
	
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
	
		try {
		
			bufferedWriter.write(s);
			bufferedWriter.newLine();
			
		}catch (Exception erro) {
			// TODO: handle exception
		}
		finally {
			bufferedWriter.close();
		}
		
	
	}

}
