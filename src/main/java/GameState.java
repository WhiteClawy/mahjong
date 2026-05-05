import java.util.ArrayList;

public class GameState {
    public Hand hand;
    public ArrayList<Tile> deck;

    public GameState(Hand hand, ArrayList<Tile> deck) {
        this.hand = hand;
        this.deck = deck;
    }
}

