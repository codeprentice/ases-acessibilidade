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

package br.org.acessobrasil.silvinha.vista.configs;

import java.awt.Color;
/**
 * Configura��es de cor da interface visual 
 *
 */
public class CoresDefault {
	
	//private static Color corPaineis = new Color(200, 200, 200);
	private static Color corPaineis = new Color(248, 251, 242);
	private static Color corListaTabela = new Color(250, 255, 255);
	private static Color corCodigoFonteSelecionado = new Color(051, 255, 000);
	//private static Color corStatusBar = new Color(255, 204, 51);
	private static Color corStatusBar = new Color(255, 255, 225);
	
	public static Color getCorPaineis() {
		return corPaineis;
	}
	public static Color getCorStatusBar() {
		return corPaineis;
	}
	
	public static Color getCorListaTabela() {
		return corListaTabela;
	}
	
	public static Color getCorCodigoFonteSelecionado() {
		return corCodigoFonteSelecionado;
	}
}
