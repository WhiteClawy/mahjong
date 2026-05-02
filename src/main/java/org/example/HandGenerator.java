package org.example;

import java.util.ArrayList;
import java.util.Collections;

public class HandGenerator {
    final private static int NUMBER_OF_COPIES = 4;
    public static GameState generate() {
        Hand hand = new Hand();
        ArrayList<Tile> deck = new ArrayList<>();
        for(Tile.Suit suit : Tile.Suit.values()){
            if(suit == Tile.Suit.HONOR) continue;
            for (int i = 1; i < 10; i++) {
                for (int j = 0; j < NUMBER_OF_COPIES; j++) {
                    deck.add(new Tile(suit, i));
                }
            }
        }
        for(int i = 1; i <= 7; i++){
            for(int j = 0; j < NUMBER_OF_COPIES; j++){
                deck.add(new Tile(Tile.Suit.HONOR, i));
            }
        }


        Collections.shuffle(deck);

        for (int i = 0; i < Hand.MAX_HAND_SIZE; i++) {
            hand.addTile(deck.get(0));
            deck.remove(deck.get(0));
        }

        return new GameState(hand, deck);
    }
}
