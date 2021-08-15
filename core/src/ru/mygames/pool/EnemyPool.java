package ru.mygames.pool;

import ru.mygames.base.Sprite;
import ru.mygames.base.SpritesPool;
import ru.mygames.math.Rect;
import ru.mygames.sprite.EnemyShip;

public class EnemyPool extends SpritesPool <EnemyShip> {

    private final Rect worldBounds;
    private final BulletPool bulletPool;

    public EnemyPool(Rect worldBounds, BulletPool bulletPool) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
    }

    @Override
    protected EnemyShip newSprite() {
        return new EnemyShip(worldBounds, bulletPool);
    }
}
