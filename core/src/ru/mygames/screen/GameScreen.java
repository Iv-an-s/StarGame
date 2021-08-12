package ru.mygames.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.mygames.base.BaseScreen;
import ru.mygames.math.Rect;
import ru.mygames.sprite.Background;
import ru.mygames.sprite.MainShip;
import ru.mygames.sprite.Star;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private Texture bg;
    private Background background;
    private MainShip mainShip;

    private TextureAtlas atlas;
    private TextureAtlas mainAtlas;

    private Star[] stars;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);

        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }

        mainAtlas = new TextureAtlas("textures/mainAtlas.tpack");
        mainShip = new MainShip(mainAtlas);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for(Star star: stars){
            star.draw(batch);
        }
        mainShip.draw(batch);
        batch.end();
    }

    private void update(float delta) {
        for (Star star: stars){
            star.update(delta);
        }
        mainShip.update(delta);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star: stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        mainAtlas.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer,button);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }
}
