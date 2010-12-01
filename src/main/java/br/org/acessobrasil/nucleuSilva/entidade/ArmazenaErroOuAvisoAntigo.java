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

/**
 * Created on 06/03/2005
 */
package br.org.acessobrasil.nucleuSilva.entidade;


/**
 * Classe que representa o resumo da avalia��o de uma tag HTML.
 * @author Acessibilidade Brasil, em 22/08/2005. Refatorado em 04/01/2006.
 * @version 1.1
 */
public final class ArmazenaErroOuAvisoAntigo {
    /**
     * N�mero de identifica��o do Ponto de verifica��o utilizado.
     */
    private int pv3;

    /**
     * Espa�os da indenta��o da tag HTML avaliada.
     */
    private Espaco espaco;

    /**
     * Posi��o da tag dentro do arquivo pesquisado.
     */
    private Posicao posicao;

    /**
     * Nome da tag ou atributo procurado.
     */
    private String procurado;

    /**
     * String que representa a tag HTML avaliada.
     */
    private String tagCompleta;

    /**
     * Se para a sua avalia��o � exigida uma outra tag HTML.
     */
    private boolean tag;

    /**
     * Construtor especifico para tags se designar tags que devem existir dentro
     * do documento.
     * @param elemento Nome da tag ou atributo que se foi utilizado
     * para avaliar a tag HTML.
     * @param ptVf N�mero de identifica��o do Ponto de verifica��o utilizado.
     */
    public ArmazenaErroOuAvisoAntigo(final String elemento, final int ptVf) {
    	//System.out.print("Validado(elemento='"+elemento+"',ptVf="+ptVf+")\n");
    	this.pv3 = ptVf;
        this.procurado = elemento;
        this.posicao = new Posicao(0, 0);
    }

    /**
     * Construtor de Validado.
     * @param tagHtml Tag HTML avaliada.
     * @param chkPoint N�mero de identifica��o do
     * Ponto de verifica��o utilizado.
     * @param elemento Nome da tag ou atributo que se foi utilizado
     * para avaliar a tag HTML.
     */
    public ArmazenaErroOuAvisoAntigo(final boolean tagHtml,
            final int chkPoint, final String elemento) {
    	//System.out.print("Validado(tagHtml="+tagHtml+",chkPoint="+chkPoint+",elemento='"+elemento+"')\n");
    	this.pv3 = chkPoint;
        this.tag = tagHtml;
        this.procurado = elemento;
    }

    /**
     * Construtor Privado de Validado.
     */
    public ArmazenaErroOuAvisoAntigo() { }

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
     * @return Retorna o valor de posicao.
     */
    public Posicao getPosicao() {
        return posicao;
    }

    /**
     * @param coords Seta o valor de posicao.
     */
    public void setPosicao(final Posicao coords) {
        this.posicao = coords;
    }

    /**
     * @return Retorna o valor de procurado.
     */
    public String getProcurado() {
        return procurado;
    }

    /**
     * @param element Seta o valor de procurado.
     */
    public void setProcurado(final String element) {
    	//System.out.print("setProcurado(final String element="+element+")\n");
        this.procurado = element;
    }

    /**
     * @return Retorna o valor de pv3.
     */
    public int getPv3() {
        return pv3;
    }

    /**
     * @param chkPoint Seta o valor de pv3.
     */
    public void setPv3(final int chkPoint) {
        this.pv3 = chkPoint;
    }

    /**
     * @return Retorna o valor de tag.
     */
    public boolean isTag() {
        return tag;
    }

    /**
     * @param isRequired Seta o valor de tag.
     */
    public void setTag(final boolean isRequired) {
        this.tag = isRequired;
    }

    /**
     * @return Retorna o valor de tagCompleta.
     */
    public String getTagCompleta() {
        return tagCompleta;
    }

    /**
     * @param tagFull Seta o valor de tagCompleta.
     */
    public void setTagCompleta(final String tagFull) {
    	//System.out.print("setTagCompleta(final String tagFull="+tagFull+")\n");
        this.tagCompleta = tagFull;
    }  
}
