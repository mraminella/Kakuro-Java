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
package model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import solver.CombGen;

public class Problem {
	
	public Cell[][] cells;
	int nRows;
	int nColumns;

	public int getnRows() {
		return nRows;
	}

	public int getnColumns() {
		return nColumns;
	}

	public Problem(int nRows, int nColumns) {
		super();
		this.cells = new Cell[nRows][nColumns];
		this.nRows = nRows;
		this.nColumns = nColumns;
		
	}

	public void initCells() { // Inizializza i puntatori della cella "a sinistra" e "sotto" di ogni risp. cella
		for(int i=0;i<nRows;i++){
			for(int j=0;j<nColumns;j++){
				cells[i][j].i = i;
				cells[i][j].j = j;
				if(j + 1 < nColumns) cells[i][j].right = cells[i][j+1];
				if(i + 1 < nRows) cells[i][j].down = cells[i+1][j];
			}
		}
	}
	
	public void unSolveCells() { // Inizializza i puntatori della cella "a sinistra" e "sotto" di ogni risp. cella
		for(int i=0;i<nRows;i++){
			for(int j=0;j<nColumns;j++){
				if(cells[i][j].getColor().equals(Color.solved))
					cells[i][j].setColor(Color.white);
			}
		}
	}
	public Cell getCell(int row, int column){
		return cells[row][column];
	}

	public void setCell(int row, int column, Cell cell){
		cells[row][column] = cell;
	}

	public void obtainLengthForCells(){ // controlla la lunghezza delle parole
		int length;
		for(int i=0;i<nRows;i++){
			for(int j=0;j<nColumns;j++){
				if(cells[i][j].getColor().equals(Color.black)){
					if(cells[i][j].getHorizSum() > 0){ // ho un vincolo di somma orizzontale (parola orizzontale)
						length = j;
						length++;
						while(length < nColumns  && cells[i][length].getColor().equals(Color.white)) {
						
							cells[i][length].horizRule = cells[i][j];
							length++;
						}
						cells[i][j].setHorizSumLength(length - j - 1);
						
					}
					if(cells[i][j].getVertSum() > 0){ // ho un vincolo di somma verticale (parola verticale)
						length = i;
						length++;
						while(length < nRows && cells[length][j].getColor().equals(Color.white)) {
						
							cells[length][j].vertRule = cells[i][j];
							length++;
						}
						cells[i][j].setVertSumLength(length - i - 1);
						
					}
				}
			}
		}
	}

	public void fillBlackSolutions(){ // Inizializzo le possibili tuple di soluzioni
										// possibili per ogni vincolo
		for(int i=0;i<nRows;i++){ // ciclo sulle righe
			for(int j=0;j<nColumns;j++){ // ciclo sulle colonne
				if(cells[i][j].getColor().equals(Color.black)){
					if(cells[i][j].getHorizSum() > 0){ // ho un vincolo di somma orizzontale (parola orizzontale)
						cells[i][j].getHorizSolutions().addAll(CombGen.getPossibleCombinations(cells[i][j].getHorizSum(), cells[i][j].getHorizSumLength()));
					}

					if(cells[i][j].getVertSum() > 0){ // ho un vincolo di somma verticale (parola verticale)
						cells[i][j].getVertSolutions().addAll(CombGen.getPossibleCombinations(cells[i][j].getVertSum(), cells[i][j].getVertSumLength()));

					}


				}
			}
		}

	}
	
	public void fillWhiteSolutions(){ // A partire dalle possibili tuple nelle celle nere
									// confronta per ogni cella bianca tutte le combinazioni
									// e determina quali cifre da 1 a 9 sono "papabili"
		for(int i=0;i<nRows;i++){ // ciclo sulle righe
			for(int j=0;j<nColumns;j++){ // ciclo sulle colonne
				if(cells[i][j].getColor().equals(Color.white)){
					cells[i][j].setDomain(intersectSolutions(cells[i][j].horizRule.getHorizSolutions(),cells[i][j].vertRule.getVertSolutions()));
				}
			}
		}
		
	}

	public Cell getCellWithLessSolutions() { // trova la cella bianca con meno cifre "papabili"
		int min = 10;
		Cell result = null;
		for(int i=0;i<nRows;i++){ // ciclo sulle righe
			for(int j=0;j<nColumns;j++){ // ciclo sulle colonne
				if(cells[i][j].getColor().equals(Color.white)){
					if(cells[i][j].getDomain().size() < min) {
						min = cells[i][j].getDomain().size();
						result = cells[i][j];
					}
				}
			}
		}
		return result;

	}
	
