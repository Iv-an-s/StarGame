package ru.mygames.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.mygames.base.Sprite;
import ru.mygames.math.Rect;
import ru.mygames.math.Rnd;

public class Star extends Sprite {

    private final Vector2 v;
    private Rect worldBounds;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        v = new Vector2(Rnd.nextFloat(-0.005f, 0.005f), Rnd.nextFloat(-0.05f, -0.1f));
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta); // сложение векторов + умножение на скаляр
        checkAndHandleBounds();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float height = Rnd.nextFloat(0.005f, 0.013f);
        setHeightProportion(height);
        float x = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float y = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(x, y);
    }

    private void checkAndHandleBounds(){
        if(getLeft() > worldBounds.getRight()){
            setRight(worldBounds.getLeft());
        }
        if(getRight() < worldBounds.getLeft()){
            setLeft(worldBounds.getRight());
        }
        if(getBottom() > worldBounds.getTop()){
            setTop(worldBounds.getBottom());
        }
        if(getTop() < worldBounds.getBottom()){
            setBottom(worldBounds.getTop());
        }
    }
}
