package ru.mygames.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Regions {

    /**
     * Более удобный аналог метода split(int tileWidth, int tileHeight) класса TextureRegion
     * Разбивает TextureRegion на фреймы
     * @param region регион
     * @param rows количество строк
     * @param cols количество столбцов
     * @param frames количество фреймов
     * @return массив регионов
     */

    public static TextureRegion[] split (TextureRegion region, int rows, int cols, int frames){
        if(region == null){
            throw new RuntimeException("Split null region");
        }
        TextureRegion[] regions = new TextureRegion[frames];
        int tileWidth = region.getRegionWidth() / cols;
        int tileHeigth = region.getRegionHeight() / rows;

        int frame = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                regions[frame] = new TextureRegion(region, tileWidth * j, tileHeigth * i, tileWidth, tileHeigth);
                if(frame == frames -1) {
                    return regions;
                }
                    frame++;
                }
            }
        return regions;
    }
}
