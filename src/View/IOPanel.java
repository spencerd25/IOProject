package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controller.IOController;
import Model.Game;

import javax.swing.*;
import java.awt.Color;

public class IOPanel extends JPanel
{
	/**
	 * Spencer Davy
	 */
	private static final long serialVersionUID = 1L;

	private IOController baseController;
	private JButton saveButton;
	private JButton loadButton;
	private JTextField titleField;
	private JLabel titleLabel;
	private JTextField rankingField;
	private JTextArea rulesArea;
	private JLabel rulesLabel;
	private JLabel rankingLabel;
	private SpringLayout baseLayout;
	private JLabel gameCountLabel;
	
	public IOPanel(IOController baseController)
	{
		
		this.baseController = baseController;
		
		saveButton = new JButton("save the game info");
		loadButton = new JButton("load the game info");
		titleField = new JTextField(15);
		titleLabel = new JLabel("Game Title: ");
		rankingField = new JTextField(5);
		rankingLabel = new JLabel("Game Ranking");
		rulesArea = new JTextArea(5, 15);
		rulesLabel = new JLabel("Game Rules: ");
		gameCountLabel = new JLabel("Current game count:");
		baseLayout = new SpringLayout();
		
		setupPanel();
		setupLayout();
		setupListeners();
	}
	
	private void setupPanel()
	{
		this.setLayout(baseLayout);
		setBackground(Color.CYAN);
		this.add(rankingField);
		this.add(rankingLabel);
		this.add(rulesArea);
		this.add(rulesLabel);
		this.add(saveButton);
		this.add(loadButton);
		this.add(titleField);
		this.add(titleLabel);
		this.add(gameCountLabel);
	}
	
	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.SOUTH, rulesLabel, -6, SpringLayout.NORTH, rulesArea);
		baseLayout.putConstraint(SpringLayout.WEST, rulesLabel, 178, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, rulesArea, 48, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, rulesArea, -21, SpringLayout.SOUTH, this);
		baseLayout.putConstraint(SpringLayout.NORTH, titleLabel, 4, SpringLayout.NORTH, saveButton);
		baseLayout.putConstraint(SpringLayout.EAST, titleLabel, -6, SpringLayout.WEST, titleField);
		baseLayout.putConstraint(SpringLayout.NORTH, titleField, 1, SpringLayout.NORTH, saveButton);
		baseLayout.putConstraint(SpringLayout.EAST, titleField, -6, SpringLayout.WEST, loadButton);
		baseLayout.putConstraint(SpringLayout.NORTH, loadButton, 0, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.NORTH, saveButton, 0, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.EAST, loadButton, -6, SpringLayout.WEST, saveButton);
		baseLayout.putConstraint(SpringLayout.EAST, saveButton, 0, SpringLayout.EAST, this);
		baseLayout.putConstraint(SpringLayout.WEST, rulesArea, 22, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.EAST, rulesArea, -22, SpringLayout.WEST, rankingLabel);
		baseLayout.putConstraint(SpringLayout.NORTH, rankingField, 6, SpringLayout.SOUTH, rankingLabel);
		baseLayout.putConstraint(SpringLayout.EAST, rankingField, -10, SpringLayout.EAST, rankingLabel);
		baseLayout.putConstraint(SpringLayout.NORTH, rankingLabel, 0, SpringLayout.NORTH, rulesArea);
		baseLayout.putConstraint(SpringLayout.EAST, rankingLabel, -21, SpringLayout.EAST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, gameCountLabel, -6, SpringLayout.NORTH, rulesArea);
		baseLayout.putConstraint(SpringLayout.EAST, gameCountLabel, -64, SpringLayout.EAST, this);
	}
	
	private void setupListeners()
	{
		saveButton.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent click)
			{
				Game tempGame = baseController.makeGameFromInput(titleField.getText(), rankingField.getText(), rulesArea.getText());
				if (tempGame != null)
				{
					baseController.saveGameInformation(tempGame);
					gameCountLabel.setText("Current game count: "+ baseController.getProjectGames().size());
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Try again with a valid number");
				}
			}
		});
		loadButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				Game tempGame = baseController.readGameInformation();
				if( tempGame != null)
					{
						titleField.setText(tempGame.getGameTitle());
						rankingField.setText(Integer.toString(tempGame.getFunRanking()));
						String temp = "";
						for(String currentRule : tempGame.getGameRules() )
						{
					temp += currentRule + "\n";
					}
						rulesArea.setText(temp);
					}
				}			
		});
	}
}