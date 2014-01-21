package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Model.Game;
import View.IOFrame;


public class IOController
{
	/**
	 * this creates the frame of the game called gameframe
	 */
	private IOFrame GameFrame;
/**
 * this creates the arraylist called projectgames.
 */
	private ArrayList<Game> projectGames;
	/**
	 * this makes a new game in project games.
	 */
	public IOController()
	{
		projectGames = new ArrayList<Game>();
	}
/**
 * this gets the list of games called projectgames.
 * @return
 */
	public ArrayList<Game> getProjectGames()
	{
		return projectGames;
	}
/**
 * this starts the game.
 */
	public void start()
	{
		GameFrame = new IOFrame(this);
	}
/**
 * this reads the game information that you type in.
 * @return
 */
	public Game readGameInformation()
	{
		String filename = "save file.txt";
		File currentSaveFile = new File(filename);
		Scanner fileReader;
		Game currentGame = null;
		int gameRanking = 0;
		String gameTitle = "";
		ArrayList<String> gameRules = new ArrayList<String>();

		try
		{
			fileReader = new Scanner(currentSaveFile);
			gameTitle = fileReader.nextLine();
			gameRanking = fileReader.nextInt();
			while (fileReader.hasNext())
			{
				gameRules.add(fileReader.nextLine());
			}

			currentGame = new Game(gameRules, gameRanking, gameTitle);
			fileReader.close();
		}
		catch (FileNotFoundException currentFileDoesNotExist)
		{
			JOptionPane.showMessageDialog(GameFrame, currentFileDoesNotExist.getMessage());
		}
		return currentGame;

	}
	/**
	 * this separates your games and makes your games out of the text you type in.
	 * @param currentInfo
	 */
	private void convertTextToGames(String currentInfo)
	{
		
		String [] gameChunks = currentInfo.split(";");
		for(String currentBlock : gameChunks)
		{
			int currentIndex = currentBlock.indexOf("\n");
			String title = currentBlock.substring(0, currentIndex);
			int nextIndex = currentBlock.indexOf("\n", currentIndex);
			String ranking = currentBlock.substring(currentIndex+1, nextIndex);
			String rules = currentBlock.substring(nextIndex+1);
			Game currentGame = makeGameFromInput(title, ranking, rules);
			projectGames.add(currentGame);
		}
	}
	/**
	 * this allows you to pick any file from your save files.
	 * @return
	 */
	public Game pickRandomGameFromSaveFile()
	{
		Game currentGame = null;
		
		String allInfo = readAllGameInformation();
		convertTextToGames(allInfo);
		int randomIndex = (int) (Math.random() * (double) projectGames.size());
		currentGame = projectGames.get(randomIndex);
		return currentGame;
	}
	/**
	 * this reads all the stuff that you type in.
	 * @return
	 */
	private String readAllGameInformation()
	{
		String fileContents = "";
		String fileName = "save file.txt";
		File currentSaveFile = new File(fileName);
		Scanner fileReader;
		
		try
		{
			fileReader = new Scanner(currentSaveFile);
			while(fileReader.hasNext())
			{
				fileContents += fileReader.nextLine();
			}
			fileReader.close();
		}
		catch(FileNotFoundException fileDoesNotExist)
		{
			JOptionPane.showMessageDialog(GameFrame, fileDoesNotExist.getMessage());
		}
		
		return fileContents;
	}
/**
 * this makes the game out of the stuff you type in
 * @param gameTitle
 * @param gameRanking
 * @param gameRules
 * @return
 */
	public Game makeGameFromInput(String gameTitle, String gameRanking, String gameRules)
	{
		Game currentGame = new Game();
		currentGame.setGameTitle(gameTitle);

		if (checkNumberFormat(gameRanking))
		{
			currentGame.setFunRanking(Integer.parseInt(gameRanking));
		}
		else
		{
			return null;
		}

		String[] temp = gameRules.split("\n");
		ArrayList<String> tempRules = new ArrayList<String>();

		for (String tempWord : temp)
		{
			tempRules.add(tempWord);
		}
		currentGame.setGameRules(tempRules);

		return currentGame;
	}
/**
 * it sees if you type in a number or not.
 * @param toBeParsed
 * @return
 */
	private boolean checkNumberFormat(String toBeParsed)
	{
		boolean isNumber = false;

		try
		{
			int valid = Integer.parseInt(toBeParsed);
			isNumber = true;
		}
		catch (NumberFormatException error)
		{
			JOptionPane.showMessageDialog(GameFrame, "Please try again with an actual number.");
		}

		return isNumber;
	}
/**
 * saves the game.
 * @param currentGame
 */
	public void saveGameInformation(Game currentGame)
	{
		PrintWriter gameWriter;
		String saveFile = "save file.txt";

		try
		{
			gameWriter = new PrintWriter(saveFile);

			gameWriter.append(currentGame.getGameTitle() + "\n");
			gameWriter.append(currentGame.getFunRanking() + "\n");
			for (int count = 0; count < currentGame.getGameRules().size(); count++)
			{
				gameWriter.append(currentGame.getGameRules().get(count) + "\n");
			}
			
			gameWriter.append(";"+"\n");
			
			gameWriter.close();
		}
		catch (FileNotFoundException noFileExists)
		{
			JOptionPane.showMessageDialog(GameFrame, "Could not create the save file. :(");
			JOptionPane.showMessageDialog(GameFrame, noFileExists.getMessage());
		}
	}
}
