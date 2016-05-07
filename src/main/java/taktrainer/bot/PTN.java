package taktrainer.bot;

import java.text.ParseException;

import static taktrainer.bot.Board.C;
import static taktrainer.bot.Board.F;
import static taktrainer.bot.Board.S;

public class PTN {

    private static char[] columns = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};


    public static Move loadPTN(String m, Board b) throws ParseException {
        if (Character.isDigit(m.charAt(0))) {
            return loadSlide(m, b);
        }
        else {
            if (m.length() == 3) {
                switch (m.charAt(2)) {
                    case('+'):
                    case('-'):
                    case('<'):
                    case('>'):
                        return loadSlide("1" + m, b);
                }
            }
            return loadPlace(m, b);
        }
    }

    private static Move loadSlide(String m, Board b) throws ParseException {
        int at = getCell(m.charAt(1), b, m.substring(2, 3));

        int offset = getOffset(b, m.charAt(3));

        int[] parts = {1};

        if (m.charAt(0) != '1') {
            int slicesInPartition = m.length() - 4;
            parts = new int[slicesInPartition];

            for (int a = 4, x = 0; a < m.length(); a++, x++) {
                parts[x] = Integer.parseInt(m.substring(a, a+1));
            }
        }

        return new Slide(at, offset, parts);
    }

    private static Move loadPlace(String m, Board b) {
        int at;

        int p = F;
        if (m.length() == 3) {
            if (m.charAt(0) == 'S') {
                p = S;
            }
            else {
                p = C;
            }
            at = getCell(m.charAt(1), b, m.substring(2, 3));
        }
        else {
            at = getCell(m.charAt(0), b, m.substring(1, 2));
        }

        return new Place(at, p);
    }

    private static int getOffset(Board b, char c) throws ParseException {
        switch (c) {
            case('+'): return b.offUp;

            case('-'): return b.offDown;

            case('<'): return b.offLeft;

            case('>'): return b.offRight;
        }

        throw new ParseException("bad direction", 4);
    }

    private static int getCell(char c, Board b, String row) {
        int a  = columnForCharacter(c);

        final int r = b.squares - (b.size * Integer.parseInt(row));

        return a + r;
    }


    private static int columnForCharacter(char c) {
        int a;

        for (a = 0; a <  columns.length; a++) {
            if (columns[a] == c) {
                break;
            }
        }

        return a;
    }

    public static String toPTN(Board b, Slide s) {
        StringBuilder sb = new StringBuilder();

        int pieces = 0;
        for (int piece : s.partition) {
            pieces += piece;
        }
        if (pieces > 1) {
            sb.append(pieces);
        }

        sb.append(cellToColumn(b.size, s.from));
        sb.append(cellToRow(b.size, s.from));

        if (s.offset < 0) {
            if (s.offset < -1) {
                sb.append("+");
            }
            else {
                sb.append("<");
            }
        }
        else if (s.offset > 1) {
            sb.append("-");
        }
        else {
            sb.append(">");
        }

        if (pieces > 1) {
            for (int piece : s.partition) {
                sb.append(piece);
            }
        }

        return sb.toString();
    }

    public static String toPTN(Board b, Place p) {
        StringBuilder sb = new StringBuilder();

        sb.append(specialPiece(p));
        sb.append(cellToColumn(b.size, p.at));
        sb.append(cellToRow(b.size, p.at));

        return sb.toString();
    }

    private static String specialPiece(Place p) {
        if (p.piece % 10 == C) {
            return "C";
        }
        else if (p.piece % 10 == S) {
            return "S";
        }
        else {
            return "";
        }
    }

    private static String cellToRow(int size, int at) {
        return String.valueOf(size - (at / size));
    }

    private static char cellToColumn(int size, int cell) {
        return columns[cell % size];
    }
}
