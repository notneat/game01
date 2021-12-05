package com.notgames.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.notgames.entities.Ammo;
import com.notgames.entities.Bullet;
import com.notgames.entities.Enemy;
import com.notgames.entities.Entity;
import com.notgames.entities.Lifepack;
import com.notgames.entities.Player;
import com.notgames.graficos.Spritesheet;
import com.notgames.graficos.UI;
import com.notgames.world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener {
	
	private static final long serialVersionUID = 1L;
	
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;

	public final int SCALE = 4;
	
	private BufferedImage image;
	
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<Bullet> bullets; 
	
	public static Spritesheet ghost1Spritesheet, itemSpritesheet, playerSpritesheet, tileSpritesheet, weaponSpritesheet;
	
	public static World world, entityWorld;
	public static Player player;
	public static Ammo ammo;
	public static Lifepack lifepack;
	
	public static Random rand;
	
	public static UI ui;

	public static Object WeaponSMG;
	public static Object WeaponRifle;
	public static Object WeaponCannon;
	
	public Game() {
		
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		frame = new JFrame("Jojinho");
		initFrame();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		//Inicializando objetos
		ui = new UI();
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		bullets = new ArrayList<Bullet>();
		playerSpritesheet = new Spritesheet("/playerSpritesheet.png");
		ghost1Spritesheet = new Spritesheet("/ghost1Spritesheet.png");
		itemSpritesheet = new Spritesheet("/itemSpritesheet.png");
		tileSpritesheet = new Spritesheet("/tileSpritesheet.png");
		weaponSpritesheet = new Spritesheet("/weaponSpritesheet.png");
		player = new Player(0,0,16,16,playerSpritesheet.getSprite(0,0,16,16));
		entities.add(player);
		world = new World("/maps/level01/tile_map.png");
		entityWorld = new World("/maps/level01/entity_map.png");
		
	}
	
	public void initFrame() {
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).tick();
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0,0,WIDTH,HEIGHT);
			
		/*RENDERIZAÇÃO DO JOGO*/
		world.render(g);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
		ui.render(g);
		/***/
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image,0,0,WIDTH*SCALE,HEIGHT*SCALE,null);
		bs.show();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amoutOfTicks = 100000.0;
		double ns = 1000000000 / amoutOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println(frames +" FPS");
				frames = 0;
				timer+=1000;
			}
		}
		stop();
	}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP ||
				e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_F) {
				player.isShooting = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_1) {
			if(player.hasSMG) {
				player.equippedSMG = true;
				player.equippedRifle = false;
				player.equippedCannon = false;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_2) {
			if(player.hasRifle) {
				player.equippedRifle = true;
				player.equippedSMG = false;
				player.equippedCannon = false;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_3) {
			if(player.hasCannon) {
				player.equippedCannon = true;
				player.equippedSMG = false;
				player.equippedRifle = false;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_R) {
			if(Game.player.LifePackAmount > 0) {
				Game.player.LifePackAmount--;
				Game.player.health+=50;
				if(Game.player.health > Game.player.maxHealth) {
					Game.player.health = Game.player.maxHealth;
				}
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_F3 && UI.showHitbox == false) {
			UI.showHitbox = true;
		} else if(e.getKeyCode() == KeyEvent.VK_F3 && UI.showHitbox == true) {
			UI.showHitbox = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_F8) {
			System.out.println("Enemy Spawned");
			Enemy en = new Enemy(0,0,16,16,Entity.ENEMY_EN);
			Game.entities.add(en);
			Game.enemies.add(en);
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP ||
				e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		player.mouseIsShooting = true;
		player.mx = (e.getX()/3);
		player.my = (e.getY()/3);		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}