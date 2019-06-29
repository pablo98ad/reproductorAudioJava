package reproductorSonidos;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Clase que encapsula el controlador del archivo que se reproduce
 * @author Pablo Avila pablo98ad
 */
public class ReproductorMusica {
	private String path;
	private Media media;
	private MediaPlayer mediaPlayer;
	private boolean seVaRepetir=false;
	private Duration duracionArchivo;

	
	public ReproductorMusica(String direccion, int volumen, double velocidad, boolean repe) {
		this.path=direccion;
		this.media = new Media(new File(this.path).toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);
        this.ajustarVolumen(volumen);
        this.mediaPlayer.setRate(velocidad);
        this.duracionArchivo= mediaPlayer.getCycleDuration();
        
        if(repe) {
        	this.setRepeticion(true);
        }else {
        	this.setRepeticion(false);
        }

		
        
        
	}
	
	public Duration getDuracionTotal() {
		return this.duracionArchivo;
		
	}
	
	/**
	 * Ajusta la velocidad de reproduccion de la cancion
	 * @param v
	 */
	public void ajustarVelocidad(double v) {
		this.mediaPlayer.setRate(v);
		
	}
	
	
	/**
	 * Obtiene la ruta del archivo
	 * @return
	 */
	public String getPath() {
		return this.path;
	}
	
	/**
	 * Metodo que cambia la reproduccion del archivo, cambia la fuente del mediaplayer
	 * @param direccion direccion del archivo	
	 * @param repe si se va a repetir la cancion
	 */
	public void cambiarCancion(String direccion, int volumen, double velocidad, boolean repe) {
		if(this.mediaPlayer!=null) {
			this.mediaPlayer.stop();
		}
		this.mediaPlayer=null;
		this.path=direccion;
		this.media = new Media(new File(this.path).toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);
        this.mediaPlayer.play();
        this.ajustarVolumen(volumen);
        this.mediaPlayer.setRate(velocidad);
        this.duracionArchivo= mediaPlayer.getCycleDuration();
        
        if(repe) {
        	this.setRepeticion(true);
        }else {
        	this.setRepeticion(false);
        }
		
		
	}
	
	public void reproducirInicio() {
        this.mediaPlayer.play();
    }
	
	/**
	 * Metodo que ajusta la repeticion del archivo	
	 * @param siono
	 */
	public void setRepeticion(boolean siono) {
		if(siono) {
			 mediaPlayer.setAutoPlay(true);//para que cuando termine se siga reproduciendo
		     mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		     seVaRepetir=true;
		}else {
			mediaPlayer.setAutoPlay(false);
			mediaPlayer.setCycleCount(0);
			seVaRepetir=false;
		}
		this.mediaPlayer.play();
	}
	/**
	 * Metodo que devuelve si la repeticion del archivo esta activo
	 * @return
	 */
	public boolean getSeVaRepetir() {
		return this.seVaRepetir;
	}
	
	/**
	 * Metodo que devuelve el nombre del archivo, se consigue usando la funcionalidad de String 
	 * lastindexof
	 * @return
	 */
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
		String time;
		int min,seg;
		double d =  this.mediaPlayer.getCurrentTime().toSeconds();
		
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
	
	/**
	 * Obtiene de un valor de 0 a 1000 el progreso de la cancion, util para que se mueva el slider 
	 * del progreso del tiempo
	 * @return
	 */
	public int obtenerProgreso() {
		int res=0;
		
		if(this.mediaPlayer!=null) {
			int duracionActual= (int)this.mediaPlayer.getCurrentTime().toMillis();
			//int duracionTotal= (int)this.mediaPlayer.getTotalDuration().toMillis();
			int duracionTotal= (int)this.mediaPlayer.getCycleDuration().toMillis();//duracion de esta cancion
			
			
			
			//System.out.println(duracionActual);
			//System.out.println(duracionTotal);
			
			//duracionActual=duracionActual/1000;
			res=(int)(duracionActual*1000)/duracionTotal;
			//System.out.println((duracionActual*1000)/duracionTotal);
			//System.out.println("prog"+res);
			//this.mediaPlayer.pause();
		}
		return res;
	}
	
	
	public MediaPlayer getMediaPlayer() {
		return this.mediaPlayer;
		
	}
	/**
	 * Metodo que ajusta el tiempo de la cancion, se utiliza para el slider del progreso del archivo
	 * @param t
	 */
	public void ajustarTiempo(int t) {
		//int duracionTotal= (int)this.mediaPlayer.getTotalDuration().toMillis();
		int duracionTotal= (int)this.mediaPlayer.getCycleDuration().toMillis();
		
		
		int duracionSeleccionada= (duracionTotal*t)/1000;
		//System.out.println(duracionSeleccionada);
		Duration dRes = new Duration(duracionSeleccionada);
		this.mediaPlayer.seek(dRes);
		
	}/*
	
	public void close() {
		this.close();
	}*/
	
}
