package org.example;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;

public class MainWindow extends Application {

    public static final int JUMP_HEIGHT = -30;
    public static final int JUMP_DURATION = 180;


    @Override
    public void start(Stage stage){
        stage.setTitle("Mahjong or something");
        stage.setMaximized(true);
        Image spriteSheet = new Image("C:\\Users\\иван\\IdeaProjects\\Majongg\\src\\main\\java\\org\\example\\MajongSpriteSheet.png");
        ImageView drawPile = new ImageView(spriteSheet);
        ImageView discardPile = new ImageView(spriteSheet);

        drawPile.setViewport(new Rectangle2D(2100, 1200, Tile.SPRITE_X, Tile.SPRITE_Y));
        discardPile.setViewport(new Rectangle2D(2400, 1200, Tile.SPRITE_X, Tile.SPRITE_Y));
        drawPile.setFitWidth(100);
        drawPile.setFitHeight(133);
        discardPile.setFitWidth(100);
        discardPile.setFitHeight(133);


//        ImageView testTile = new ImageView(spriteSheet);
//        testTile.setViewport(new Rectangle2D(0, 0, 300, 400));


        // 1-1-1, 2-2-2, 3-3-3, 4-4-4, 5-5
//        Hand test1 = new Hand();
//        test1.addTile(new Tile(Tile.Suit.BAMBOO, 1));
//        test1.addTile(new Tile(Tile.Suit.BAMBOO, 1));
//        test1.addTile(new Tile(Tile.Suit.BAMBOO, 1));
//        test1.addTile(new Tile(Tile.Suit.BAMBOO, 2));
//        test1.addTile(new Tile(Tile.Suit.BAMBOO, 2));
//        test1.addTile(new Tile(Tile.Suit.BAMBOO, 2));
//        test1.addTile(new Tile(Tile.Suit.BAMBOO, 3));
//        test1.addTile(new Tile(Tile.Suit.BAMBOO, 3));
//        test1.addTile(new Tile(Tile.Suit.BAMBOO, 3));
//        test1.addTile(new Tile(Tile.Suit.BAMBOO, 4));
//        test1.addTile(new Tile(Tile.Suit.BAMBOO, 4));
//        test1.addTile(new Tile(Tile.Suit.BAMBOO, 4));
//        test1.addTile(new Tile(Tile.Suit.BAMBOO, 5));
//        test1.addTile(new Tile(Tile.Suit.BAMBOO, 5));
//        Hand test4 = new Hand();
//        test4.addTile(new Tile(Tile.Suit.BAMBOO, 1));
//        test4.addTile(new Tile(Tile.Suit.BAMBOO, 2));
//        test4.addTile(new Tile(Tile.Suit.BAMBOO, 3));
//        test4.addTile(new Tile(Tile.Suit.BAMBOO, 4));
//        test4.addTile(new Tile(Tile.Suit.BAMBOO, 5));
//        test4.addTile(new Tile(Tile.Suit.BAMBOO, 6));
//        test4.addTile(new Tile(Tile.Suit.BAMBOO, 7));
//        test4.addTile(new Tile(Tile.Suit.BAMBOO, 8));
//        test4.addTile(new Tile(Tile.Suit.BAMBOO, 8));
//        test4.addTile(new Tile(Tile.Suit.CIRCLES, 1));
//        test4.addTile(new Tile(Tile.Suit.CIRCLES, 2));
//        test4.addTile(new Tile(Tile.Suit.CIRCLES, 3));
//        test4.addTile(new Tile(Tile.Suit.BAMBOO, 5));
//        test4.addTile(new Tile(Tile.Suit.BAMBOO, 5));
        VBox main = new VBox(50);
        main.setAlignment(Pos.CENTER);
        Label title = new Label("Mahjong or Something");
        title.setFont(Font.font(24));
        title.setAlignment(Pos.CENTER);

        ArrayList<Tile> selectedTiles = new ArrayList<Tile>();
        //ArrayList<ImageView> selectedTilesSprites = new ArrayList<>();
        HashMap<Tile, ImageView> tileViews = new HashMap<>();

        HBox handSprites = new HBox(10);
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        handSprites.setAlignment(Pos.CENTER);
        Button generateBtn = new Button("Generate Hand");
        boolean[] isHandGenerated = {false};
        boolean[] isWin = {false};
        Button checkBtn = new Button("Are u winning son?");
        Label winState = new Label("...");
        buttons.getChildren().add(generateBtn);
        buttons.getChildren().add(checkBtn);
        buttons.getChildren().add(drawPile);
        buttons.getChildren().add(discardPile);

        GameState game[] = {HandGenerator.generate()};
        //GameState game = new GameState(test4, null);
        //ArrayList<Tile> hand = game[0].hand.getTiles();

        discardPile.setOnMouseClicked(event ->{
            ArrayList<Tile> tilesToDiscard = new ArrayList<>(selectedTiles);
            int[] remaining = {tilesToDiscard.size()};
            selectedTiles.clear();
            for (Tile tile : tilesToDiscard) {
                ImageView t = tileViews.get(tile);
                ImageView copy = t;
                moveToDiscardAnimation(t, discardPile, () -> {
                    game[0].hand.discardTile(tile);
                    Tile newTile = game[0].deck.remove(0);
                    //moveToHandAnimation(tile.newTile, copy);
                    game[0].hand.addTile(newTile);
                    remaining[0]--;
                    if(remaining[0] == 0){  // ✅ only when ALL discards done!
                        game[0].hand.sortHand();
                        isWin[0] = WinChecker.isWinningHand(game[0].hand);
                        showHand(handSprites, game[0].hand.getTiles(), spriteSheet, selectedTiles, tileViews);
                    }
                });
            }
        });

        checkBtn.setOnAction(event -> {
            if(isHandGenerated[0]) winState.setText(isWin[0] ? "Nah, I`d win." : "i`m cooked");
            else winState.setText("Bro, where is the hand");
        });

        generateBtn.setOnAction(event -> {
            generateBtn.setText("Regenerate hand");
            game[0] = HandGenerator.generate();
            game[0].hand.sortHand();
            selectedTiles.clear();
            isHandGenerated[0] = true;
            isWin[0] = WinChecker.isWinningHand(game[0].hand);
//            if(isHandGenerated[0]) winState.setText(isWin[0] ? "Nah, I`d win." : "i`m cooked");
//            else winState.setText("Bro, where is the hand");
            showHand(handSprites, game[0].hand.getTiles(), spriteSheet, selectedTiles, tileViews);
        });





        main.getChildren().add(title);
        main.getChildren().add(buttons);
        main.getChildren().add(winState);
        main.getChildren().add(handSprites);

        Scene scene = new Scene(main, 400, 500);


        stage.setScene(scene);
        stage.show();
    }
    private static void showHand(HBox hbox, ArrayList<Tile> hand, Image spriteSheet, ArrayList<Tile> selectedTiles, HashMap<Tile, ImageView> tileViews){
        hbox.getChildren().clear();
        for (int i = 0; i < Hand.MAX_HAND_SIZE; i++) {
            ImageView t = new ImageView(spriteSheet);
            int index = i;
            int[] coords = hand.get(i).getSpriteCoords();
            boolean[] isSelected = {false};
            tileViews.put(hand.get(i), t);
            t.setViewport(new Rectangle2D(coords[1], coords[0], Tile.SPRITE_X, Tile.SPRITE_Y));
            t.setFitWidth(100);
            t.setFitHeight(133);
            t.setOnMouseEntered(event -> {
                if(!isSelected[0]){
                    TranslateTransition anim = new TranslateTransition(Duration.millis(JUMP_DURATION), t);
                    anim.setToY(JUMP_HEIGHT);
                    anim.play();
                }
            });
            t.setOnMouseExited(event -> {
                if(!isSelected[0]){
                    TranslateTransition anim = new TranslateTransition(Duration.millis(JUMP_DURATION), t);
                    anim.setToY(0);
                    anim.play();
                }
            });
            t.setOnMouseClicked(event -> {
                isSelected[0] = !isSelected[0];
                if(isSelected[0]){
                    selectedTiles.add(hand.get(index));
                }else{
                    selectedTiles.remove(hand.get(index));
                }
                TranslateTransition anim = new TranslateTransition(Duration.millis(JUMP_DURATION), t);
                anim.setToY(isSelected[0] ? JUMP_HEIGHT : 0);
                anim.play();
            });
            hbox.getChildren().add(t);
        }
    }
    private static void moveToDiscardAnimation(ImageView tile, ImageView to, Runnable onFinished){
        double targetX = to.localToScene(0, 0).getX() - tile.localToScene(0, 0).getX();
        double targetY = to.localToScene(0, 0).getY() - tile.localToScene(0, 0).getY();

        TranslateTransition move = new TranslateTransition(Duration.millis(JUMP_DURATION*2), tile);
        FadeTransition fade = new FadeTransition(Duration.millis(JUMP_DURATION*2), tile);
        move.setToX(targetX);
        move.setToY(targetY);
        fade.setToValue(0);

        ParallelTransition anim = new ParallelTransition(move, fade);
        anim.setOnFinished(event -> onFinished.run());
        anim.play();
    }
    private static void moveToHandAnimation(ImageView tile, ImageView to, Runnable onFinished){
        TranslateTransition move = new TranslateTransition(Duration.millis(JUMP_DURATION), tile);
        move.setToX(to.getY());
        move.setToY(to.getY());
        move.setOnFinished(event -> onFinished.run());
        move.play();
    }
    public static void main(String[] args){
        launch(args);
    }
}