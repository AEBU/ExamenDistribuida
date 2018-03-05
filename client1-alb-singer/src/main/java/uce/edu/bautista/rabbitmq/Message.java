package uce.edu.bautista.rabbitmq;

import java.io.Serializable;

/**
 * Created by Alexis on 02/03/2018.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private String mensaje;

    public Message() {
    }

    public Message(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Message{" +
                "mensaje='" + mensaje + '\'' +
                '}';
    }
}
