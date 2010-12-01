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

package br.org.acessobrasil.silvinha.negocio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import br.org.acessobrasil.nucleuSilva.util.Conexao;
import br.org.acessobrasil.silvinha.entidade.PontoVerificacao;
import br.org.acessobrasil.silvinha.excessoes.ExceptionDialog;
import br.org.acessobrasil.silvinha.util.lang.TokenLang;
import br.org.acessobrasil.silvinha.vista.panels.PainelAvaliacao;
/**
 * Gera os pontos de verifica��o
 * aqui ocorre a conex�o com o banco de dados
 */
public class PontosDeVerificacaoFactory {
	
	private static Logger log = Logger.getLogger("br.org.acessibrasil.silvinha");
	
    /**
     * Conjunto de Pontos de Verifica&ccedil;&atilde; que ser&atilde;o utilizados para 
     * avalia&ccedil;&atilde;o dos erros no conte&uacute;do das p&aacute;ginas de acordo
     * com as regras de Acessibilidade do E-GOV. 
     */
    private static HashMap<Integer, PontoVerificacao> pontosVerificacaoEgov;
    
    /**
     * Conjunto de Pontos de Verifica&ccedil;&atilde; que ser&atilde;o utilizados para 
     * avalia&ccedil;&atilde;o dos erros no conte&uacute;do das p&aacute;ginas de acordo
     * com as regras de Acessibilidade do E-GOV. 
     */
    private static HashMap<Integer, PontoVerificacao> avisosGenericosEgov;

    /**
     * Conjunto de Pontos de Verifica&ccedil;&atilde; que ser&atilde;o utilizados para 
     * avalia&ccedil;&atilde;o dos avisos gen�ricos no conte&uacute;do das p&aacute;ginas de acordo
     * com as regras de Acessibilidade do WCAG. 
     */
    private static HashMap<Integer, PontoVerificacao> pontosVerificacaoWcag;
    
    /**
     * Conjunto de Pontos de Verifica&ccedil;&atilde; que ser&atilde;o utilizados para 
     * avalia&ccedil;&atilde;o dos avisos gen�ricos no conte&uacute;do das p&aacute;ginas de acordo
     * com as regras de Acessibilidade do WCAG. 
     */
    private static HashMap<Integer, PontoVerificacao> avisosGenericosWcag;
    
    
    static 
    {
    	pontosVerificacaoEgov = new HashMap<Integer, PontoVerificacao>();
        avisosGenericosEgov = new HashMap<Integer, PontoVerificacao>();
        pontosVerificacaoWcag = new HashMap<Integer, PontoVerificacao>();
        avisosGenericosWcag = new HashMap<Integer, PontoVerificacao>();
        inicializaPontosVerificacaoErros();
    }
    
    private PontosDeVerificacaoFactory(){}
    
    public static HashMap<Integer, PontoVerificacao> getPontosDeVerificacao(int tipoAvaliacao)
    {
    	
    	switch (tipoAvaliacao)
    	{
    	case PainelAvaliacao.EMAG:
    	{
    		return pontosVerificacaoEgov;
    	}
    	case PainelAvaliacao.WCAG:
    	{
    		return pontosVerificacaoWcag;
    	}
    	default:
    	{
    		return pontosVerificacaoWcag;
    	}
    	}
    }
    
    public static HashMap<Integer, PontoVerificacao> getAvisosGenericos(int tipoAvaliacao)
    {
    	switch (tipoAvaliacao)
    	{
    	case PainelAvaliacao.EMAG:
    	{
    		return avisosGenericosEgov;
    	}
    	case PainelAvaliacao.WCAG:
    	{
    		return avisosGenericosWcag;
    	}
    	default:
    	{
    		return avisosGenericosWcag;
    	}
    	}
    }

    
    
    /**
     * M&eacute;todo respons&aacute;vel por inicializar o atributo <code>pontosVerificacao</code> 
     * a partir de uma consulta ao Banco de Dados.
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static void inicializaPontosVerificacaoErros() 
    {
    	
    	
    	try 
    	{
    		final String sqlWCAG = "SELECT V.PV3, TA.GL, TA.CP, TA.PRIORIDADE, R.IDREGRA, " +
    		"TA.EXIGENCIA, R.DESCRICAO_REGRA FROM VALIDANTE V " + 
    		"INNER JOIN TIPOAVALIACAOERRO TA ON TA.PV3 = V.PV3 " + 
    		"INNER JOIN REGRA R ON V.REGRA = R.IDREGRA " + 
    		"WHERE TA.IDORGAO = 1";
    		final String sqlEGOV = "SELECT V.PV3, TA.GL, TA.CP, TA.PRIORIDADE, R.IDREGRA, " +
    		"TA.EXIGENCIA, R.DESCRICAO_REGRA FROM VALIDANTE V " + 
    		"INNER JOIN TIPOAVALIACAOERRO TA ON TA.PV3 = V.PV3 " + 
    		"INNER JOIN REGRA R ON V.REGRA = R.IDREGRA " + 
    		"WHERE TA.IDORGAO = 2";
    		Conexao con = new Conexao();
    		Statement st = con.getCon().createStatement();
    		//inicializa��o WCAG
    		ResultSet rs = st.executeQuery(sqlWCAG);
    		PontoVerificacao pve = null;
    		while (rs.next()) {
    			pve = new PontoVerificacao();
    			pve.setCp(rs.getInt("cp"));
    			pve.setGl(rs.getInt("gl"));
    			pve.setPrioridade(rs.getInt("PRIORIDADE"));
    			pve.setIdRegra(rs.getInt("IDREGRA"));
    			pve.setExigencia(rs.getString("EXIGENCIA").charAt(0));
    			pve.setLinhas(new ArrayList<Integer>());
    			if (pve.getExigencia() == 'g') {
    				avisosGenericosWcag.put(rs.getInt("pv3"), pve);
    			} else {
    				pontosVerificacaoWcag.put(rs.getInt("pv3"), pve);
    			}
    		}
    		rs.close();
    		//inicializa��o EGOV
    		rs = st.executeQuery(sqlEGOV);
    		pve = null;
    		while (rs.next()) {
    			pve = new PontoVerificacao();
    			pve.setCp(rs.getInt("cp"));
    			pve.setGl(rs.getInt("gl"));
    			pve.setPrioridade(rs.getInt("PRIORIDADE"));
    			pve.setIdRegra(rs.getInt("IDREGRA"));
    			pve.setExigencia(rs.getString("EXIGENCIA").charAt(0));
    			pve.setLinhas(new ArrayList<Integer>());
    			if (pve.getExigencia() == 'g') {
    				avisosGenericosEgov.put(rs.getInt("pv3"), pve);
    			} else {
    				pontosVerificacaoEgov.put(rs.getInt("pv3"), pve);
    			}
    		}
    		rs.close();
    		///fim da inicializa��o
    		st.close();
    		con.close();
    	} 
    	catch (Exception e)
    	{
        	ExceptionDialog.showExceptionDialog(TokenLang.ERRO_1);
        	log.fatal(e);
    	}
    }


}
