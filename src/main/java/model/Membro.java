package model;

public class Membro {

    private Integer id;     // ID_MEMBRO
    private Integer roll;   // ROLL
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String sexo;
    private boolean ativo;
    private Integer idIgreja;
    private Integer idMembroSelecionado = null;


    // Getters e Setters
    public Integer getIdIgreja() {  return idIgreja; }

    public void setIdIgreja(Integer idIgreja) { this.idIgreja = idIgreja; }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoll() {
        return roll;
    }

    public void setRoll(Integer roll) {
        this.roll = roll;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
