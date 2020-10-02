package application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BoardGame {
	final int size=3;
	private final GridPane gridPane=new GridPane();
	private final HBox bottom=new HBox();
	private final VBox up=new VBox();
	private final StackPane text_btm = new StackPane();
	private Board board;
	private TextField field; 	// Create a TextField
	private Button shuffle;	 // Create a shuffle button
	private Button infoBtn;
	private Button reset;	 // Create a shuffle button
	private Image pic;
	private Text success_text;


	public BoardGame()	//the constructor of the board 
	{
		this.board= new Board(size);
		this.success_text=new Text();
		this.field= new TextField("1");
		this.field.setPrefWidth(50);
		this.field.setAlignment(Pos.BOTTOM_CENTER);
		this.shuffle = new Button("shuffle");
		this.reset = new Button("reset");
		this.infoBtn= new Button("?");
		this.infoBtn.setFont(new Font("Arial",18));
		this.success_text=new Text();
		this.up.getChildren().addAll(reset,infoBtn);
		setBottom();				//call a method which put buttons and field on the bottom of the screen
		ViewBoard();				//call a method which show the state of the board (right now- the wanted board)
		setInfoBtn(infoBtn);		//call a method which set the info button
		setShuffle(shuffle);		//call a method which set the shuffle button 
		setReset(reset);			//call a method which set the reset button
		ClickShuffleField(field);	//call a a method which set the field of shuffle
		text_btm.getChildren().add(success_text);
	}

	public HBox getBottom()		//return the bottom of the screen
	{
		return bottom;
	}
	public void setBottom()		// a method which put buttons and field on the bottom of the screen
	{
		this.bottom.getChildren().add(shuffle);
		this.bottom.getChildren().add(field);
		this.bottom.setAlignment(Pos.BOTTOM_CENTER);
	}

	public void ViewBoard()		//a method which show the state of the board
	{
		for(int i=0; i<size; i++)
			for (int j = 0; j < size; j++)
				showBoard(i, j);
	}

	public Rectangle showBoard(int i, int j)	//a method which creates a slice of the board and put it a picture
	{
		int val=board.get(new IntPair(i, j));
		if(val!=0)
		{
			if(val==1)  pic = new Image(getClass().getResourceAsStream("1.jpg"));
			else if(val==2)  pic = new Image(getClass().getResourceAsStream("2.jpg"));
			else if(val==3)  pic = new Image(getClass().getResourceAsStream("3.jpg"));
			else if(val==4)  pic = new Image(getClass().getResourceAsStream("4.jpg"));
			else if(val==5)  pic = new Image(getClass().getResourceAsStream("5.jpg"));
			else if(val==6)  pic = new Image(getClass().getResourceAsStream("6.jpg"));
			else if(val==7)  pic = new Image(getClass().getResourceAsStream("7.jpg"));
			else if(val==8)  pic = new Image(getClass().getResourceAsStream("8.jpg"));
			final Rectangle rect = new Rectangle();
			rect.setFill(new ImagePattern(pic));
			rect.setX(i);
			rect.setY(j);
			rect.setWidth(80);
			rect.setHeight(80);
			GridPane.setRowIndex(rect, i);
			GridPane.setColumnIndex(rect, j);
			GridPane.setConstraints(rect, j, i);
			gridPane.getChildren().addAll(rect);
			return rect;
		}
		return null;
	}


	public void createBoard()									//a method which creates the board on the grid pane
	{
		gridPane.getChildren().clear();
		field.setText("1");										//each time the board is created, initialize the value in the text field of shuffle to "1"
		success_text.setText("");									//each time the board is created, initialize the value in the text "" (not visible on screen)
		for(int i=0; i<size; i++){
			for (int j = 0; j < size; j++) {
				Rectangle rect=showBoard(i,j);						//creates one slice of the board 
				if(rect instanceof Rectangle)	
				{
					rect.setOnMouseClicked(new EventHandler<MouseEvent>(){	//define a click option in order to move the slice if possible

						@Override
						public void handle(MouseEvent event) {

							IntPair source=new IntPair(GridPane.getRowIndex(rect),GridPane.getColumnIndex(rect));	//get the place of the wanted slice
							IntPair dest=board.getEmpty();			//find the indexes of the empty place
							int index=0;
							boolean flag=false;
							while(index<board.possibleMoves().size()&& flag==false)		//check if the slice is can be moved
							{
								if(board.possibleMoves().get(index).getI()==source.getI() && board.possibleMoves().get(index).getJ()==source.getJ())
								{
									board.move(source);
									GridPane.setConstraints(rect, dest.getJ(), dest.getI());
									rect.setX(source.getI());
									rect.setY(source.getJ());
								}
								index++;
							}
							if(board.isDone()==true)				//check if the game is over
								Winner();							//call a method which tells the player the he completed the puzzle
						}					
					});
				}
			}
		}
	}
	public void Winner() 										//a method which tells the player the he completed the puzzle
	{
		success_text.setText("You Did It!");
		success_text.setFont(new Font("Arial",18));
		success_text.setFill(Color.GREEN);
	}

	public GridPane getGridPane() {
		return gridPane;
	}

	public void setInfoBtn(Button infoBtn)					 //call a method which set the info button
	{
		infoBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				Button btn = new Button("ok");	
				alert.setTitle("Instructions");
				alert.setHeaderText("HOW TO PLAY");
				alert.setContentText("1. Click on ''reset'' button.\n 2.Choose the number of shuffles you want or click on the piece that you want to swap until the puzzle is complete.");
				alert.showAndWait();
				btn.setOnKeyReleased(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {
						alert.close();
					}
				});
			}
		});		 		}

	public Button getInfoBtn() {
		return infoBtn;
	}

	public VBox getUp() {
		return up;
	}


	public void setReset(Button reset) 				//call a method which set the reset button
	{
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				board.Shuffle();						//call the shuffle method from "board" and reset
				createBoard();							//create the board all over again
			}
		});
	}

	public void setShuffle(Button shuffle) 			//call a method which set the shuffle button 
	{
		shuffle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!board.isDone())					//if the board is not done then shuffle
				{
					try{
						String str_num=field.getText();
						int ret_num= Integer.parseInt(str_num);
						for(int i=0; i<ret_num; i++)
						{
							board.ShuffleOnePlace();
							createBoard();				 
							if(board.isDone()==true)
								Winner();						 
						}
					}
					catch(Exception e){
						success_text.setText("Wrong input");
						success_text.setFont(new Font("Arial",18));
						success_text.setFill(Color.RED);
					}
				}
			}
		});
	}

	public void ClickShuffleField(TextField field) 			//call a a method which set the field of shuffle
	{
		field.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(!board.isDone()){
					if(event.getCode().equals(KeyCode.ENTER))		//set "ENTER" key option to the text field
					{
						try{										//if the input is not number- catch the exception
							String str_num=field.getText();
							int ret_num= Integer.parseInt(str_num);
							for(int i=0; i<ret_num; i++)
							{
								board.ShuffleOnePlace();
								createBoard();				 
								if(board.isDone()==true)
									Winner();						 
							}
						}
						catch(Exception e){
							success_text.setText("Wrong input");
							success_text.setFont(new Font("Arial",18));
							success_text.setFill(Color.RED);
						}

					}
				}
			}
		});
	}

	public StackPane getText_btm() {
		return text_btm;
	}


}
