package com.notgames.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.notgames.entities.*;
import com.notgames.main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int TILE_SIZE = 16;
	
	public static final int ENTITY_SIZE = 16;
	
	public World(String path) {
		try {
            BufferedImage map = ImageIO.read(getClass().getResource("/maps/level01/tile_map.png"));
            int[] pixels = new int[map.getWidth() * map.getHeight()];
 
            WIDTH = map.getWidth();
            HEIGHT = map.getHeight();
            tiles = new Tile[map.getWidth() * map.getHeight()];
            map.getRGB(0,0,map.getWidth(),map.getHeight(),pixels,0,map.getWidth());
 
            for(int xx = 0; xx < map.getWidth(); xx++) {
                for(int yy = 0; yy < map.getHeight(); yy++) {
                    int pixelAtual = pixels[xx + (yy * map.getWidth())];
                    tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
                    
                    if(pixelAtual == 0xFF000000) {
                        //Chao
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
                    } else if(pixelAtual == 0xFF4D4D4D) {
                        //Parede
                        tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.TILE_STONEWALL);
                    } else if(pixelAtual == 0xFFFFECBC) {
                        //Areia
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_SANDFLOOR);
                    } else if(pixelAtual == 0xFF1CA3EC) {
                        //Agua
                        tiles[xx + (yy * WIDTH)] = new WaterTile(xx*16,yy*16,Tile.TILE_WATER);
                    }
                    
                }
            }
 
            BufferedImage entity_map = ImageIO.read(getClass().getResource("/maps/level01/entity_map.png"));
            int[] enity_pixels = new int[entity_map.getWidth() * entity_map.getHeight()];
            entity_map.getRGB(0,0,entity_map.getWidth(),entity_map.getHeight(),enity_pixels,0,entity_map.getWidth());
 
            for(int xx = 0; xx < entity_map.getWidth(); xx++) {
                for(int yy = 0; yy < entity_map.getHeight(); yy++) {
                    int pixelAtual = enity_pixels[xx + (yy * entity_map.getWidth())];
                       
                    if(pixelAtual == 0xFF0000FF) {
                        //Player
                        Game.player.setMask(4,-1,7,15);
                        Game.player.setX(xx*16);
                        Game.player.setY(yy*16);
                            
                    } else if(pixelAtual == 0xFFFF0000) {
                        //Inimigo
                        Enemy en = new Enemy(xx*16, yy*16, 16, 16, Entity.ENEMY_EN);
                            Game.entities.add(en);
                            Game.enemies.add(en);
                    } else if(pixelAtual == 0xFFFF6565) {
                        //Vida
                            Game.entities.add(new Lifepack(xx*16, yy*16, 16, 16, Entity.LIFEPACK_EN));
                    } else if(pixelAtual == 0xFFFFFF00) {
                        //Munição
                        Game.entities.add(new Ammo(xx*16, yy*16, 16, 16, Entity.AMMO_EN));
                    } else if(pixelAtual == 0xFF7A8400) {
                        //Canhao
                        Game.entities.add(new WeaponCannon(xx*16, yy*16, 16, 16, WeaponHandler.CANNON_RIGHT));
                    } else if(pixelAtual == 0xFFAA8400) {
                        //SMG
                        Game.entities.add(new WeaponSMG(xx*16, yy*16, 16, 16, WeaponHandler.SMG_RIGHT));
                    } else if(pixelAtual == 0xFFBE8400) {
                        //Rifle
                        Game.entities.add(new WeaponRifle(xx*16, yy*16, 16, 16, WeaponHandler.RIFLE_RIGHT));
                    }
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static boolean isFree(int xNext, int yNext) {
		int x1 = xNext/TILE_SIZE;
		int y1 = yNext/TILE_SIZE;
		
		int x2 = (xNext+TILE_SIZE-1)/TILE_SIZE;
		int y2 = yNext/TILE_SIZE;
		
		int x3 = xNext/TILE_SIZE;
		int y3 = (yNext+TILE_SIZE-1)/TILE_SIZE;
		
		int x4 = (xNext+TILE_SIZE-1)/TILE_SIZE;
		int y4 = (yNext+TILE_SIZE-1)/TILE_SIZE;
		
		return !((tiles[x1+(y1*World.WIDTH)].isWall) ||
				(tiles[x2+(y2*World.WIDTH)].isWall) ||
				(tiles[x3+(y3*World.WIDTH)].isWall) ||
				(tiles[x4+(y4*World.WIDTH)].isWall));
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
}
