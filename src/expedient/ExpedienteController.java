package expedient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Rubert
 */
public class ExpedienteController implements Initializable {

    @FXML
    private ImageView imgView_imagen;

    @FXML
    private Button btn_modificar;
    @FXML
    private Button btn_agregar;
    @FXML
    private Button btn_nuevo;
    @FXML
    private Button btn_eliminar;

    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_apellido;
    @FXML
    private TextField txt_edad;
    @FXML
    private TextField txt_sexo;
    @FXML
    private TextField txt_domicilio;

    @FXML
    private TableView<Persona> tbl_epersonas;
    @FXML
    private TableColumn col_nombre;
    @FXML
    private TableColumn col_apellido;
    @FXML
    private TableColumn col_edad;
    @FXML
    private TableColumn col_sexo;
    @FXML
    private TableColumn col_domicilio;
    
    
    
    
    ObservableList<Persona> personas;

    private int posicionPersonaEnTabla;

    @FXML
    private void guardar(ActionEvent event) {
        Persona persona = new Persona();
        persona.nombre.set(txt_nombre.getText());
        persona.apellido.set(txt_apellido.getText());
        persona.edad.set(Integer.parseInt(txt_edad.getText()));
        persona.domicilio.set(txt_domicilio.getText());
        persona.sexo.set(txt_sexo.getText());
        persona.foto.set(nuevaFotoStr);

        // Guardamos los datos de la persona el la bdd
        persona.saveNewbdd();
        loadLista();
    }

    @FXML
    private void modificar(ActionEvent event) {
        Persona persona = new Persona();
        persona.nombre.set(txt_nombre.getText());
        persona.apellido.set(txt_apellido.getText());
        persona.edad.set(Integer.parseInt(txt_edad.getText()));
        persona.domicilio.set(txt_domicilio.getText());
        persona.sexo.set(txt_sexo.getText());
//        personas.set(posicionPersonaEnTabla, persona);
        final Persona persona2 = getTablaPersonasSeleccionada();
        persona.id.set(persona2.getId());
        persona.foto.set(persona2.foto.get());
        
        persona.saveUpDatebdd();
        loadLista();
    }

    @FXML
    private void nuevo(ActionEvent event) {
        txt_nombre.setText("");
        txt_apellido.setText("");
        txt_edad.setText("");
        txt_sexo.setText("");
        txt_domicilio.setText("");
        imgView_imagen.setImage(null);
        btn_agregar.setDisable(false);
        btn_modificar.setDisable(true);
        btn_eliminar.setDisable(true);
        
    }

    @FXML
    private void eliminar(ActionEvent event) {
//        personas.remove(posicionPersonaEnTabla);
        final Persona persona = getTablaPersonasSeleccionada();
        persona.deletebdd();
        loadLista();
    }

    @FXML
    private void cargarImagen(ActionEvent event) {
        try {
            JFileChooser file = new JFileChooser(System.getProperty("user.dir"));
            file.showOpenDialog(file);
            File archivo = file.getSelectedFile();
            
            apiEncodeBase64 encode = new apiEncodeBase64();
            nuevaFotoStr = encode.encoder(archivo);
            
            byte[] fileContent = Files.readAllBytes(archivo.toPath());
            ByteArrayInputStream bis = new ByteArrayInputStream(fileContent);
            Image img = new Image(bis);
            imgView_imagen.setImage(img);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error Importando - " + ex);
        }

    }

    private String nuevaFotoStr = "";

    private final ListChangeListener<Persona> selectorTablaPersonas
            = new ListChangeListener<Persona>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends Persona> c) {
            ponerPersonaSeleccionada();
        }
    };

    public Persona getTablaPersonasSeleccionada() {
        if (tbl_epersonas != null) {
            List<Persona> tabla = tbl_epersonas.getSelectionModel().getSelectedItems();
            
            if (tabla.size() == 1) {
                final Persona competicionSeleccionada = tabla.get(0);
                
                return competicionSeleccionada;
            }
        }
        return null;
    }

    private void ponerPersonaSeleccionada() {
        final Persona persona = getTablaPersonasSeleccionada();
        posicionPersonaEnTabla = personas.indexOf(persona);
        
        apiEncodeBase64 decode = new apiEncodeBase64();
        
        if (persona != null) {

            txt_nombre.setText(persona.getNombre());
            txt_apellido.setText(persona.getApellido());
            txt_edad.setText(persona.getEdad().toString());
            txt_domicilio.setText(persona.getDomicilio());
            txt_sexo.setText(persona.getSexo());
            imgView_imagen.setImage( decode.decoderV2(persona.foto.get()));

            btn_modificar.setDisable(false);
            btn_eliminar.setDisable(false);
            btn_agregar.setDisable(true);

        }
    }

    private void inicializarTablaPersonas() {
        
        col_nombre.setCellValueFactory(new PropertyValueFactory<Persona, String>("nombre"));
        col_apellido.setCellValueFactory(new PropertyValueFactory<Persona, String>("apellido"));
        col_edad.setCellValueFactory(new PropertyValueFactory<Persona, Integer>("edad"));
        col_domicilio.setCellValueFactory(new PropertyValueFactory<Persona, String>("domicilio"));
        col_sexo.setCellValueFactory(new PropertyValueFactory<Persona, String>("sexo"));

        personas = FXCollections.observableArrayList();
        tbl_epersonas.setItems(personas);

        loadLista();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.inicializarTablaPersonas();
        btn_modificar.setDisable(true);
        btn_eliminar.setDisable(true);

        final ObservableList<Persona> tablaPersonaSel = tbl_epersonas.getSelectionModel().getSelectedItems();
        tablaPersonaSel.addListener(selectorTablaPersonas);
    }

    
    public void loadLista() {
        personas.clear();
        conexionMySQL con = new conexionMySQL("root", "bbd_expediente", "isac84alejandro");
//        conexionMySQL con = new conexionMySQL("robert1", "bbd_expediente", "123.Hola");
        String sql = "SELECT * FROM `tbl_persona` WHERE (`activo` = '0')";
        ResultSet res;
        res = con.query(sql);

        try {
            while (res.next()) {
                System.out.println( res.getInt("id_persona") + " - " + res.getString("nombre") );
                
                Persona persona = new Persona();
                persona.id.set( res.getInt("id_persona")  );
                persona.nombre.set( res.getString("nombre") );
                persona.apellido.set( res.getString("apellido") );
                persona.edad.set( res.getInt("edad") );
                persona.sexo.set( res.getString("sexo") );
                persona.domicilio.set( res.getString("domicilio") );
                persona.foto.set( res.getString("foto") );
                
                personas.add(persona);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
