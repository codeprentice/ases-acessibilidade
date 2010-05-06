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

package br.org.acessobrasil.silvinha.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import br.org.acessobrasil.silvinha.autenticador.ConexaoAutenticador;
import br.org.acessobrasil.silvinha.criptografia.CriptoMariano;
import br.org.acessobrasil.silvinha.entidade.DadosEmpresa;
import br.org.acessobrasil.silvinha.util.HSQLDB;
/**
 * Implementa��o 
 *
 */
public class DadosEmpresaDAOHSSQLImpl implements DadosEmpresaDAO {
	
	private static Logger log = Logger.getLogger("br.org.acessobrasil.silvinha");
    private ConexaoAutenticador con;
    private Statement st;
    private String sql;
    
    public DadosEmpresaDAOHSSQLImpl() {
    	
    	sql = new String();
        try {
            con = new ConexaoAutenticador();
            st = con.getCon().createStatement(); 
           
        } catch (SQLException sqle) {
        	log.error(sqle.getMessage(), sqle);
        } catch (ClassNotFoundException cnfe) {
            log.error(cnfe.getMessage(), cnfe);
        }
        HSQLDB.setSt(st);
    }

    /**
     * @see br.org.acessobrasil.silvinha.persistencia.DadosEmpresaDAO#inserir(
     * br.org.acessobrasil.silvinha.entidade.DadosEmpresa)
     */
    public boolean inserir (DadosEmpresa dados) throws SQLException, ClassNotFoundException {
        sql = "INSERT INTO t1 (t1,t2,t3,t4,t5,t6,t7) values ('"
            + CriptoMariano.criptografa(dados.getCnpj()) + "','"
            + CriptoMariano.criptografa(dados.getUrl()) + "','"
            + CriptoMariano.criptografa(dados.getEmail()) + "','"
            + CriptoMariano.criptografa(dados.getNome()) + "','"
            + CriptoMariano.criptografa(dados.getEndereco()) + "','" 
            + CriptoMariano.criptografa(dados.getSerial()) + "','"
            + CriptoMariano.criptografa(dados.getTelefone()) + "')";
        int resultado = st.executeUpdate(sql);
        st.close();
        con.getCon().close();
        if (resultado > 0) {
            return true;
        }
        return false;
    }

    /**
     * @see br.org.acessobrasil.silvinha.persistencia.DadosEmpresaDAO#consultar()
     */
    public DadosEmpresa consultar() {
        try {
            sql = "SELECT * FROM t1;";
            ResultSet rs = st.executeQuery(sql);
            return processaRs(rs);
        } catch (SQLException sqle) {
        	log.error(sqle.getMessage(), sqle);
            return null;
        }
    }

    /**
     * 
     * @param rs
     * @return
     * @throws SQLException
     */
    private DadosEmpresa processaRs(ResultSet rs) throws SQLException {
        DadosEmpresa de = new DadosEmpresa();
        if (rs.next()) {
            de.setCnpj(CriptoMariano.descriptografa(rs.getString(1)));
            de.setUrl(CriptoMariano.descriptografa(rs.getString(2)));
            de.setEmail(CriptoMariano.descriptografa(rs.getString(3)));
            de.setNome(CriptoMariano.descriptografa(rs.getString(4)));
            de.setEndereco(CriptoMariano.descriptografa(rs.getString(5))); 
            de.setSerial(CriptoMariano.descriptografa(rs.getString(6)));
            de.setTelefone(CriptoMariano.descriptografa(rs.getString(7)));
        }
        return de;
    }
}
