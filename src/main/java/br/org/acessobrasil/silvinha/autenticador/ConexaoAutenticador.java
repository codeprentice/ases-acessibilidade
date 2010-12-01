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

package br.org.acessobrasil.silvinha.autenticador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * conex�o para propriedades
 *
 * Construido em 20/08/2005
 *
 */
public class ConexaoAutenticador {
    
    Connection con;
//    private static String[] init = {"-database.3","basedados/users", "-dbname.3", "users",
//        							"%1", "%2", "%3", "%4","%5", "%6", "%7", "%8", "%9"  };
    /*
    static {
    	Server hsqlServer = new Server();
    	hsqlServer.main(init);
    }
    */
    /**Construtor para a classe Conexao.java
     * @throws ClassNotFoundException 
     * @throws SQLException 
     * 
     */
    public ConexaoAutenticador() throws ClassNotFoundException, SQLException {
   
    	Class.forName("org.hsqldb.jdbcDriver");// instancia o driver
        con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/users", "sa", ""); // conecta
    }
    public Connection getCon(){        
        return con;
    }
   
}
