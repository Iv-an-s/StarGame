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
    private Logo logo;
    //private Vector2 pos;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        logo = new Logo();
        //pos = new Vector2();
        // подставим единичную матрицу проекций вместо дефолтной
        // (сможем вводить коориднаты в сетке OpenGL от центра)
        //batch.getProjectionMatrix().idt(); // Метод idt() превращает дефолтную матрицу проекций в единичную
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        logo.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        logo.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        logo.touchDown(touch, pointer, button);
        return false;
    }
}
