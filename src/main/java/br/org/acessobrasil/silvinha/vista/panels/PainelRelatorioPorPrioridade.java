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

package br.org.acessobrasil.silvinha.vista.panels;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SizeSequence;
import javax.swing.table.TableColumn;

import br.org.acessobrasil.silvinha.entidade.PontoVerificacao;
import br.org.acessobrasil.silvinha.vista.configs.CoresDefault;
import br.org.acessobrasil.silvinha.vista.panels.relatorio.PainelRelatorio;
import br.org.acessobrasil.silvinha.vista.tableComponents.LinhasCellEditor;
import br.org.acessobrasil.silvinha.vista.tableComponents.LinhasCellRenderer;
import br.org.acessobrasil.silvinha.vista.tableComponents.RelatorioTableModel;
import br.org.acessobrasil.silvinha.vista.tableComponents.SimpleTextAreaRenderer;
/**
 * Exibe o relat�rio por prioridades
 *
 */
public class PainelRelatorioPorPrioridade extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3273032414748667618L;

	public static SizeSequence ss;
	public static HashMap<Integer, ArrayList<Integer>> lineMap;
	
	private JTable table;
	
	private PainelRelatorio painelRelatorio;
	
    public PainelRelatorioPorPrioridade(HashSet<PontoVerificacao> relatorio, String TipoAndPrioridade, PainelRelatorio painelRelatorio) {
    	super(new BorderLayout());
    	this.painelRelatorio = painelRelatorio;
    	
    	//System.out.println("br.org.acessobrasil.silvinha.vista.panels.PainelRelatorioPorPrioridade.java");
    	ss = new SizeSequence();
    	ss.insertEntries(0, 100, 0);
    	lineMap = new HashMap<Integer, ArrayList<Integer>>();
        this.setBackground(CoresDefault.getCorPaineis());
        
        RelatorioTableModel rtb = new RelatorioTableModel(relatorio,TipoAndPrioridade);
        //final JTable table = new JTable(rtb);
        table = new JTable(rtb);
        table.setBackground(CoresDefault.getCorListaTabela());

        TableColumn column = null;
        for (int i = 0; i < 4; i++) {
            column = table.getColumnModel().getColumn(i);
            column.setResizable(false);
            if (i == 0) {
                column.setPreferredWidth(50); //coluna do Ponto de Verificacao
            } else if (i == 1) {
                column.setPreferredWidth(300); //colunas das regras
                column.setCellRenderer(new SimpleTextAreaRenderer());
            } else if (i == 2) {
            	column.setPreferredWidth(130); //colunas das ocorrencias
            } else if (i == 3) {
            	column.setPreferredWidth(277); //colunas das linhas
            	column.setCellRenderer(new LinhasCellRenderer());
            	column.setCellEditor(new LinhasCellEditor());
            }
        }
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        table.addMouseListener(this);
        //Add the scroll pane to this panel.
        this.add(scrollPane, BorderLayout.CENTER);
        
        this.setVisible(true);
    }

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() >= 2) {
			int row = this.table.getSelectedRow();
			int col = this.table.getSelectedColumn();

			if (row == -1) {
				return;
			}
			if (col == 1 || col==0) {
				/*
				 * Abrir um help
				 */
				String texto = table.getValueAt(row,0).toString();
				this.painelRelatorio.openHelp(texto);
			}
		}
		
	}

	public void mouseEntered(MouseEvent arg0) {
		
	}

	public void mouseExited(MouseEvent arg0) {
		
	}

	public void mousePressed(MouseEvent arg0) {
		
	}

	public void mouseReleased(MouseEvent arg0) {
		
	}
    
}