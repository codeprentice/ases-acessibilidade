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

package br.org.acessobrasil.nucleuSilva.regras;

import java.util.ArrayList;
import java.util.Iterator;

import br.org.acessobrasil.nucleuSilva.entidade.Atributo;
import br.org.acessobrasil.nucleuSilva.entidade.Regra;

/**
 * Implementa��o da regra de avalia��o de uma tag no
 * qual ele busca:
 * <ol>
 * <li>Exist&ecirc;ncia ou n�o do atributo </li>
 * <li>Array de valores que podem existir no atributo. </li>
 * <li>Array de valores que n�o podem existir no atributo. </li>
 * <li>Tamanho m�nimo do atributo. </li>
 * <li>Tamanho m�ximo do atributo. </li>
 * </ol>
 * 
 * @author Acessibilidade Brasil.
 */
public class BuscaAtr {

    /**
     * Avalia��o do atributo sobre a vis�o apresentada em banco.
     * @param regra conjunto de regras para a tag.
     * @param valor conte�do de um atributo.
     * @return verdadeiro se ele n�o tiver nenhum problema com os par�metros
     * oferecidos.
     */
    private boolean confere(final Regra regra, final String valor) {
        /*
         * Valor sim.
         */
        final String sim = regra.getVs();
        /*
         *  Valor n�o.
         */
        final String nao = regra.getVn();
        /*
         * Valor m�nimo.
         */
        final int minimo = regra.getTi();
        /*
         * Valor m�ximo.
         */
        final int maximo = regra.getTx();

        if (maximo != 0 && valor.length() > maximo) {
            return false;
        }

        if (minimo != 0 && valor.length() < minimo) {
            return false;
        }

        if (sim != null && !"".equals(sim)) {
            String [] array = sim.split(";");
            boolean bool = true;
            for (int i = 0; i < array.length; i++) {
                String string = array[i];
                if (valor.toUpperCase().indexOf(string.toUpperCase()) >= 0) {
                    bool = false;
                }
            }

            if (bool) {
                return false;
            }
        }

        if (nao != null && !"".equals(nao)) {
            String [] array = nao.split(";");
            for (int i = 0; i < array.length; i++) {
                String string = array[i];
                if (valor.toUpperCase().indexOf(string.toUpperCase()) >= 0) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Iterador de atributos para que possam ser avaliados. ele tem o objetivo
     * de afirmar se o atributo � incorreto, mas ela tenta sempre afirmar que a
     * tag � incorreta.
     * @param regra para avalia��o do atributo.
     * @param atributos atributos da tag.
     * @return falso se o conjunto de tags n�o contiver erros.
     */
    public boolean validacao(final Regra regra, final ArrayList<Atributo> atributos) {
   	
        boolean bol = true;
     
        for (Atributo atr : atributos) {
        	if (regra.getProcurado().equalsIgnoreCase(atr.getTipo())) {
                bol = !confere(regra, atr.getConteudo());
            }
        }

        return bol;
    }

    /**
     * Iterador de atributos para que possam ser avaliados. ele tem o objetivo
     * de afirmar se o atributo � incorreto, mas ela tenta sempre afirmar que a
     * tag � correta.
     * @param regra para avalia��o do atributo.
     * @param atributos atributos da tag.
     * @return falso se o conjunto de tags n�o contiver erros.
     */
    public boolean validacaoIncertiva(final Regra regra,
            final ArrayList<Atributo> atributos) {

        boolean bol = false;
        Iterator<Atributo> iatributo = atributos.iterator();
        Atributo atrib = null;
        while (iatributo.hasNext()) {
            atrib = iatributo.next();
            if (regra.getProcurado().equalsIgnoreCase(atrib.getTipo())) {
                bol = true;
                bol = !confere(regra, atrib.getConteudo());
            }
        }

        return bol;
    }

}
