package es.uji.ei1027.clubesportiu.model;

public class Usuario {
    private String email;
    private String password;
    private String tipo; // "normal" o "promotor"

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "email='" + email + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
