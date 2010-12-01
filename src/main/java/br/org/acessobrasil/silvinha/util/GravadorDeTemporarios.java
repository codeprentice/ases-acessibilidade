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

package br.org.acessobrasil.silvinha.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import br.org.acessobrasil.silvinha.entidade.RelatorioDaUrl;
import br.org.acessobrasil.silvinha.entidade.ResumoDoRelatorio;
import br.org.acessobrasil.silvinha.excessoes.ExceptionDialog;
import br.org.acessobrasil.silvinha.negocio.Gerente;
import br.org.acessobrasil.silvinha.vista.listeners.ExecutarAgoraListenerLocal;
import br.org.acessobrasil.silvinha.vista.listeners.ExecutarAgoraListenerWeb;
/**
 * Grava os arquivos tempor�rios 
 *
 */
public class GravadorDeTemporarios {

	public GravadorDeTemporarios() {
	}

	public static boolean gravarRelatorios(String fileName,
			ArrayList<File> arquivos) {

		// Create a buffer for reading the files
		byte[] buf = new byte[1024];
		try {
			// Create the ZIP file

			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					fileName));
			// Compress the files
			for (File arquivo : arquivos) {
				FileInputStream in = new FileInputStream(arquivo);
				// Add ZIP entry to output stream.
				out.putNextEntry(new ZipEntry(arquivo.getName()));
				// Transfer bytes from the file to the ZIP file
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				// Complete the entry
				out.closeEntry();
				in.close();
				arquivo.deleteOnExit();
			}
			// Complete the ZIP file
			out.close();
			return true;
		} catch (IOException e) {
			ExceptionDialog.showExceptionDialog("[Zipper]: " + e.getMessage());
			return false;
		}
	}

	public static void gravarTemp(RelatorioDaUrl relatorio) {
		ArrayList<RelatorioDaUrl> relatorios = new ArrayList<RelatorioDaUrl>();
		Properties p = new Properties();
		relatorios.add(relatorio);
		if (Gerente.profundidade > 5) {
			p = ExecutarAgoraListenerLocal.painelAvaliacao.verificaOpcoes();
		}
		if (Gerente.profundidade <= 5) {
			p = ExecutarAgoraListenerWeb.painelAvaliacao.verificaOpcoes();
		}
		ResumoDoRelatorio rDR = new ResumoDoRelatorio(relatorios, p);
		gravarRelatorios("temp/" + relatorio.hashCode(), rDR
				.gerarRelatorioTemporarios(relatorio));
		relatorios.clear();
	}

}
