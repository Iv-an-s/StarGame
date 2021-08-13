package ru.mygames.pool;

import ru.mygames.base.SpritesPool;
import ru.mygames.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newSprite() {
        return new Bullet();
    }
}