	public void clear() { // elimina le soluzioni già trovate dalle celle bianche
		for(int i=0;i<nRows;i++){ // ciclo sulle righe
			for(int j=0;j<nColumns;j++){ // ciclo sulle colonne
				if(cells[i][j].getColor().equals(Color.solved)){
					cleanOtherSolutions(cells[i][j]);

					}
			}
		}

	}
	
	public boolean isSolutionOk() {

		for(int i=0;i<nRows;i++){
			for(int j=0;j<nColumns;j++){
				if(cells[i][j].getColor().equals(Color.white) && cells[i][j].getDomain().isEmpty()) return false;
				if(cells[i][j].getColor().equals(Color.black)){
					if(cells[i][j].getHorizSum() > 0 && cells[i][j].getHorizSolutions().size() == 0) return false;
					if(cells[i][j].getVertSum() > 0 && cells[i][j].getVertSolutions().size() == 0) return false;
				}
			}
		}
		return true;
	}
	
	public boolean isSolutionComplete() {

		for(int i=0;i<nRows;i++){
			for(int j=0;j<nColumns;j++){
				if(cells[i][j].getColor().equals(Color.white)) return false;
			}
		}
		return true;
	}
	
	public boolean inferenceOverAllDomains() {

		for(int i=0;i<nRows;i++){
			for(int j=0;j<nColumns;j++){
				if(cells[i][j].getColor().equals(Color.white)) inferenceOverDomains(cells[i][j]);
			}
		}
		return true;
	}
	
	public void inferenceOverDomains(Cell cell) {
		int solution;
		Iterator<Integer> solutionIterator = cell.getDomain().iterator();
		Set<Integer> newSolution = new HashSet<Integer>();
		
		/*
		 * 		HORIZ
		 *
		 */
		while(solutionIterator.hasNext()) {
			solution = solutionIterator.next();
		//	Cell toClean; 
			// adesso devo togliere le combinazioni che non comprendono  numero
			// Tolgo le soluzioni senza il numero della soluzione dalla regola orrizzontale
			int newFactor = cell.horizRule.getHorizSum() - solution; 
			if(cell.horizRule.getHorizSumLength() - 1 > 1)	
				for(Solution s : CombGen.getPossibleCombinations(newFactor, cell.horizRule.getHorizSumLength() - 1)) {
					newSolution.addAll(s.values);
				}	
			else newSolution.add(newFactor);
		}
		
		
		
		/*
		 * 		VERT
		 *
		 */
		Set<Integer> newSolutionVert = new HashSet<Integer>();
		solutionIterator = cell.getDomain().iterator();
		while(solutionIterator.hasNext()) {
			solution = solutionIterator.next();
		//	Cell toClean; 
			// adesso devo togliere le combinazioni che non comprendono  numero
			// Tolgo le soluzioni senza il numero della soluzione dalla regola orrizzontale
			int newFactor = cell.vertRule.getVertSum() - solution; 
			if(cell.vertRule.getVertSumLength() - 1 > 1)
				for(Solution s : CombGen.getPossibleCombinations(newFactor, cell.vertRule.getVertSumLength() - 1)) {
					newSolutionVert.addAll(s.values);
				}	
			else newSolutionVert.add(newFactor);
		}
		
		Iterator<Integer> newSolutionIterator = newSolution.iterator();
		while(newSolutionIterator.hasNext()) {
			Integer i = newSolutionIterator.next();
			if(!newSolutionVert.contains(i))
				newSolutionIterator.remove();
		}
		/* HORIZ */
		Cell rightCell = cell.horizRule.right;
		while(rightCell.getColor() == Color.white) {
			if(rightCell != cell) {
				Iterator<Integer> rightCellIterator = rightCell.getDomain().iterator();
				while(rightCellIterator.hasNext()) {
					int i = rightCellIterator.next();
					if(!newSolution.contains(i))
						rightCellIterator.remove();
				}
			}
			 if(rightCell.right != null)	rightCell = rightCell.right;
			    else break;
		}
		/* VERT */
		Cell downCell = cell.vertRule.down;
		while(downCell.getColor() == Color.white) {
			if(downCell != cell) {
				Iterator<Integer> downCellIterator = downCell.getDomain().iterator();
				while(downCellIterator.hasNext()) {
					int i = downCellIterator.next();
					if(!newSolutionVert.contains(i))
						downCellIterator.remove();
				}
			}
			 if(downCell.down != null)	downCell = downCell.down;
			    else break;
		}
		
	}
	
