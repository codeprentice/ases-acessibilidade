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

package br.org.acessobrasil.ases.entidade;

/**
 * @author Mariano Aloi  Construido em 22/08/2005
 * alterado por Renato Tomaz Nati em 10/8/2007
 */
public final class Tag {
	
    /**
     * Posi��o da tag HTML na p�gina avaliada.
     */
    private int linha;
    
    private int coluna;
    
	private Posicao p;
    /**
     * Texto da tag HTML.
     * 
     */
    private String tagInteira;
   
  
    /**
     * Espa�os ou tabula��es, utilizadas para formatar a tag dentro da
     * linha na p�gina avaliada.
     */
    private Espaco espaco;
    /**
     * ponto de verifica��o 
     */
    private String idRegra;

    /**
     * Construtor de Tag.
     * @param linha local do erro
     * @param coluna local do erro
     * @param text texto do erro
     * @param tabs numero de tags
     * @param espaco n�mero de espa�os
     */
    public Tag(int linha, int coluna,
            final String text, int tabs, int espaco) {
    	idRegra="";
    	this.p.setLinha(linha);
    	this.p.setColuna(coluna);
    	this.linha = linha;
    	this.coluna = coluna;
    	this.tagInteira = text;
        this.espaco.setEspacoLinha(espaco);
        this.espaco.setTabulacaoLinha(tabs);
    }

  

    
    /**
     * Construtor de Tag.
     * @param position Posi��o da tag HTML na p�gina avaliada.
     * @param text Texto da tag HTML.
     * @param espacos Dados referentes a espa�amento.
     * @param chkPoint N�mero de identifica��o do
     * Ponto de verifica��o utilizado.
     */
   

    /**
     * @return Retorna o valor de espaco.
     */
    public Espaco getEspaco() {
        return espaco;
    }

    /**
     * @param espacos Seta o valor de espaco.
     */
    public void setEspaco(final Espaco espacos) {
        this.espaco = espacos;
    }

    /**
     * @return Retorna o valor de p.
     */
    public Posicao getP() {
        return p;
    }

    /**
     * @param coords Seta o valor de p.
     */
    public void setP(final Posicao coords) {
        this.p = coords;
    }

  

    /**
     * @return Retorna o valor de texto.
     */
    public String getTagInteira() {
        return tagInteira;
    }

    /**
     * @param text Seta o valor de texto.
     */
    public void setTagInteira(final String text) {
        this.tagInteira = text;
    }




	public String getIdRegra() {
		return idRegra;
	}




	public void setIdRegra(String idRegra) {
		this.idRegra = idRegra;
	}




	public int getColuna() {
		return coluna;
	}




	public void setColuna(int coluna) {
		this.coluna = coluna;
	}




	public int getLinha() {
		return linha;
	}




	public void setLinha(int linha) {
		this.linha = linha;
	}

}
