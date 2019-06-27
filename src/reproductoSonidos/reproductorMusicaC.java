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
import javax.swing.DefaultListModel;
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
import java.util.ArrayList;

import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.ChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.MouseAdapter;
import java.awt.event.InputMethodEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.List;
import java.awt.ScrollPane;

public class reproductorMusicaC extends javafx.application.Application{

	private JFrame frame;
	private static ReproductorMusica mus;
	private JTextField resultado;
	private JTextField tiempo;
	private JTextField txtHistorico;

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
		ArrayList<HistoricoSonido> histMus = new ArrayList<HistoricoSonido>();
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
		frame.setBounds(100, 100, 588, 338);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		/*ImageIcon img = new ImageIcon("Ficheros/calc.png");
		frame.setIconImage(img.getImage());*/
		
		JLabel tituloReproductorJ = new JLabel("Reproductor musica Java");
		tituloReproductorJ.setHorizontalAlignment(SwingConstants.CENTER);
		tituloReproductorJ.setForeground(Color.ORANGE);
		tituloReproductorJ.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tituloReproductorJ.setBounds(10, 11, 562, 22);
		frame.getContentPane().add(tituloReproductorJ);
		
		resultado = new JTextField("Elije una cancion");
		resultado.setBackground(new Color(102, 0, 255));
		resultado.setForeground(Color.MAGENTA);
		resultado.setFont(new Font("Tahoma", Font.BOLD, 13));
		resultado.setHorizontalAlignment(SwingConstants.CENTER);
		resultado.setEditable(false);
		resultado.setBounds(20, 44, 552, 22);
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
		pausa.setBounds(244, 268, 60, 30);
		frame.getContentPane().add(pausa);
		reanudar.setBounds(244, 268, 60, 30);
		frame.getContentPane().add(reanudar);
		reanudar.setVisible(false);
		
		
		//SLIDER AJUSTAR VOLUMEN
		tiempo = new JTextField();
		tiempo.setHorizontalAlignment(SwingConstants.CENTER);
		tiempo.setBounds(174, 268, 60, 30);
		frame.getContentPane().add(tiempo);
		tiempo.setColumns(10);
		
		JSlider sliderVol = new JSlider();
		sliderVol.setOrientation(SwingConstants.VERTICAL);
		sliderVol.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				mus.ajustarVolumen(sliderVol.getValue());
			}
		});
		sliderVol.setBounds(548, 76, 24, 162);
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
		
		sliderProgreso.setBounds(20, 242, 518, 22);
		frame.getContentPane().add(sliderProgreso);
		
		

		JList historico = new JList() ;
		JScrollPane desliz= new JScrollPane(historico);
		desliz.setBounds(49, 141, 472, 95);
		//frame.getContentPane().add(historico);
		frame.getContentPane().add(desliz);
		
		
		//BOTON ELEGIR CANCION
		JButton eligeFileBoton = new JButton("Elegir Cancion...");
		eligeFileBoton.setMargin(new Insets(0, 0, 0, 0));
		eligeFileBoton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
		        chooser.setFileFilter(filter);
		        int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
		        	HistoricoSonido d= new HistoricoSonido(mus.getPath(), mus.getNombreCancion());
		        	histMus.add(d);
		        	
		        	//Crear un objeto DefaultListModel
		        	DefaultListModel listModel = new DefaultListModel();
		        	//Recorrer el contenido del ArrayList
		        	for(int i=0; i<histMus.size(); i++) {
		        	    //A�adir cada elemento del ArrayList en el modelo de la lista
		        	    listModel.add(i, histMus.get(i));
		        	}
		        	//Asociar el modelo de lista al JList
		        	historico.setModel(listModel);
		        	
		        	
		        	
		        	
		        	
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
		eligeFileBoton.setBounds(234, 77, 108, 22);
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
		repeticionBoton.setBounds(314, 268, 60, 30);
		frame.getContentPane().add(repeticionBoton);
		
		txtHistorico = new JTextField("Historico");
		txtHistorico.setMargin(new Insets(0, 0, 0, 0));
		txtHistorico.setHorizontalAlignment(SwingConstants.CENTER);
		txtHistorico.setForeground(new Color(250, 128, 114));
		txtHistorico.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtHistorico.setEditable(false);
		txtHistorico.setColumns(10);
		txtHistorico.setBackground(Color.GRAY);
		txtHistorico.setBounds(53, 108, 468, 18);
		frame.getContentPane().add(txtHistorico);
		
		
		
		
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
		
		historico.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 1) {
		        	
		            // Double-click detected
		            int index = list.locationToIndex(evt.getPoint());
		            System.out.println("asfadf "+index);
		            mus.cambiarCancion(histMus.get(index).getDireccion());
		            mus.reproducirInicio();
		            resultado.setText(mus.getNombreCancion());
		            
		            
		        } 
		            // Triple-click detected
		            //int index = list.locationToIndex(evt.getPoint());
		            
		    }
		});
		
		
		
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
