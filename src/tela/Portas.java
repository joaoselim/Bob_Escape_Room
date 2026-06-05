package tela;

import personagem.Jogador;

public class Portas {

    private boolean portaEscritorio = false;
    private boolean portaQuarto = false;
    private boolean portaHotel = false;

    public String tentarUsarPorta(String cenarioAtual, Jogador jogador) {

        // PORTA DO HOTEL
        if (cenarioAtual.equals("Entrada") && jogador.x <= 195) {

            if (!portaHotel) {
                portaHotel = true;
                System.out.println("Porta do hotel destrancada");
                return cenarioAtual;
            }

            System.out.println("Concluiu o jogo, encerrando");
            return cenarioAtual;
        }

        // PORTA DO ESCRITÓRIO
        if (cenarioAtual.equals("Entrada") && jogador.x >= 910 && jogador.x <= 1065) {

            if (!portaEscritorio) {
                portaEscritorio = true;
                System.out.println("Porta do escritório destrancada");
                return cenarioAtual;
            }

            return "Escritorio";
        }

        // SAIR DO ESCRITÓRIO
        if (cenarioAtual.equals("Escritorio") && jogador.x >= 1100) {
            return "Entrada";
        }

        // PORTA DO QUARTO
        if (cenarioAtual.equals("Corredor") && jogador.x >= 360 && jogador.x <= 510) {

            if (!portaQuarto) {
                portaQuarto = true;
                System.out.println("Porta do quarto destrancada");
                return cenarioAtual;
            }

            return "Quarto";
        }

        // SAIR DO QUARTO
        if (cenarioAtual.equals("Quarto") && jogador.x >= 1065) {
            return "Corredor";
        }

        return null;
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