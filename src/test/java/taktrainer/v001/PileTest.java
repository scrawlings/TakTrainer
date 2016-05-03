package taktrainer.v001;

import org.junit.Test;
import taktrainer.v001.Pile;
import taktrainer.v001.Tile;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static taktrainer.v001.Tile.Player.A;
import static taktrainer.v001.Tile.Player.B;
import static taktrainer.v001.Tile.TileType.flat;
import static taktrainer.v001.Tile.TileType.wall;
import static taktrainer.v001.Tile.TileType.cap;
import static taktrainer.v001.Tile.as;
import static taktrainer.v001.Tile.unplayedTile;

public class PileTest {

    @Test(expected=IndexOutOfBoundsException.class)
    public void testPartitionFail() throws Exception {
        Pile pile = new Pile();
        pile.push(as(flat, unplayedTile(A)));
        pile.push(as(flat, unplayedTile(A)));
        pile.push(as(flat, unplayedTile(B)));

        pile.partitions(4);
    }

    @Test
    public void testPartitionSmearingAll() throws Exception {
        Tile tileBottom = unplayedTile(A);
        Tile tileMiddle = unplayedTile(A);
        Tile tileTop = unplayedTile(B);

        Pile pileIn = new Pile();
        pileIn.push(as(flat, tileBottom));
        pileIn.push(as(wall, tileMiddle));
        pileIn.push(as(flat, tileTop));

        Pile pileOutA = new Pile();
        pileOutA.push(as(flat, tileBottom));
        Pile pileOutB = new Pile();
        pileOutB.push(as(flat, tileMiddle));
        Pile pileOutC = new Pile();
        pileOutC.push(as(flat, tileTop));

        assertThat(pileIn.partitions(3)).containsExactly(asList(pileOutA, pileOutB, pileOutC));
    }

    @Test
    public void testPartitionComplexAll_Pile3_Parts2() throws Exception {
        Tile tileBottom = unplayedTile(A);
        Tile tileMiddle = unplayedTile(A);
        Tile tileTop = unplayedTile(B);

        Pile pileIn = new Pile();
        pileIn.push(as(flat, tileBottom));
        pileIn.push(as(wall, tileMiddle));
        pileIn.push(as(flat, tileTop));

        Pile pileOutA1 = new Pile();
        pileOutA1.push(as(flat, tileBottom));
        pileOutA1.push(as(flat, tileMiddle));
        Pile pileOutA2 = new Pile();
        pileOutA2.push(as(flat, tileTop));

        Pile pileOutB1 = new Pile();
        pileOutB1.push(as(flat, tileBottom));
        Pile pileOutB2 = new Pile();
        pileOutB2.push(as(flat, tileMiddle));
        pileOutB2.push(as(flat, tileTop));

        assertThat(pileIn.partitions(2)).containsExactly(asList(pileOutA1, pileOutA2), asList(pileOutB1, pileOutB2));
    }

    @Test
    public void testPartitionComplexAll_Pile5_Parts3() throws Exception {
        Tile tileBottom = unplayedTile(A);
        Tile tileBotMid = unplayedTile(A);
        Tile tileMiddle = unplayedTile(B);
        Tile tileTopMid = unplayedTile(B);
        Tile tileTop = unplayedTile(B);

        // abc d e
        // ab cd e
        // a bcd e
        // ab c de
        // a bc de
        // a b cde

        Pile pileIn = new Pile();
        pileIn.push(as(flat, tileBottom));
        pileIn.push(as(wall, tileBotMid));
        pileIn.push(as(wall, tileMiddle));
        pileIn.push(as(flat, tileTopMid));
        pileIn.push(as(cap, tileTop));

        Pile pileOutA1 = new Pile();
        pileOutA1.push(as(flat, tileBottom));
        pileOutA1.push(as(wall, tileBotMid));
        pileOutA1.push(as(flat, tileMiddle));
        Pile pileOutA2 = new Pile();
        pileOutA2.push(as(flat, tileTopMid));
        Pile pileOutA3 = new Pile();
        pileOutA3.push(as(flat, tileTop));
        List<Pile> partA = asList(pileOutA1, pileOutA2, pileOutA3);

        Pile pileOutB1 = new Pile();
        pileOutB1.push(as(flat, tileBottom));
        pileOutB1.push(as(wall, tileBotMid));
        Pile pileOutB2 = new Pile();
        pileOutB2.push(as(flat, tileMiddle));
        pileOutB2.push(as(flat, tileTopMid));
        Pile pileOutB3 = new Pile();
        pileOutB3.push(as(flat, tileTop));
        List<Pile> partB = asList(pileOutB1, pileOutB2, pileOutB3);

        Pile pileOutC1 = new Pile();
        pileOutC1.push(as(flat, tileBottom));
        Pile pileOutC2 = new Pile();
        pileOutC2.push(as(wall, tileBotMid));
        pileOutC2.push(as(flat, tileMiddle));
        pileOutC2.push(as(flat, tileTopMid));
        Pile pileOutC3 = new Pile();
        pileOutC3.push(as(flat, tileTop));
        List<Pile> partC = asList(pileOutC1, pileOutC2, pileOutC3);

        Pile pileOutD1 = new Pile();
        pileOutD1.push(as(flat, tileBottom));
        pileOutD1.push(as(wall, tileBotMid));
        Pile pileOutD2 = new Pile();
        pileOutD2.push(as(flat, tileMiddle));
        Pile pileOutD3 = new Pile();
        pileOutD3.push(as(flat, tileTopMid));
        pileOutD3.push(as(flat, tileTop));
        List<Pile> partD = asList(pileOutD1, pileOutD2, pileOutD3);

        Pile pileOutE1 = new Pile();
        pileOutE1.push(as(flat, tileBottom));
        Pile pileOutE2 = new Pile();
        pileOutE2.push(as(wall, tileBotMid));
        pileOutE2.push(as(flat, tileMiddle));
        Pile pileOutE3 = new Pile();
        pileOutE3.push(as(flat, tileTopMid));
        pileOutE3.push(as(flat, tileTop));
        List<Pile> partE = asList(pileOutE1, pileOutE2, pileOutE3);

        Pile pileOutF1 = new Pile();
        pileOutF1.push(as(flat, tileBottom));
        Pile pileOutF2 = new Pile();
        pileOutF2.push(as(wall, tileBotMid));
        Pile pileOutF3 = new Pile();
        pileOutF3.push(as(flat, tileMiddle));
        pileOutF3.push(as(flat, tileTopMid));
        pileOutF3.push(as(flat, tileTop));
        List<Pile> partF = asList(pileOutF1, pileOutF2, pileOutF3);

        assertThat(pileIn.partitions(3)).containsExactly(partA, partB, partC, partD, partE, partF);
    }
}