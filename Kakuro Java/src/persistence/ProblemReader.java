/*
*    Kakuro Java 
*
*    Copyright 2018 Marco Raminella.
*    
*    This file is part of Kakuro Java.
*
*    Kakuro Java is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    Kakuro Java is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with Kakuro Java.  If not, see <http://www.gnu.org/licenses/>.
* 
*/
package persistence;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import model.Cell;
import model.Color;
import model.Problem;
import model.Solution;

public class ProblemReader {
	final static String FILE_NAME = "problema.txt";
	final static Charset ENCODING = StandardCharsets.UTF_8;


	public static Problem readProblema() {
		Problem result = null;
		Path path = Paths.get(FILE_NAME);
		try ( Scanner scanner = new Scanner(path,ENCODING.name()) ) {
				String line;
				int rows = Integer.parseInt(scanner.nextLine());
				int columns = Integer.parseInt(scanner.nextLine());
				result = new Problem(rows,columns);
				int currentColumn = 0;
				int currentRow = 0;
				while(currentRow < rows) {
					currentColumn = 0;
					line = scanner.nextLine();
					StringTokenizer st = new StringTokenizer(line);
					while(currentColumn < columns){
						int value = Integer.parseInt(st.nextToken("/").substring(1));
						if(value == -1){
							
							result.setCell(currentRow, currentColumn, new Cell()); 
							value = Integer.parseInt(st.nextToken(",").substring(1));
							
						} 
						else {
							//result.getCell(currentRow, currentColumn).setColor(Color.black);
							int horizSum = value;
							int vertSum =  Integer.parseInt(st.nextToken(",").substring(1));
							result.setCell(currentRow, currentColumn, new Cell(horizSum,vertSum)); 
						}
						currentColumn++;
					}
					currentRow++;
				}
				
				result.initCells();
				
		} catch (IOException e) {
			System.out.println("File problema.txt inesistente o malformato");
		}
		return result;
	}



}
