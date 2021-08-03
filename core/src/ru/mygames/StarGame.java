package ru.mygames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;

import ru.mygames.screen.MenuScreen;


public class StarGame extends Game {

	@Override
	public void create () {
		setScreen(new MenuScreen()); // устанавливаем первоначальный экран
	}
}
