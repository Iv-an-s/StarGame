package ru.mygames.pool;

import ru.mygames.base.Sprite;
import ru.mygames.base.SpritesPool;
import ru.mygames.math.Rect;
import ru.mygames.sprite.EnemyShip;
import ru.mygames.sprite.Explosion;

public class EnemyPool extends SpritesPool <EnemyShip> {

    private final Rect worldBounds;
    private final BulletPool bulletPool;
    private final ExplosionPool explosionPool;

    public EnemyPool(Rect worldBounds, BulletPool bulletPool, ExplosionPool explosionPool) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
    }

    @Override
    protected EnemyShip newSprite() {
        return new EnemyShip(worldBounds, bulletPool, explosionPool);
    }
}
