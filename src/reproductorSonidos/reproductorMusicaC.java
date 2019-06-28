package reproductorSonidos;

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
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import javax.swing.JComboBox;

public class reproductorMusicaC extends javafx.application.Application{

	private JFrame frame;
	private static ReproductorMusica mus;
	private JTextField resultado;
	private JTextField tiempo;
	private JTextField txtHistorico;
	private boolean  repeticion=false;
	private Color colorBotonRepe;

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
		//Inicializacion de variables
		ArrayList<HistoricoSonido> histMus = new ArrayList<HistoricoSonido>();
		JFileChooser chooser = new JFileChooser();
		JButton reanudar  = new JButton("\u25BA");
		JButton pausa = new JButton("||");
		JSlider sliderProgreso = new JSlider();
		sliderProgreso.setValue(0);
		sliderProgreso.setMaximum(1000);
		
		mus = null;//Primera vez que se  ejecuta la refencia a null hasta que no elija archivo
		
		//Filtro de extensiones para cuando creemos el boton de elejir archivo
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Archivos de Musica(.mp3, .wav)", "mp3", "wav");
		
        
        //Ventana principal del programa
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 588, 338);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		//Titulo label del programa
		JLabel tituloReproductorJ = new JLabel("Reproductor musica Java");
		tituloReproductorJ.setFocusable(false);
		tituloReproductorJ.setHorizontalAlignment(SwingConstants.CENTER);
		tituloReproductorJ.setForeground(Color.ORANGE);
		tituloReproductorJ.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tituloReproductorJ.setBounds(10, 11, 562, 22);
		frame.getContentPane().add(tituloReproductorJ);
		
		//titulo del archivo que se va moviendo con una marquesina hacia la derecha
		resultado = new JTextField(primerVezTitulo("Esperando archivo..."));//al principio si no escoje archivo
		resultado.setBackground(new Color(102, 0, 255));
		resultado.setForeground(Color.MAGENTA);
		resultado.setFont(new Font("Tahoma", Font.BOLD, 13));
		resultado.setHorizontalAlignment(SwingConstants.LEFT);
		resultado.setEditable(false);
		resultado.setBounds(20, 44, 552, 22);
		frame.getContentPane().add(resultado);
		resultado.setColumns(10);
		
		//BOTON REANUDAR
		reanudar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		reanudar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(mus!=null) {//si no hay archivo no se ejecuta
					//se esconde este boton y sale el de pausa
					pausa.setVisible(true);
					reanudar.setVisible(false);
					mus.reanudar();//reanudamos la cancion cuando pulsamos en reanudar
				}
			}
		});
		reanudar.setBounds(244, 268, 60, 30);
		frame.getContentPane().add(reanudar);
		reanudar.setVisible(false);
		
		
		
		//BOTON PAUSA
		pausa.setFont(new Font("Tahoma", Font.PLAIN, 10));
		pausa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(mus!=null) {//si no hay archivo no se ejecuta
					//se esconde este boton y sale el de reanudar
					reanudar.setVisible(true);
					pausa.setVisible(false);
					mus.pausa();//pausamos la cancion cuando pulsamos en el boton pausa
				}
				
			}
		});
		pausa.setBounds(244, 268, 60, 30);
		frame.getContentPane().add(pausa);

		
		//Cuadro de texto donde vemos los segundos de la cancion
		tiempo = new JTextField();
		tiempo.setFocusable(false);
		tiempo.setHorizontalAlignment(SwingConstants.CENTER);
		tiempo.setBounds(174, 268, 60, 30);
		frame.getContentPane().add(tiempo);
		tiempo.setColumns(10);
		
		
		//SLIDER AJUSTAR VOLUMEN
		JSlider sliderVol = new JSlider();
		sliderVol.setOrientation(SwingConstants.VERTICAL);
		//lisenner para si movemos la ruleta cuando el raton entre por el slider ajustar el volumen
		sliderVol.addMouseWheelListener(new MouseWheelListener() {
		    @Override
		    public void mouseWheelMoved(MouseWheelEvent e) {
		        int direccion = e.getWheelRotation();
		        if (direccion < 0) {
		            //direccion arriba
		            sliderVol.setValue(sliderVol.getValue() + 5);
		        } else {
		        	//direccion abajo
		            sliderVol.setValue(sliderVol.getValue() - 5);
		        }
		    }
		});
		//lissener para que cuando clikeemos en el slider y le cambiemos el valor se cambie el volumen
		sliderVol.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(mus!=null) {
				mus.ajustarVolumen(sliderVol.getValue());
				}
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
		
		//SLIDER PROGRESO ARCHIVO
		//Para que cuando se arrastre el raton sobre el slider ajuste el progreso de la cancion
				sliderProgreso.addMouseMotionListener(new MouseMotionAdapter() {
					@Override
					public void mouseDragged(MouseEvent arg0) {
						if(mus!=null) {
							mus.ajustarTiempo(sliderProgreso.getValue());
						}
					}
				});
				//lissener para que cuando movamos la ruleta estando en el slider se mueva la cancion
				sliderProgreso.addMouseWheelListener(new MouseWheelListener() {
				    @Override
				    public void mouseWheelMoved(MouseWheelEvent e) {
				    	if(mus!=null) {
				        int direccion = e.getWheelRotation();
				        if (direccion > 0) {
				            //direccion derecha
				        	sliderProgreso.setValue(sliderProgreso.getValue() + 10);
				        } else {
				        	//direccion izquiierda
				        	sliderProgreso.setValue(sliderProgreso.getValue() - 10);
				        }
				        mus.ajustarTiempo(sliderProgreso.getValue());
				    	}
				    }
				});
		sliderProgreso.setBounds(20, 242, 518, 22);
		frame.getContentPane().add(sliderProgreso);
		
		
		//LISTA DE CANCIONES HISTORICAS
		JList historico = new JList() ;
		JScrollPane desliz= new JScrollPane(historico);
		desliz.setBounds(49, 128, 472, 108);
		frame.getContentPane().add(desliz);
		
		
		//SELECTOR DE VELOCIDAD
				JComboBox selectorVelocidad = new JComboBox();
				selectorVelocidad.setMaximumRowCount(9);
				
				selectorVelocidad.setAutoscrolls(true);
				selectorVelocidad.setBounds(384, 268, 60, 30);
				frame.getContentPane().add(selectorVelocidad);
				selectorVelocidad.addItem("x0.25");
				selectorVelocidad.addItem("x0.5");
				selectorVelocidad.addItem("x0.75");
				selectorVelocidad.addItem("x1");
				selectorVelocidad.addItem("x1.10");
				selectorVelocidad.addItem("x1.25");
				selectorVelocidad.addItem("x1.5");
				selectorVelocidad.addItem("x2");
				selectorVelocidad.addItem("x4");
				selectorVelocidad.setSelectedIndex(3);
				selectorVelocidad.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(mus!=null) {
							mus.ajustarVelocidad(Double.parseDouble(selectorVelocidad.getSelectedItem().toString().substring(1, selectorVelocidad.getSelectedItem().toString().length())));
						}
					}
				});
				selectorVelocidad.addMouseWheelListener(new MouseWheelListener() {
					public void mouseWheelMoved(MouseWheelEvent e) {
						if(mus!=null) {
					        int direccion = e.getWheelRotation();
					        if (direccion > 0) {
					            //direccion abajo
					        	if(selectorVelocidad.getSelectedIndex()!=0) {
					        		selectorVelocidad.setSelectedIndex(selectorVelocidad.getSelectedIndex()-1);
					        	}
					        } else {
					        	//direccion arriba
					        	if(selectorVelocidad.getSelectedIndex()!=selectorVelocidad.getItemCount()-1/*el numero total de items del jcombobox*/) {
					        		selectorVelocidad.setSelectedIndex(selectorVelocidad.getSelectedIndex()+1);
					        	}
					        }
					        mus.ajustarVelocidad(Double.parseDouble(selectorVelocidad.getSelectedItem().toString().substring(1, selectorVelocidad.getSelectedItem().toString().length())));
					    	}
						
					}
				});
				
		
		
		
		//BOTON ELEGIR CANCION
		JButton eligeFileBoton = new JButton("Elegir Cancion...");
		//le cambiamos el primer directorio a buscar por el escritorio del usuario que esta
		chooser.setCurrentDirectory(new File  
				(System.getProperty("user.home") + System.getProperty("file.separator")+ "Desktop"));
        
		eligeFileBoton.setMargin(new Insets(0, 0, 0, 0));
		eligeFileBoton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				chooser.setFileFilter(filter);//le asignamos el filtro de archivos al elegidor
		        int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
		        	
		        	//si ya habia archivo elegido
		        	if(mus!=null)  {
		        		mus.getMediaPlayer().stop();//paramos la cancion
		        		mus.cambiarCancion(chooser.getSelectedFile().getAbsolutePath(),repeticion);
		        		
		        	}else {//si es la primera vez que se elije un archivo
		        		mus = new ReproductorMusica(chooser.getSelectedFile().getAbsolutePath(), repeticion);
		        		
		        	}
		        	//añadimos esa ruta y nombre de archivo al historico
		        	HistoricoSonido d= new HistoricoSonido(mus.getPath(), mus.getNombreCancion());
		        	histMus.add(d);
		        	sliderVol.setValue(50);//CAMBIAR, MUY INCOMODO
		        	
		        	//Crear un objeto DefaultListModel
		        	DefaultListModel listModel = new DefaultListModel();
		        	//Recorrer el contenido del ArrayList
		        	for(int i=0; i<histMus.size(); i++) {
		        	    //Añadir cada elemento del ArrayList en el modelo de la lista
		        	    listModel.add(i, histMus.get(i));
		        	}
		        	//Asociar el modelo de lista al JList
		        	historico.setModel(listModel);
		        	
		        	
		        	
		        	
		        	
		        	selectorVelocidad.setSelectedIndex(3);//para no desincronizarlo!
		            mus.reproducirInicio();//lo reproducimos
		            resultado.setText(primerVezTitulo(mus.getNombreCancion()));//cambiamos el titulo 
		            
		            
		            
		        }else {
		        //	resultado.setText("Intentalo de nuevo");
		        }
		       
		        
				
				
			}
		});
		eligeFileBoton.setBounds(234, 77, 108, 22);
		frame.getContentPane().add(eligeFileBoton);
		
		
		//BOTON REPETICION CANCION
		JButton repeticionBoton = new JButton("\uD83D\uDD04");
		colorBotonRepe= repeticionBoton.getBackground();//para volver al estilo inicial
		repeticionBoton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(mus!=null) {
					if(mus.getSeVaRepetir()==false) {
						mus.setRepeticion(true);
						repeticion=true;
						repeticionBoton.setBackground(new Color(102,255,51));
					}else {
						mus.setRepeticion(false);
						repeticion=false;
						repeticionBoton.setBackground(colorBotonRepe);//para volver al estilo inicial
					}
				}
				
			}
		});
		repeticionBoton.setMargin(new Insets(0, 0, 0, 0));
		repeticionBoton.setBounds(314, 268, 60, 30);
		frame.getContentPane().add(repeticionBoton);
		
		txtHistorico = new JTextField("Historico");
		txtHistorico.setFocusable(false);
		txtHistorico.setMargin(new Insets(0, 0, 0, 0));
		txtHistorico.setHorizontalAlignment(SwingConstants.CENTER);
		txtHistorico.setForeground(new Color(250, 128, 114));
		txtHistorico.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtHistorico.setEditable(false);
		txtHistorico.setColumns(10);
		txtHistorico.setBackground(Color.GRAY);
		txtHistorico.setBounds(49, 108, 472, 18);
		frame.getContentPane().add(txtHistorico);
		
		
		
		
		Timer tiempoYBarraProgreso = new Timer (20, new ActionListener () //hacemos un hilo para que se actualize la duracion de la musica
		{ 
		    public void actionPerformed(ActionEvent e) 
		    { 
		    	if(mus!=null) {
			    	if(mus.getMediaPlayer().getStatus()==javafx.scene.media.MediaPlayer.Status.PLAYING || mus.getMediaPlayer().getStatus()==javafx.scene.media.MediaPlayer.Status.READY && mus.getMediaPlayer()!=null) {
				    	sliderProgreso.setValue(mus.obtenerProgreso());
				    	System.out.println(mus.obtenerProgreso()+"   "+sliderProgreso.getValue());
		            	//sliderProgreso.setValue(sliderProgreso.getValue());
		            	tiempo.setText(mus.getProgreso());
		            	try {
		            		mus.getMediaPlayer().play();
		            	}catch(Exception e1) {}
			    	}
		    	}
		     } 
		}); 
		

		tiempoYBarraProgreso.start();
		
		Timer efectoMarquesinaTituloCancion = new Timer (200, new ActionListener () //hacemos un hilo para que se actualize la duracion de la musica
				{ 
					
				    public void actionPerformed(ActionEvent e) 
				    { 
				    	String resultadoo=resultado.getText();
				    	String p= resultadoo.substring(resultadoo.length()-1,resultadoo.length())+resultadoo.substring(0,resultadoo.length()-1);
				    	resultado.setText(p);
				    	
				    	
				    }
				}); 
				

		efectoMarquesinaTituloCancion.start();
		
		
		
		historico.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 1) {
		        	
		            // Double-click detected
		            int index = list.locationToIndex(evt.getPoint());
		            System.out.println("asfadf "+index);
		            mus.cambiarCancion(histMus.get(index).getDireccion(),repeticion);
		            mus.reproducirInicio();
		            resultado.setText(primerVezTitulo(mus.getNombreCancion()));
		            
		            
		        } 
		            // Triple-click detected
		            //int index = list.locationToIndex(evt.getPoint());
		            
		    }
		});
		
		
		
		frame.setTitle("Reproductor Musica Java v1.0 by Pablo98ad");
		try {
			frame.setIconImage(ImageIO.read(new File("Ficheros/calc.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

		
	private String primerVezTitulo(String t) {
		//al cambiar una cancion o el primer mensaje quie aparece en el texto resultado es tratado por este metodo
		//para poder hacer la marquesina en direccion derecha
		String espacios="                                                                                                                            ";
		t="\u200e"+t+"\u200f";
		String pantalla= espacios.substring(0,(espacios.length()/2)-t.length())+t;
		System.out.println(pantalla.length());
		
		pantalla=pantalla+espacios.substring(0, espacios.length()-pantalla.length());
		
		System.out.println(pantalla.length());
		System.out.println(espacios.length());
		return pantalla;
	}
	@Override
	public void start(Stage arg0) throws Exception {
		
		//mus.reproducirInicio();
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
