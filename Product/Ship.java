import java.util.Scanner;

public class Ship {
    private String name;
    private int length;
    private Direction direction;
    private int x;
    private int y;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public void setLength(int length) {
        this.length = length;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLength() {
        return length;
    }

    public Direction getDirection() {
        return direction;
    }
    public void inputShip(ShipType shipType, Board board){
        Scanner sc = new Scanner(System.in);
        int shipChoice;
        while(true){
            int i = 1;
            board.outputBoard();
            System.out.println("Danh sach tau hien co:");
            for(Ship list : shipType.getList()){
                System.out.println(i + " " + list.getName() + " " + list.getLength() + "x1");
                i++;
            }
            shipChoice = Game.inputChoice(1, shipType.getList().size());
            shipChoice--;
            System.out.println("Vui long nhap thong tin tau");
            this.length = shipType.getList().get(shipChoice).getLength();
            System.out.print("Nhap toa do X ");
            this.x = Game.inputChoice(0, board.getSize() - 1);
            System.out.print("Nhap toa do Y ");
            this.y = Game.inputChoice(0, board.getSize() - 1);
            System.out.println("Danh sach huong");
            for(Direction dir : Direction.values()){
                System.out.print((dir.ordinal() + 1) + ". " + dir.vietnammese + " ");
            }
            System.out.println();
            int directionChoice = Game.inputChoice(1, 4) - 1;
            this.direction = Direction.values()[directionChoice];
            if(board.validShip(this) == true){
                shipType.getList().remove(shipChoice);
                Game.clearScreen();
                break;
            }else{
                Game.clearScreen();
            }
        }
    }
}
