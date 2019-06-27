package calculadora;

//import java.awt.Color;
//import java.awt.EventQueue;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import javax.swing.JFrame;
import javax.swing.JLabel;
//import java.awt.Font;
//import java.awt.Image;
//import java.awt.Toolkit;
//import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
import java.net.URL;
//import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.ChangeEvent;
//import java.awt.Insets;

public class reproductorMusicaC extends javafx.application.Application{

	private JFrame frame;
	private static ReproductorMusica mus;
	private JTextField resultado;
	private JTextField tiempo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)  {
		mus = new ReproductorMusica("Ficheros/mus1.mp3");
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
		
		JButton pausa = new JButton("||");
		pausa.setFont(new Font("Tahoma", Font.PLAIN, 10));
		pausa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mus.pausa();//pausamos la cancion cuando pulsamos en el boton pausa
			}
		});
		pausa.setBounds(167, 161, 60, 30);
		frame.getContentPane().add(pausa);
		
		JButton reanudar = new JButton("\u25BA");
		reanudar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		reanudar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mus.reanudar();//reanudamos la cancion cuando pulsamos en reanudar
				}
		});
		reanudar.setBounds(49, 161, 60, 30);
		frame.getContentPane().add(reanudar);
		
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
		
		JSlider sliderProgreso = new JSlider();
		sliderProgreso.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				mus.ajustarTiempo(sliderProgreso.getValue());
			}
		});
		sliderProgreso.setBounds(10, 202, 232, 22);
		frame.getContentPane().add(sliderProgreso);
		
		JButton eligeFileBoton = new JButton("Elegir Cancion...");
		eligeFileBoton.setMargin(new Insets(0, 0, 0, 0));
		eligeFileBoton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
		        chooser.setFileFilter(filter);
		        int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
		        	mus.pausa();
		        	System.out.println("Elegiste abrir este archivo: " +
		                    chooser.getSelectedFile().getName());
		           
		            mus= new ReproductorMusica( chooser.getSelectedFile().getAbsolutePath());
		            mus.reproducirInicio();
		            resultado.setText(mus.getNombreCancion());
		        }else {
		        	resultado.setText("Intentalo de nuevo");
		        }
		       
		        
				
				
			}
		});
		eligeFileBoton.setBounds(188, 55, 108, 22);
		frame.getContentPane().add(eligeFileBoton);
		
		Timer timer = new Timer (100, new ActionListener () //hacemos un hilo para que se actualize la duracion de la musica
		{ 
		    public void actionPerformed(ActionEvent e) 
		    { 
		    	
		    	tiempo.setText(mus.getProgreso());
		       // System.out.println(mus.getProgreso());
		        //slider_1.setValue((int) mus.obtenerProgreso());
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
