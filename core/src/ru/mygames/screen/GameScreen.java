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
import ru.mygames.pool.ExplosionPool;
import ru.mygames.sprite.Background;
import ru.mygames.sprite.Bullet;
import ru.mygames.sprite.EnemyShip;
import ru.mygames.sprite.ExitButton;
import ru.mygames.sprite.GameOverMessage;
import ru.mygames.sprite.MainShip;
import ru.mygames.sprite.NewGameButton;
import ru.mygames.sprite.Star;
import ru.mygames.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private Texture bg;
    private Background background;
    private MainShip mainShip;

    private TextureAtlas atlas;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private Star[] stars;
    private GameOverMessage gameOverMessage;
    private NewGameButton newGameButton;

    private Music music;
    private Sound laserSound;
    private Sound bulletSound;
    private Sound explosionSound;

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
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyPool = new EnemyPool(worldBounds, bulletPool, explosionPool);
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        mainShip = new MainShip(atlas, bulletPool, explosionPool, laserSound);
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        enemyEmitter = new EnemyEmitter(worldBounds, bulletSound, enemyPool, atlas);

        gameOverMessage = new GameOverMessage(atlas);
        newGameButton = new NewGameButton(atlas, this);

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
    }

    public void startNewGame(){
        mainShip.startNewGame();
        bulletPool.freeAllActiveSprites();
        enemyPool.freeAllActiveSprites();
        explosionPool.freeAllActiveSprites();
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
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for(Star star: stars){
            star.draw(batch);
        }
        if(!mainShip.isDestroyed()){
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        }else{
            gameOverMessage.draw(batch);
            if (explosionPool.getActiveSprites().isEmpty()){
                newGameButton.draw(batch);
            }
        }
        explosionPool.drawActiveSprites(batch);
        batch.end();
    }

    private void update(float delta) {
        for (Star star: stars){
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (!mainShip.isDestroyed()){
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta);
        } else {
            gameOverMessage.update(delta);
            newGameButton.update(delta);
        }
    }

    private void checkCollisions(){
        if(mainShip.isDestroyed()){
            return;
        }
        List<EnemyShip> enemyShipList = enemyPool.getActiveSprites();
        for(EnemyShip enemyShip : enemyShipList){
            if (enemyShip.isDestroyed()){
                continue;
            }
            float minDist = enemyShip.getHalfWidth() + mainShip.getHalfWidth();
            if (mainShip.pos.dst(enemyShip.pos) < minDist){
                mainShip.damage(enemyShip.getBulletDamage() * 2);
                enemyShip.destroy();
            }
        }

        List<Bullet> bulletList = bulletPool.getActiveSprites();
        for (Bullet bullet : bulletList){
            if (bullet.isDestroyed()){
                continue;
            }
            for (EnemyShip enemyShip : enemyShipList){
                if (enemyShip.isDestroyed() || bullet.getOwner()!= mainShip){
                    continue;
                }
                if (enemyShip.isBulletCollision(bullet)){
                    enemyShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
            }
            if (bullet.getOwner()!= mainShip && mainShip.isBulletCollision(bullet)){
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
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
        gameOverMessage.resize(worldBounds);
        newGameButton.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        laserSound.dispose();
        bulletSound.dispose();
        explosionSound.dispose();
        music.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (mainShip.isDestroyed()){
            newGameButton.touchDown(touch, pointer, button);
        }else{
            mainShip.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (mainShip.isDestroyed()){
            newGameButton.touchUp(touch, pointer, button);
        }else {
            mainShip.touchUp(touch, pointer,button);
        }
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
