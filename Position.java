//PROG2 VT2020, Inlämmning 2
//Mohammed Tahmid Chowdhury moch8386

import java.util.Objects;

public class Position {
    private final int coordinateX;
    private final int coordinateY;

    public Position(int x, int y){
        this.coordinateX = x;
        this.coordinateY = y;

    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Position){
            return coordinateX == ((Position) o ).coordinateX && coordinateY ==((Position) o).coordinateY;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinateX, coordinateY);
    }

    @Override
    public String toString(){
        return  String.format("X: %d, Y: %d", coordinateX, coordinateY);
    }
}
