package com.alura.hotel.view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import com.alura.hotel.controller.HuespedController;
import com.alura.hotel.controller.ReservaController;
import com.alura.hotel.modelo.Huesped;
import com.alura.hotel.modelo.Reserva;
import com.alura.hotel.util.JCalendarCellEditor;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JPanel btnCerrarBusqueda;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modeloReservas;
	private DefaultTableModel modeloHuespedes;
	private TableRowSorter<DefaultTableModel> sorterReservas;
	private TableRowSorter<DefaultTableModel> sorterHuespedes;
	private JLabel labelAtras;
	private JLabel labelExit;
	private JLabel lblBuscar;
	private JLabel lblCerrarBusqueda;
	private JLabel lblEditar;
	private JLabel lblEliminar;
	private JTabbedPane panel;
	private JTable tablaSeleccionada;
	private List<Reserva> reservas;
	private List<Huesped> huespedes;
	int xMouse, yMouse;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/alura/hotel/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setHorizontalAlignment(JTextField.RIGHT);
		txtBuscar.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtBuscar.setBackground(Color.WHITE);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtBuscar.setColumns(10);
		txtBuscar.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				int ascii = e.getKeyChar();
				if(ascii == 10){
					filtrarTabla();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
			
		});
		contentPane.add(txtBuscar);
		
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(300, 62, 300, 42);
		contentPane.add(lblNewLabel_4);
		
		UIManager.put("TabbedPane.selected", new Color(25, 155, 2));
		panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(180, 40, 4));
		panel.setForeground(Color.WHITE);
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		

		//Tabla, modelo y sorter - Reservas
		modeloReservas = new DefaultTableModel(){
			public boolean isCellEditable(int row, int column){
				if(column == 0 || column == 3){
					return false;
				} else {
					return true;
				}
			}
		};
		sorterReservas = new TableRowSorter<>(modeloReservas);
		tbReservas = new JTable(modeloReservas);
		tbReservas.setRowSorter(sorterReservas);
		tbReservas.setRowHeight(25);
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		tbReservas.setName("Reservas");
		modeloReservas.addColumn("Numero de Reserva");
		modeloReservas.addColumn("Fecha Check In");
		modeloReservas.addColumn("Fecha Check Out");
		modeloReservas.addColumn("Valor");
		modeloReservas.addColumn("Forma de Pago");

		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(getClass().getResource("/com/alura/hotel/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);
		
		//Especificando Editores de Celdas - Reservas

		//JComboBox para editar forma de pago
		TableColumn colFormaPago = tbReservas.getColumnModel().getColumn(4);
		JComboBox<String> formaPago = new JComboBox<>();
		formaPago.setBackground(SystemColor.text);
		formaPago.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		formaPago.setFont(new Font("Roboto", Font.PLAIN, 16));
		formaPago.setModel(new DefaultComboBoxModel<>(new String[] {"Tarjeta de Crédito", "Tarjeta de Débito", "Dinero en efectivo"}));
		colFormaPago.setCellEditor(new DefaultCellEditor(formaPago));

		//JDateChooser para editar fecha de entrada
		TableColumn colFechaEntrada = tbReservas.getColumnModel().getColumn(1);
		LocalDate minDateEntrada = LocalDate.now();
		colFechaEntrada.setCellEditor(new JCalendarCellEditor(new JCheckBox(), 1, 2, 3, new BigDecimal("750.00"), minDateEntrada, null));

		//JDateChooser para editar fecha de salida
		TableColumn colFechaSalida = tbReservas.getColumnModel().getColumn(2);
		colFechaSalida.setCellEditor(new JCalendarCellEditor(new JCheckBox(), 1, 2, 3, new BigDecimal("750.00"), null, null));



		//Tabla, modelo y sorter - Huespedes
		modeloHuespedes = new DefaultTableModel(){
			public boolean isCellEditable(int row, int column){
				if(column == 0 || column == 6){
					return false;
				} else {
					return true;
				}
			}
		};
		sorterHuespedes = new TableRowSorter<>(modeloHuespedes);
		tbHuespedes = new JTable(modeloHuespedes);
		tbHuespedes.setRowSorter(sorterHuespedes);
		tbHuespedes.setRowHeight(25);
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		tbHuespedes.setName("Huespedes");
		modeloHuespedes.addColumn("Número de Huesped");
		modeloHuespedes.addColumn("Nombre");
		modeloHuespedes.addColumn("Apellido");
		modeloHuespedes.addColumn("Fecha de Nacimiento");
		modeloHuespedes.addColumn("Nacionalidad");
		modeloHuespedes.addColumn("Telefono");
		modeloHuespedes.addColumn("Número de Reserva");

		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(getClass().getResource("/com/alura/hotel/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);

		//Especificando Editores de Celdas - Reservas

		//JTextField para editar nombre
		TableColumn colNombre = tbHuespedes.getColumnModel().getColumn(1);
		JTextField nombre = new JTextField();
		nombre.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				int ascii = e.getKeyChar();
				
				boolean esLetraMinuscula = (ascii >= 97 && ascii <= 122) || ascii == 241;
				boolean esLetraMayuscula = (ascii >= 65 && ascii <= 90) || ascii == 209;
				boolean esEspacio = ascii == 32;
				boolean esLetraConAcento = ascii == 225 || ascii == 233 || ascii == 237 || ascii == 243 || ascii == 250;

				if (!(esLetraMinuscula || esLetraMayuscula || esEspacio || esLetraConAcento)) {
					e.consume();
				}
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
			
		});
		colNombre.setCellEditor(new DefaultCellEditor(nombre));

		//JTextField para editar apellido
		TableColumn colApellido = tbHuespedes.getColumnModel().getColumn(2);
		JTextField apellido = new JTextField();
		apellido.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				int ascii = e.getKeyChar();
				
				boolean esLetraMinuscula = (ascii >= 97 && ascii <= 122) || ascii == 241;
				boolean esLetraMayuscula = (ascii >= 65 && ascii <= 90) || ascii == 209;
				boolean esEspacio = ascii == 32;
				boolean esLetraConAcento = ascii == 225 || ascii == 233 || ascii == 237 || ascii == 243 || ascii == 250;

				if (!(esLetraMinuscula || esLetraMayuscula || esEspacio || esLetraConAcento)) {
					e.consume();
				}
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
			
		});
		colApellido.setCellEditor(new DefaultCellEditor(apellido));

		//JDateChooser para editar fecha de nacimiento
		TableColumn colFechaNacimiento = tbHuespedes.getColumnModel().getColumn(3);
		LocalDate maxDateNacimiento = LocalDate.now().minusYears(18);
		colFechaNacimiento.setCellEditor(new JCalendarCellEditor(new JCheckBox(), 3, null, maxDateNacimiento));

		//JComboBox para editar nacionalidad
		TableColumn colNacionalidad = tbHuespedes.getColumnModel().getColumn(4);
		JComboBox<String> nacionalidad = new JComboBox<>();
		nacionalidad.setBackground(SystemColor.text);
		nacionalidad.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		nacionalidad.setFont(new Font("Roboto", Font.PLAIN, 16));
		nacionalidad.setModel(new DefaultComboBoxModel<>(new String[] {"afgano-afgana", "alemán-", "alemana", "árabe-árabe", "argentino-argentina", "australiano-australiana", "belga-belga", "boliviano-boliviana", "brasileño-brasileña", "camboyano-camboyana", "canadiense-canadiense", "chileno-chilena", "chino-china", "colombiano-colombiana", "coreano-coreana", "costarricense-costarricense", "cubano-cubana", "danés-danesa", "ecuatoriano-ecuatoriana", "egipcio-egipcia", "salvadoreño-salvadoreña", "escocés-escocesa", "español-española", "estadounidense-estadounidense", "estonio-estonia", "etiope-etiope", "filipino-filipina", "finlandés-finlandesa", "francés-francesa", "galés-galesa", "griego-griega", "guatemalteco-guatemalteca", "haitiano-haitiana", "holandés-holandesa", "hondureño-hondureña", "indonés-indonesa", "inglés-inglesa", "iraquí-iraquí", "iraní-iraní", "irlandés-irlandesa", "israelí-israelí", "italiano-italiana", "japonés-japonesa", "jordano-jordana", "laosiano-laosiana", "letón-letona", "letonés-letonesa", "malayo-malaya", "marroquí-marroquí", "mexicano-mexicana", "nicaragüense-nicaragüense", "noruego-noruega", "neozelandés-neozelandesa", "panameño-panameña", "paraguayo-paraguaya", "peruano-peruana", "polaco-polaca", "portugués-portuguesa", "puertorriqueño-puertorriqueño", "dominicano-dominicana", "rumano-rumana", "ruso-rusa", "sueco-sueca", "suizo-suiza", "tailandés-tailandesa", "taiwanes-taiwanesa", "turco-turca", "ucraniano-ucraniana", "uruguayo-uruguaya", "venezolano-venezolana", "vietnamita-vietnamita"}));
		colNacionalidad.setCellEditor(new DefaultCellEditor(nacionalidad));

		//JTextField para editar numero de telefono
		TableColumn colTelefono = tbHuespedes.getColumnModel().getColumn(5);
		JTextField telefono = new JTextField();
		telefono.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				int ascii = e.getKeyChar();
				
				boolean esNumero = (ascii >= 48 && ascii <= 57);

				if (!esNumero) {
					e.consume();
				}

				if (telefono.getText().replace(" ", "").length() == 10) {
					e.consume();
				}
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
			
		});
		colTelefono.setCellEditor(new DefaultCellEditor(telefono));

		

		//Agregando datos a las tablas
		cargarTablasDesdeDB();



		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(getClass().getResource("/com/alura/hotel/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea cerrar la sesion y volver al menu pricipal?", "Confirmación", JOptionPane.OK_CANCEL_OPTION);
				if(confirmacion == 0){
					MenuPrincipal principal = new MenuPrincipal();
					principal.setVisible(true);
					dispose();
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				filtrarTabla();
			}
			@Override
			public void mouseEntered(MouseEvent e) { 
				btnbuscar.setBackground(new Color(12, 100, 150));
				lblBuscar.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { 
				 btnbuscar.setBackground(new Color(12, 138, 199));
			     lblBuscar.setForeground(Color.white);
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

		btnCerrarBusqueda = new JPanel();
		btnCerrarBusqueda.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtBuscar.setText(null);
				filtrarTabla();
			}
			@Override
			public void mouseEntered(MouseEvent e) { 
				btnCerrarBusqueda.setBackground(new Color(230, 27, 4));
				lblCerrarBusqueda.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { 
				 btnCerrarBusqueda.setBackground(new Color(200, 23, 4));
			     lblCerrarBusqueda.setForeground(Color.white);
			}
		});
		btnCerrarBusqueda.setLayout(null);
		btnCerrarBusqueda.setBackground(new Color(200, 23, 4));
		btnCerrarBusqueda.setBounds(515, 137, 18, 18);
		btnCerrarBusqueda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnCerrarBusqueda.setVisible(false);
		contentPane.add(btnCerrarBusqueda);
		
		lblCerrarBusqueda = new JLabel("X");
		lblCerrarBusqueda.setBounds(0, 0, 18, 18);
		btnCerrarBusqueda.add(lblCerrarBusqueda);
		lblCerrarBusqueda.setHorizontalAlignment(SwingConstants.CENTER);
		lblCerrarBusqueda.setForeground(Color.WHITE);
		lblCerrarBusqueda.setFont(new Font("Roboto", Font.BOLD, 14));
		lblCerrarBusqueda.setVisible(false);
		
		JPanel btnEditar = new JPanel();
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					editarRegistro();
				} catch (Exception exception) {
					return;
				} finally {	
					txtBuscar.setText(null);
					filtrarTabla();
					sincronizarViewModel();
				}

			}
			@Override
			public void mouseEntered(MouseEvent e) { 
				btnEditar.setBackground(new Color(12, 100, 150));
				lblEditar.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { 
				 btnEditar.setBackground(new Color(12, 138, 199));
			     lblEditar.setForeground(Color.white);
			}
		});
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					eliminarRegistro();
				} catch (Exception exception) {
					return;
				} finally {	
					txtBuscar.setText(null);
					filtrarTabla();
					sincronizarViewModel();
				}

			}
			@Override
			public void mouseEntered(MouseEvent e) { 
				btnEliminar.setBackground(new Color(12, 100, 150));
				lblEliminar.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { 
				 btnEliminar.setBackground(new Color(12, 138, 199));
			     lblEliminar.setForeground(Color.white);
			}
		});
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);

		setResizable(false);
	}

	private void sincronizarViewModel() {
		modeloReservas.fireTableDataChanged();
		modeloHuespedes.fireTableDataChanged();
	}

	private void eliminarRegistro() {

		if(tieneFilaSeleccionada()){

			int confirmacion = JOptionPane.showConfirmDialog(null, "Este proceso no podrá revertirse. ¿Desea eliminar el registro?", "Confirmación", JOptionPane.OK_CANCEL_OPTION);
			if(confirmacion == 0){

				Integer idHuesped = 0;
				Integer idReserva = 0; 
				String nombreDeTabla = tablaSeleccionada.getName();
				Integer registrosAfectadosEnBaseDeDatos = 0;

				if (nombreDeTabla.equals("Huespedes")) {
					idHuesped = Integer.valueOf(tablaSeleccionada.getValueAt(tablaSeleccionada.getSelectedRow(), 0).toString());
					idReserva = Integer.valueOf(tablaSeleccionada.getValueAt(tablaSeleccionada.getSelectedRow(), 6).toString());

				} else if(nombreDeTabla.equals("Reservas")){
					idReserva = Integer.valueOf(tablaSeleccionada.getValueAt(tablaSeleccionada.getSelectedRow(), 0).toString());
					idHuesped = ReservaController.getHuespedId(idReserva);
				}

				registrosAfectadosEnBaseDeDatos = HuespedController.eliminarHuesped(idHuesped);
				registrosAfectadosEnBaseDeDatos = ReservaController.eliminarReserva(idReserva);

				if(registrosAfectadosEnBaseDeDatos > 0){
					JOptionPane.showMessageDialog(this, "Item eliminado con éxito!");
					limpiarTablas();
					cargarTablasDesdeDB();
				} else {
					JOptionPane.showMessageDialog(null, "Ocurrio un error al eliminar el registro. La operación no pudo ser completada");
				}

				tbReservas.clearSelection();
				tbHuespedes.clearSelection();
				
			}
		} else {
			JOptionPane.showMessageDialog(null, "Debe seleccionar una fila para eliminarla");
		}
		
	}

	private void editarRegistro(){
		if(tieneFilaSeleccionada()){

			int confirmacion = JOptionPane.showConfirmDialog(null, "Este proceso no podrá revertirse. ¿Desea editar el registro?", "Confirmación", JOptionPane.OK_CANCEL_OPTION);
			if(confirmacion == 0){

				tablaSeleccionada.getCellEditor().stopCellEditing();

				String nombreDeTabla = tablaSeleccionada.getName();
				Integer registrosAfectadosEnBaseDeDatos = 0;
				
				if (nombreDeTabla.equals("Huespedes")) {
					if(verificarDatosHuesped()){
						System.out.println(verificarDatosHuesped());
						Huesped huespedActualizado = obtenerHuespedActualizado();
						registrosAfectadosEnBaseDeDatos = HuespedController.editarHuesped(huespedActualizado);
					}
				} else if(nombreDeTabla.equals("Reservas")){
					if(verificarDatosReserva()){
						Reserva reservaActualizada = obtenerReservaActualizada();
						registrosAfectadosEnBaseDeDatos = ReservaController.editarReserva(reservaActualizada);
					}
				}

				if(registrosAfectadosEnBaseDeDatos > 0){
					JOptionPane.showMessageDialog(this, "Registro actualizado con éxito!");
					limpiarTablas();
					cargarTablasDesdeDB();
				} else {
					JOptionPane.showMessageDialog(null, "Ocurrio un error al actualizar el registro. La operación no pudo ser completada");
					limpiarTablas();
					cargarTablasDesdeGIU();
				}

				tbReservas.clearSelection();
				tbHuespedes.clearSelection();	
			}

		} else {
			JOptionPane.showMessageDialog(null, "Debe seleccionar una fila para editar los datos");
		}

	}

	private Boolean verificarDatosReserva() {

		Boolean datosValidos = true;

		LocalDate fechaEntrada = (LocalDate) tbReservas.getValueAt(tablaSeleccionada.getSelectedRow(), 1);
		LocalDate fechaSalida = (LocalDate) tbReservas.getValueAt(tablaSeleccionada.getSelectedRow(), 2);
		BigDecimal valor = (BigDecimal) tbReservas.getValueAt(tablaSeleccionada.getSelectedRow(), 3);

		if(fechaEntrada == null){
			datosValidos = false;
		}
		if(fechaSalida == null){
			datosValidos = false;
		}
		if(valor == null){
			datosValidos = false;
		}

		return datosValidos;
	}

	private Boolean verificarDatosHuesped() {

		Boolean datosValidos = true;

		Object nombre = tbHuespedes.getValueAt(tablaSeleccionada.getSelectedRow(), 1);
		Object apellido = tbHuespedes.getValueAt(tablaSeleccionada.getSelectedRow(), 2);
		LocalDate fechaNacimiento = (LocalDate) tbHuespedes.getValueAt(tablaSeleccionada.getSelectedRow(), 3);
		Object telefono = tbHuespedes.getValueAt(tablaSeleccionada.getSelectedRow(), 5);

		if(nombre == null || nombre.toString().trim().isEmpty()){
			datosValidos = false;
		}
		if(apellido == null || apellido.toString().trim().isEmpty()){
			datosValidos = false;
		}
		if(fechaNacimiento == null){
			datosValidos = false;
		}
		if(telefono == null || (telefono.toString().trim().length() < 10)){
			datosValidos = false;
		}

		return datosValidos;
	}

	private Reserva obtenerReservaActualizada(){

		Integer id = (Integer) tbReservas.getValueAt(tablaSeleccionada.getSelectedRow(), 0);
		LocalDate fechaEntrada = (LocalDate) tbReservas.getValueAt(tablaSeleccionada.getSelectedRow(), 1);
		LocalDate fechaSalida = (LocalDate) tbReservas.getValueAt(tablaSeleccionada.getSelectedRow(), 2);
		BigDecimal valor = (BigDecimal) tbReservas.getValueAt(tablaSeleccionada.getSelectedRow(), 3);
		String formaDePago = (String) tbReservas.getValueAt(tablaSeleccionada.getSelectedRow(), 4);

		Reserva reservaActualizada = new Reserva(id, fechaEntrada, fechaSalida, valor, formaDePago);

		return reservaActualizada;

	}

	private Huesped obtenerHuespedActualizado(){

		Integer id = (Integer) tbHuespedes.getValueAt(tablaSeleccionada.getSelectedRow(), 0);
		String nombre = (String) tbHuespedes.getValueAt(tablaSeleccionada.getSelectedRow(), 1);
		String apellido = (String) tbHuespedes.getValueAt(tablaSeleccionada.getSelectedRow(), 2);
		LocalDate fechaNacimiento = (LocalDate) tbHuespedes.getValueAt(tablaSeleccionada.getSelectedRow(), 3);
		String nacionalidad = (String) tbHuespedes.getValueAt(tablaSeleccionada.getSelectedRow(), 4);
		Long telefono = Long.valueOf(tbHuespedes.getValueAt(tablaSeleccionada.getSelectedRow(), 5).toString());
		Integer reserva_ID = (Integer) tbHuespedes.getValueAt(tablaSeleccionada.getSelectedRow(), 6);

		Huesped huespedActualizado = new Huesped(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, reserva_ID);

		return huespedActualizado;

	}

	private void limpiarTablas() {
        modeloReservas.getDataVector().removeAllElements();
		modeloHuespedes.getDataVector().removeAllElements();

		sincronizarViewModel();
    }

	private boolean tieneFilaSeleccionada() {
		int indiceTablaSeleccionada = panel.getSelectedIndex();
		JScrollPane contenedorTablaSeleccionada = (JScrollPane) panel.getComponentAt(indiceTablaSeleccionada);
		tablaSeleccionada = (JTable) contenedorTablaSeleccionada.getViewport().getView();

		int filaSeleccionada = tablaSeleccionada.getSelectedRowCount();

		if(filaSeleccionada > 0){
			return true;
		}

		return false;
				
	}

	private void filtrarTabla() {
		RowFilter<DefaultTableModel, Object> filtro = null;
		try {
			filtro = RowFilter.numberFilter(ComparisonType.EQUAL, Integer.valueOf(txtBuscar.getText()), 0);
		} catch (IllegalArgumentException e) {
			try {
				filtro = RowFilter.regexFilter(txtBuscar.getText(), 1, 2);
			} catch (IllegalArgumentException | NullPointerException e2) {
				return;
			}
		}
		sorterReservas.setRowFilter(filtro);
		sorterHuespedes.setRowFilter(filtro);

		if(!(filtro == null || txtBuscar.getText().trim().isEmpty())){
			btnCerrarBusqueda.setVisible(true);
			lblCerrarBusqueda.setVisible(true);
		} else {
			btnCerrarBusqueda.setVisible(false);
			lblCerrarBusqueda.setVisible(false);
		}
		sincronizarViewModel();
	}

	private void cargarTablasDesdeDB() {
		reservas = ReservaController.listarReservas();
		reservas.forEach(reserva -> modeloReservas.addRow(new Object[]{reserva.getId(), reserva.getFechaEntrada(), reserva.getFechaSalida(), reserva.getValor(), reserva.getFormaDePago()}));
		
		huespedes = HuespedController.listarHuespedes();
		huespedes.forEach(huesped -> modeloHuespedes.addRow(new Object[]{huesped.getId(), huesped.getNombre(), huesped.getApellido(), huesped.getFechaDeNacimiento(), huesped.getNacionalidad(), huesped.getTelefono(), huesped.getReserva_ID()}));

		sincronizarViewModel();
	}

	private void cargarTablasDesdeGIU(){
		reservas.forEach(reserva -> modeloReservas.addRow(new Object[]{reserva.getId(), reserva.getFechaEntrada(), reserva.getFechaSalida(), reserva.getValor(), reserva.getFormaDePago()}));

		huespedes.forEach(huesped -> modeloHuespedes.addRow(new Object[]{huesped.getId(), huesped.getNombre(), huesped.getApellido(), huesped.getFechaDeNacimiento(), huesped.getNacionalidad(), huesped.getTelefono(), huesped.getReserva_ID()}));
		
		sincronizarViewModel();
	}



	//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}
}
