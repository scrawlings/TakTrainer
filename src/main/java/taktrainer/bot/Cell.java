package taktrainer.bot;

public class Cell {
    int cell;
    Cell stack;

    public Cell(int cell, Cell stack) {
        this.cell = cell;
        this.stack = stack;
    }
}
