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
import java.util.Set;


public class Cell {
	private Color color;
	private Set<Solution> horizSolutions; // Se la cella è nera e ha un vincolo verticale, sono 
											// le possibili combinazioni di somme
	private Set<Solution> vertSolutions;	// Come sopra, per il vincolo verticale
	private Set<Integer> domain;		// Se la cella è bianca, sono le possibili cifre trovate
	private int horizSum;	// Il vincolo di somma orizzontale
	private int vertSum;		/// Il vincolo di somma verticale
	private int horizSumLength; 
	private int vertSumLength; 
	public int i;
	public int j;
	public Cell horizRule; // Se la cella è bianca, è un vettore alla cella nera
							// che contiene la regola di somma orizzontale
	public Cell vertRule;	// Idem con la regola verticale
	public Cell right;		// La cella a destra
	public Cell down;		// La cella sotto
	
	public int getHorizSumLength() {
		return horizSumLength;
	}
	public void setDomain(Set<Integer> solutions) {
		this.domain = solutions;
	}
	public void setHorizSumLength(int horizSumLength) {
		this.horizSumLength = horizSumLength;
	}
	public int getVertSumLength() {
		return vertSumLength;
	}
	public void setVertSumLength(int vertSumLength) {
		this.vertSumLength = vertSumLength;
	}
	public Cell(int horizSum, int vertSum) {
		super();
		this.horizSum = horizSum;
		this.vertSum = vertSum;
		horizSolutions = new HashSet<Solution>();
		vertSolutions = new HashSet<Solution>();
		this.color = Color.black;
	}

	public Cell() {
		super();
		domain = new HashSet<Integer>();
		this.color = Color.white;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Set<Integer> getDomain() {
		return domain;
	}
	public int getHorizSum() {
		return horizSum;
	}
	public void setHorizSum(int horizSum) {
		this.horizSum = horizSum;
	}
	public Set<Solution> getHorizSolutions() {
		return horizSolutions;
	}
	public Set<Solution> getVertSolutions() {
		return vertSolutions;
	}
	public int getVertSum() {
		return vertSum;
	}
	public void setVertSum(int vertSum) {
		this.vertSum = vertSum;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		if(this.color.equals(Color.white)){
			sb.append("w");
			sb.append(" ");
			// sb.append(this.horizRule != null ? this.horizRule.toString() : "null");
			// sb.append(this.vertRule != null ? this.vertRule.toString() : "null");
			//sb.append(solutionsAmount);
			for(Integer solution : this.domain){
				sb.append(solution.toString());
				sb.append(";");
			}
		} else if(this.color.equals(Color.black))
			{
			sb.append("b");
			sb.append(horizSum);
			
			sb.append(":");/*
			for(Solution solution : this.horizSolutions){
				sb.append(solution.toString());
				sb.append(";");
			} */
			sb.append(this.horizSolutions.size());
			//sb.append('\n');
			sb.append("/");
			sb.append(vertSum);
			
			sb.append(":");
			/*
			for(Solution solution : this.vertSolutions){
				sb.append(solution.toString());
				sb.append(";");
			}
			*/
			sb.append(this.vertSolutions.size());
			
			}
		else if(this.color.equals(Color.solved)) {
			sb.append("S ");
			for(Integer solution : this.domain){
				sb.append(solution.toString());
				sb.append(";");
			};
		}
		return sb.toString();
		
	}
	
	
}
