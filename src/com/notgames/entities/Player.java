package com.notgames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.notgames.graficos.Spritesheet;
import com.notgames.graficos.UI;
import com.notgames.main.Game;
import com.notgames.world.Camera;
import com.notgames.world.World;

public class Player extends Entity {
	
	public boolean left,right,up,down;
	public int rightDir = 0, leftDir = 1, upDir = 2, downDir = 3, nullDir = 4;
	public int dir = nullDir;
	public int curAnimation = 0, curFrames = 0, targetFrames = 80;
	private int damageFrames = 0;
	
	public double health = 100, maxHealth = 100;
	public double spd = 0.1;
	public int LifePackAmount = 0;
	public int ammo = 0;
	
	public boolean isDamaged = false;
	
	public boolean hasSMG = false;
	public boolean hasCannon = false;
	public boolean hasRifle = false;
	
	public boolean equippedSMG = false;
	public boolean equippedCannon = false;
	public boolean equippedRifle = false;
	
	public boolean isShooting = false, mouseIsShooting = false;
	
	public int mx, my;
	
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	private BufferedImage damageRightPlayer;
	private BufferedImage damageLeftPlayer;
	private BufferedImage damageUpDownPlayer;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[2];
		leftPlayer = new BufferedImage[2];
		upPlayer = new BufferedImage[2];
		downPlayer = new BufferedImage[2];
		
		rightPlayer[0] = Game.playerSpritesheet.getSprite(16,0,16,16);
		rightPlayer[1] = Game.playerSpritesheet.getSprite(32,0,16,16);
		
		leftPlayer[0] = Game.playerSpritesheet.getSprite(16,16,16,16);
		leftPlayer[1] = Game.playerSpritesheet.getSprite(32,16,16,16);
		
		downPlayer[0] = Game.playerSpritesheet.getSprite(16,32,16,16);
		downPlayer[1] = Game.playerSpritesheet.getSprite(32,32,16,16);
		
		upPlayer[0] = Game.playerSpritesheet.getSprite(16,48,16,16);
		upPlayer[1] = Game.playerSpritesheet.getSprite(32,48,16,16);
		
