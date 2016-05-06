package taktrainer.bot;

public class Place implements Move {
    final int at;
    final int piece;

    public Place(int at, int piece) {
        this.at = at;
        this.piece = piece;
    }
}
