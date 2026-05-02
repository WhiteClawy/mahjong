package org.example;

import java.util.ArrayList;
import java.util.Collections;

public class WinChecker {
    public static boolean isWinningHand(Hand hand){
        ArrayList<Tile> sortedTiles = new ArrayList<>(hand.getTiles());
        Collections.sort(sortedTiles);
        return canFormComb(sortedTiles, false, 0);
    }
    private static boolean canFormComb(ArrayList<Tile> tiles, boolean hasPair, int setsFound) {
        if(tiles.size() < 2 && !hasPair) return false;
        if(tiles.size() < 3 && hasPair) return false;
        Tile t0 = tiles.get(0);

        if (!hasPair && t0.equals(tiles.get(1))) {
            Tile r0 = tiles.remove(0);
            Tile r1 = tiles.remove(0);
            if(canFormComb(tiles, true, setsFound)) return true;
            tiles.add(0, r1);
            tiles.add(0, r0);
        }

        if (tiles.size() >= 3 && t0.equals(tiles.get(1)) && t0.equals(tiles.get(2))) {
            Tile r0 = tiles.remove(0);
            Tile r1 = tiles.remove(0);
            Tile r2 = tiles.remove(0);
            if(canFormComb(tiles, hasPair, setsFound + 1)) return true;
            tiles.add(0, r2);
            tiles.add(0, r1);
            tiles.add(0, r0);
        }

        if (tiles.size() >= 3 && t0.getSuit() != Tile.Suit.HONOR) {
            Tile r0 = tiles.remove(0);
            Tile r1 = findAndRemove(tiles, t0.getSuit(), t0.getValue() + 1);
            Tile r2 = findAndRemove(tiles, t0.getSuit(), t0.getValue() + 2);
            if(r1 != null && r2 != null){
                if(canFormComb(tiles, hasPair, setsFound + 1)) return true;
            }
            if(r2 != null) tiles.add(0, r2);
            if(r1 != null) tiles.add(0, r1);
            tiles.add(0, r0);
        }
        return false;
    }
    private static Tile findAndRemove(ArrayList<Tile> tiles, Tile.Suit suit, int value){
        for(int i = 0; i < tiles.size(); i++){
            Tile t = tiles.get(i);
            if(t.getSuit() == suit && t.getValue() == value){
                return tiles.remove(i);
            }
        }
        return null;
    }
}
