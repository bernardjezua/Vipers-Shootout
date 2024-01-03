/***********************************************************
* This is "Viper's Shootout", a mini shooting game in which the player
* gets to control Viper, a character in a well-known shooting game, VALORANT. The
* goal of the game is to survive until the timer runs out.
* 
* Viper is able to move via UP, DOWN, LEFT, RIGHT keys and shoot by pressing the spacebar key.
* Slimes will appear on the right side of the screen and move towards the left and back.
* The Witch (boss) will appear when 30 seconds have elapsed in the game timer.
*
* @author Bernard Jezua R. Tandang
* @created_date 2023-08-04 00:50
*
***********************************************************/

package Main;

import game.GameStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
    public void start(Stage stage) {
        GameStage newGame = new GameStage();
        newGame.setStage(stage);
    }

	public static void main(String[] args) {
		launch(args);
	}

}
