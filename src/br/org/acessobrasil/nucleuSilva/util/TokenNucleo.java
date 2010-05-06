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

package br.org.acessobrasil.nucleuSilva.util;

/**
 * Configura��es do motor antigo
 * @author Acessibilidade Brasil.
 */
final public class TokenNucleo {
    /**
     * login com o banco
     */
    final public static String LOGIN           = "sa";

    /**
     * senha com o banco
     */
    final public static String SENHA           = "";

    /**
     * expres�o para conex�o jdbc
     */
    final public static String CONECTOR        = "jdbc:hsqldb:hsql:";

    /**
     * url para localizar o banco
     */
    final public static String URL             = "//localhost/regras";

    /**
     * drive do banco
     */
    final public static String DRIVER          = "org.hsqldb.jdbcDriver";
    /**
     * representa��o do nome da tag de linha.
     */
    static public final String LINHA             = "Lin";

    /**
     * representa�� da tag de coluna.
     */
    static public final String COLUNA            = "Col";

    /**
     * representa���o da tag que ira envolver todas as outras tag�s.
     */
    static public final String MAE           = "ABC";

    /*
     * Deve ser populado pelo banco com o construtor, assim evita erro de
     * desenvolvimento
     */

        

    /**
     * lista das tags automaticas.
     */
    public static final int [] automaticos       = { 10, 100, 58, 33, 94, 5,
            82, 104, 105, 106, 107, 108, 109    };

    /**
     * defini��o de um atributo para o corretor por parametro.
     */
   // public static final int    PARAMETROATRIBUTO = 1;

    /**
     * defini��o de uma tag para o corretor por parametro.
     */
   // public static final int    PARAMETROTAG      = 2;

    /**
     * defini��o de que quem recebera o parametro � um atributo de dimens�o.
     */
   // public static final int    PARAMETRODIMENSAO = 3;

    /**
     * constante que define a busca a tag relacionada a tag avaliada.
     */
    public static final int    BUSCATAG          = 1;

    /**
     * constante que define que a tag deve existir.
     */
    public static final int    EXIST             = 2;

    /**
     * constante que define que a tag so pode ser validade de forma unica, assim
     * ele deve utilizar um codigo estruturado.
     */
    public static final int    HARDCODED         = 3;

    /**
     * constante que define a tag tem de existir.
     */
    public static final int    HAVER             = 4;

    /**
     * constante que define a busca a tag n�o pode existir no documento.
     */
    public static final int    NEXIT             = 5;

    /**
     * constante que define o texto estar� se repetindo.
     */
    public static final int    REPET             = 6;

    /**
     * constante que define a busca na tag de um atributo especifico.
     */
    public static final int    BUSCAATR          = 7;

    /**
     * constante que define a busca de uma tag filha ao seu contexto.
     */
    public static final int    TAGINTERNA        = 9;

    /**
     * constante que define o atributo n�o pode existir na tag.
     */
    public static final int    TAGN              = 10;

    /**
     * constante que define a obrigatoriedade nos eventos de m�o
     */
    public static final int    OBR               = 11;

    /**
     * constante que define que se o atributo existir ai sim se avalia
     */
    public static final int    ATRI              = 12;

    /**
     * constante que define que se a tag existir ent�o ela deve ser avaliada 
     */
    public static final int    INFO              = 13;

    /**
     * verificador se a tag deve ser corrigida automaticamente ou n�o
     * 
     * @param atribuido
     *            ponto de verifica��o buscado na lista de automaticos
     * @return booleano se existe ou n�o
     */
    public static boolean contem(int atribuido) {
   	
    	for (int i = 0; i < automaticos.length; i++)
            if (automaticos[i] == atribuido) return true;
        return false;

    }

}
