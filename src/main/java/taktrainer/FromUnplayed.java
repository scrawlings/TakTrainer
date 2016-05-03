package taktrainer;

public class FromUnplayed implements From {
    final Unplayed unplayed;

    public FromUnplayed(Unplayed unplayed) {
        this.unplayed = unplayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FromUnplayed that = (FromUnplayed) o;

        return unplayed != null ? unplayed.equals(that.unplayed) : that.unplayed == null;

    }

    @Override
    public int hashCode() {
        return unplayed != null ? unplayed.hashCode() : 0;
    }
}
