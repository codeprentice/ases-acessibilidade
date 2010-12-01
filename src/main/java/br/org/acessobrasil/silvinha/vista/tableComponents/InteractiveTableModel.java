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

import java.util.Vector;

import javax.swing.table.AbstractTableModel;
/**
 * Modelo de tabela interativa 
 *
 */
public class InteractiveTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;

	public static final int TITLE_INDEX = 0;

	public static final int ARTIST_INDEX = 1;

	public static final int ALBUM_INDEX = 2;

	public static final int HIDDEN_INDEX = 3;

	protected String[] columnNames;

	protected Vector dataVector;

	public InteractiveTableModel(String[] columnNames) {
		this.columnNames = columnNames;
		dataVector = new Vector();
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public boolean isCellEditable(int row, int column) {
		if (column == HIDDEN_INDEX)
			return false;
		else
			return true;
	}

	public Class getColumnClass(int column) {
		switch (column) {
		case TITLE_INDEX:
		case ARTIST_INDEX:
		case ALBUM_INDEX:
			return String.class;
		default:
			return Object.class;
		}
	}

	public Object getValueAt(int row, int column) {
		AudioRecord record = (AudioRecord) dataVector.get(row);
		switch (column) {
		case TITLE_INDEX:
			return record.getTitle();
		case ARTIST_INDEX:
			return record.getArtist();
		case ALBUM_INDEX:
			return record.getAlbum();
		default:
			return new Object();
		}
	}

	public void setValueAt(Object value, int row, int column) {
		AudioRecord record = (AudioRecord) dataVector.get(row);
		switch (column) {
		case TITLE_INDEX:
			record.setTitle((String) value);
			break;
		case ARTIST_INDEX:
			record.setArtist((String) value);
			break;
		case ALBUM_INDEX:
			record.setAlbum((String) value);
			break;
		default:
			// System.out.println("invalid index");
		}
		fireTableCellUpdated(row, column);
	}

	public int getRowCount() {
		return dataVector.size();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public boolean hasEmptyRow() {
		if (dataVector.size() == 0)
			return false;
		AudioRecord audioRecord = (AudioRecord) dataVector.get(dataVector.size() - 1);
		if (audioRecord.getTitle().trim().equals("") && audioRecord.getArtist().trim().equals("") && audioRecord.getAlbum().trim().equals("")) {
			return true;
		} else
			return false;
	}

	public void addEmptyRow() {
		dataVector.add(new AudioRecord());
		fireTableRowsInserted(dataVector.size() - 1, dataVector.size() - 1);
	}
}