	public void cleanOtherSolutions(Cell cell) {
		
		// PREAMBOLO: questo metodo viene chiamato per una cella che ha una sola soluzione
		// possibile
		int solution;
		Iterator<Integer> solutionIterator = cell.getDomain().iterator();
		
			solution = solutionIterator.next();
		
			
			/*
			 * 			INFERENZA SULLE CELLE ADIACENTI
			 * 
			 */
			
			Cell toClean; 
			// adesso devo togliere le combinazioni che non comprendono questo numero
			// Tolgo le soluzioni senza il numero della soluzione dalla regola orrizzontale
			Iterator<Solution> iterator = cell.horizRule.getHorizSolutions().iterator();
			while(iterator.hasNext()) {
				Solution horizRuleSolution = iterator.next();
				if(!horizRuleSolution.getValues().contains(solution)) {
					iterator.remove();
				}
			}
			
			// Tolgo le combinazioni senza la soluzione dalla regola verticale
			iterator = cell.vertRule.getVertSolutions().iterator();
			while(iterator.hasNext()) {
				Solution vertRuleSolution = iterator.next();
				if(!vertRuleSolution.getValues().contains(solution)) {
					iterator.remove();
				}
			}
			/*
			 * Ricalcolo tutte le soluzioni possibili a partire dalle combinazioni rimanenti
			 * 
			 * ELIMINATO perchè andava a far "ricomparire" celle "white" dove in realtà
			 * la soluzione era già stata trovata
			 *  
			toClean = cell.horizRule.right; // scorro sugli elementi a destra della cella nera col vincolo orizzontale
			while(toClean != null && toClean.getColor().equals(Color.white) && toClean != cell) {
				toClean.getDomain().clear();
				toClean.getDomain().addAll(intersectSolutions(toClean.horizRule.getHorizSolutions(),toClean.vertRule.getVertSolutions()));
				toClean = toClean.right;
			}
			toClean = cell.vertRule.down; // scorro sugli elementi sotto della cella nera col vincolo verticale
			while(toClean != null && toClean.getColor().equals(Color.white) && toClean != cell) {
				toClean.getDomain().clear();
				toClean.getDomain().addAll(intersectSolutions(toClean.horizRule.getHorizSolutions(),toClean.vertRule.getVertSolutions()));
				toClean = toClean.down;
			}
			
			cell.getDomain().clear();
			cell.getDomain().add(solution);
			*/
			/*
			 * 						FINE INFERENZA
			 * 
			 */
			
			// fillWhiteSolutions(); // Ricalcolo le soluzioni possibili su tutto il kakuro, come dice Civo
	
			
			// Dato che le nuove cifre possibili nelle bianche includeranno anche il numero della soluzione
			// che ho appena trovato, dovrò togliere tale numero dalle altre celle bianche,
			// che non possono avere tale soluzione
			toClean = cell.horizRule.right;
			while(toClean != null && ( toClean.getColor().equals(Color.white) || toClean.getColor().equals(Color.solved)  )) {
				if(toClean != cell){
					toClean.getDomain().remove(solution);
				}
				toClean = toClean.right;
			}
			toClean = cell.vertRule.down;
			while(toClean != null && ( toClean.getColor().equals(Color.white)|| toClean.getColor().equals(Color.solved))) {
				if(toClean != cell){
				toClean.getDomain().remove(solution);
				}
				toClean = toClean.down;
			}

		
	}

	public Set<Integer> intersectSolutions(Set<Solution> set1, Set<Solution> set2)
	{
		Set<Integer> result = new HashSet<Integer>();
		Set<Integer> temp = new HashSet<Integer>();
		for(Solution solution : set1) {
			result.addAll(solution.getValues());
		}
		for(Solution solution : set2) {
			temp.addAll(solution.getValues());
		}
		result.retainAll(temp);
		return result;
	}
	
		@Override
		public String toString(){
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<nRows;i++){
				for(int j=0;j<nColumns;j++){
					sb.append(cells[i][j].toString());
					sb.append("||\t");
				}
				sb.append('\n');
			}
			return sb.toString();
		}
	}
