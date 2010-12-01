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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import br.org.acessobrasil.silvinha.entidade.DadosConfig;
import br.org.acessobrasil.silvinha.excessoes.ExceptionDialog;
import br.org.acessobrasil.silvinha.persistencia.DadosConfigDAOHSSQLImpl;
import br.org.acessobrasil.silvinha.util.PropertyLoader;
import br.org.acessobrasil.silvinha.util.lang.TokenLang;
import br.org.acessobrasil.silvinha.vista.configs.CoresDefault;
import br.org.acessobrasil.silvinha.vista.frames.classePrincipal.FrameSilvinha;
import br.org.acessobrasil.silvinha2.mli.GERAL;
/**
 * Painel de configura��o de idioma usu�rio, etc. 
 *
 */
public class PainelConfig extends SuperPainelCentral implements ActionListener {
	
	private static final long  serialVersionUID       = -3348105187778647672L;
	private FrameSilvinha framePai;
	
	/**
	 * Flag para saber se a configura��o est� ok
	 * de E-mail e SMTP
	 */
	public static Boolean configOK = null;
	
    DadosConfig dc;

    private final Color corDefault = CoresDefault.getCorPaineis();

    private static JLabel lblEmail;
    private static JLabel lblSmtp;
    private static JLabel lblUsuario;
    private static JLabel lblIdioma;
    
    /**
     * Nomes das op��es de idioma
     */
    /*
    String opcoes[] = {"Portugu�s"};
    /*/
    //String opcoes[] = {"Portugu�s","English"};
    //*/
    String opcoes[] = {"Portugu�s","English","Espa�ol"};
    
	/**
	 * opcoes_lang � o resumido do opcoes ex: pt, en, es
	 */  
    String opcoes_lang[] = {"pt","en","es"};
    private static JButton btnSalvar;
    private static JButton btnCancelar;
    
    private static JTextField tfUsuario;
    private static JTextField tfEmailRelatorio;
    private static JTextField tfSMTP;
    private static JComboBox cbIdioma;
    private static JPanel pnTextFields;
    private static JPanel pnButtons;
    
    /**
     * Construtor de PainelConfig.
     * Inicializa os componentes da tela.
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public PainelConfig(FrameSilvinha framePai) throws ClassNotFoundException, SQLException {
    	
    	this.framePai = framePai;
    	this.setBackground(corDefault);
    	
    	GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(1, 7, 1, 7);
        
        this.setLayout(gridbag);
        Border border = BorderFactory.createLineBorder(new Color(0,0,0),1);
        
        tfUsuario = new JTextField();
        tfEmailRelatorio = new JTextField();
        tfSMTP = new JTextField();
        
        pnTextFields = new JPanel();
        lblUsuario = new JLabel();
        lblEmail = new JLabel();
        lblSmtp = new JLabel();
        lblIdioma = new JLabel();
        cbIdioma=new JComboBox(opcoes);
         
        //INICIALIZA�AO DO PAINEL DE TEXT FIELDS
        pnTextFields.setBackground(corDefault);
        Dimension tamanhoFields = new Dimension(190, 20);

		GridBagLayout bag = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		pnTextFields.setLayout(bag);
		
		
        lblUsuario.setText(GERAL.USUARIO);
		lblUsuario.setHorizontalAlignment(JLabel.RIGHT);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(2, 10, 2, 10);
		pnTextFields.add(lblUsuario, gbc);
		
        tfUsuario.setMaximumSize(tamanhoFields);
        tfUsuario.setMinimumSize(tamanhoFields);
        tfUsuario.setPreferredSize(tamanhoFields);
        tfUsuario.setAlignmentY(JLabel.WEST);
        tfUsuario.addActionListener(null);
        gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2, 0, 2, 10);
		pnTextFields.add(tfUsuario, gbc);
		
        lblEmail.setText(GERAL.EMAIL);
		lblEmail.setHorizontalAlignment(JLabel.RIGHT);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 1;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(2, 10, 2, 10);
		pnTextFields.add(lblEmail, gbc);
		
        tfEmailRelatorio.setMaximumSize(tamanhoFields);
        tfEmailRelatorio.setMinimumSize(tamanhoFields);
        tfEmailRelatorio.setPreferredSize(tamanhoFields);
		tfEmailRelatorio.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		tfEmailRelatorio.addActionListener(null);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(2, 0, 2, 10);
		pnTextFields.add(tfEmailRelatorio, gbc);
		
//      lblSmtp.setText(TokenLang.EMAIL_CAD);
//		lblSmtp.setHorizontalAlignment(JLabel.RIGHT);
//		gbc.fill = GridBagConstraints.BOTH;
//		gbc.gridwidth = 1;
//		gbc.weightx = 1.0;
//		gbc.insets = new Insets(2, 10, 2, 10);
//		pnTextFields.add(lblSmtp, gbc);
//		
//      tfSMTP.setMaximumSize(tamanhoFields);
//      tfSMTP.setMinimumSize(tamanhoFields);
//      tfSMTP.setPreferredSize(tamanhoFields);
//		tfSMTP.setAlignmentX(SwingConstants.WEST);
//      gbc.fill = GridBagConstraints.NONE;
//		tfSMTP.addActionListener(null);
//		gbc.gridwidth = GridBagConstraints.REMAINDER;
//		gbc.insets = new Insets(2, 0, 2, 10);
//		pnTextFields.add(tfSMTP, gbc);
		
		pnTextFields.setBorder(BorderFactory.createTitledBorder(border, GERAL.DADOS));

		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 2;
        gridbag.setConstraints(pnTextFields, c);
		this.add(pnTextFields);
        
		//JPanel pnIdioma =  new JPanel(new GridLayout(2,3));
		JPanel pnIdioma =  new JPanel();
		pnIdioma.setBorder(BorderFactory.createTitledBorder(border, GERAL.IDIOMAS));
		
		lblIdioma.setText(GERAL.POR_FAVOR_SELECIONE_IDIOMA);
		lblIdioma.setHorizontalAlignment(JLabel.CENTER);
		pnIdioma.add(lblIdioma);
		pnIdioma.setBackground(corDefault);
		pnIdioma.add(cbIdioma);
		
		gridbag.setConstraints(pnIdioma, c);
		this.add(pnIdioma,c);
		
        pnButtons = new JPanel();
        pnButtons.setBackground(corDefault);
        
        btnSalvar = new JButton(GERAL.BTN_SALVAR);
        btnSalvar.setActionCommand("SALVAR");
        btnSalvar.addActionListener(this);
        btnCancelar = new JButton(GERAL.BTN_CANCELAR);
        btnCancelar.setActionCommand("CANCELAR");
        btnCancelar.addActionListener(this);
        
        pnButtons.add(btnSalvar);
        pnButtons.add(btnCancelar);
        
        c.weighty = 1.0;
        c.gridheight = 2;
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(pnButtons, c);
		this.add(pnButtons);
        
        verificaConfig();
        if (dc == null) {
        	dc = new DadosConfig();
        }
        mostraDadosConfig();
        setVisible(true);
    }
    
    public void verificaConfig() throws ClassNotFoundException, SQLException {
        DadosConfigDAOHSSQLImpl dcd = new DadosConfigDAOHSSQLImpl();
        dc = dcd.consultar();
    }
    
    public DadosConfig leDadosConfig(){
    	dc.setAdmin(tfUsuario.getText());
    	dc.setEmail(tfEmailRelatorio.getText());
//    	dc.setSmtp(tfSMTP.getText());
    	return dc;
    }
    
    /**
     * M�todo que recupera as configura��es j� salvas.
     */
    public void mostraDadosConfig() {
    	tfUsuario.setText(dc.getAdmin());
    	tfEmailRelatorio.setText(dc.getEmail());
    	int indice=0;
    	for(int i=0;i<opcoes_lang.length;i++){
    		if(TokenLang.LANG.equals(opcoes_lang[i])){
    			indice=i;
    			break;
    		}
    	}
    	cbIdioma.setSelectedIndex(indice);
//    	tfSMTP.setText(dc.getSmtp());
    }
    
