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

import java.sql.SQLException;
import java.sql.Statement;

import org.hsqldb.Server;
/**
 * Servidor do banco
 */
public class HSQLDB {
	
	/**
	 * Servidor do banco
	 */
	private static Server server;
	private static Statement st;
	private static final boolean usa = false;
	/**
	 * String de inicializa&ccedil;&atilde;o do Banco
	 */
	private static String comandoInicializador[] = 
	{"-database.0","basedados/regras", "-dbname.0", "regras",
	 "-database.1","basedados/links", "-dbname.1", "links", 
	 "-database.2","basedados/propriedades", "-dbname.2", "propriedades", 
	 "%1", "%2", "%3", "%4","%5", "%6", "%7", "%8", "%9"};
	
	/**
	 * Construtor
	 */
	public HSQLDB() {
		if(usa)
		server = new Server();	
	}
	
	public void iniciaBanco() {
		if(usa)
		Server.main(HSQLDB.comandoInicializador);
	}
	
	public static void finalizaBanco() {
		if(!usa) return;
		boolean arg0=true;
		System.out.println("estado: "+server.getState()+"check: "+arg0);
		server.shutdown();
		System.out.println("estado: "+server.getState()+"check: "+arg0);
		server.stop();
		System.out.println("estado: "+server.getState()+"check: "+arg0);
		try {
			st.executeUpdate("SHUTDOWN");
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		System.out.println("estado: "+server.getState()+"check: "+arg0);
	}

	public static void setSt(Statement st) {
		HSQLDB.st = st;
	}
}


