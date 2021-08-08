package ru.mygames.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.mygames.base.BaseScreen;
import ru.mygames.math.Rect;
import ru.mygames.sprite.Background;
import ru.mygames.sprite.Logo;

public class MenuScreen extends BaseScreen {

    private Texture bg;
    private Background background;

    private Texture img;
    private Logo logo;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        img = new Texture("badlogic.jpg");
        background = new Background(bg);
        logo = new Logo(img);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        logo.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        logo.touchDown(touch, pointer, button);
        return false;
    }

    private void update(float delta){
        logo.update(delta);
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        logo.draw(batch);
        batch.end();
    }
}
