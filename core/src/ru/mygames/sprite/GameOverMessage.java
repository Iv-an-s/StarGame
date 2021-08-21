package ru.mygames.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.mygames.base.Sprite;
import ru.mygames.math.Rect;

public class GameOverMessage extends Sprite {

    private static final float HEIGHT = 0.08f;
    private static final float BOTTOM_MARGIN = 0.01f;

    public GameOverMessage(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        this.pos.set(0f, 0f);
        setBottom(BOTTOM_MARGIN);
    }
}
