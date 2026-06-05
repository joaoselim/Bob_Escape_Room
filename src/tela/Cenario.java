package tela;

import personagem.Jogador;

public class Cenario {

    public Portas portas;

    public Cenario() {
        this.portas = new Portas();
    }

    public String updateCenario(String cenarioAtual, Jogador jogador, int screenWidth) {

        if (jogador.consumirInteracao()) {

            // Se estiver em uma tela de foco, W volta para o escritório
            if (cenarioAtual.equals("Prateleira")) {
                jogador.x = 80;
                return "Escritorio";
            }

            if (cenarioAtual.equals("CofreFechado")) {
                jogador.x = 590;
                return "Escritorio";
            }

            if (cenarioAtual.equals("CofreAberto")) {
                jogador.x = 590;
                return "Escritorio";
            }

            // Primeiro tenta usar portas
            String destinoPorta = portas.tentarUsarPorta(cenarioAtual, jogador);

            if (destinoPorta != null) {

                if (!destinoPorta.equals(cenarioAtual)) {
                    reposicionarJogadorDepoisDaPorta(cenarioAtual, destinoPorta, jogador, screenWidth);
                }

                return destinoPorta;
            }

            // Se não era porta, tenta interações normais do mapa
            String destinoInteracao = verificarInteracoesDoMapa(cenarioAtual, jogador);

            if (!destinoInteracao.equals(cenarioAtual)) {
                return destinoInteracao;
            }
        }

        // TROCA DE CENÁRIO AO SAIR DA TELA

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

    private String verificarInteracoesDoMapa(String cenarioAtual, Jogador jogador) {

        // MESA DA ENTRADA
        if (cenarioAtual.equals("Entrada") && jogador.x >= 450 && jogador.x <= 610) {
            System.out.println("Chave Escritorio");
            return cenarioAtual;
        }

        // MÁQUINA DE SALGADINHO
        if (cenarioAtual.equals("Bar") && jogador.x >= 1050) {
            System.out.println("Papel Cifrado");
            return cenarioAtual;
        }

        // MESINHA DO QUARTO
        if (cenarioAtual.equals("Quarto") && jogador.x >= 600 && jogador.x <= 730) {
            System.out.println("Dolar");
            return cenarioAtual;
        }

        // LIXEIRA DO ESCRITÓRIO
        if (cenarioAtual.equals("Escritorio") && jogador.x >= 125 && jogador.x <= 250) {
            System.out.println("Chave Quarto");
            return cenarioAtual;
        }

        // PRATELEIRA
        if (cenarioAtual.equals("Escritorio") && jogador.x <= 125) {
            return "Prateleira";
        }

        // COFRE
        if (cenarioAtual.equals("Escritorio") && jogador.x >= 400 && jogador.x <= 780) {
            return "CofreFechado";
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
            jogador.x = 1000;
        }

        // Escritório -> Entrada
        else if (cenarioAntigo.equals("Escritorio") && cenarioNovo.equals("Entrada")) {
            jogador.x = 980;
        }

        // Corredor -> Quarto
        else if (cenarioAntigo.equals("Corredor") && cenarioNovo.equals("Quarto")) {
            jogador.x = 1000;
        }

        // Quarto -> Corredor
        else if (cenarioAntigo.equals("Quarto") && cenarioNovo.equals("Corredor")) {
            jogador.x = 430;
        }
    }

    public boolean deveDesenharJogador(String cenarioAtual) {

        if (cenarioAtual.equals("Prateleira")) {
            return false;
        }

        if (cenarioAtual.equals("CofreFechado")) {
            return false;
        }

        if (cenarioAtual.equals("CofreAberto")) {
            return false;
        }

        return true;
    }

    public boolean permiteMovimentoJogador(String cenarioAtual) {

        if (cenarioAtual.equals("Prateleira")) {
            return false;
        }

        if (cenarioAtual.equals("CofreFechado")) {
            return false;
        }

        if (cenarioAtual.equals("CofreAberto")) {
            return false;
        }

        return true;
    }
}