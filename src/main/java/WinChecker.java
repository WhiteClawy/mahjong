import java.util.ArrayList;
import java.util.Collections;

public class WinChecker {
    public static boolean isWinningHand(Hand hand) {
        ArrayList<Tile> tiles = new ArrayList<>(hand.getTiles());
        Collections.sort(tiles);
        Counts counts = Counts.fromTiles(tiles);
        return canPartitionIntoGroups(counts);
    }

    
    private static boolean canPartitionIntoGroups(Counts counts) {
        if (counts.total == 0) return true;

        TileKey first = counts.findFirst();
        if (first == null) return true;

        int c = counts.get(first);

        // Pair
        if (c >= 2) {
            counts.dec(first, 2);
            if (canPartitionIntoGroups(counts)) return true;
            counts.inc(first, 2);
        }

        // Triple
        if (c >= 3) {
            counts.dec(first, 3);
            if (canPartitionIntoGroups(counts)) return true;
            counts.inc(first, 3);
        }

        // Sequence (only suited tiles)
        if (first.suit != Tile.Suit.HONOR && first.value <= 7) {
            TileKey k1 = new TileKey(first.suit, first.value + 1);
            TileKey k2 = new TileKey(first.suit, first.value + 2);
            if (counts.get(k1) > 0 && counts.get(k2) > 0) {
                counts.dec(first, 1);
                counts.dec(k1, 1);
                counts.dec(k2, 1);
                if (canPartitionIntoGroups(counts)) return true;
                counts.inc(k2, 1);
                counts.inc(k1, 1);
                counts.inc(first, 1);
            }
        }

        return false;
    }

    private record TileKey(Tile.Suit suit, int value) {}

    private static final class Counts {
        // suits by row: 0 circles, 1 bamboo, 2 characters => values 1..9; 3 honor => values 1..7
        private final int[][] bySuitValue = new int[4][10];
        private int total = 0;

        static Counts fromTiles(ArrayList<Tile> tiles) {
            Counts c = new Counts();
            for (Tile t : tiles) {
                int s = t.getSuit().row;
                int v = t.getValue();
                if (s < 0 || s >= 4) continue;
                if (v < 1 || v >= c.bySuitValue[s].length) continue;
                c.bySuitValue[s][v]++;
                c.total++;
            }
            return c;
        }

        int get(TileKey k) {
            return bySuitValue[k.suit.row][k.value];
        }

        void dec(TileKey k, int n) {
            bySuitValue[k.suit.row][k.value] -= n;
            total -= n;
        }

        void inc(TileKey k, int n) {
            bySuitValue[k.suit.row][k.value] += n;
            total += n;
        }

        TileKey findFirst() {
            for (Tile.Suit suit : Tile.Suit.values()) {
                int s = suit.row;
                int maxV = (suit == Tile.Suit.HONOR) ? 7 : 9;
                for (int v = 1; v <= maxV; v++) {
                    if (bySuitValue[s][v] > 0) {
                        return new TileKey(suit, v);
                    }
                }
            }
            return null;
        }
    }
}