    public void actionPerformed(ActionEvent e) {
    	String command = e.getActionCommand();
    	if (command.equals("SALVAR")) 
    	{
    		if (tfUsuario.getText().trim().equals("") || tfEmailRelatorio.getText().trim().equals(""))
    		{
    			ExceptionDialog.showExceptionDialog(GERAL.PREENCHA_DADOS_SOLICITADOS);
    			return;
    		}
        	dc = leDadosConfig();
    		DadosConfigDAOHSSQLImpl dcd;
			try {
				dcd = new DadosConfigDAOHSSQLImpl();
				dcd.inserir(dc);
			} catch (ClassNotFoundException e1) {
				ExceptionDialog.showExceptionDialog(e1.getMessage());
			} catch (SQLException e1) {
				ExceptionDialog.showExceptionDialog(e1.getMessage());
    		} catch (Exception ex) {
    			ExceptionDialog.showExceptionDialog(ex.getMessage());
    		}
    		configOK = true;
    		/*
    		 * Salvar a op��o do idioma que o usu�rio escolheu
    		 */
    		String oldConfLang = TokenLang.LANG;
    		String newConfLang = salvaOpcaoIdioma();
    		if(oldConfLang.equals(newConfLang)){
    			//N�o trocou
    			//System.out.print("N�o trocou de idioma\n");
    			JOptionPane.showMessageDialog(this, GERAL.GRAV_ALTER_SUCESSO);
    		}else{
    			//System.out.print("Trocou de idioma de "+oldConfLang +" para "+newConfLang);
    			JOptionPane.showMessageDialog(this, GERAL.ALTER_IDIOMA_SUCESSO);
    		}
    		framePai.showPainelAvaliacao();
    		
    	} 
    	else if (command.equals("CANCELAR")) 
    	{
    		if (configOK) 
    		{
    			framePai.showPainelAvaliacao();
    		} 
    		else 
    		{
    			JOptionPane.showMessageDialog(this, GERAL.CADASTRO_OBRIGATORIO);
    		}
    	} 
    }
    
    /**
     * Salvar a op��o do idioma que o usu�rio escolheu
     *
     */
    private String salvaOpcaoIdioma(){
    	try {
    		
			Properties opts = new PropertyLoader().loadProperty("config_geral");
			String opcao=cbIdioma.getSelectedItem().toString();
			String lang="pt";
			opts.setProperty("idioma",opcao);
			//lang � o resumido ex: pt, en, es
			for(int i=0;i<opcoes.length;i++){
				if(opcoes[i].equals(opcao)){
					lang=opcoes_lang[i];
				}
			}
			opts.setProperty("lang",lang);
			//n�o precisa pois pedimos para ele reabrir
			//TokenLang.LANG=lang;
			PropertyLoader.saveProperty("config_geral",opts);
			return lang;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "pt";
    }

	@Override
	public boolean showBarraUrl() {
		return false;
	}
        
}