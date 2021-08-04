package ru.mygames.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.mygames.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private static final float V_LEN = 0.5f;

    private Texture img;
    private Vector2 pos;
    private Vector2 v;
    private Vector2 touch;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        pos = new Vector2();
        v = new Vector2();
        touch = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(img, pos.x, pos.y);
        batch.end();
        if (touch.dst(pos) > V_LEN){
            pos.add(v);
        }else{
            pos.set(touch);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY); // манипуляция с Y нужна чтобы событийную
        // систему координат с началом отсчета в верхнем левом углу привести к системе координат для
        // отрисовки, где ноль по Y внизу.
        v.set(touch.cpy().sub(pos)).setLength(V_LEN);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        pos.set(screenX, Gdx.graphics.getHeight() - screenY);
        return false;
    }
}
