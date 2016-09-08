package com.pfariasmunoz.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class Drop extends ApplicationAdapter {
	// Assets
	private Texture dropImage;
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;

	// camera and spritebatch
	private OrthographicCamera camera;
	private SpriteBatch batch;

	// phisical representation
	private Rectangle bucket;

	private Vector3 touchPos;

	private Array<Rectangle> raindrops;

	// to keep track of the last time we spawned a raindrop
	// the time is in nanoseconds, that is why we use a long
	private long lastDropTime;

	
	@Override
	public void create () {
		// load the images for the droplet and the bucket, 64x64 pixels each
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));

		// load the drop sound effect and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		// start the playback of the background music immediately
		rainMusic.setLooping(true);
		rainMusic.play();

		// camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// the batch
		batch = new SpriteBatch();

		// position 20 pix above the botton edge of the screen
		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;

		// instantiate the raindrops
		raindrops = new Array<Rectangle>();
		spawnRaindrop();


		// touch position
		touchPos = new Vector3();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		// render the bucket
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bucketImage, bucket.x, bucket.y);
		for (Rectangle raindrop : raindrops) {
			batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		batch.end();

		// position of the bucket within the camera
		if (Gdx.input.isTouched()) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}

		// making the bucket move
		if(Gdx.input.isKeyPressed(Keys.LEFT)) bucket.x -= 300 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) bucket.x += 300 * Gdx.graphics.getDeltaTime();

		// make sure our bucket stays within the screen limits
		if (bucket.x < 0) bucket.x = 0;
		if (bucket.x > 800 - 64) bucket.x = 800 -64;

		// check how mush time has passed since we spawned a new
		// raindrop, and creates a new one if necessary:
		if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

		// make the raindrops move. if the raindrop is beneath the bottom
		// edge of the screen, we remove it from the array

		Iterator<Rectangle> iterator = raindrops.iterator();
		while (iterator.hasNext()) {
			Rectangle raindrop = iterator.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if (raindrop.y + 64 < 0) iterator.remove();
			// if a raindrop hit the bucket we playback a drop sound
			// a remove it from the array
			if (raindrop.overlaps(bucket)) {
				dropSound.play();
				iterator.remove();
			}
		}


	}
	
	@Override
	public void dispose () {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
	}

	/**
	 * To facilitate the creation of raindrops, which instantiates a
	 * new Rectangle, set is to a random position at the top edge of
	 * the screen and adds it to the rraindrops array
	 */
	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800 - 64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}
}
