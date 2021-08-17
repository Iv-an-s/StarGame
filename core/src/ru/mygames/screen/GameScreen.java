package ru.mygames.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import ru.mygames.base.BaseScreen;
import ru.mygames.base.Sprite;
import ru.mygames.math.Rect;
import ru.mygames.pool.BulletPool;
import ru.mygames.pool.EnemyPool;
import ru.mygames.sprite.Background;
import ru.mygames.sprite.Bullet;
import ru.mygames.sprite.EnemyShip;
import ru.mygames.sprite.MainShip;
import ru.mygames.sprite.Star;
import ru.mygames.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private Texture bg;
    private Background background;
    private MainShip mainShip;

    private TextureAtlas atlas;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private Star[] stars;

    private Music music;
    private Sound laserSound;
    private Sound bulletSound;

    private EnemyEmitter enemyEmitter;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);

        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        enemyPool = new EnemyPool(worldBounds, bulletPool);
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        mainShip = new MainShip(atlas, bulletPool, laserSound);
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        enemyEmitter = new EnemyEmitter(worldBounds, bulletSound, enemyPool, atlas);

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    private void freeAllDestroyed(){
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for(Star star: stars){
            star.draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        batch.end();
    }

    private void update(float delta) {
        for (Star star: stars){
            star.update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyEmitter.generate(delta);
    }

    private void checkCollisions(){
        List<EnemyShip> enemyActiveShips = enemyPool.getActiveSprites();
        for (int i = 0; i < enemyActiveShips.size(); i++) {
            if (!mainShip.isOutside(enemyActiveShips.get(i))){
                enemyActiveShips.get(i).destroy();
            }
        }
        List<Bullet> activeBullets = bulletPool.getActiveSprites();
        for (int i = 0; i < activeBullets.size(); i++) {
            if (!mainShip.isOutside(activeBullets.get(i))){
                activeBullets.get(i).destroy();
            }
            if (activeBullets.get(i).getOwner().equals(mainShip)){
                for (int j = 0; j < enemyActiveShips.size(); j++) {
                    if (!activeBullets.get(i).isOutside(enemyActiveShips.get(j))){
                        activeBullets.get(i).destroy();
                        enemyActiveShips.get(j).setHp(enemyActiveShips.get(j).getHp()-1);
                        if (enemyActiveShips.get(j).getHp() == 0){
                            enemyActiveShips.get(j).destroy();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star: stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        laserSound.dispose();
        bulletSound.dispose();
        music.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer,button);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }
}
