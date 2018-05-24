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


public class Solution {
	Set<Integer> values;

	public Set<Integer> getValues() {
		return values;
	}
	public Solution() {
	 values = new HashSet<Integer>();
	}
	public void addSolution(int solution){
		values.add(solution);
	}
	
	@Override
	public int hashCode(){
		return values.hashCode();
	}
	
	@Override
	public boolean equals(Object o){
		if (o == null) return false;
	    if (o == this) return true;
	    if (!(o instanceof Solution))return false;
	    Solution otherSolution = (Solution) o;
	   return this.values.equals(otherSolution.getValues());
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int solution : values){
			sb.append(solution);
			sb.append(",");
		}
		return sb.toString();
	}
}
