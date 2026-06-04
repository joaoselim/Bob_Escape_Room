//AQUI REALIZAR ANALISE DE QUAL CENARIO DEVE SER MOSTRADO E MANDAR COMO VARIAVEL DEFINIDA
package tela;
import personagem.Jogador;

public class Cenario {

    public Portas portas;
    public Cenario() {
        this.portas = new Portas();
    }

    public String updateCenario(String cenarioAtual, Jogador jogador, int screenWidth){

        //TROCA DE CENÁRIOS AO SAIR DA TELA
        // Entrada -> Corredor
        if (cenarioAtual.equals("Entrada") && jogador.x >= 1260) {

            jogador.x = -50;
            return "Corredor";
        }

        // Corredor -> Entrada
        if (cenarioAtual.equals("Corredor") && jogador.x <= -100) {

            jogador.x = screenWidth - jogador.tamanhoPersonagem;
            return "Entrada";
        }

        // Corredor -> Bar
        if (cenarioAtual.equals("Corredor") && jogador.x >= 1260) {

            jogador.x = -50;
            return "Bar";
        }

        // Bar -> Corredor
        if (cenarioAtual.equals("Bar") && jogador.x <= -100) {

            jogador.x = screenWidth - jogador.tamanhoPersonagem;
            return "Corredor";
        }


        return cenarioAtual;
    }

}
