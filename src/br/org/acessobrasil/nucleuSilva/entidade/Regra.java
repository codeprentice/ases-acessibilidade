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

package br.org.acessobrasil.nucleuSilva.entidade;


/**
 * Classe que representa uma regra ou recomenda��o de acessibilidade.
 * @author Acessibilidade Brasil, em 22/08/2005. Refatorado em 04/01/2006.
 * @version 1.1
 */
public final class Regra {
    /**
     * N�mero de identifica��o do Ponto de verifica��o utilizado.
     */
    private int pv3;

    /**
     * Atitude ou comportamento necess�rio para a tag ser avaliada.
     */
    private int atitude;

    /**
     * Tamanho m�nimo que o valor de um atrbuto deve ter.
     */
    private int ti;

    /**
     * Tamanho m�ximo que o valor de um atrbuto deve ter.
     */
    private int tx;

    /**
     * Valida��o hierarquica.
     */
    private int hierarquia;

    /**
     * Se existe existe alguma depend�ncia.
     */
    private int dep;

    /**
     * Nome da tag HTML ou atributo necess�rios para a avalia��o.
     */
    private String procurado;

    /**
     * Valor que o atributo deve ter.
     */
    private String vs;

    /**
     * valor que o atributo n�o pode ter.
     */
    private String vn;

    /**
     * @see java.lang.Object#toString()
     * @return string contendo Procurado + VS + VN + PV3.
     */
    @Override public String toString() {
        return this.procurado + " " + this.vs + " " + this.vn + " " + this.pv3;
    }

    /**
     * @return Retorna o valor de atitude.
     */
    public int getAtitude() {
   	
    	return atitude;
    }

    /**
     * @param atitudeID Seta o valor de atitude.
     */
    public void setAtitude(final int atitudeID) {
    	 
    	this.atitude = atitudeID;
    }

    /**
     * @return Retorna o valor de dep.
     */
    public int getDep() {
        return dep;
    }

    /**
     * @param depend Seta o valor de dep.
     */
    public void setDep(final int depend) {
        this.dep = depend;
    }

    /**
     * @return Retorna o valor de hierarquia.
     */
    public int getHierarquia() {
        return hierarquia;
    }

    /**
     * @param hierarq Seta o valor de hierarquia.
     */
    public void setHierarquia(final int hierarq) {
        this.hierarquia = hierarq;
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
     * @return Retorna o valor de ti.
     */
    public int getTi() {
        return ti;
    }

    /**
     * @param tamMin Seta o valor de ti.
     */
    public void setTi(final int tamMin) {
        this.ti = tamMin;
    }

    /**
     * @return Retorna o valor de tx.
     */
    public int getTx() {
        return tx;
    }

    /**
     * @param tamMax Seta o valor de tx.
     */
    public void setTx(final int tamMax) {
        this.tx = tamMax;
    }

    /**
     * @return Retorna o valor de vn.
     */
    public String getVn() {
        return vn;
    }

    /**
     * @param valueNo Seta o valor de vn.
     */
    public void setVn(final String valueNo) {
        this.vn = valueNo;
    }

    /**
     * @return Retorna o valor de vs.
     */
    public String getVs() {
        return vs;
    }

    /**
     * @param valueYes Seta o valor de vs.
     */
    public void setVs(final String valueYes) {
        this.vs = valueYes;
    }
}
