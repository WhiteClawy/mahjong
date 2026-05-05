public class Tile implements Comparable<Tile> {
    public static final int SPRITE_X = 300;
    public static final int SPRITE_Y = 400;

    public enum Suit {
        CIRCLES(0), BAMBOO(1), CHARACTERS(2), HONOR(3);

        public final int row;

        Suit(int row) {
            this.row = row;
        }
    }

    private Suit suit;
    private int value;

    public Suit getSuit() { return suit; }
    public int getValue() { return value; }

    private int[] spriteCoords;

    public int[] getSpriteCoords() { return spriteCoords; }

    public Tile(Suit suit, int value) {
        this.suit = suit;
        this.value = value;
        this.spriteCoords = new int[]{suit.row * SPRITE_Y, (value - 1) * SPRITE_X};
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Tile)) return false;
        Tile other = (Tile) obj;
        return this.suit == other.suit && this.value == other.value;
    }

    @Override
    public int compareTo(Tile other) {
        if (this.getSuit() != other.getSuit()) {
            return this.getSuit().row - other.getSuit().row;
        }
        return this.getValue() - other.getValue();
    }
}

