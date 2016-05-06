package taktrainer.bot;

public class PTN {

    private static String[] columns = {"a", "b", "c", "d", "e", "f", "g", "h"};

    public static String toPTN(Board b, Place p) {
        StringBuilder sb = new StringBuilder();

        sb.append(cellToColumn(b.size, p.at));
        sb.append(cellToRow(b.size, p.at));
        sb.append(specialPiece(p));

        return sb.toString();
    }

    private static String specialPiece(Place p) {
        if (p.piece % 10 == Board.C) {
            return "C";
        }
        else if (p.piece % 10 == Board.S) {
            return "S";
        }
        else {
            return "";
        }
    }

    private static String cellToRow(int size, int at) {
        return String.valueOf(size - (at / size));
    }

    private static String cellToColumn(int size, int cell) {
        return columns[cell % size];
    }
}
