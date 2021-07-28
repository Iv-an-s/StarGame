package ru.mygames;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class StarGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture wallpaper;
	TextureRegion region;

	//private int x = 0;
	
	@Override
	public void create () { // срабатывает при старте игры, и нужен для инициализации
		// различных объектов, которые нам понадобятся
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		wallpaper = new Texture("imageForHW1.jpg");
		region = new TextureRegion(img, 25, 25, 100, 50);
	}

	@Override
	public void render () { // срабатывает 60 раз в сек. Метод можно представить как бесконечный
		// цикл, в котором разворачиваются все события. Например batch отрисовывает все графические
		// объекты.
//		x++;
		ScreenUtils.clear(1f, 1f, 1f, 0); // очистка экрана перед следующим кадром. Иначе наложение следующего кадра на предыдущий
		batch.begin(); // метод говорит, что сейчас графическому процессору будет передан список
		// объектов на отрисовку
		batch.draw(wallpaper, 0, 0);
		//batch.draw(img, x, 0); // с помощью данного метода рисуем нужную текстуру с
		// определенными параметрами
		//batch.setColor(0.56f, 0.32f, 0.67f, 0.5f);
		//batch.draw(img, 300, 300, 100, 100);
		//batch.setColor(1f, 1f, 1f, 0.3f);
		//batch.draw(region, 230, 50, 100, 50);
		batch.end(); // при срабатывании метода end дается команда все отрисовать
	}
	
	@Override
	public void dispose () {  // срабатывает в самом конце. Его задача - очистить память.
		batch.dispose();
		img.dispose();
	}
}
