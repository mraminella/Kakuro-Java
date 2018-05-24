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


public class TreeSolver {
	public static Node initTree(Problem problem) {
		Node node = new Node(0,0);
		Cell beginning = problem.getCellWithLessSolutions();
		createChildren(node,beginning);
		return node;
	}
	
	public static void createChildren(Node father, Cell cell) {
		Node child;
		if ( cell == null) return;
		for(Integer domainValue : cell.getDomain()) {
				child = new Node(cell.i,cell.j);
				child.setValue(domainValue);
				child.setFather(father);
				father.getChildren().add(child);
		}
	}
	
	public static Node explore(Node node, Problem problem) {
			/*
			 *	ESPLORAZIONE DEPTH-FIRST RICORSIVA 
			 * 
			 */
		if(problem.isSolutionComplete()) return node;
		Iterator<Node> childrenIterator = node.getChildren().iterator();
		Node nextChild = null;
		while(childrenIterator.hasNext()) {
			//Gui gui = new Gui(problem);
			//gui.setVisible(true);
			nextChild = childrenIterator.next();
			Set<Integer> domain = new HashSet<Integer>(); // Salvo il dominio in caso di Rollback..
			domain.addAll(problem.getCell(nextChild.getI(), nextChild.getJ()).getDomain()); // 	 ..
			// Metto la soluzione del nodo in analisi nel Kakuro e vedo cosa succede
			problem.getCell(nextChild.getI(), nextChild.getJ()).getDomain().clear();
			problem.getCell(nextChild.getI(), nextChild.getJ()).getDomain().add(nextChild.getValue());
			/*
			 * 	CSP
			 */
			problem.cleanOtherSolutions(problem.getCell(nextChild.getI(), nextChild.getJ()));
			problem.getCell(nextChild.getI(), nextChild.getJ()).setColor(Color.solved);
			createChildren(nextChild,problem.getCellWithLessSolutions());
			problem.clear();
			//Gui gui = new Gui(problem);
			//gui.setVisible(true);
			if(problem.isSolutionOk()) {
				/*
				 * Ricorsione depth-first
				 */
				if(explore(nextChild,problem) != null) return nextChild;
			}
			/*
			 * Rollback: la soluzione corrente è sbagliata e bisogna provare la successiva
			 */
			domain.remove(node.getValue()); // Rimuovo la soluzione del dominio copia..
			childrenIterator.remove();		//.. e dal dominio sulla matrice vera
			problem.getCell(nextChild.getI(), nextChild.getJ()).getDomain().addAll(domain); // Ripristino del dominio
			problem.getCell(nextChild.getI(), nextChild.getJ()).setColor(Color.white);
			problem.fillBlackSolutions();	// Ripristino dei legami fa i vincoli
			problem.fillWhiteSolutions();	// Ripristino dei domini
			problem.clear();		
		}
		
		return null;
	}
}
