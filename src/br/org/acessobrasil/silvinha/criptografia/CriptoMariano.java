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

package br.org.acessobrasil.silvinha.criptografia;

import java.text.DecimalFormat;

/**
 * Classe que serve para criptografar informa&ccedil;&otilde;es de forma bem simples.
 * @author Mariano Aloi
 *
 * Construido em 31/08/2005
 *
 */
public class CriptoMariano {
    
    /**
     * M�todo que criptografa a String passada como par�metro
     * 
     * @param original
     * @return String
     */
    public static String criptografa(String original) {
        /*
         * pega o valor ascii de cada letra, multiplicar por 3, 
         * colocar no formato de 4 algarismos e colocar um numero de 1 a 9 randomico
         */
		
    	byte[] array = original.getBytes();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            byte b = array[i];
            int i1 = (int) (Math.random()*9);
            sb.append(i1+new DecimalFormat("0000").format(b*3));
        }
        return sb.toString();
    }
    /**
     * Apenas adapta ao criptografador de maior porte
     * @param original
     * @return String
     */
    public static String criptografa(int original) {
        return criptografa(String.valueOf(original));
    }
    
    /**
     * 
     * Descriptografa um String
     * @param cript
     * @return String
     */
    /*
     * M�todo que ao receber um texto avalia se o tamanho do texto est� em algum multiplo de 5
     * quebra o texto em grupos de 5
     * e le apenas os 4 ultimos algarismos
     * desses ultimos  se divide por 3 e se constroi o ascii do texto
     */
    public static String descriptografa(String cript){
        if(cript.length() % 5 != 0){
            new Throwable("String Errada");
            return null;
        }
        int p = 0;
        byte[] frase = new byte[cript.length()/5];
        while(cript.length() > p){
            String s = cript.substring(p+1, p+5
                    );
            int e = Integer.valueOf(s);
            int b = e/3;
            frase[p/5] = (byte) (b);           
            p +=5;
        }
        return new String(frase);
    }
}
