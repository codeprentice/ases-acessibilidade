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

package br.org.acessobrasil.nucleuSilva.negocio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.org.acessobrasil.nucleuSilva.entidade.Atributo;
import br.org.acessobrasil.nucleuSilva.entidade.Hierarquia;
import br.org.acessobrasil.nucleuSilva.entidade.Regra;
import br.org.acessobrasil.nucleuSilva.entidade.ArmazenaErroOuAvisoAntigo;
import br.org.acessobrasil.nucleuSilva.regras.BuscaAtr;
import br.org.acessobrasil.nucleuSilva.util.Conexao;
import br.org.acessobrasil.nucleuSilva.util.TokenNucleo;
import br.org.acessobrasil.silvinha.util.lang.TokenLang;


/**
 * Classe que avalia cada tag HTML individualmente.
 * @author Acessibilidade Brasil, em 22/08/2005.
 */
public final class Validar {
	
	private static Logger log = Logger.getLogger("br.org.acessobrasil.silvinha");
    /**
     * Como as regras est&atilde;o no banco nada melhor que abrir uma
     * conex&atilde;o, e para n&atilde;o ficar abrindo e fechando sucessivamente
     * a conex&atilde;o e aberta e fechada no m&eacute;todo chamador.
     */
    private Conexao conexaoBanco;

    /**
     * Atributos da tag.
     */
    private ArrayList<Atributo> listaAtributos;
    
    private HashMap<String, ArrayList<Regra>> mapaRegras;
    private HashMap<Integer, ArrayList<Regra>> mapaDependencias;
    private PreparedStatement pstBuscaRegras;
    /**
     * Pega as regras da Tag de um determinado orgao ou de eventos e deste orgao.
     * Os Eventos s�o do idnome 21 a 26
     * ONCLICK'
     * ONMOUSEDOWN'
     * ONMOUSEMOVE'
     * ONMOUSEOUT'
     * ONMOUSEOVER'
     * ONMOUSEUP'		
     */
    private final String sqlBuscaRegras = " SELECT e.idatitude, v.pv3, e.tammin, e.tammax, n.nome, "
        				 		    	       + " e.valor_sim, e.valor_nao, v.H, e.Dep "
        				 		    	       
        				 		    	       
        				 		    	       + " FROM tag t INNER JOIN validante v ON (t.idtag=v.idtag) "
        				 		    	       + " INNER JOIN esperado e ON (e.idesperado = v.idesperado) "
        				 		    	       + " INNER JOIN nome n ON (n.idnome=e.idnome)  "
        				 		    	       + " INNER JOIN tipoavaliacaoerro ta ON (ta.pv3 = v.pv3) "
        				 		    	       + " WHERE (NOME_TAG = ? "
        				 		    	       + " AND ta.IDORGAO = ?) " 
        				 		    	       + " OR (e.idatitude = 11 "
        				 		    	       + " AND ta.IDORGAO = ?) ";
 

    private PreparedStatement pstBuscaDependencias;
    private final String sqlBuscaDependencias = "SELECT e.idatitude, 0 as pv3, "
        			                                 + " e.tammin,  e.tammax, "
        			                                 + " n.nome,  e.valor_sim,  e.valor_nao,  0 as  H, e.Dep "
        			                                 + " FROM esperado e INNER JOIN nome n "
        			                                 + " ON (n.idnome = e.idnome) WHERE e.idesperado = ?";

    /**
     * Tipo de avalia��o: wcag e emag.
     */
    private int orgao;
 
    
    public Validar(final Conexao conexao, final int tpAvaliacao) 
    throws SQLException {
    	
    	this.conexaoBanco = conexao;
		this.orgao = tpAvaliacao;
		this.pstBuscaRegras = conexaoBanco.getCon().prepareStatement(sqlBuscaRegras);
		this.pstBuscaDependencias = conexaoBanco.getCon().prepareStatement(sqlBuscaDependencias);
		this.mapaRegras = new HashMap<String, ArrayList<Regra>>();
		this.mapaDependencias = new HashMap<Integer, ArrayList<Regra>>();
    }
    
