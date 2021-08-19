package ru.mygames.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.mygames.base.BaseButton;
import ru.mygames.math.Rect;
import ru.mygames.pool.BulletPool;
import ru.mygames.pool.EnemyPool;
import ru.mygames.pool.ExplosionPool;

public class NewGameButton extends BaseButton {

    private final MainShip mainShip;
    private final EnemyPool enemyPool;
    private final BulletPool bulletPool;
    private final ExplosionPool explosionPool;


    public NewGameButton(TextureAtlas atlas, MainShip mainShip, EnemyPool enemyPool, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(atlas.findRegion("button_new_game"));
        this.mainShip = mainShip;
        this.enemyPool = enemyPool;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.05f);
        setBottom(worldBounds.getBottom()/2);
    }

    @Override
    public void action() {
        enemyPool.destroyAllActiveSprites();
        bulletPool.destroyAllActiveSprites();
        explosionPool.destroyAllActiveSprites();
        mainShip.flushDestroy();
    }
}
