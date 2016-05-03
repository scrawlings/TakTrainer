package taktrainer.v001;

public class FromPlayed implements From {
    final At from;
    private int size;

    public FromPlayed(At from, int size) {
        this.from = from;
        this.size = size;
    }
}
