package music;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MusicLod extends Thread {
	private File f;
	private FileInputStream fis;
	private BufferedInputStream bio;
	private Player play;
	private String name;

	public MusicLod(String name) {
		System.out.println("C:\\Users\\Administrator\\Desktop\\" + name + ".mp3");
		this.name = name;
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
		this.interrupt();// 해당 쓰레드를 중지 상태로 만듬
	}
}