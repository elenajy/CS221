import java.io.*;
import java.util.*;

/**
 * 
 * @author elena
 * @version 1.0 CS221 Fall 2020
 *
 */

public class FormatChecker {
	
	/**
	 * Carries out the central checking function
	 * 
	 * @param input files which the program checks
	 * @throws IOException 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws IOException {
	
		int row;
		int col;
		Scanner fileScan = null;
		
		if (args.length > 0) {
			for (int arg = 0; arg < args.length; arg++) {
				// try block runs code as if there are no exceptions
				try {
					fileScan = new Scanner(new File(args[arg]));
					// initializes dimension variables
					row = fileScan.nextInt();
					col = fileScan.nextInt();
					int rowCounter = 0;
					
					// counts number of rows to make sure rows and cols match given (e.g., 3x4 vs 4x3)
					while (fileScan.hasNextLine()) {
						if(fileScan.nextLine().length() != 0) {
							rowCounter++;
						} 
					}
					
					// reopens file so cursor goes to beginning of file
					fileScan = new Scanner(new File(args[arg]));
					fileScan.nextLine();
					
					if (row != rowCounter) {
						System.out.println("Specified dimensions do not match given dimensions");
						throw new ArrayIndexOutOfBoundsException();
					}
					
					// checks each value to see if they are a double (valid file format)
					double placeholder = 0;
					for (int val = 0; val < row*col; val++) {
						if(fileScan.hasNextDouble()) {
							placeholder =  fileScan.nextDouble();
						} else if (fileScan.hasNext()){
							throw new NumberFormatException();
						} else {
							throw new ArrayIndexOutOfBoundsException();
						}					
					}
					
					// checks if there are more numbers that are outside of specified dimensions
					if (fileScan.hasNext()) {
						throw new ArrayIndexOutOfBoundsException();
					}
					else {
						System.out.println(args[arg]+": VALID");
					}	
				} 
				catch (FileNotFoundException e) {
					System.out.println("File Not Found");
					System.out.println(e.toString());
					System.out.println(args[arg]+": INVALID");
				} catch ( InputMismatchException e) {
					System.out.println("Input values need to be doubles");
					System.out.println(e.toString());
					System.out.println(args[arg]+": INVALID");
				} catch (NumberFormatException e) {
					System.out.println("Number format exception");
					System.out.println(e.toString());
					System.out.println(args[arg]+": INVALID");
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Array index out of bounds");
					System.out.println(e.toString());
					System.out.println(args[arg]+": INVALID");
				}
				finally {
					row = 0;
					col = 0;
					if (fileScan != null) {
						fileScan.close();
					}
					System.out.println("------------------------");
				}
			}
			
		}
		else {
			System.out.println("Please input some files.");
		}

	}
}