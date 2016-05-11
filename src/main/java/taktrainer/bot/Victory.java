package taktrainer.bot;

import static taktrainer.bot.Board.*;

public class Victory {
    private static int[] queue;
    private static int[] visited;
    private static int v;
    private static int front;
    private static int back;


    public static int winByRoads(Board b) {
        queue = new int[b.squares];
        visited = new int[b.squares];

        final int p1win = winByRoads(b, P1);
        final int p2win = winByRoads(b, P2);

        if (p1win == P1 && p2win == NoPlayer) {
            return P1;
        }
        if (p1win == NoPlayer && p2win == P2) {
            return P2;
        }

        return NoPlayer;
    }

    private static int winByRoads(Board b, int player) {

        v = 0;
        front = 0;
        back = 0;

        for (int a = 0; a < b.size; a++) {
            noteStartingCell(a, b, player);
        }

        if (flood(b, player, lowerEdge)) return player;

        v = 0;
        front = 0;
        back = 0;

        for (int a = 0; a < b.squares; a += b.size) {
            noteStartingCell(a, b, player);
        }

        if (flood(b, player, rightEdge)) return player;

        return NoPlayer;

    }

    private static void noteStartingCell(int a, Board b, int player) {
        if ((b.board[a] != null) && (b.board[a].cell == (player + C) || b.board[a].cell == (player + F))) {
            queue[back] = a;
            back += 1;
            visited[v] = a;
            v += 1;
        }
    }

    private static boolean flood(Board b, int player, VictoryTest victoryTest) {
        while (front != back) {
            if (queue[front] < (b.squares - b.size)) {
                if (testInDirection(b, player, b.offDown, victoryTest)) {
                    return true;
                }
            }
            if (queue[front] >= b.size) {
                if (testInDirection(b, player, b.offUp, victoryTest)) {
                    return true;
                }
            }
            if (queue[front] % b.size != b.size - 1) {
                if (testInDirection(b, player, b.offRight, victoryTest)) {
                    return true;
                }
            }
            if (queue[front] % b.size != 0) {
                if (testInDirection(b, player, b.offLeft, victoryTest)) {
                    return true;
                }
            }
            front += 1;
        }
        return false;
    }

    private static interface VictoryTest {
        public boolean test(int c, Board b);
    }

    private static class VictoryTestDown implements VictoryTest {
        @Override
        public boolean test(int c, Board b) {
            return c >= b.squares - b.size;
        }
    }

    private static class VictoryTestAcross implements VictoryTest {
        @Override
        public boolean test(int c, Board b) {
            return (c % b.size) == b.size - 1;
        }
    }

    private static final VictoryTest lowerEdge = new VictoryTestDown();
    private static final VictoryTest rightEdge = new VictoryTestAcross();


    private static boolean testInDirection(Board b, int player, int direction, VictoryTest victoryTest) {
        int s = queue[front] + direction;
        if ((b.board[s] != null) && (notYetVisited(visited, v, s) && ((b.board[s].cell == (player + C) || b.board[s].cell == (player + F))))) {
            if (victoryTest.test(s, b)) {
                return true;
            }
            else {
                queue[back] = s;
                back += 1;
                visited[v] = s;
                v += 1;
            }
        }
        return false;
    }

    private static boolean notYetVisited(int[] visited, int v, int cell) {
        for (int a = 0; a < v; a++) {
            if (visited[a] == cell) return false;
        }
        return true;
    }

    public static int flatsMajority(Board b) {
        int p1 = 0, p2 = 0;

        for (Cell c : b.board) {
            if (c != null)  {
                if (c.cell == P2F || c.cell == P2C) {
                    p2 += 1;
                }
                else if (c.cell == P1F || c.cell == P1C) {
                    p1 += 1;
                }
            }
        }

        if (p1 == p2) {
            return NoPlayer;
        }

        return (p1 > p2) ? P1 : P2;
    }
}
