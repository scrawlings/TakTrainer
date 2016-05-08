package taktrainer.bot;

import static taktrainer.bot.Board.*;

public class Victory {

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