    /**
     * Respons�vel pela comunica��o com as outras classes
     * ele popula algumas vari�veis globais e tamb�m repassa o
     * trabalho para outro m�todo.
     * @param node N� a ser avaliado.
     * @param ConexaoBanco Conexao com o banco de dados.
     * @param tpAvaliacao tipo de avalia��o.
     * @return a lista de erros da tag.
     * @throws SQLException Erro no banco de dados ou na instru��o SQL.
     */
    public ArrayList<ArmazenaErroOuAvisoAntigo> validar(final Node node) 
    throws SQLException, Exception
    {
        return distrinchar(node);
    }


    /**
     * C�digo para avalia��o da tag, ela pega um conjunto
     * de tags e as submete a uma regra especifica. <br/> Existem 11
     * avalia��es sendo a 8 restrita ent�o na contagem ser�o 12 menos a 8.
     * <ol>
     * <li>BUSCA TAG � Busca uma tag por causa dessa tag.(script x noscript); </li>
     * <li>EXIST � A tag deve existir; </li>
     * <li>HARD CODE � S� pode ser implementado com c�digo estruturado; </li>
     * <li>HAVER � Se houver a tag ela deve ser avaliada na sua forma��o; </li>
     * <li>NEXIST � A tag n�o pode existir; </li>
     * <li>REPET � se houver uma repeti��o de caracteres(Regra de imagens ASCII); </li>
     * <li>BUSCA ATR � busca erro no atributo; </li>
     * <li>TAG INTERNA � tag que deve ser interna; </li>
     * <li>ATRN � atributo n�o pode existir; </li>
     * <li>OBRI � Eventos de Mouse que s�o obrigados a ter evento de teclado;</li>
     * <li>ATRI � Atributo n�o precisa existir, mas se existir ele deve ser avaliado.</li>
     * </ol>
     *
     * @param regras regra na qual a tag ser� avaliada.
     * @return retorna um br.org.acessobrasil.nucleuSilva.entidade.Validado
     * (erro que ocorreu na tag).
     */
    private ArmazenaErroOuAvisoAntigo validacao(final Regra regras) {
        boolean invalido = false;
        boolean tag = false;

        
        switch (regras.getAtitude()) {
        case TokenNucleo.BUSCATAG:
            invalido = true;
            tag = true;

            break;

        case TokenNucleo.EXIST:
            invalido = true;
            tag = false;

            break;

        case TokenNucleo.HARDCODED:

            // hard coded
            break;

        case TokenNucleo.INFO:
        case TokenNucleo.HAVER:
            invalido = true;
            tag = true;
            break;

        case TokenNucleo.NEXIT:
            invalido = true;
            tag = false;

            break;

        case TokenNucleo.REPET:
            invalido = true;

            break;

        case TokenNucleo.BUSCAATR:
            invalido = new BuscaAtr().validacao(regras, listaAtributos);
            tag = false;

            break;

        case TokenNucleo.TAGINTERNA:
            break;

        case TokenNucleo.TAGN:
        case TokenNucleo.OBR:
            invalido = !new BuscaAtr().validacao(regras, listaAtributos);
            tag = false;

            break;

        case TokenNucleo.ATRI:
            invalido = new BuscaAtr().validacaoIncertiva(regras, listaAtributos);
            tag = false;

            break;

        default:
            break;
        }

        ArmazenaErroOuAvisoAntigo valido = null;

        /*
         * Se estiver com erro montar um validado.
         */
        if (invalido) {
        	//System.out.print("tag="+tag+"\n");
            valido = new ArmazenaErroOuAvisoAntigo(tag, regras.getPv3(), regras.getProcurado());
        }

        return valido;
    }

