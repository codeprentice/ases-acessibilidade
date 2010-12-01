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

package br.org.acessobrasil.silvinha.vista.componentes;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
/**
 * Barra de progresso da Avalia��o
 *
 */
public class AvaliacaoProgressBar extends JFrame  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1940536356028617669L;

	private static JProgressBar progressBar;
    
    private int totalPaginas;
    private int paginasAvaliadas;
    private boolean finalizado;
    
    private JLabel lb1;
    private JLabel lb2;
    private JLabel lb3;
    private JLabel lb4;

    
    public AvaliacaoProgressBar() 
    {
    	
    	super("Progress Bar");
		
    	JPanel panel = new JPanel();
    	panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    	
        totalPaginas = 0;
        paginasAvaliadas = 0;
        finalizado = false;

        progressBar = new JProgressBar(0, totalPaginas);
        progressBar.setValue(0);
//        progressBar.setStringPainted(true);
        progressBar.setIndeterminate(true);
        
        lb1 = new JLabel("//Estou em: Avaliacao Progress Bar// Total p�ginas a avaliar: ");
        lb2 = new JLabel(String.valueOf(totalPaginas));
        lb3 = new JLabel("//Estou em: Avaliacao Progress Bar//  p�ginas avaliadas: ");
        lb4 = new JLabel(String.valueOf(paginasAvaliadas));
        
        panel.add(lb1);
        panel.add(lb2);
        panel.add(lb3);
        panel.add(lb4);
        panel.add(progressBar);
        add(panel, BorderLayout.PAGE_START);

        pack();
        
    }


//    public static void main(String[] args) {
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                createAndShowGUI();
//            }
//        });
//    	try
//    	{
//    		Thread.sleep(10000);
//    	} catch (Exception e)
//    	{
//    		e.printStackTrace();
//    	}
//        for (int i = 0; i < 100000; i++)
//        {
//        	AvaliacaoProgressBar.incrementaPaginasAvaliadas();
//        }
//        AvaliacaoProgressBar.setFinalizado(true);
//    }

	/**
	 * @return Returns the finalizado.
	 */
	public boolean isFinalizado() {
		return finalizado;
	}

	/**
	 * @param finalizado The finalizado to set.
	 */
	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}

	/**
	 * @return Returns the paginasAvaliadas.
	 */
	public int getPaginasAvaliadas() {
		return paginasAvaliadas;
	}

	/**
	 * @param paginasAvaliadas The paginasAvaliadas to set.
	 */
	public void setPaginasAvaliadas(int paginasAvaliadas) {
		this.paginasAvaliadas = paginasAvaliadas;
	}

	/**
	 * @return Returns the totalPaginas.
	 */
	public int getTotalPaginas() {
		return totalPaginas;
	}

	public void incrementaTotalPaginas() {
		totalPaginas++;
		progressBar.setMaximum(totalPaginas);
        lb2.setText(String.valueOf(totalPaginas));
	}

	public void incrementaPaginasAvaliadas() {
		paginasAvaliadas++;
        progressBar.setValue(paginasAvaliadas);
        lb4.setText(String.valueOf(paginasAvaliadas));
        if (finalizado) {
            Toolkit.getDefaultToolkit().beep();
            progressBar.setValue(progressBar.getMinimum());
        }
	}
	
}
