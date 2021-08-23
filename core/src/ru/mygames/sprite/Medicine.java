package ru.mygames.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.mygames.base.Sprite;
import ru.mygames.math.Rect;
import ru.mygames.math.Rnd;
import ru.mygames.pool.ExplosionPool;

public class Medicine extends Sprite {

    private static final float MEDICINE_HEIGHT = 0.04f;
    private static final float RELOAD_INTERVAL = 10f;

    private ExplosionPool explosionPool;

    private final Vector2 v;
    private Rect worldBounds;
    private float reloadTimer;

    public Medicine(TextureAtlas atlas, ExplosionPool explosionPool) {
        super(atlas.findRegion("medcine1"));
        this.v = new Vector2(0f, -0.2f);
        this.explosionPool = explosionPool;
        destroy();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getTop() < worldBounds.getBottom() || isDestroyed()){
            reloadTimer += delta;
            if (reloadTimer > RELOAD_INTERVAL){
                flushDestroy();
                setBottom(worldBounds.getTop());
                pos.x = getX();
                reloadTimer = 0f;
            }
        }
        pos.mulAdd(v, delta);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if(!this.isDestroyed()) {
            super.draw(batch);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(MEDICINE_HEIGHT);
        setBottom(worldBounds.getTop());
        pos.x = getX();
    }

    private float getX(){
        return Rnd.nextFloat(worldBounds.getLeft() + getHalfWidth(), worldBounds.getRight() - getHalfWidth());
    }

    public boolean isBulletCollision(Bullet bullet){
        return !(
                bullet.getRight() < getLeft()
                        || bullet.getLeft() > getRight()
                        || bullet.getBottom() > getTop()
                        || bullet.getTop() < pos.y
        );
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    private void boom(){
        Explosion explosion = explosionPool.obtain();
        explosion.set(pos, getHeight()*2);

    }
}
