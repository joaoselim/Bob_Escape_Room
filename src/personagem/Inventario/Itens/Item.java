package personagem.Inventario.Itens;

public class Item {

    private boolean chave_escritorio;
    private boolean chave_quarto;
    private boolean chave_hotel;
    private boolean dollar;
    private boolean papel;

    //GETTERS E SETTERS
    public boolean isChave_Patrão() {return chave_escritorio;}
    public void setChave_Escritorio(boolean chave_Patrão) {
        this.chave_escritorio = chave_Patrão;
    }

    public boolean isChave_quarto() {return chave_quarto;}
    public void setChave_quarto(boolean chave_quarto) {
        this.chave_quarto = chave_quarto;
    }

    public boolean isChave_hotel() {return chave_hotel;}
    public void setChave_hotel(boolean chave_hotel) {
        this.chave_hotel = chave_hotel;
    }

    public boolean isDollar() {return dollar;}
    public void setDollar(boolean dollar) {
        this.dollar = dollar;
    }

    public boolean isPapel() {return papel;}
    public void setPapel(boolean papel) {
        this.papel = papel;
    }
}
