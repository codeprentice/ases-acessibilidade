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

package br.org.acessobrasil.silvinha.vista.tableComponents;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;

import br.org.acessobrasil.ases.entidade.EstadoSilvinha;
import br.org.acessobrasil.silvinha.vista.panels.relatorio.PainelRelatorio;

/**
 * Editor de c�lulas da tabela tabela
 * 
 */
public class LinhasCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4655773545165297062L;

	PainelLinhas painel;

	ArrayList<Integer> linhas;

	protected static final String EDIT = "edit";

	public LinhasCellEditor() {

		painel = new PainelLinhas();
	}

	/**
	 * Handles events from the editor button and from the dialog's OK button.
	 */
	public void actionPerformed(ActionEvent e) {
		if (EDIT.equals(e.getActionCommand())) {
			painel.setVisible(true);
			// Make the renderer reappear.
			// fireEditingStopped();
		}
	}

	// Implement the one CellEditor method that AbstractCellEditor doesn't.
	public Object getCellEditorValue() {
		return linhas;
	}

	// Implement the one method defined by TableCellEditor.
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		linhas = (ArrayList<Integer>) value;
		painel = new PainelLinhas(linhas);
		painel.setForeground(table.getSelectionForeground());
		painel.setBackground(table.getSelectionBackground());
		return painel;
	}

	class PainelLinhas extends JPanel implements MouseListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7534419443362893335L;

		public PainelLinhas() {
			this.setVisible(true);
			this.addMouseListener(this);
			this.setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
		}

		public PainelLinhas(ArrayList<Integer> linhas) {
			this.setLinhas(linhas);
			this.addMouseListener(this);
			this.setVisible(true);
			this.setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
		}

		public void setLinhas(ArrayList<Integer> linhas) {
			this.setLayout(new GridLayout(0, 8));
			for (Integer linha : linhas) {
				JLabel l = new JLabel(String.valueOf(linha));
				this.add(l);
			}
		}

		public void mouseClicked(MouseEvent e) {
			Object o = this.getComponentAt(e.getPoint());
			if (o instanceof JLabel) {
				JLabel l = (JLabel) o;
				fireEditingStopped();
				if (EstadoSilvinha.painel_Atual == EstadoSilvinha.PAINEL_RELATORIO_EDICAO) {
					PainelRelatorio.mostrarLinhaTabEdic(Integer.parseInt(l.getText()));
				} else if (EstadoSilvinha.painel_Atual == EstadoSilvinha.PAINEL_RELATORIO_ORIGINAL) {
					PainelRelatorio.mostrarLinhaTabOrig(Integer.parseInt(l.getText()));
				}

			}
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

	}

}
