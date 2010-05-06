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

/**
 * Classe que ir� verificar a veracidade do sistema em rela��o ao n�mero serial apresentado. 
 * @author Mariano Aloi Construido em 03/09/2005
 */
public class ValidarSerial {
    
    //Faixa de primos varidos para avalia��o
    private final int [] PRIMOS = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37,
            41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97 };
    //CNPJ apresentado
    private String       CNPJ;
    //Serial apresentado
    private String       serial;

    /**
     * M�todo principal para avalia��o do N�mero serial apresentado no formulario.
     * 
     * @param CNPJ
     * @param serial
     * @return Se o n�mero serial � valido ou n�o
     * @throws NumberFormatException
     */
    public boolean validar(String CNPJ, String serial)throws NumberFormatException {
    	
    	this.CNPJ = CNPJ;
        this.serial = serial;

        try{
        if (!verificarCNPJ())  return false; 
        if (!verificarPrimos()) return false;
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    /**
     * M�todo que retorna boolean, se baseando no fator do ultimos 4 algarismos do numero serial
     * 
     * @return boolean
     */
    private boolean verificarPrimos()throws NumberFormatException{
        int avaliado = 0;
        try {
            //pega os ultimos 4 algarismos do serial e os transforma em um inteiro valido
            avaliado = Integer.valueOf(serial.substring(serial.length() - 4));
        }
        catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
        int alg = 0;
        // avalia se o n�mero � divisivel apenas por 2 numeros primos
        for (int i = 0; i < PRIMOS.length;) {
            int primo = PRIMOS[i];
            if (avaliado % primo == 0) {
                alg++;
                avaliado /= primo;
            }
            else i++;
        }
        if (alg == 2) return true;
        else return false;
    }

    /**
     * M�todo que retorna se o cnpj � compativel com o serial apresentado
     * 
     * @return se o serial e valido.
     */
    private boolean verificarCNPJ() {
        if ((serial.substring(0, 4) + " " + serial.substring(14, 18))
                .equals(CNPJ.substring(6, 7) + CNPJ.substring(8, 11) + " "
                        + CNPJ.substring(0, 2)
                        + CNPJ.substring(CNPJ.length() - 2))) return true;
        else return false;
    }

}
