//AQUI REALIZAR ANALISE DE QUAL CENARIO DEVE SER MOSTRADO E MANDAR COMO VARIAVEL DEFINIDA
package tela;

import personagem.Jogador;

public class Cenario {

    public Portas portas;

    public Cenario() {
        this.portas = new Portas();
    }

    public String updateCenario(String cenarioAtual, Jogador jogador, int screenWidth) {

        // ===== TROCA DE CENÁRIO POR INTERAÇÃO COM W =====
        if (jogador.consumirInteracao()) {

            String novoCenario = portas.tentarUsarPorta(cenarioAtual, jogador);

            if (!novoCenario.equals(cenarioAtual)) {

                reposicionarJogadorDepoisDaPorta(cenarioAtual, novoCenario, jogador, screenWidth);

                return novoCenario;
            }
        }

        // ===== TROCA DE CENÁRIO AO SAIR DA TELA =====

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

    private void reposicionarJogadorDepoisDaPorta(
            String cenarioAntigo,
            String cenarioNovo,
            Jogador jogador,
            int screenWidth
    ) {

        // Entrada -> Escritório
        if (cenarioAntigo.equals("Entrada") && cenarioNovo.equals("Escritorio")) {
            jogador.x = 950;
        }

        // Escritório -> Entrada
        else if (cenarioAntigo.equals("Escritorio") && cenarioNovo.equals("Entrada")) {
            jogador.x = 980;
        }

        // Corredor -> Quarto
        else if (cenarioAntigo.equals("Corredor") && cenarioNovo.equals("Quarto")) {
            jogador.x = 900;
        }

        // Quarto -> Corredor
        else if (cenarioAntigo.equals("Quarto") && cenarioNovo.equals("Corredor")) {
            jogador.x = 430;
        }
    }
}
