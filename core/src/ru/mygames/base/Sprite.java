package ru.mygames.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.mygames.math.Rect;
import ru.mygames.utils.Regions;

public class Sprite extends Rect {

    protected float angle; // угол поворота картинки
    protected float scale = 1f; // параметр, отвечающий за скалирование, масштаб объекта. По умолчанию 1:1
    protected TextureRegion[] regions; // Т.к. анимация будет состоять из нескольких текстур
    protected int frame; // указывает на текущий элемент массива регионов

    public Sprite(TextureRegion region) { // Для того чтобы отрисовать текстуру, мы ее заворачиваем в Sprite, и вызываем метод draw()
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public Sprite(TextureRegion region, int rows, int cols, int frames){
        regions = Regions.split(region, rows, cols, frames);
    }

    public void setHeightProportion(float height){
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public void update (float delta){ // delta -константа из метода render(), символизирующая временной
        // промежуток между срабатываниями метода render(). Метод будет вызываться из render().
    }

    public void draw(SpriteBatch batch){
        batch.draw(
                regions[frame],  // рисуем текущий фрейм нашего региона
                getLeft(), getBottom(),  // выбираем точку отрисовки
                halfWidth, halfHeight, // точка поворота
                getWidth(), getHeight(), // ширина и выстота
                scale, scale, // скалирование по оси Х и скалирование по оси У
                angle // угол поворота
        );
    }

    public void resize(Rect worldBounds){

    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
        return false;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
