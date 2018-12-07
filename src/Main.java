import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {

    private Timeline loop;
    private Stage window;
    private Game game;
    private GridPane content;
    private int rectSize = 10;

    @Override
    public void init(){

    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();

        game = new Game(60,50);

        HBox header = new HBox();
        Button options = new Button("Play/pause");
        options.setOnAction(e -> {
            if(loop.getStatus() == Animation.Status.PAUSED){
                loop.play();
            } else {
                loop.pause();
            }
        });

        header.getChildren().add(options);
        header.setPadding(new Insets(10));
        header.setAlignment(Pos.CENTER);

        content = new GridPane();
        content.setHgap(1);
        content.setVgap(1);
        content.setAlignment(Pos.CENTER);

        root.setTop(header);
        root.setCenter(content);

        window = primaryStage;
        window.setTitle("Game of Life");
        window.setScene(new Scene(root, 700, 700));
        window.show();

        draw();
        runLoop();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void runLoop(){
        loop = new Timeline(new KeyFrame(Duration.millis(100), e->{
            game.step();
            draw();
        }));
        loop.setCycleCount(Animation.INDEFINITE);
        loop.play();
    }

    private void draw(){
        content.getChildren().clear();
        boolean[][] grid = game.getGrid();
        for(int i = 0; i<game.getWidth()-1; i++){
            for(int j = 0; j<game.getHeight()-1; j++){
                Tile tile = new Tile(i, j, rectSize, Color.WHITE);
                StackPane tileStack = new StackPane();
                tileStack.setOnMouseClicked(e -> {
                    game.flipTile(tile.getX(), tile.getY());
                    draw();
                });
                if(grid[i][j]){
                    tile.setColor(Color.GREEN);
                } else {
                    tile.setColor(Color.LIGHTGRAY);
                }
                tileStack.getChildren().add(new Rectangle(tile.getSize(), tile.getSize(), tile.getColor()));
                content.add(tileStack,i,j);
            }
        }
    }
}
