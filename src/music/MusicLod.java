package music;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MusicLod extends Thread {
	private File f;
	private FileInputStream fis;
	private BufferedInputStream bio;
	private Player play;
	private boolean onOff = true;
	public int check = 0;

	public MusicLod(String name) {
		System.out.println("C:\\Users\\Administrator\\Desktop\\" + name + ".mp3");
		try {
			f = new File("C:\\Users\\Administrator\\Desktop\\" + name + ".mp3");
			fis = new FileInputStream(f);
			bio = new BufferedInputStream(fis);
			play = new Player(bio);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("erorr1");
		}
	}

	@Override
	public void run() {
		try {
			play.play();
		} catch (JavaLayerException e) {
			e.printStackTrace();
			System.out.println("erorr2");
		}
	}

	public void Close() {
		play.close();
		onOff = false;
		this.interrupt();// 해당 쓰레드를 중지 상태로 만듬
	}

	public void limited() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int limit = 0;
				while (onOff) {
					try {
						Thread.sleep(999);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					limit = play.getPosition();
					System.out.println(limit);
					if (limit > 60000) {
						play.close();
						onOff = false;
					}
				}
			}
		}).start();
		this.interrupt();// 해당 쓰레드를 중지 상태로 만듬
	}

	public void OneInfinite() {
		try {
			do {
				play.play();
				fis = new FileInputStream(f);
				bio = new BufferedInputStream(fis);
				play = new Player(bio);
			} while (true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}