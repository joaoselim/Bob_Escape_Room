package tela;

import personagem.Jogador;

public class Portas {

    private boolean portaEscritorio = false;
    private boolean portaQuarto = false;
    private boolean portaHotel = false;

    public void destrancar(int x, String cenarioAtual){
        if(cenarioAtual.equals("Entrada") && x > 100){
            portaEscritorio = true;
        }
        if(cenarioAtual.equals("Entrada") && x <= 195){
            portaHotel = true;
        }
        if(cenarioAtual.equals("Corredor")){
            portaQuarto = true;
        }
    }

    public boolean isPortaEscritorio() {
        return portaEscritorio;
    }

    public boolean isPortaQuarto() {
        return portaQuarto;
    }

    public boolean isPortaHotel() {
        return portaHotel;
    }
}
