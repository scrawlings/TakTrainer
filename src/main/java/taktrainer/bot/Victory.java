package taktrainer.bot;

import static taktrainer.bot.Board.*;

public class Victory {


    public static int winByRoads(Board b) {
        return winByRoads(b, P1);
    }

    private static int winByRoads(Board b, int player) {
        int[] queue = new int[b.squares];
        int[] visited = new int[b.squares];
        int v = 0;
        int front = 0;
        int back = 0;

        for (int a = 0; a < b.size; a++) {
            if ((b.board[a] != null) && (b.board[a].cell == (player + C) || b.board[a].cell == (player + F))) {
                queue[back] = a;
                back += 1;
                visited[v] = a;
                v += 1;
            }
        }

        while (front != back) {
            if (queue[front] < (b.squares - b.size)) {
                int s = queue[front] + b.offDown;
                if ((b.board[s] != null) && (notYetVisited(visited, v, s) && ((b.board[s].cell == (player + C) || b.board[s].cell == (player + F))))) {
                    if (s >= b.squares - b.size) {
                        return player;
                    }
                    else {
                        queue[back] = s;
                        back += 1;
                        visited[v] = b.board[s].cell;
                        v += 1;
                    }
                }
            }
            front += 1;
        }

        return NoPlayer;

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
