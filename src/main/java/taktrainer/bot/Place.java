package taktrainer.bot;

public class Place implements Move {
    final int at;
    final int piece;

    public Place(int at, int piece) {
        this.at = at;
        this.piece = piece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Place place = (Place) o;

        if (at != place.at) return false;
        return piece == place.piece;

    }

    @Override
    public int hashCode() {
        int result = at;
        result = 31 * result + piece;
        return result;
    }
}
