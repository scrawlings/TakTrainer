package taktrainer;

public class At {
    final int x;
    final int y;

    public At(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        At at = (At) o;

        if (x != at.x) return false;
        return y == at.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "At(" + x + "," + y + ')';
    }
}
