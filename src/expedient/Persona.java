package expedient;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Persona {

    conexionMySQL con;

    public SimpleIntegerProperty id;
    public SimpleStringProperty nombre;
    public SimpleStringProperty apellido;
    public SimpleIntegerProperty edad;
    public SimpleStringProperty domicilio;
    public SimpleStringProperty sexo;
    public SimpleStringProperty foto;

    public Persona() {
        id = new SimpleIntegerProperty();
        nombre = new SimpleStringProperty();
        apellido = new SimpleStringProperty();
        edad = new SimpleIntegerProperty();
        domicilio = new SimpleStringProperty();
        sexo = new SimpleStringProperty();
        foto = new SimpleStringProperty();

        con = new conexionMySQL("robert1", "bbd_expediente", "123.Hola");
    }
    
    public Integer getId() {
        return id.get();
    }

    public String getNombre() {
        return nombre.get();
    }

    public String getApellido() {
        return apellido.get();
    }

    public Integer getEdad() {
        return edad.get();
    }

    public String getDomicilio() {
        return domicilio.get();
    }

    public String getSexo() {
        return sexo.get();
    }

    public boolean saveNewbdd() {
        try {
            String sql = "INSERT INTO `tbl_persona` (`nombre`, `apellido`, `sexo`, `edad`, `domicilio`, `foto`) VALUES ";
            sql += "('" + nombre.get() + "', '" + apellido.get() + "', '" + sexo.get() + "', " + edad.get() + ", '" + domicilio.get() + "', '" + foto.get() + "'  );";
            String s = con.exeScriptInsert(sql);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return false;
    }
    
    public boolean saveUpDatebdd() {
        try {
            // UPDATE `tbl_aux` SET `dato` = 'SANDRO' WHERE (`id_aux` = '97');
            String sql = "UPDATE `tbl_persona` SET ";
            sql += "`nombre` = '" + nombre.get() + "',";
            sql += "`apellido` = '" + apellido.get() + "',";
            sql += "`sexo` = '" + sexo.get() + "',";
            sql += "`edad` = '" + edad.get() + "',";
            sql += "`domicilio` = '" + domicilio.get() + "',";
            sql += "`foto` = '" + foto.get() + "'";
            sql += " WHERE (`id_persona` = '" + id.get() + "')";
            
            con.exeScript(sql);
            System.out.println(sql);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return false;
    }
    
    public boolean deletebdd() {
        try {
            // UPDATE `tbl_aux` SET `dato` = 'SANDRO' WHERE (`id_aux` = '97');
            String sql = "UPDATE `tbl_persona` SET ";
            sql += "`activo` = '1'";
            sql += " WHERE (`id_persona` = '" + id.get() + "')";
            
            con.exeScript(sql);
            System.out.println(sql);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return false;
    }
}
