import java.util.Random;

public class Game {

    private boolean [][] grid;
    private int width, height;

    private Random rng = new Random();

    public Game(int width, int height){
        this.width = width;
        this.height = height;
        grid = new boolean[width][height];

        for (int i = 0; i<grid.length; i++){
            for (int j = 0; j<grid[0].length; j++){
                grid[i][j] = rng.nextBoolean();
            }
        }

    }

    public void step(){
        boolean[][] tempGrid = new boolean[width][height];
        for (int i = 0; i<grid.length-1; i++){
            for (int j = 0; j<grid[0].length-1; j++){
                int neighbours = numNeighbours(i,j);
                if(grid[i][j]){
                    tempGrid[i][j] = (neighbours >= 2) && (neighbours <= 3);
                } else {
                    tempGrid[i][j] = neighbours == 3;
                }
            }
        }
        grid = tempGrid;
    }

    public boolean[][] getGrid(){
        return grid;
    }

    private int numNeighbours(int x, int y){
        int counter = 0;
        for(int i = -1; i<=1; i++){
            for(int j = -1; j<=1; j++){

                if((i == 0) && (j == 0)){
                    continue;
                }

                int xPos;
                int yPos;

                if((x + i) < 0){
                    xPos = (width-1) + (x+i);
                } else {
                    xPos = (x + i) % (width-1);
                }

                if((y + j) < 0){
                    yPos = (height-1) + (y+j);
                } else {
                    yPos = (y + j) % (height-1);
                }

                if (grid[xPos][yPos]){
                    counter++;
                }
            }
        }
        return counter;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void flipTile(int x, int y){
        grid[x][y] = !grid[x][y];
    }
}