		damageRightPlayer = Game.playerSpritesheet.getSprite(0,64,16,16);
		damageLeftPlayer = Game.playerSpritesheet.getSprite(16,64,16,16);
		damageUpDownPlayer = Game.playerSpritesheet.getSprite(32,64,16,16);
			
}
	public void tick() {
		moved = false;
		if(right && World.isFree((int)(x+spd),this.getY())) {
			moved = true;
			dir = rightDir;
			x+=spd;
		} else if(left && World.isFree((int)(x-spd),this.getY())) {
			moved = true;
			dir = leftDir;
			x-=spd;
		}
		if(up && World.isFree(this.getX(),(int)(y-spd))) {
			moved = true;
			dir = upDir;
			y-=spd;
		} else if(down && World.isFree(this.getX(),(int)(y+spd))) {
			moved = true;
			dir = downDir;
			y+=spd;
		}
		
		if(moved) {
			curFrames++; 
				if(curFrames == targetFrames) {
					curFrames = 0;
					curAnimation++;
					if(curAnimation == rightPlayer.length) {
						curAnimation = 0;
					}
				}
			}
		
		checkLifePackCollision();
		checkAmmoCollision();
		checkGunCollision();
		
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 15) {
				this.damageFrames = 0;
				isDamaged = false;
			}
		}
		
		if(mouseIsShooting) {
			
			mouseIsShooting = false;
			
			if(equippedSMG || equippedRifle || equippedCannon && ammo > 0) {
				//Atirar
				ammo-=1;
				
				int px = 0, py = 0;
				double angle = 0;
				
				if(dir == rightDir) {
					px = 16;
					py = 5;
					angle = Math.atan2(my - (this.getY()+py - Camera.y),mx - (this.getX()+px - Camera.x));
				} else {
					px = -2;
					py = 5;
					angle = Math.atan2(my - (this.getY()+py - Camera.y),mx - (this.getX()+px - Camera.x));
				}
				
				double dx = Math.cos(angle);
				double dy = Math.sin(angle);
			
			Bullet bullet = new Bullet(this.getX()+px,this.getY()+py,3,3,null,dx,dy);
			Game.bullets.add(bullet);
			}
		}
		
		if(health <= 0) {
			Game.ui = new UI();
			Game.entities = new ArrayList<Entity>();
			Game.enemies = new ArrayList<Enemy>();
			Game.playerSpritesheet = new Spritesheet("/playerSpritesheet.png");
			Game.ghost1Spritesheet = new Spritesheet("/ghost1Spritesheet.png");
			Game.itemSpritesheet = new Spritesheet("/itemSpritesheet.png");
			Game.tileSpritesheet = new Spritesheet("/tileSpritesheet.png");
			Game.weaponSpritesheet = new Spritesheet("/weaponSpritesheet.png");
			Game.player = new Player(0,0,16,16,Game.playerSpritesheet.getSprite(0,0,16,16));
			Game.entities.add(Game.player);
			Game.world = new World("/maps/level01/tile_map.png");
			Game.entityWorld = new World("/maps/level01/entity_map.png");
			return;
		}
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*16 - Game.HEIGHT);
	}
	
	public void checkAmmoCollision() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Ammo) {
				if(Entity.isColliding(this,atual)) {
					ammo+=2;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkLifePackCollision() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Lifepack) {
				if(Entity.isColliding(this,atual)) {
					LifePackAmount+=1;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkGunCollision() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof WeaponSMG) {
				if(Entity.isColliding(this,atual)) {
					hasSMG = true;
					Game.entities.remove(atual);
				}
			}
			if(atual instanceof WeaponRifle) {
				if(Entity.isColliding(this,atual)) {
					hasRifle = true;
					Game.entities.remove(atual);
				}
			}
			if(atual instanceof WeaponCannon) {
				if(Entity.isColliding(this,atual)) {
					hasCannon = true;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
		if(dir == rightDir) {
			g.drawImage(rightPlayer[curAnimation],this.getX() - Camera.x,this.getY() - Camera.y, null);
			if(equippedSMG) {
				//desenhar arma direita
				g.drawImage(WeaponHandler.SMG_RIGHT,this.getX()+6 - Camera.x,this.getY()+1 - Camera.y,null);
			}
			if(equippedRifle) {
				//desenhar arma direita
				g.drawImage(WeaponHandler.RIFLE_RIGHT,this.getX()+6 - Camera.x,this.getY()+1 - Camera.y,null);
			}
			if(equippedCannon) {
				//desenhar arma direita
				g.drawImage(WeaponHandler.CANNON_RIGHT,this.getX()+5 - Camera.x,this.getY()+3 - Camera.y,null);
			}
		}
		 if(dir == leftDir) {
			g.drawImage(leftPlayer[curAnimation],this.getX() - Camera.x,this.getY() - Camera.y, null);
			if(equippedSMG) {
				//desenhar arma direita
				g.drawImage(WeaponHandler.SMG_LEFT,this.getX()-6 - Camera.x,this.getY()+1 - Camera.y,null);
			}
			if(equippedRifle) {
				//desenhar arma direita
				g.drawImage(WeaponHandler.RIFLE_LEFT,this.getX()-6 - Camera.x,this.getY()+1 - Camera.y,null);
			}
			if(equippedCannon) {
				//desenhar arma direita
				g.drawImage(WeaponHandler.CANNON_LEFT,this.getX()-5 - Camera.x,this.getY()+3 - Camera.y,null);
			}
		}
		if(dir == upDir) {
			g.drawImage(upPlayer[curAnimation],this.getX() - Camera.x,this.getY() - Camera.y, null);
		}
		if(dir == downDir) {
			g.drawImage(downPlayer[curAnimation],this.getX() - Camera.x,this.getY() - Camera.y, null);
			if(equippedSMG || equippedRifle || equippedCannon) {
				//desenhar arma baixo
					g.drawImage(WeaponHandler.WEAPON_FRONT,(int) (this.getX()+1 - Camera.x),this.getY()+5 - Camera.y,null);
				}
			}
		} else {
			if(dir == rightDir) {
				g.drawImage(damageRightPlayer,this.getX() - Camera.x,this.getY() - Camera.y, null);
			} else if(dir == leftDir) {
				g.drawImage(damageLeftPlayer,this.getX() - Camera.x,this.getY() - Camera.y, null);
			}
			if(dir == upDir || dir == downDir) {
				g.drawImage(damageUpDownPlayer,this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
		} 
		if(dir == nullDir) {
			g.drawImage(rightPlayer[0],this.getX() - Camera.x,this.getY() - Camera.y, null);
		}
	}
}

