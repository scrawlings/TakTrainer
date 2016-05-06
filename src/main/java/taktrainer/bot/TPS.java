package taktrainer.bot;

import java.text.ParseException;

public class TPS {

    public static String toTPS(Board b) {
        StringBuilder sb = new StringBuilder();
        sb.append("[TPS \"");

        for (int row = 0; row < b.size; row++) {
            int emptyCount = 0;
            boolean first = true;
            for (int cell = row * b.size; cell < (row + 1) * b.size; cell++) {
                if (b.board[cell] == b.EMPTY) {
                    emptyCount++;
                }
                else {
                    if (!first) {
                        sb.append(",");
                    }
                    if (emptyCount > 0) {
                        sb.append("x").append(emptyCount).append(",");
                    }

                    Cell reversedForPrinting = null;
                    Cell c = b.board[cell];

                    while (c != null) {
                        reversedForPrinting = new Cell(c.cell, reversedForPrinting);
                        c = c.rest;
                    }

                    while (reversedForPrinting != null) {
                        sb.append(reversedForPrinting.cell / 10);
                        if (reversedForPrinting.cell % 10 == b.S) {
                            sb.append("S");
                        }
                        if (reversedForPrinting.cell % 10 == b.C) {
                            sb.append("C");
                        }
                        reversedForPrinting = reversedForPrinting.rest;
                    }

                    emptyCount = 0;
                    first = false;
                }
            }
            if (emptyCount > 0) {
                if (!first) {
                    sb.append(",");
                }
                sb.append("x").append(emptyCount);
            }
            if (row + 1 < b.size) {
                sb.append("/");
            }
        }

        sb.append(" ").append(b.turn / 10);
        sb.append(" ").append(b.move);
        sb.append("\"]");
        return sb.toString();
    }


    public static void loadTPS(Board b, String tps) throws ParseException {
        String prefix = tps.substring(0, 6);

        if (!prefix.equals("[TPS \"")) {
            throw new ParseException("Invalid TPS prefix", 0);
        }

        int cell = 0;
        boolean processingCells = true;
        int a = 6;
        while (processingCells) {
            switch (tps.charAt(a)) {
                case 'x':
                    a++;
                    int run = Integer.parseInt(String.valueOf(tps.charAt(a)));
                    cell += (run - 1);
                    break;

                case '2':
                case '1':
                    int piece = (tps.charAt(a) == '1') ? b.P1 : b.P2;
                    switch (tps.charAt(a+1)) {
                        case 'S':
                            piece += b.S;
                            a++;
                            break;
                        case 'C':
                            piece += b.C;
                            a++;
                            break;
                        default:
                            piece += b.F;
                    };

                    Cell c = new Cell(piece, b.board[cell]);
                    b.board[cell] = c;
                    break;

                case ',':
                case '/':
                    cell++;
                    break;

                case ' ':
                    processingCells = false;
                    break;
            }
            a++;
        }

        boolean processingTurn = true;
        while (processingTurn) {
            if (tps.charAt(a) != ' ') {
                b.turn = 10 * Integer.parseInt(String.valueOf(tps.charAt(a)));
                processingTurn = false;
            }
            a++;
        }


        boolean processingMove = true;
        b.move = 0;
        while (processingMove) {
            if (tps.charAt(a) != ' ') {
                b.move = (b.move * 10) + Integer.parseInt(String.valueOf(tps.charAt(a)));
                if (!Character.isDigit(tps.charAt(a+1))) {
                    processingMove = false;
                }
            }
            a++;
        }
    }
}
