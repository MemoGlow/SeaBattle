import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
public class Game {
    Player p1;
    Player p2;
    ShipType shipType;
    public boolean endGame(){
        if(p1.getBoard().getRemainShip() == 0 || p2.getBoard().getRemainShip() == 0) return true;
        return false;
    }
    public boolean restartGame(){
        System.out.println("Ban co muon khoi dong lai tro choi");
        System.out.println("1. Co");
        System.out.println("2. Khong");
        int choice = inputChoice(1, 2);
        if(choice == 1) return true;
        return false;
    }
    void pauseScreen(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhan enter de tiep tuc");
        sc.nextLine();
        System.out.println("-".repeat(30));
    }
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public void saveGame(){
        try {
            FileWriter file = new FileWriter("data.txt");
            file.write(this.toString());
            file.close();
        } catch (IOException e){
        }
    }
    public boolean loadGame(){
        File file;
        Scanner sc;
        try{
            file = new File("data.txt");
            sc = new Scanner(file);
        }catch (FileNotFoundException e){
            System.out.println("Khong co du lieu game");
            pauseScreen();
            clearScreen();
            return false;
        }
        if(!sc.hasNextLine()){
            System.out.println("Khong co du lieu game");
            pauseScreen();
            clearScreen();
            return false;
        }
        p1.setName(sc.nextLine());
        for(int i = 0; i < p1.getBoard().getSize(); i++){
            for(int j = 0; j < p1.getBoard().getSize(); j++){
                p1.getBoard().getData()[i][j] = Integer.parseInt(sc.nextLine());
            }
        }
        p2.setName(sc.nextLine());
        for(int i = 0; i < p1.getBoard().getSize(); i++){
            for(int j = 0; j < p1.getBoard().getSize(); j++){
                p2.getBoard().getData()[i][j] = Integer.parseInt(sc.nextLine());
            }
        }
        p1.getBoard().updateChange();
        p2.getBoard().updateChange();
        return true;
    }
    public void updateRank(Player player){
        try{
            ArrayList<Player> players = new ArrayList<Player>();
            new FileWriter("data.txt", false).close();
            Scanner sc = new Scanner(new File("rank.txt"));
            players.add(player);
            while(sc.hasNextLine()){
                Player p = new Player();
                p.setName(sc.nextLine());
                p.setNumberOfShotCell(sc.nextInt());
                p.getBoard().setRemainShip(sc.nextInt());
                players.add(p);
                if(sc.hasNextLine()) sc.nextLine();
            }
            Collections.sort(players);
            StringBuilder rank = new StringBuilder();
            for(Player p : players){
                rank.append(p.toString());
            }
            new FileWriter("rank.txt", false).close();
            FileWriter file = new FileWriter("rank.txt", false);
            file.write(String.valueOf(rank));
            file.close();
        }catch(IOException ex){
            ;
        }
    }
    public void loadRank(){
        try{
            new File ("rank.txt");
            Scanner sc = new Scanner(new File("rank.txt"));
            int stt = 1;
            System.out.println(String.format("%4s%16s%1s%1s", "STT", "|Ten nguoi choi", "|So phat ban", "|So tau con lai"));
            while(sc.hasNextLine()){
                System.out.print(String.format("%3s%-2s", String.valueOf(stt++), ""));
                System.out.print(String.format("%s%-14s", "|", sc.nextLine()));
                System.out.print(String.format("|%-11s", sc.nextInt()));
                System.out.print(String.format("|%s", sc.nextInt()));
                System.out.println();
                if(sc.hasNextLine()) sc.nextLine();
            }
        }
        catch (IOException ex){

        }
    }
    public static int inputChoice(int min, int max){
        Scanner sc = new Scanner(System.in);
        int choice;
        while(true){
            System.out.print("Lua chon cua ban la: ");
            try{
                choice = Integer.parseInt(sc.nextLine());
                if(choice < min || choice > max){
                    System.out.println("Vui long nhap lai");
                }else return choice;
            }catch(NumberFormatException ex){
                System.out.println("Vui long nhap lai");
            }
        }
    }
    public String toString(){
        StringBuilder data = new StringBuilder();
        int size = p1.getBoard().getSize();
        data.append(p1.getName());
        data.append("\n");
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                data.append(p1.getBoard().getData()[i][j]);
                data.append("\n");
            }
        }
        data.append(p2.getName());
        data.append("\n");
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                data.append(p2.getBoard().getData()[i][j]);
                data.append("\n");
            }
        }
        return String.valueOf(data);
    }
    public int gameInit(){
        Scanner sc = new Scanner(System.in);
        int choice;
        while(true){
            clearScreen();
            System.out.println("Chao mung ban den voi SeaBattle");
            System.out.println("-".repeat(30));
            System.out.println("1. Tro choi moi");
            System.out.println("2. Tiep tuc tro choi");
            System.out.println("3. BXH");
            System.out.println("4. Thoat tro choi");
            choice = inputChoice(1, 4);
            clearScreen();

            p1 = new Player();
            p2 = new Player();
            if(choice == 1){
                try{
                    new FileWriter("data.txt", false).close();
                }catch (IOException ex){

                }
                System.out.println("Cac che do choi");
                System.out.println("1. PVP");
                System.out.println("2. PVE");
                choice = inputChoice(1, 2);
                clearScreen();
                if(choice == 1){
                    p1.setUp();
                    pauseScreen();
                    clearScreen();
                    p2.setUp();
                    pauseScreen();
                    clearScreen();
                    return 0;
                }else if(choice == 2){
                    System.out.println("Che do dang duoc phat trien");
                    pauseScreen();
                    clearScreen();
                }
            }else if(choice == 2){
                boolean continu = loadGame();
                if(continu) return 0;
            }else if(choice == 3){
                loadRank();
                pauseScreen();
                clearScreen();
            }else if(choice == 4){
                return 4;
            }
        }
    }
    void outputStatusBoard(Player player, Player opp){
            System.out.println("-".repeat(30));
            System.out.println("Thong tin cua " + Color.ANSI_RED + player.getName() + Color.ANSI_RESET);
            System.out.println("So o da ban: " + player.getNumberOfShotCell());
            System.out.println("So tau da pha: " + (new ShipType().getList().size() - opp.getBoard().getRemainShip()));
            System.out.println("So tau con lai: " + player.getBoard().getRemainShip());
            System.out.println("-".repeat(80));
            System.out.printf("Bang hien tai: %25s %3s Bang doi thu:\n", "||", "");
            System.out.print("x\\y");
            for(int i = 0; i < player.getBoard().getSize(); i++){
                System.out.print(" " + i + " ");
            }
            System.out.printf("%7s", "||");
            System.out.printf("%8s",  "x\\y");
            for(int i = 0; i < player.getBoard().getSize(); i++){
                System.out.print(" " + i + " ");
            }
            System.out.println();
            for(int i = 0; i < opp.getBoard().getSize(); i++){
                System.out.print(" " + i + " ");
                for(int j = 0; j < opp.getBoard().getSize(); j++){
                    if(player.getBoard().getData()[i][j] > 0){
                        System.out.print(Color.ANSI_YELLOW_BACKGROUND + " " + player.getBoard().getData()[i][j] + " " + Color.ANSI_RESET );
                    }else if(player.getBoard().getData()[i][j] == 0){
                        System.out.print(" . ");
                    }else if(player.getBoard().getData()[i][j] == -1){
                        System.out.print(Color.ANSI_RED_BACKGROUND + " x " + Color.ANSI_RESET );
                    }else if(player.getBoard().getData()[i][j] == -2){
                        System.out.print(Color.ANSI_GREEN_BACKGROUND + " o " + Color.ANSI_RESET );
                    }
                }
                System.out.printf("%7s", "||");
                System.out.printf("%7d ", i);
                for(int j = 0; j < opp.getBoard().getSize(); j++){
                    if(opp.getBoard().getData()[i][j] == -2){
                        System.out.print(Color.ANSI_GREEN_BACKGROUND + " o " + Color.ANSI_RESET );
                    }else if(opp.getBoard().getData()[i][j] == -1){
                        System.out.print(Color.ANSI_RED_BACKGROUND + " x " + Color.ANSI_RESET );
                    }else{
                        System.out.print(" . ");
                    }
                }
                System.out.println();
            }
            System.out.println("-".repeat(80));
            System.out.println(Color.ANSI_RED_BACKGROUND + " x " + Color.ANSI_RESET + ": Ban trung");
        System.out.println(Color.ANSI_GREEN_BACKGROUND + " o " + Color.ANSI_RESET + ": Ban truot");
    }
    int gameTurn(Player player, Player opp){
        while(true){
            outputStatusBoard(player, opp);
            System.out.println("Luot choi cua " + Color.ANSI_RED + player.getName() + Color.ANSI_RESET);
            Scanner sc = new Scanner(System.in);
            System.out.println("Cac chuc nang:");
            System.out.println("1. Dat lenh khai hoa");
            System.out.println("2. Ket thuc luot");
            System.out.println("3. Thoat ve man hinh chinh");
            int choice = inputChoice(1, 3);
            clearScreen();
            outputStatusBoard(player, opp);
            if(choice == 3){
                return 3;
            }
            if(choice == 2){
                pauseScreen();
                clearScreen();
                return 2;
            }
            else if(choice == 1){
                int continueShoot = player.shoots(opp);
                saveGame();
                clearScreen();
                opp.getBoard().updateChange();
                outputStatusBoard(player, opp);
                if(continueShoot == 1){
                    System.out.println("|" + Color.ANSI_RED + "CHUC MUNG BAN DA BAN TRUNG" + Color.ANSI_RESET + "|");
                    pauseScreen();
                    clearScreen();
                }else if(continueShoot == -1) {
                    System.out.println("|" + Color.ANSI_RED + "BAN DA BAN TAI O NAY" + Color.ANSI_RESET + "|");
                    System.out.println("|" + Color.ANSI_RED + "VUI LONG THUC HIEN LAI" + Color.ANSI_RESET + "|");
                    System.out.println("-".repeat(35));
                    pauseScreen();
                    clearScreen();
                }else if(continueShoot == 0){
                    System.out.println("|" + Color.ANSI_RED + "RAT TIEC BAN DA BAN TRUOT" + Color.ANSI_RESET + "|");
                    pauseScreen();
                    clearScreen();
                    return 0;
                }

            }

        }
    }

    public static void main(String[] args) {
        Game game;
        game = new Game();
        while(true){
            int exitStatus = game.gameInit();
            if(exitStatus == 4) break;
            int cur = 1;
            game.saveGame();
            while(true){
                if(cur == 1){
                    int status = game.gameTurn(game.p1, game.p2);
                    if(status == 3) break;
                    if(game.endGame()){
                        System.out.println("P1 win");
                        game.updateRank(game.p1);
                        game.pauseScreen();
                        Game.clearScreen();
                        break;
                    }
                    cur = 2;
                }else{
                    int status = game.gameTurn(game.p2, game.p1);
                    if(status == 3) break;
                    if(game.endGame()){
                        System.out.println("P2 win");
                        game.updateRank(game.p2);
                        game.pauseScreen();
                        Game.clearScreen();
                        break;
                    }
                    cur = 1;
                }
            }

        }
    }
}