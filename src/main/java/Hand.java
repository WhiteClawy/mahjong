import java.util.ArrayList;
import java.util.Collections;

public class Hand {
    public static final int MAX_HAND_SIZE = 14;
    private ArrayList<Tile> hand;
    private ArrayList<Tile> discardPile;

    public Hand() {
        this.hand = new ArrayList<Tile>();
        this.discardPile = new ArrayList<>();
    }

    public ArrayList<Tile> getTiles() { return hand; }

    public void addTile(Tile tile) {
        if (hand.size() < MAX_HAND_SIZE) {
            hand.add(tile);
        } else {
            System.out.println("Too many tiles idiot");
        }
    }

    public void discardTile(Tile tile) {
        if (hand.contains(tile)) {
            hand.remove(tile);
            discardPile.add(tile);
        } else {
            System.out.println("Tile is not in hand");
        }
    }

    public void sortHand() {
        Collections.sort(hand);
    }
}