    /**
     * 
     * M&eacute;todo respons�vel por listar os atributos e passar a
     * avalia��o para outro m�todo.
     * aqui se constroi o atributo tagCompleta que se refere a tag atual
     * @param node N� a ser avaliado.
     * @return a lista de erros da tag.
     * @throws SQLException Erro no banco de dados ou na instru��o SQL.
     */
    private ArrayList<ArmazenaErroOuAvisoAntigo> distrinchar(final Node node)
    throws SQLException, Exception {
        final String name = node.getNodeName();
        final NamedNodeMap attrs = node.getAttributes();
        this.listaAtributos = new ArrayList<Atributo>();
      
        Atributo atributo = null;
     
       
        getTextContent(node);
       
        
        //text=node.getChildNodes().item(0).getChildNodes().item(0).getLocalName();
        
        for (int i = 0; i < attrs.getLength(); i++) {
        
        	atributo = new Atributo(attrs.item(i).getNodeName(),
                    		        attrs.item(i).getNodeValue());
            this.listaAtributos.add(atributo);
        }
       
     
        
        //Busca as regras para esta tag
        //retornar� para o validar(Node) que retornar� para o Geral
        return motorRegra(this.buscaRegras(name));
    }

    /**
     * M�todo que extra� conte�do de uma tag HTML, colocando-o como valor
     * de um atributo chamado #text.
     * @param node N� avaliado.
     */
    private void getTextContent(final Node node) {
        final String nodeText = "#text";
        final String qtde = "#children";
        String valor = "";
        String nodeName = null;
        Atributo atributo = null;
        boolean achou = false;
        int children = -1;
       
     
        if (this.listaAtributos != null) {
            try {
                NodeList lista = node.getChildNodes();

                for (int i = 0; i < lista.getLength(); i++) {
                    nodeName = lista.item(i).getNodeName();

                    if (!TokenNucleo.COLUNA.equals(nodeName)
                            && !TokenNucleo.LINHA.equals(nodeName)) {
                        children++;

                        if (nodeText.equals(nodeName)) {
                            valor += lista.item(i).getNodeValue();
                            achou = true;
                        }
                    }
                }

                if (achou) {
                    atributo = new Atributo(nodeText, valor);
                    this.listaAtributos.add(atributo);
                }

                atributo = new Atributo(qtde, String.valueOf(children));
                this.listaAtributos.add(atributo);

            } catch (Exception exc) {
            	log.error(exc.getMessage(), exc);
            }
        }
    }

    /**
     * Construtor de uma instru��o sql para ser passada ao motor
     * de avalia��o.
     * @param dep Id do ponto de verifica��o que ser� usado na depend�ncia.
     * @return verdadeiro se a dependecia estiver com algum erro
     * @throws SQLException Erro no banco de dados ou na instru��o SQL.
     */
    private boolean buscaDependencia(final int dep) 
    throws SQLException, Exception {
        return motorRegra(this.buscaDependencias(dep)).size() != 0;
    }

