package ru.mygames;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class StarGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture background;
	TextureRegion region;

	//private int x = 0;
	
	@Override
	public void create () { // срабатывает при старте игры, и нужен для инициализации
		// различных объектов, которые нам понадобятся
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		background = new Texture("imageForHW1.jpg");
		//region = new TextureRegion(img, 25, 25, 100, 50);

		Vector2 v1 = new Vector2(3,2);
		Vector2 v2 = new Vector2();
		v2.set(2,1);

		v1.add(v2);
		System.out.println(String.format("v1.add(v2), v1.x = %s, v1.y = %s", v1.x, v1.y));

		v1.set(7, 7);
		v2.set(3, 1);
		v1.sub(v2);
		System.out.println(String.format("v1.sub(v2), v1.x = %s, v1.y = %s", v1.x, v1.y));
		System.out.println("v1.len() = " + v1.len());
		v1.scl(0.5f); // скалирование. Умножение вектора на скаляр
		System.out.println("v1.len() = after scl " + v1.len());

		v1.nor(); // нормализация. Приведение вектора к единичной длине. Например для участия в расчетах
		// где нужно учитывать направление, но не влиять на величину другого вектора
		System.out.println("v1.len() = after nor " + v1.len());

		v2.cpy(); // используется когда нужна операция над копией вектора, но без изменения
		// исходного вектора
		// опасый метод, т.к. если попадает в render, то 60 раз в секунду будет создаваться новый
		// объект
		System.out.println("v2.x = " + v2.x + "v2.y = " + v2.y);
		v2.cpy().add(v1);
		System.out.println("v2.x = " + v2.x + "v2.y = " + v2.y);

		// Скалярное произведение векторов. Используется для определения угла между векторами.

		v1.set(1, 1);
		v2.set(-1, 1);
		v1.nor();
		v2.nor();
		System.out.println(Math.acos(v1.dot(v2)));

	}

	@Override
	public void render () { // срабатывает 60 раз в сек. Метод можно представить как бесконечный
		// цикл, в котором разворачиваются все события. Например batch отрисовывает все графические
		// объекты.
//		x++;
		ScreenUtils.clear(1f, 1f, 1f, 0); // очистка экрана перед следующим кадром. Иначе наложение следующего кадра на предыдущий
		batch.begin(); // метод говорит, что сейчас графическому процессору будет передан список
		// объектов на отрисовку
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(img, 0, 0); // с помощью данного метода рисуем нужную текстуру с
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
		background.dispose();
	}
}
