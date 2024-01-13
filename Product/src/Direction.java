public enum Direction {
    NORTH (-1, 0, "Bac"), SOUTH(1, 0, "Nam"),  EAST(0, 1, "Dong"), WEST(0, -1,  "Tay");
    public final int dx;
    public final int dy;
    public final String vietnammese;
    private Direction(int dx, int dy, String vietnammese){
        this.dx = dx;
        this.dy = dy;
        this.vietnammese = vietnammese;
    }
}