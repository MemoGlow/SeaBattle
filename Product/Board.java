public class Board {
    private int remainCell;
    private int remainShip;
    private int size = 10;
    private int data[][];
    private int symbol;
    Board(){
        symbol = 1;
        size = 10;
        data = new int[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                data[i][j] = 0;
            }
        }
    }

    public int[][] getData() {
        return data;
    }

    public int getSize() {
        return size;
    }

    public boolean validShip(Ship ship){
        int x = ship.getX();
        int y = ship.getY();
        int dx = ship.getDirection().dx;
        int dy = ship.getDirection().dy;
        if(ship.getX() < 0 || ship.getX() > 9 || ship.getY() < 0 || ship.getY() > 9) return false;
        for(int i = 0; i < ship.getLength(); i++){
            if(x + dx * i< 0|| x + dx * i > 9 ||  y + dy * i < 0|| y + dy * i> 9){
                return false;
            }
            if(data[x + dx * i][y + dy * i] != 0){
                return false;
            }
        }
        return true;
    }
    public void outputBoard(){
        System.out.println("Bang hien tai");
        System.out.print("x\\y");
        for(int i = 0; i < size; i++){
            System.out.print(" " + i + " ");
        }
        System.out.println();
        for(int i = 0; i < size; i++){
            System.out.print(" " + i + " ");
            for(int j = 0; j < size; j++){

                if(data[i][j] == 0){
                    System.out.print(" . ");
                }else{
                    System.out.print(Color.ANSI_YELLOW_BACKGROUND + " " + data[i][j] + " " + Color.ANSI_RESET );
                }
            }
            System.out.println();
        }
    }

    public int getRemainCell() {
        return remainCell;
    }

    public int getRemainShip() {
        return remainShip;
    }

    public void placeShip(Ship ship){
        for(int i = 0; i < ship.getLength(); i++){
            data[ship.getX() + ship.getDirection().dx * i][ship.getY() + ship.getDirection().dy * i] = symbol;
        }
        symbol++;
    }

    public void setRemainShip(int remainShip) {
        this.remainShip = remainShip;
    }

    public void updateChange(){
        int cntCell = 0;
        int cntShip = 0;
        int[] mark = new int[size + 5];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(data[i][j] > 0){
                    cntCell++;
                    mark[data[i][j]] = 1;
                }

            }
        }
        for(int i = 1; i < size + 5; i++){
            if(mark[i] == 1) cntShip++;
        }
        remainShip = cntShip;
    }
}
