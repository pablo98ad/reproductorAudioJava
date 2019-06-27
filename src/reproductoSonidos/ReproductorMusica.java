package reproductoSonidos;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class ReproductorMusica {
	String path;
	Media media;
	MediaPlayer mediaPlayer;
	
	public ReproductorMusica(String direccion) {
		this.path=direccion;
		this.media = new Media(new File(this.path).toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);
		
		
	}
	
	public void reproducirInicio() {
        try {
        	//Duration d = new Duration(100000.00);
        	//this.mediaPlayer.setStartTime(d);
      //  this.mediaPlayer.setAutoPlay(true);
        this.mediaPlayer.play();
        
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
	
	public String getNombreCancion() {
		String n= this.path.substring(this.path.lastIndexOf('\\')+1, this.path.lastIndexOf('.'));
		//por ejempo "C:/Pablo/Escritorio/hola.mp3" cojeria solo "hola"
		return n;
	}
	
	
	/**
	 * Devuelve un string con el  progreso de la cancion en minutos y segundos reales
	 * @return
	 */
	public String getProgreso() {
		String time="";
		double d =  this.mediaPlayer.getCurrentTime().toSeconds();
		int min,seg;
		min= (int) (d/60);
		seg= (int) (d%60);
		time=String.format("%d:%02d", min,seg);
		return time;
				
		
	}
	
	public void ajustarVolumen(int v) {
		double vv=(double)v/100;
		//System.out.println(vv);
		this.mediaPlayer.setVolume(vv);
		
		
	}
	
	public void pausa() {
		this.mediaPlayer.pause();
		
		//System.out.println(obtenerProgreso());
	}
	
	public void reanudar() {
		this.mediaPlayer.play();
		
	}
	/*
	public double obtenerProgreso() {
		int duracionActual= (int)this.mediaPlayer.getCurrentTime().toMillis();
		int duracionTotal= (int)this.mediaPlayer.getTotalDuration().toMillis();
		//System.out.println(duracionActual);
		//System.out.println(duracionTotal);
		
		//duracionActual=duracionActual/100;
		int res=(int)(duracionActual*100)/duracionTotal;
		return res;
	}
	*/
	public void ajustarTiempo(int t) {
		int duracionTotal= (int)this.mediaPlayer.getTotalDuration().toMillis();
		
		int duracionSeleccionada= (duracionTotal*t)/100;
		//System.out.println(duracionSeleccionada);
		Duration dRes = new Duration(duracionSeleccionada);
		this.mediaPlayer.seek(dRes);
		
	}/*
	
	public void close() {
		this.close();
	}*/
	
}
