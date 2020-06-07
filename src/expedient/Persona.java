
package expedient;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Persona {
    
    public SimpleStringProperty nombre = new SimpleStringProperty();
    public SimpleStringProperty apellido = new SimpleStringProperty();
    public SimpleIntegerProperty edad = new SimpleIntegerProperty();
    public SimpleStringProperty domicilio = new SimpleStringProperty();
    public SimpleStringProperty sexo = new SimpleStringProperty();
    
    
    public String getNombre(){
        return nombre.get();
    }
    
    public String getApellido(){
        return apellido.get();
    }
    
    public Integer getEdad (){
        return edad.get();
    }
    
    public String getDomicilio(){
        return domicilio.get();
    }
    
    public String getSexo(){
        return sexo.get();
    }
    
}
