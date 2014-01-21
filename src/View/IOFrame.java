package View;

import java.awt.Color;

import javax.swing.JFrame;

import Controller.IOController;
import View.IOPanel;
import Controller.IOController;

public class IOFrame extends JFrame
{
	/**
	 * Author Spencer Davy
	 */
	private static final long serialVersionUID = 1L;

	private IOController baseController;
	
	private IOPanel basePanel;
	
	public IOFrame(IOController baseController)
	{
		this.setBaseController(baseController);
		basePanel = new IOPanel(baseController);
		
		setupFrame();
	}
	
	private void setupFrame()
	{
		this.setContentPane(basePanel);
		this.setSize(550, 500);
		this.setVisible(true);
	}

	public IOController getBaseController()
	{
		return baseController;
	}

	public void setBaseController(IOController baseController)
	{
		this.baseController = baseController;
	}
}
