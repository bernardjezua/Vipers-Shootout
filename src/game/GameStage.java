package game;

import javafx.geometry.Pos;
import javafx.scene.Group;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GameStage {
	
	// Class attributes
	private Scene scene;
	private Scene splashScene;
	private Scene aboutScene;
	private Scene instructionsScene;
	private Stage stage;
	private Group root;
	private Canvas canvas;
	private MediaPlayer mediaPlayer;

	// Constants for window dimensions and assets
	static final int WINDOW_HEIGHT = 500;
	static final int WINDOW_WIDTH = 800;
	private static final int INSTRUCTION_WINDOW_HEIGHT = 900;
	private static final Image ICON = new Image("splash/icon.png");
	private static final Image ABOUT_DEV = new Image("splash/dev.png",800,600,false,false);
	private static final Image INSTRUCTIONS = new Image("splash/instructions.png",800,900,false,false);
	private static final Media MUSIC = new Media(GameStage.class.getResource("/splash/bgm.mp3").toExternalForm());

	// Fonts and colors
	static final Font DESC_FONT = Font.font("Arial", FontWeight.BOLD,14);
	public static final Color FONT_COLOR = Color.WHITE;

	// Class constructor
	public GameStage() {
		this.root = new Group(); // group of leaf nodes
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
	}

	// Methods to set up the game stage and scenes
	public void setStage(Stage stage) {
		
		this.stage = stage;
		// set stage elements here
		this.root.getChildren().addAll(this.createCanvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT),canvas);
		this.stage.setTitle("Viper's Shootout | Mini Shooting Game");
		
		this.initSplash(stage);
		this.initAbout(stage);
		this.initInstructions(stage);
		
		this.stage.setScene(this.splashScene);
		this.stage.setResizable(false);
		stage.getIcons().add(ICON);
		music();
		this.stage.show();
	}
	
	
	// Method that plays background music when the stage is called
	void music() {
	    mediaPlayer = new MediaPlayer(MUSIC);
	    mediaPlayer.setVolume(0.12);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Play the music indefinitely
        mediaPlayer.play();
	}

	// Initializes the splash screen, about screen, and instructions screen
	private void initSplash(Stage stage) {
		StackPane root = new StackPane();
        root.getChildren().addAll(this.createCanvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT),this.createVBox());
        this.splashScene = new Scene(root,GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
	}
	
	private void initAbout(Stage stage) {
		ScrollPane root = new ScrollPane();
        this.aboutScene = new Scene(root,GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
        root.setContent(this.createAboutPane());
	}
	
	private void initInstructions(Stage stage) {
		ScrollPane root = new ScrollPane();
        this.instructionsScene = new Scene(root,GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
        root.setContent(this.createInstructionPane());
	}

	// Methods to transition between scenes
	private void setGame(Stage stage) { 
	    GraphicsContext gc = this.canvas.getGraphicsContext2D();
	    GameTimer gameTimer = new GameTimer(gc, scene);
	    gameTimer.start();
	    stage.setScene(scene);
	}
	
	private void setMenu(Stage stage) {
        stage.setScene(splashScene);
	}

	private void setAbout(Stage stage) {
        stage.setScene(aboutScene);
	}

	private void setInstructions(Stage stage) {
        stage.setScene(instructionsScene);
	}
	
	// Methods to create various UI components
	private Canvas createCanvas(int width, int height) {
    	Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image bg = new Image("splash/background.png");
        gc.drawImage(bg, 0, 0);
        return canvas;     
    }

	private VBox createVBox() {
    	VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(8);
        
        Image title = new Image("splash/shootout.png", 350, 130, false, false);
        ImageView titleView = new ImageView(title);
        
        Image image = new Image("splash/newgame.png", 100, 50, false, false);
        ImageView imageView = new ImageView(image);
        
        Image image2 = new Image("splash/about.png", 100, 50, false, false);
        ImageView imageView2 = new ImageView(image2);
        
        Image image3 = new Image("splash/howto.png", 100, 50, false, false);
        ImageView imageView3 = new ImageView(image3);

        Image image4 = new Image("splash/volume.png", 30, 30, false, false);
        ImageView imageView4 = new ImageView(image4);
        
       
        Button b1 = new Button();
        Button b2 = new Button();
        Button b3 = new Button();
        Button b4 = new Button();
        

        b1.setGraphic(imageView);
        b2.setGraphic(imageView2);
        b3.setGraphic(imageView3);
        b4.setGraphic(imageView4);
        
        b1.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
        b2.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
        b3.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
        b4.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
        
        b1.setOnMouseClicked(event -> setGame(stage));
        b2.setOnMouseClicked(event -> setAbout(stage));
        b3.setOnMouseClicked(event -> setInstructions(stage));
        b4.setOnMouseClicked(event -> mediaPlayer.pause());
        b4.setOnMouseReleased(event -> mediaPlayer.play());
        
        vbox.getChildren().addAll(titleView, b1, b2, b3, b4);
        
        return vbox;
    }
	
	private Pane createAboutPane() {
		Pane about = new Pane();
		ImageView img1 = new ImageView(ABOUT_DEV);
		
		Label aboutBern = new Label("AGENT: Bernard Jezua R. Tandang\n"
				+ "Batch 2021, B.S. Computer Science\n\n"
				+ "ABOUT ME:\nHi! My name is Bern. I love to play VALORANT\n"
				+ "with my friends whenever I feel bored, and that\n"
				+ "is why I chose it as my theme and concept.");
		aboutBern.setFont(DESC_FONT);
		aboutBern.setTextFill(FONT_COLOR);
		aboutBern.relocate(310,136);
		
		Label refCode = new Label("CMSC 22 A.Y. 2022-2023 Assets. (2023). Ever\nwing-FX [Source Code].\n\n"
				+ "CMSC 22 A.Y. 2022-2023 Assets. (2023). Mini\nProject Template [Source Code].\n\n"
				+ "Gaspared. (2019). Add Music to JavaFx. https://\ngithub.com/Gaspared/JavaFX-music"); 
		refCode.setFont(Font.font("Arial", 11));
		refCode.setTextFill(FONT_COLOR);
		refCode.relocate(78,328);
		
		Label musicUsed = new Label("Skiff, E. (2020, June 2). Underclocked 8-\nBit BGM. https://youtu.be/hrgzWEgCCFg/\n"); 
		musicUsed.setFont(Font.font("Arial", 12));
		musicUsed.setTextFill(FONT_COLOR);
		musicUsed.relocate(78,506);
		
		Label imageUsed = new Label("Scotti, L. (2022, October). Pearl Map. https://www.pc\ngamesn.com/valorant/pearl-map/\n"
				+ "\nu/zekeymomoo. (2020, July 17). Viper Pixel Art.\nhttps://imgur.com/gRwYcXD\n"
				+ "\n"
				+ "u/realcaptainkimchi. (2021, September 17). Viper\nSprite. https://reddit.com/r/PixelArt/comments/pprjep/\ni_made_these_little_pixel_sprites_of_agents_from/\n"
				+ "\n"
				+ "** Other images were drawn/free-to-use via Canva.\n"
				+ "** VALORANT owns the property of Viper. This project\nis under Fair Use for academic purposes."); 
		imageUsed.setFont(Font.font("Arial", 12));
		imageUsed.setTextFill(FONT_COLOR);
		imageUsed.relocate(420,328);
		
		Image image = new Image("splash/return.png", 100, 50, false, false);
        ImageView imageView = new ImageView(image);
		
		Button b1 = new Button();
		b1.setGraphic(imageView);
		b1.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
		b1.setOnMouseClicked(event -> setMenu(stage));
		b1.relocate(654, 34);
		
		about.getChildren().addAll(createCanvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT), img1, aboutBern, musicUsed, refCode, imageUsed, b1);
		return about;
	}

	private Pane createInstructionPane() {
		Pane instructions = new Pane();
		ImageView img1 = new ImageView(INSTRUCTIONS);
		Label viperDesc = new Label("The player controls Viper, a character in the well-known shooting game, VALORANT.\n"
				+ "She can move horizontally and vertically using cursor keys (UP, DOWN, LEFT, RIGHT).\n"
				+ "Viper can shoot bullets from her gun by using the SPACEBAR key. She has a random\n"
				+ "initial strength from 100 to 150 while her bullet strength is equivalent to her\n"
				+ "current strength. The goal is to SURVIVE until the timer runs out. DODGE or FIGHT!\n"
				+ "As Viper would say, \"If you lose focus, you die. Breathe when it's over.\"");
		viperDesc.setFont(DESC_FONT);
		viperDesc.setTextFill(FONT_COLOR);
		viperDesc.relocate(105,150);
		
		Label slimeDesc = new Label("These creatures randomly appear\n"
				+ "from the right side of the screen.\n"
				+ "At the start of seven of them are\n"
				+ "spawned, and 3 more spawn every\n"
				+ "5 seconds.");
		slimeDesc.setFont(Font.font("Arial", 11));
		slimeDesc.setTextFill(FONT_COLOR);
		slimeDesc.relocate(144,347);
		
		Label witchDesc = new Label("The Witch spawns when the game reaches 30\n"
				+ "seconds, it will appear and move similar\n"
				+ "to the slime. She throws a cursed potion\n"
				+ "every 1.5 seconds as her offense."); 
		witchDesc.setFont(Font.font("Arial", 11));
		witchDesc.setTextFill(FONT_COLOR);
		witchDesc.relocate(486,349);
		
		Label slimeWarn = new Label("> Slime dies immediately when hit by Viper's\n"
				+ "bullet. They also move at random speed.\n"
				+ "> If Viper collides with a slime, Viper's strength\nis reduced by 30 to 40."); 
		slimeWarn.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 11.5));
		slimeWarn.setTextFill(FONT_COLOR);
		slimeWarn.relocate(79,440);
		
		Label witchWarn = new Label("> If Viper is hit by a cursed potion, Viper's strength is\nreduced by 30.\n"
				+ "> If Viper collides with The Witch, Viper's strength is\nreduced by 50.\n"
				+ "> The Witch dies when Viper is able to shoot as many\nbullets depending on her own strength. \nTIP: Collect ATTACK INCREASE power ups!"); 
		witchWarn.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 12));
		witchWarn.setTextFill(FONT_COLOR);
		witchWarn.relocate(425,440);
		
		Label defItem1 = new Label("Immortality - Viper is immune\n"
				+ "to any slime/boss attacks for\n"
				+ "3 seconds.");
		defItem1.setFont(Font.font("Arial", 13));
		defItem1.setTextFill(FONT_COLOR);
		defItem1.relocate(150,617);
		
		Label defItem2 = new Label("Regeneration - Viper's HP/\n"
				+ "strength is added by 50.");
		defItem2.setFont(Font.font("Arial", 13));
		defItem2.setTextFill(FONT_COLOR);
		defItem2.relocate(150,750);
		
		Label atkItem1 = new Label("Attack Increase - Viper's\nbullet strength is increased\nby 100 for 3 seconds.");
		atkItem1.setFont(Font.font("Arial", 13));
		atkItem1.setTextFill(FONT_COLOR);
		atkItem1.relocate(510,617);
		
		Label atkItem2 = new Label("Speed Increase - Viper's speed\nis doubled for 3 seconds.");
		atkItem2.setFont(Font.font("Arial", 13));
		atkItem2.setTextFill(FONT_COLOR);
		atkItem2.relocate(510,750);
		
		Image image = new Image("splash/return.png", 100, 50, false, false);
        ImageView imageView = new ImageView(image);
		
		Button b1 = new Button();
		b1.setGraphic(imageView);
		b1.setStyle("-fx-background-color:transparent;-fx-padding:0;-fx-background-size:0;");
		b1.setOnMouseClicked(event -> setMenu(stage));
		b1.relocate(654, 34);
		
		instructions.getChildren().addAll(createCanvas(GameStage.WINDOW_WIDTH,GameStage.INSTRUCTION_WINDOW_HEIGHT),img1,viperDesc, slimeDesc, witchDesc, slimeWarn,
				witchWarn, defItem1, defItem2, atkItem1, atkItem2, b1);
        return instructions;
    }

}

