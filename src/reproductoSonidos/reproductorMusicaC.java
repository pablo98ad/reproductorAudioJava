package reproductoSonidos;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Time;

import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.ChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

public class reproductorMusicaC extends javafx.application.Application{

	private JFrame frame;
	private static ReproductorMusica mus;
	private JTextField resultado;
	private JTextField tiempo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)  {
		
		Application.launch(args);
	      
		
	}

	
	
	/**
	 * Create the application.
	 */
	public reproductorMusicaC() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */

	private void initialize() {
		JFileChooser chooser = new JFileChooser();
		JButton reanudar  = new JButton("\u25BA");
		JButton pausa = new JButton("||");
		JSlider sliderProgreso = new JSlider();
		sliderProgreso.setValue(1);
		sliderProgreso.setMaximum(1000);
		
		mus = new ReproductorMusica("Ficheros/mus1.mp3");
		
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Archivos de Musica(.mp3, .wav)", "mp3", "wav");
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 312, 390);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		/*ImageIcon img = new ImageIcon("Ficheros/calc.png");
		frame.setIconImage(img.getImage());*/
		
		JLabel tituloReproductorJ = new JLabel("Reproductor musica Java");
		tituloReproductorJ.setHorizontalAlignment(SwingConstants.CENTER);
		tituloReproductorJ.setForeground(Color.ORANGE);
		tituloReproductorJ.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tituloReproductorJ.setBounds(10, 11, 286, 22);
		frame.getContentPane().add(tituloReproductorJ);
		
		resultado = new JTextField("Elije una cancion");
		resultado.setBackground(new Color(102, 0, 255));
		resultado.setForeground(Color.MAGENTA);
		resultado.setFont(new Font("Tahoma", Font.BOLD, 13));
		resultado.setHorizontalAlignment(SwingConstants.CENTER);
		resultado.setEditable(false);
		resultado.setBounds(20, 44, 158, 33);
		frame.getContentPane().add(resultado);
		resultado.setColumns(10);
		
		//BOTON REANUDAR
		
		reanudar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		reanudar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(mus!=null) {
					pausa.setVisible(true);
					reanudar.setVisible(false);
					mus.reanudar();//reanudamos la cancion cuando pulsamos en reanudar
				}
			}
		});
		
		
		
		
		//BOTON PAUSA
		pausa.setFont(new Font("Tahoma", Font.PLAIN, 10));
		pausa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(mus!=null) {
					reanudar.setVisible(true);
					pausa.setVisible(false);
					mus.pausa();//pausamos la cancion cuando pulsamos en el boton pausa
				}
				
			}
		});
		pausa.setBounds(96, 161, 60, 30);
		frame.getContentPane().add(pausa);
		reanudar.setBounds(96, 161, 60, 30);
		frame.getContentPane().add(reanudar);
		reanudar.setVisible(false);
		
		
		//SLIDER AJUSTAR VOLUMEN
		tiempo = new JTextField();
		tiempo.setHorizontalAlignment(SwingConstants.CENTER);
		tiempo.setBounds(244, 202, 52, 22);
		frame.getContentPane().add(tiempo);
		tiempo.setColumns(10);
		
		JSlider sliderVol = new JSlider();
		sliderVol.setOrientation(SwingConstants.VERTICAL);
		sliderVol.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				mus.ajustarVolumen(sliderVol.getValue());
			}
		});
		sliderVol.setBounds(259, 88, 24, 103);
		frame.getContentPane().add(sliderVol);
		
		
		
		//si la cancion se esta reproduciendo
		//PARA CADA CAMBIO  bps DE LA CANCION
			/*
		mus.getMediaPlayer().currentTimeProperty().addListener(new javafx.beans.value.ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
            	if(mus.getMediaPlayer().getStatus()==javafx.scene.media.MediaPlayer.Status.PLAYING || mus.getMediaPlayer().getStatus()==javafx.scene.media.MediaPlayer.Status.READY && mus.getMediaPlayer()!=null) {
	            	sliderProgreso.setValue(mus.obtenerProgreso());
	            	//sliderProgreso.setValue(sliderProgreso.getValue());
	            	tiempo.setText(mus.getProgreso());
	            	try {
	            		mus.getMediaPlayer().play();
	            	}catch(Exception e) {}
	            	System.out.println(sliderProgreso.getValue());
	            	System.out.println(mus.obtenerProgreso());
	            	System.out.println(mus.getProgreso());
	            	System.out.println();
            	}else {
            		System.out.println("sdfsdfsdf");
            	}
            }
		}
            );
		*/
		
			
		
		
		
		
		
		
		
		//Para que cuando se arrastre el boton sobre el slider ajuste el progreso de la cancion
				sliderProgreso.addMouseMotionListener(new MouseMotionAdapter() {
					@Override
					public void mouseDragged(MouseEvent arg0) {
						mus.ajustarTiempo(sliderProgreso.getValue());
						
					}
				});
		
		sliderProgreso.setBounds(10, 202, 232, 22);
		frame.getContentPane().add(sliderProgreso);
		
		
		//BOTON ELEGIR CANCION
		JButton eligeFileBoton = new JButton("Elegir Cancion...");
		eligeFileBoton.setMargin(new Insets(0, 0, 0, 0));
		eligeFileBoton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
		        chooser.setFileFilter(filter);
		        int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
		        	mus.getMediaPlayer().stop();
		        	System.out.println("Elegiste abrir este archivo: " +
		                    chooser.getSelectedFile().getName());
		           
		            mus.cambiarCancion(chooser.getSelectedFile().getAbsolutePath());
		            mus.reproducirInicio();
		            resultado.setText(mus.getNombreCancion());
		        }else {
		        	resultado.setText("Intentalo de nuevo");
		        }
		       
		        
				
				
			}
		});
		eligeFileBoton.setBounds(188, 55, 108, 22);
		frame.getContentPane().add(eligeFileBoton);
		
		
		//BOTON REPETICION CANCION
		JButton repeticionBoton = new JButton("\uD83D\uDD04");
		repeticionBoton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(mus!=null) {
					if(mus.getSeVaRepetir()==false) {
						mus.setRepeticion(true);
						repeticionBoton.setBackground(new Color(102,255,51));
					}else {
						mus.setRepeticion(false);
						repeticionBoton.setBackground(new Color(240,240,240));
					}
				}
				
			}
		});
		repeticionBoton.setMargin(new Insets(0, 0, 0, 0));
		repeticionBoton.setBounds(166, 161, 60, 30);
		frame.getContentPane().add(repeticionBoton);
		
		Timer timer = new Timer (10, new ActionListener () //hacemos un hilo para que se actualize la duracion de la musica
		{ 
		    public void actionPerformed(ActionEvent e) 
		    { 
		    	
		    	if(mus.getMediaPlayer().getStatus()==javafx.scene.media.MediaPlayer.Status.PLAYING || mus.getMediaPlayer().getStatus()==javafx.scene.media.MediaPlayer.Status.READY && mus.getMediaPlayer()!=null) {
		            
		    	sliderProgreso.setValue(mus.obtenerProgreso());
            	//sliderProgreso.setValue(sliderProgreso.getValue());
            	tiempo.setText(mus.getProgreso());
            	try {
            		mus.getMediaPlayer().play();
            	}catch(Exception e1) {}
		    	}
		     } 
		}); 
		

		timer.start();
		
		frame.setTitle("Reproductor Musica Java by Pablo98ad");
		try {
			frame.setIconImage(ImageIO.read(new File("Ficheros/calc.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

	@Override
	public void start(Stage arg0) throws Exception {
		
		mus.reproducirInicio();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					reproductorMusicaC window = new reproductorMusicaC();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
}
