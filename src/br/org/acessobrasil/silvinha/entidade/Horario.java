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

import java.text.DecimalFormat;
/**
 * Hor�rio no formato padr�o 
 *
 */
public class Horario {
	    int    id;
	    String hora;
	    /*
	     * (non-Javadoc)
	     *
	     * @see java.lang.Object#toString()
	     */
	    @Override
	    public String toString() {
	        return hora;
	    }
	    /**
	     * Construtor para a classe horario$silvaAcessivel.java
	     * @param num
	     */
	    public Horario(final int num) {
	    	//System.out.println("br.org.acessobrasil.silvinha.entidade.Horario.java");
	    
	    	this.id = num;
	        this.hora = new DecimalFormat("00").format(num) + ":00";
	    }
	    /**
	     * Implementa&ccedil;&atilde;o de encapsulamento da field hora que retorna o
	     * seu valor em String
	     * @return hora que &eacute; String.
	     */
	    public String getHora() {
	        return hora;
	    }

	    /**
	     * Implementa&ccedil;&atilde;o de encapsulamento da field hora que recebe o
	     * seu valor em String
	     * @param horario
	     *            &eacute; um String para ser populado.
	     */
	    public void setHora(final String horario) {
	        this.hora = horario;
	    }

	    /**
	     * Implementa&ccedil;&atilde;o de encapsulamento da field id que retorna o
	     * seu valor em int
	     *
	     * @return id que &eacute; int.
	     */
	    public int getId() {
	        return id;
	    }

	    /**
	     * Implementa&ccedil;&atilde;o de encapsulamento da field id que recebe o
	     * seu valor em int
	     *
	     * @param id &eacute; um int para ser populado.
	     */
	    public void setId(int id) {
	        this.id = id;
	    }
	    
}
