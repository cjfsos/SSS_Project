package DataBase;

import java.io.Serializable;

public class Server_DTO implements Serializable{
	private String no;
	private String Stitle;
	private String singer;
	private String album;
	private String genre;
	

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getStitle() {
		return Stitle;
	}

	public void setStitle(String stitle) {
		Stitle = stitle;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String[] getArray() {
		String[] returnData = new String[5];
		returnData[0]=this.no;
		returnData[1]=this.Stitle;
		returnData[2]=this.singer;
		returnData[3]=this.album;
		returnData[4]=this.genre;
		return returnData;
	}
	
	public String[] currently() {
		String[] returnData = new String[3];
		returnData[0]=this.Stitle;
		returnData[1]=this.singer;
		returnData[2]=this.genre;
		return returnData;
	}
}