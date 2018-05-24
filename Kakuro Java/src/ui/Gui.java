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
package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import persistence.ProblemReader;
import model.Problem;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class Gui extends JFrame {
	private JTable table;

	/**
	 * Create the frame.
	 */
	

	public Gui(Problem problem) {
		setResizable(false);
		setTitle("Kakuro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		getContentPane().add(panel, BorderLayout.CENTER);
		
		table = new JTable();
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		Object [][] model = new Object[problem.getnRows()][problem.getnColumns()];
		String[] names = new String[problem.getnRows()];
		boolean[] columnEditables = new boolean[problem.getnColumns()];
		for(int i = 0; i < problem.getnRows(); i++) {
			names[i] = "New column";
			for(int j = 0; j < problem.getnColumns(); j++) {
				model[i][j] = problem.getCell(i, j);
				columnEditables[j] = true;
			}
		}
		
		table.setModel(new DefaultTableModel(model, names));
			/*new Object[][] {
				
				MADONNA ADDOLORATA
				
				{problem.getCell(0, 0), problem.getCell(0, 1), problem.getCell(0, 2), problem.getCell(0, 3), problem.getCell(0, 4)},
				{problem.getCell(1, 0), problem.getCell(1, 1), problem.getCell(1, 2), problem.getCell(1, 3), problem.getCell(1, 4)},
				{problem.getCell(2, 0), problem.getCell(2, 1), problem.getCell(2, 2), problem.getCell(2, 3), problem.getCell(2, 4)},
				{problem.getCell(3, 0), problem.getCell(3, 1), problem.getCell(3, 2), problem.getCell(3, 3), problem.getCell(3, 4)},
				{problem.getCell(4, 0), problem.getCell(4, 1), problem.getCell(4, 2), problem.getCell(4, 3), problem.getCell(4, 4)},
				
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column"
			} 
		)  {
			boolean[] columnEditables = new boolean[] {
				true, true, true, true, true
			};
			
				columnEditables
			);
			
			*/
		table.getColumnModel().getColumn(0).setResizable(true);
		table.setRowHeight(50);
	//	for(int i=0; i<table.getRowCount(); i++)
		//	for(int j=0; j<table.getColumnCount(); j++) {
	//			table.getCellEditor(i, j).
				//table.selectionBackground(problem.getCell(0, 0).getColor());
		//	}	
		panel.add(table);
		this.setLocation(400,200);
		this.pack();
		
	}
	
	public void update() {
		table.updateUI();
	}

}
