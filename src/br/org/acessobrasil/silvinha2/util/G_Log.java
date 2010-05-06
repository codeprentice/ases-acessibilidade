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

package br.org.acessobrasil.silvinha2.util;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe para criar logs 
 */
public class G_Log {
	/**
	 * vari�vel para gerar ou n�o o log
	 */
	private boolean isOn;
	private boolean systemPrint = true;
	private static boolean allOff=false;
	private String myFileName;
	public G_Log(String nomeArq){
		//Trocar para false para n�o gerar logs
		this.isOn=true;
		this.myFileName=nomeArq;
	}

	/**
	 * Adiciona um texto ao log
	 */
	public synchronized void addLog(String conteudo){
		if (!this.isOn || allOff){
			if(systemPrint){
				System.out.print(conteudo);
			}
			return;
		}
		try {
			Date startDate = new Date();
			SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			
			FileWriter arq = new FileWriter("log/"+this.myFileName,true);
			arq.append(dateFormat.format(startDate)+"\t"+conteudo);
			arq.close();
		} catch (IOException e) {
			/*
			 * Erro de IO
			 */
		}
		
	}
	
	/**
	 * Turn off<br>
	 * se desligado envia o print para o System.out 
	 */
	public void desliga(){
		this.isOn=false;
	}
	
	public void systemPrint(boolean valor){
		systemPrint=valor;
	}
	/**
	 * Wrapper de fun��o
	 */
	public void debug(String conteudo){
		this.addLog(conteudo+"\r\n");
	}
	
	/**
	 * Desliga todos os logs gravados em disco
	 * @param valor true para desligar os logs
	 */
	public static void desligaTodosOsLogs(boolean valor){
		allOff=valor;
	}
}
