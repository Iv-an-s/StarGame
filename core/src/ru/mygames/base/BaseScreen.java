package ru.mygames.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.mygames.math.MatrixUtils;
import ru.mygames.math.Rect;

public class BaseScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;

    private Rect screenBounds;
    private Rect worldBounds;
    private Rect glBounds;

    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;

    private Vector2 touch;

    @Override
    public void show() {  // метод выполняется первым, в нем инициализируем объекты (вместо метода create)
        System.out.println("show");
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        screenBounds = new Rect();
        worldBounds = new Rect();
        glBounds = new Rect(0, 0, 1f, 1f);
        worldToGl = new Matrix4();
        screenToWorld = new Matrix3();
        touch = new Vector2();
    }

    @Override
    public void render(float delta) {    // 60 раз в сек
        ScreenUtils.clear(0.5f, 0.2f, 1f, 0);
    }

    @Override
    public void resize(int width, int height) {  // срабатывает после show(),
        // а также когда по каким-либо причинам меняется размер экрана
        //  (например ручками растягиваем). В методе можно задать размеры игрового поля.
        System.out.println(String.format("resize width = %s, height = %s", width, height));
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = width / (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * aspect);
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        batch.setProjectionMatrix(worldToGl);
        resize(worldBounds);

    }

    public void resize (Rect worldBounds){
        System.out.println(String.format("worldBounds width = %s, height = %s", worldBounds.getWidth(), worldBounds.getHeight()));
    }

    @Override
    public void pause() { // срабатывает когда мы сворачиваем игру
        System.out.println("pause");
    }

    @Override
    public void resume() { // срабатывает когда разворачиваем игру
        System.out.println("resume");
    }

    @Override
    public void hide() { // вызывается, когда закрываем экран
        System.out.println("hide");
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
        batch.dispose();
    }

// методы InputProcessor

    @Override
    public boolean keyDown(int keycode) { // срабатывает при нажатии клавиши
        System.out.println("keyDown keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) { // срабатывает, когда отпускаем клавишу
        System.out.println("keyUp keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) { //
        System.out.println("keyTyped character = " + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) { // когда "тапаем" по тачскрину
        // или кликаем мышкой по экрану. Срабатывает в момент нажания. Приходят координаты точки клика.
        // pointer - это номер ... когда говорим о мультитаче, а button- номер кнопки на мыши
        System.out.println(String.format("touchDown screenX = %s, screenY = %s", screenX, screenY));
        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        touchDown(touch, pointer, button);
        return false;
    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        System.out.println(String.format("touchDown touchX = %s, touchY = %s", touch.x, touch.y));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) { // то же что и touchDown
        // но срабатывает при отпускании кнопки мыши или когда убираем палец с экрана планшета/тачпада
        System.out.println(String.format("touchUp screenX = %s, screenY = %s", screenX, screenY));
        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        touchUp(touch, pointer, button);
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        System.out.println(String.format("touchUp touchX = %s, touchY = %s", touch.x, touch.y));
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) { // срабатывает когда
        // зажали кнопку мыши, протащили не отпуская и отпустили в другом месте
        System.out.println(String.format("touchDrugged screenX = %s, screenY = %s", screenX, screenY));
        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        touchDragged(touch, pointer);
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
        System.out.println(String.format("touchDragged touchX = %s, touchY = %s", touch.x, touch.y));
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) { // срабатывает при любом движении мыши
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        System.out.println(String.format("scrolled amountX = %s, amountY = %s", amountX, amountY));
        return false;
    }
}
