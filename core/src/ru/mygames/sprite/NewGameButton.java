package ru.mygames.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.mygames.base.BaseButton;
import ru.mygames.math.Rect;
import ru.mygames.screen.GameScreen;

public class NewGameButton extends BaseButton {

    private static final float HEIGHT = 0.05f;

    private final GameScreen gameScreen;

    public NewGameButton(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom()/2);
    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}
