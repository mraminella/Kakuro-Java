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
package solver;
import java.util.HashSet;
import java.util.Set;

import model.Solution;


public class CombGen {
	

	public static Set<Solution> getPossibleCombinations(int num, int cells) {
		Set<Solution> result = new HashSet<Solution>();
		if(cells > 2){ // esplorazione delle soluzioni
			int tempFactor = num / cells;
			for(int firstFactor = 1; firstFactor < tempFactor; firstFactor++){
				Set<Solution> innerSolutions = getPossibleCombinations(num - firstFactor, cells - 1);
				for(Solution solNew : innerSolutions){
				Solution sol = new Solution();
				boolean validSolution = true;
				sol.addSolution(firstFactor);			
					for(int value : solNew.getValues()){
						if(value == firstFactor){
							validSolution = false;
							break;
						}
						sol.addSolution(value);
					}
					if(validSolution) result.add(sol);
				}
				
			}
		}
		else // cells == 2, caso radice
		{
			int firstFactor = num / 2 + 1; // l'addendo più grande
			int secondFactor = num - firstFactor; // l'addendo più piccolo
			while (firstFactor <= 9 && firstFactor < num) {
				Solution sol = new Solution();
				sol.addSolution(firstFactor); 
				sol.addSolution(secondFactor);
				result.add(sol);
				firstFactor++;
				secondFactor--;
			}
		}
		
		return result;
	}
	
	
}
