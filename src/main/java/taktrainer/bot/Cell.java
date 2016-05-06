package taktrainer.bot;

public class Cell {
    int cell;
    Cell rest;

    public Cell(int cell, Cell rest) {
        this.cell = cell;
        this.rest = rest;
    }
}
