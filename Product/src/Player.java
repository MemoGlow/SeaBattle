import java.util.Random;
import java.util.Scanner;

public class Player implements Comparable<Player>{
    private String name;
    private Board board;
    private int numberOfShotCell;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Board getBoard() {
        return board;
    }
    public int getNumberOfShotCell() {
        return numberOfShotCell;
    }
    public void setNumberOfShotCell(int numberOfShotCell) {
        this.numberOfShotCell = numberOfShotCell;
    }
    Player(){
        board = new Board();
    }
    public void setUp(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Vui long nhap ten cua ban: ");
        name = sc.nextLine();
        System.out.println("Vui long chon che do xep tau");
        System.out.println("1. Tu dong");
        System.out.println("2. Thu cong");
        int choice = Game.inputChoice(1, 2);
        Game.clearScreen();
        if(choice == 1){
            autoSetUp();
        }else if(choice == 2){
            manualSetUp();
        }
        board.updateChange();
        board.outputBoard();
    }
    public void manualSetUp(){
        ShipType shipType = new ShipType();
        int symbol = 1;
        while(shipType.getList().size() > 0){
            Ship ship = new Ship();
            ship.inputShip(shipType, board);
            board.placeShip(ship);
            Game.clearScreen();
        }
    }
    public void autoSetUp(){
        Random rand = new Random();
        ShipType shipType = new ShipType();
        for(Ship list : shipType.getList()){
            while(true){
                Ship ship = new Ship();
                ship.setLength(list.getLength());
                ship.setX(rand.nextInt(board.getSize()));
                ship.setY(rand.nextInt(board.getSize()));
                ship.setDirection(Direction.values()[rand.nextInt(4)]);
                if(board.validShip(ship) == true){
                    board.placeShip(ship);
                    break;
                }
            }
        }
    }

    int shoots(Player opp){
        System.out.println("Vui long nhap toa do");
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap X ");
        int x = Game.inputChoice(0, board.getSize() - 1);
        System.out.print("Nhap Y ");
        int y = Game.inputChoice(0, board.getSize() - 1);
        numberOfShotCell++;
        if(opp.board.getData()[x][y] > 0){
            opp.board.getData()[x][y] = -1;
            return 1;
        }else if(opp.board.getData()[x][y] == 0){
            opp.board.getData()[x][y] = -2;
            return 0;
        }else{
            return -1;
        }
    }
    public String toString(){
        return String.format("%s\n%d %d\n", name, numberOfShotCell, board.getRemainShip());
    }
    public int compareTo(Player p){
        if(numberOfShotCell > p.numberOfShotCell) return 1;
        else if(numberOfShotCell == p.numberOfShotCell){
            if(board.getRemainShip() < p.getBoard().getRemainShip()) return 1;
            else if(board.getRemainShip() == p.getBoard().getRemainShip()){
                if(name.compareTo(p.getName()) < 0) return 1;
                else if(name.compareTo(p.getName()) == 0) return 0;
            }
        }
        return -1;
    }

}
