import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail
 */
public class CircuitTracer {

	/** launch the program
	 * @param args three required arguments:
	 *  first arg: -s for stack or -q for queue
	 *  second arg: -c for console output or -g for GUI output
	 *  third arg: input file name 
	 */
	public static void main(String[] args) {
		new CircuitTracer(args); //create this with args
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private void printUsage() {
		System.out.println("Usage: java CircuitTracer  [-s | -q] [-c | -g] [fileName]");
		System.out.println("-s: stack, -q: queue, -c: console, -g: gui");
		// See https://en.wikipedia.org/wiki/Usage_message for format and content guidance
	}
	
	/** 
	 * Set up the CircuitBoard and all other components based on command
	 * line arguments.
	 * 
	 * @param args command line arguments passed through from main()
	 * @throws filenotfoundexception is file is not found
	 * @throws invalidfileformat if file is not in proper format
	 */
	public CircuitTracer(String[] args) {
		if (args.length != 3) {
			printUsage();
			return; //exit the constructor immediately
		}
		
		// either set sup a queue or a stack to store data
		Storage<TraceState> stateStore;
		if (args[0].equals("-q")) { 
			stateStore = new Storage<TraceState>(Storage.DataStructure.queue);
		} else if (args[0].equals("-s")) {
			stateStore = new Storage<TraceState>(Storage.DataStructure.stack);
		} else {
			printUsage();
			return; 
		}
		
		if (args[1].equals("-g")) {
			System.out.println("A GUI is not available");
			return;			
		} else if (!args[1].equals("-c")){
			printUsage();
			return;
		}
		
		// checks and validates file
		final String inputedFileName = args[2];
		final File inputedFile = new File(inputedFileName);
			if (!inputedFile.exists()) {
				System.out.println("FileNotFoundException: File does not exist");
				return;
			} else if (!inputedFile.isFile()) {
		    	System.out.println("File error");
				return;
		   }
		
		try {
			CircuitBoard editingBoard = new CircuitBoard(args[2]);
			List<TraceState> bestPaths = new ArrayList<TraceState>();
			Point start = editingBoard.getStartingPoint();
				
			// need to check each direction
			
			// check up
			if (editingBoard.isOpen(start.x-1, start.y)) {
				stateStore.store(new TraceState(editingBoard, start.x-1, start.y));
			} 
			
			// check down
			if (editingBoard.isOpen(start.x+1, start.y)) { 
				stateStore.store(new TraceState(editingBoard, start.x+1, start.y));
			}
			
			// check left
			if (editingBoard.isOpen(start.x, start.y-1)) {
				stateStore.store(new TraceState(editingBoard, start.x, start.y-1));
			}					
			
			// check right
			if (editingBoard.isOpen(start.x, start.y+1)) {
				stateStore.store(new TraceState(editingBoard, start.x, start.y+1));
			}
				
			// recursion algorithm here
			
			/**
			 * Algorithm:
			 * -    retrieve the next TraceState object from stateStore
	-	if that TraceState object is a solution (ends with a position adjacent to the ending component),
		- 		if bestPaths is empty or the TraceState object's path is equal in length to one of the TraceStates in bestPaths,
		-			add it to bestPaths
		- 		else if that TraceState object's path is shorter than the paths in bestPaths,
	- 			clear bestPaths and add the current TraceState as the new shortest path
	-	else generate all valid next TraceState objects from the current TraceState and add them to stateStore
			 */
			
			
			// for each trace state, checks each direction to see if it is a valid path 
			while(!stateStore.isEmpty()) {
				TraceState tracer = stateStore.retrieve();
				if(tracer.isComplete()) {
					if(bestPaths.isEmpty() || tracer.pathLength() == bestPaths.get(0).pathLength()) {
						bestPaths.add(tracer);
					} else if (tracer.pathLength() < bestPaths.get(0).pathLength()) {
						bestPaths.clear();
						bestPaths.add(tracer);
					}
				} else {
					// up
					if(tracer.isOpen(tracer.getRow()+1, tracer.getCol())) {
						stateStore.store(new TraceState(tracer, tracer.getRow()+1, tracer.getCol()));
					}
					// down
					if(tracer.isOpen(tracer.getRow()-1, tracer.getCol())) {
						stateStore.store(new TraceState(tracer, tracer.getRow()-1, tracer.getCol()));
					}
					// left
					if(tracer.isOpen(tracer.getRow(), tracer.getCol()-1)) {
						stateStore.store(new TraceState(tracer, tracer.getRow(), tracer.getCol()-1));
					}
					// right
					if(tracer.isOpen(tracer.getRow(), tracer.getCol()+1)) {
						stateStore.store(new TraceState(tracer, tracer.getRow(), tracer.getCol()+1));
					}
				
				}
			}
			
			// prints out best (shortest) paths			
			if(!bestPaths.isEmpty())
			{
				for(TraceState path: bestPaths)
				{
					System.out.println(path.toString());
				}
			}
			
			
		}
				
		catch(FileNotFoundException e) {
			System.out.println("FileNotFoundException: File not found.");
			printUsage();
		}
		
		catch (InvalidFileFormatException e) {
			System.out.println("InvalidFileFormatException: Inputted file is not formatted correctly.");
			printUsage();
		}
	}
	
} // class CircuitTracer
