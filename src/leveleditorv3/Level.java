package leveleditorv3;

import java.util.ArrayList; //TODO: less specific import?
import processing.core.*;
import entities.*;
import java.io.*;	//TODO: more specific import?

import leveleditorv3.Defaults;
import leveleditorv3.Images;

public class Level {

	public String projectPath;
	public String name;
	
	public ArrayList<Entity> entityList;
	public int tileSize;
	public int map[][][];
	public PImage[] tileset;
	PApplet p;
	
	//
	public Level (String thisPath, String _name, PApplet _p) {
		p = _p;
		projectPath = thisPath;
		name = _name;
		
		entityList = new ArrayList<Entity>();
		tileSize = Defaults.TILE_SIZE;
		tileset = getTileset(Images.tilesetImage);
		
		map = new int[Defaults.LEVEL_LAYERS][Defaults.LEVEL_HEIGHT][Defaults.LEVEL_WIDTH];
	}
	
	public Level(String thisPath, String _name, int[][][] _map, PApplet _p) {
		p = _p;
		projectPath = thisPath;
		name = _name;
		
		entityList = new ArrayList<Entity>();
		tileSize = Defaults.TILE_SIZE;
		tileset = getTileset(Images.tilesetImage);
		map = _map; //TODO: arrayCopy()?
	}
	
	//returns the closest tile edge on the same axis as n
	public int snapToGrid (float n) {
		n /= tileSize;
		return ((int) n) * tileSize;
	}
	
	//returns the index of the closest tile edge on the same axis as pos
	public int tileCoord(float pos){
		return (int) (((int) pos) / tileSize);
	}
	
	//returns the "type" of tile that tiles with ID are.
	//TODO use an enum
	public int tileType(int id){
		return 0;
		
	}
	
	public void readLevel () {
		
		String fullPath = projectPath + "\\src\\data\\levels\\" + name;
		
		try {
			
			String line = null;
			int currentLayer = 0;
			
			//read the text file specified by levelname
			FileReader reader = new FileReader(fullPath);
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			int lineIter = 0;
			
			System.out.println("read initialized, reading file: " + fullPath);
			
			while ((line = bufferedReader.readLine()) != null) {
				//System.out.println(line);
				if ((line.charAt(0) == '#') && (line.charAt(1) == 'L')){
					
					if (Character.getNumericValue(line.charAt(2)) <= map.length) {
						//System.out.println("layer: " + line.charAt(2));
						currentLayer = Character.getNumericValue(line.charAt(2));
						lineIter = 0;
					}
				}
				//if there's a '#' but not an 'L', we know to skip that line and switch to the next mode (in this case just break)
				else if (line.charAt(0) == '#') {
					System.out.println("done with layers");
					break;
				}
				//if there's not '#' then it's a data line
				else {
					//read in csv values using line.split
					String[] splitLine = line.split(",");
					for (int i = 0; i < splitLine.length; i++) {
						//TODO only set it if there's room in currentLayer
						map[currentLayer][lineIter][i] = Integer.parseInt(splitLine[i]);
						// System.out.println(splitLine[i]);
					}
					
					lineIter ++;
				}
			}
			
			System.out.println("done reading file");
			
			bufferedReader.close();
			
		}
		catch(FileNotFoundException ex) {
			System.out.println("File not found: " + fullPath);
		}
		catch(IOException ex) {
			System.out.println("Error reading file: " + fullPath);
		}
		//write the current level map
		
	}

	//TODO: look into the processing Files part of the reference to actually save arrays to a file instead of doing this
	public void writeLevel() {
		//read the current level map to the file levelname
		//TODO what if the file already exists?
		
		String fullPath = projectPath + "\\src\\data\\levels\\" + name;
		
		try {
			String line = "";
			
			//FileWriter writer = new FileWriter(thisPath + "out.txt");
			FileWriter writer = new FileWriter(fullPath);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			
			System.out.println("write initialized, writing to file: " + fullPath);
			
			for (int l = 0; l < map.length; l++){
				//write the header of the info to the file
				line += "#L";
				line += Integer.toString(l);
				bufferedWriter.write(line);
				bufferedWriter.newLine();
				line = "";
				
				//write the data of each layer to the file
				for (int y = 0; y < map[0].length; y++) {
					for (int x = 0; x < map[0][0].length; x++) {
						bufferedWriter.write(Integer.toString(map[l][y][x]));
						bufferedWriter.write(",");
					}
					bufferedWriter.newLine();
					//line = "";
				}
			}
			
			System.out.println("done writing to file");
			
			bufferedWriter.close();
		}
		catch(IOException ex) {
			System.out.println("Error writing to file: " + fullPath);  
		}
	}

	//just an output function to see the current level
	//of course java's println function is slow as fuck
	public void printCurrentLevelRepresentation() {
		String line = "";
		
		for (int l = 0; l < map.length; l++){
			System.out.println("LAYER " + Integer.toString(l));
			for (int y = 0; y < map[0].length; y++){
				for (int x = 0; x < map[0][0].length; x++){
					line += Integer.toString(map[l][y][x]);
					line += ",";
				}
				System.out.println(line);
				line = "";
			}
		}
	}
	
	//TODO: general "breakimage(image, height, width)" function

	//getTileset
	//takes a tileset image, and makes all the tiles in it into separate images and outputs that list in an array.
	public PImage[] getTileset (PImage tilesetImage) {
		PImage[] output = new PImage[(tilesetImage.width/tileSize)*(tilesetImage.height/tileSize)];
		int nextEmpty = 1;
		for (int i=0; i<tilesetImage.width/tileSize; i++) {
			for (int j=0; j<tilesetImage.height/tileSize; j++) {
				//output[nextEmpty] = createImage(tileSize, tileSize, ARGB);
				//output[nextEmpty] = get(i*tileSize, j*tileSize, tileSize, tileSize);
				output[nextEmpty] = Images.extract(tilesetImage, i*tileSize, j*tileSize, tileSize, tileSize, p);
				if (output[nextEmpty] != null){
					nextEmpty++;
				}
				//nextEmpty ++;
			}
		}
		output[0] = blankTile();
		return output;
	}
	
	public PImage blankTile() {
		PImage out = p.createImage(tileSize, tileSize, PConstants.ARGB);
		out.loadPixels();
		for (int i = 0; i < out.pixels.length; i++) {
			out.pixels[i] = Colors.alphaOnly;
		}
		return out;
	}
}
