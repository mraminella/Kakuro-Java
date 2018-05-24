/*
*    Kakuro Java remote notifying sensor network
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
package ui;

import java.util.Set;

import persistence.ProblemReader;
import solver.CombGen;
import model.Cell;
import model.Color;
import model.Node;
import model.Problem;
import model.Solution;
import model.TreeSolver;

public class Main {
public static void main(String[] args) {
			/*
			if(args.length == 2){
				Set<Solution> solutions;
			solutions = CombGen.getPossibleCombinations(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
			for(Solution solution : solutions){
				for(int value : solution.getValues())
					System.out.print(value + " ");
				System.out.print("; ");
			}
			System.out.println();
			}
		*/
		
		Problem problem = ProblemReader.readProblema();
		problem.obtainLengthForCells();
		problem.fillBlackSolutions();
		problem.fillWhiteSolutions();
	
		// problem.findSuitableSolutionsAmount();
		//System.out.println(problem.toString());
	
		Node father = TreeSolver.initTree(problem);
		//TreeSolver.explore(father, problem);
		 Cell cell = problem.getCellWithLessSolutions();
	//	 cell.setColor(Color.solved);
	//	 problem.inferenceOverDomains(cell);
		Gui gui = new Gui(problem);
		gui.setVisible(true);
		int j = 0;
		while(j < 1000) {
			problem.cleanOtherSolutions(problem.getCellWithLessSolutions());
			while (problem.getCellWithLessSolutions() != null) {
			 cell = problem.getCellWithLessSolutions() ;
			 cell.setColor(Color.solved);
			 problem.inferenceOverDomains(cell);
		//	 problem.fillWhiteSolutions();
			
			} 
			 problem.unSolveCells();
			 j++;
		}
		TreeSolver.explore(father, problem);
	}
}