    /**
     * Motor que gerencia a forma de avalia��o da tag, ele forma
     * as regras para a tag, verifica hierarquias e depend�ncias.
     * @param sql Express�o sql que ir� buscar as regras para com a tag.
     * @return Uma lista de erros.
     * @throws SQLException Erro no banco de dados ou na instru��o SQL.
     */
    private ArrayList<ArmazenaErroOuAvisoAntigo> motorRegra(ArrayList<Regra> listaRegras)
    throws SQLException, Exception {
        /*
         * retorno de erros.
         */
        final ArrayList<ArmazenaErroOuAvisoAntigo> regras = new ArrayList<ArmazenaErroOuAvisoAntigo>();

        /*
         * Erros que ser�o avaliados em Hierarquia
         * ap�s terminar todas as regras.
         */
        final ArrayList<Hierarquia> regHieraq = new ArrayList<Hierarquia>();

        ArmazenaErroOuAvisoAntigo tempVal = null;

        for (Regra regraTag : listaRegras) {

            tempVal = validacao(regraTag);

            /*
             * Se tag Errada.
             */
            if (tempVal != null) {
                boolean testeErrada = true; // avalia se a tag se mantem errada

                /*
                 * tag tem depend�ncia de avalia��o.
                 */
                if (regraTag.getDep() != 0) {
                    /*
                     * tag correta em rela��o a depend�ncia.
                     */
                    if (!buscaDependencia(regraTag.getDep())) {
                        testeErrada = false;
                    }
                }

                /*
                 * tag depende de uma hierarquia.
                 */
                if (regraTag.getHierarquia() != 0) {
                    /*
                     * Quando depende de uma
                     * hierarquia a tag pode n�o
                     * estar errada, assim ela e
                     * guardada em uma lista para
                     * que depois ela possa ser
                     * avaliada com a sua hierarquia.
                     */
                    regHieraq.add(new Hierarquia(tempVal, regraTag.getHierarquia()));
                    testeErrada = false;
                }

                /*
                 * tag � colocada na lista de erros.
                 */
                if (testeErrada) {
                    regras.add(tempVal);
                }
            }
        }

        /*
         * Teste de hierarquia.
         */
        Hierarquia ele1 = null;

        /*
         * Teste de hierarquia.
         */
        Hierarquia ele2 = null;

        for (int x = 0; x < regHieraq.size(); x++) {
            ele1 = regHieraq.get(x);

            for (int y = x + 1; y < regHieraq.size(); y++) {
                ele2 = regHieraq.get(y);

                //TODO se tiver de ter mais de uma hierarquia coloque com ; os
                //hierarquizados, array e produ��o.
                if (ele1.getPv3() == ele2.getValidado().getPv3()) {
                    regras.add(ele1.getValidado());
                }
            }
        }

        return regras;
    }

    
    /**
     * Retorna as regras de uma tag espec�fica
     * @param tag
     * @return
     * @throws Exception
     */
    private ArrayList<Regra> buscaRegras(String tag) 
    throws Exception {    	
    	if (mapaRegras.containsKey(tag)){
    		return mapaRegras.get(tag);
    	}else{
    		this.pstBuscaRegras.setString(1, tag.toUpperCase());
    		this.pstBuscaRegras.setInt(2, orgao);
    		this.pstBuscaRegras.setInt(3, orgao);
    		ResultSet rs = pstBuscaRegras.executeQuery();
    		ArrayList<Regra> regras = new ArrayList<Regra>();
    		populaListaDoResultSet(regras, rs);
    		mapaRegras.put(tag, regras);
    		rs.close();
    		return regras;
    	}
    }
    
    private ArrayList<Regra> buscaDependencias(int dep) 
    throws Exception {
    	if (mapaDependencias.containsKey(Integer.valueOf(dep))) 
    	{
    		return mapaDependencias.get(Integer.valueOf(dep));
    	}
    	else 
    	{
    		this.pstBuscaDependencias.setInt(1, dep);
    		ResultSet rs = pstBuscaDependencias.executeQuery();
    		ArrayList<Regra> regras = new ArrayList<Regra>();
    		populaListaDoResultSet(regras, rs);
    		mapaDependencias.put(dep, regras);
    		rs.close();
    		return regras;
    	}
    }

    private void populaListaDoResultSet(ArrayList<Regra> lista, ResultSet rs) 
    throws Exception {
        while (rs.next())
        {
        	Regra regraTag = new Regra();
        	regraTag.setAtitude(rs.getInt("idatitude"));
        	regraTag.setPv3(rs.getInt("pv3"));
        	regraTag.setTi(rs.getInt("tammin"));
        	regraTag.setTx(rs.getInt("tammax"));
        	regraTag.setProcurado(rs.getString("nome"));
        	regraTag.setVs(rs.getString("valor_sim"));
        	regraTag.setVn(rs.getString("valor_nao"));
        	regraTag.setHierarquia(rs.getInt("H"));
        	regraTag.setDep(rs.getInt("Dep"));
        
        	
        	lista.add(regraTag);
        }
    }
    
}
