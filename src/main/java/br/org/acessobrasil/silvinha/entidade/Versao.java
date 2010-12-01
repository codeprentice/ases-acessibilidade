/*******************************************************************************
 * Copyright 2005, 2006, 2007, 2008 Acessibilidade Brasil
 * Este arquivo � parte do programa ASES - Avaliador e Simulador para AcessibilidadE de S�tios
 * O ASES � um software livre; voc� pode redistribui-lo e/ou modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como
 * publicada pela Funda��o do Software Livre (FSF); na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o posterior.
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; sem uma garantia implicita de ADEQUA��O a qualquer  MERCADO ou APLICA��O EM PARTICULAR. Veja a Licen�a P�blica Geral GNU para maiores detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU, sob o t�tulo "LICENCA.txt", junto com este programa, se n�o, escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *******************************************************************************/

/*******************************************************************************
 * Copyright (c) 2005, 2006, 2007 Acessibilidade Brasil.
 * 
 * This file is part of ASES.
 *
 * ASES is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * A copy of the license can be found at 
 * http://www.gnu.org/copyleft/lesser.txt.
 *******************************************************************************/

package br.org.acessobrasil.silvinha.entidade;
/**
 * Vers�o do software 
 *
 */
public class Versao implements Comparable {
	
	private int parte1;
	private int parte2;
	private int parte3;
	
	public Versao(String versao){
		
		
		String[] partes = versao.split("\\.");
		this.parte1 = Integer.parseInt(partes[0]);
		this.parte2 = Integer.parseInt(partes[1]);
		this.parte3 = Integer.parseInt(partes[2]);
	}
	
	public int compareTo(Object o) {
		if (o == null) {
			throw new NullPointerException();
		}
		Versao v = (Versao)o;
		if (this.parte1 < v.getParte1()) {
			return -1;
		} else if (this.parte1 > v.getParte1()) {
			return 1;
		}
		if (this.parte2 < v.getParte2()) {
			return -1;
		} else if (this.parte2 > v.getParte2()) {
			return 1;
		}
		if (this.parte3 < v.getParte3()) {
			return -1;
		} else if (this.parte3 > v.getParte3()) {
			return 1;
		}
		return 0;
	}
	
	
	public boolean equals(Object o) {
		Versao v = (Versao)o;
		if (this.parte1 == v.getParte1() &&
			this.parte2 == v.getParte2() &&
			this.parte3 == v.getParte3()) 
		{
			return true;
		} else 
		{
			return false;
		}
	}
	
	/**
	 * @return Returns the parte1.
	 */
	public int getParte1() {
		return parte1;
	}
	
	/**
	 * @param parte1 The parte1 to set.
	 */
	public void setParte1(int parte1) {
		this.parte1 = parte1;
	}
	
	/**
	 * @return Returns the parte2.
	 */
	public int getParte2() {
		return parte2;
	}
	
	/**
	 * @param parte2 The parte2 to set.
	 */
	public void setParte2(int parte2) {
		this.parte2 = parte2;
	}
	
	/**
	 * @return Returns the parte3.
	 */
	public int getParte3() {
		return parte3;
	}
	
	/**
	 * @param parte3 The parte3 to set.
	 */
	public void setParte3(int parte3) {
		this.parte3 = parte3;
	}
	
}
