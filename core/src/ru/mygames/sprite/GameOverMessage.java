package ru.mygames.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.mygames.base.Sprite;
import ru.mygames.math.Rect;

public class GameOverMessage extends Sprite {

    public GameOverMessage(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.075f);
        this.pos.set(0f, 0f);
    }
}
