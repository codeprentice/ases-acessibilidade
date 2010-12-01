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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe que prepara o conte�do da p�gina HTML para o processo de
 * localiza��o e posicionamento das tags (tagar).
 * @author Acessibilidade Brasil.
 */
public class PrepararConteudo {
    /**
     * Construtor de PrepararConteudo.
     */
    private PrepararConteudo() { }

    /**
     * M�todo que substitui o conte�do das tags SCRIPTS e STYLE, ou coment�rios
     * HTML por espa�os, mas mantendo as quebras de linha.
     * @param content Conte�do da p�gina.
     * @return
     */
    public static StringBuilder ignorarConteudo(final StringBuilder content) {
        String string = ignorarComentarios(ignorarConteudoTags(content.toString()));
        return new StringBuilder(string);
    }

    /**
     * M�todo que substitui coment�rios HTML por espa�os,
     * mas mantendo as quebras de linha.
     * @param content Conte�do da p�gina.
     * @return Conte�do da p�gina sem coment�rios.
     */
    public static String ignorarComentarios(final String content) {
        String pattern = "<!--(.*?)-->";
        final Pattern ptn = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher mtc = ptn.matcher(content);

        String aux = content.toString();

        while (mtc.find()) {
            aux = aux.replace(mtc.group(), trocarCaracteres(mtc.group()));
        }

        return aux;
    }

    /**
     * M�todo que atualiza o conte�do das tags por espa�os.
     * @param string Conte�do da p�gina.
     * @return Conte�do da p�gina sem conte�do entre as tags SCRIPT e STYLE.
     */
    public static String ignorarConteudoTags(final String string) {
      	
    	String pattern = "(<SCRIPT(.*?)</SCRIPT>|<STYLE(.*?)</STYLE>)";
        final Pattern ptn = Pattern.compile(pattern,
                Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        final Matcher mtc = ptn.matcher(string);

        String content = string;
        String aux = null;
        String startTag = "", meio = "", endTag = "";
        int pos1 = 0, pos2 = 0;

        while (mtc.find()) {
            aux = mtc.group();

            pos1 = aux.indexOf(">") + 1;
            pos2 = aux.lastIndexOf("<");

            startTag = aux.substring(0, pos1);
            meio = trocarCaracteres(aux.substring(pos1, pos2));
            endTag = aux.substring(pos2, aux.length());

            content = content.replace(mtc.group(), startTag + meio + endTag);
        }

        return content;
    }

    /**
     * M�todo que substitu� os caracteres da string por espa�o, com exce��o
     * da quebra de linha.
     * @param string String a ser substitu�do.
     * @return String preenchida por espa�os.
     */
    private static String trocarCaracteres(final String string) {
        char[] caracter = string.toCharArray();
        String aux = "";
        for (char pos : caracter) {
            aux += (pos != '\n') ? " " : pos;
        }
        return aux;
    }
}
