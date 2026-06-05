package tela;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;

import personagem.Controle;
import personagem.Jogador;

public class PainelJogo extends JPanel implements Runnable {

    final int screenWidth = 1365;
    final int screenHeight = 562;

    Thread gameThread;

    Controle controle = new Controle();

    Jogador jogador = new Jogador(controle);

    Cenario cenario = new Cenario();

    BufferedImage background;

    String cenarioAtual;

    public PainelJogo() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);

        this.addKeyListener(controle);
        this.setFocusable(true);

        cenarioAtual = "Entrada";
        carregarCenario(cenarioAtual);
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        while (gameThread != null) {

            update();

            repaint();

            try {
                Thread.sleep(16);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {

        if (cenario.permiteMovimentoJogador(cenarioAtual)) {
            jogador.update(cenarioAtual);
        }

        String novoCenario = cenario.updateCenario(cenarioAtual, jogador, screenWidth);

        if (!novoCenario.equals(cenarioAtual)) {

            cenarioAtual = novoCenario;
            carregarCenario(cenarioAtual);
        }
    }

    private void carregarCenario(String nomeCenario) {

        String caminhoImagem = "";

        if (nomeCenario.equals("Entrada")) {

            caminhoImagem = "/tela/Cenarios/entrada.png";

        } else if (nomeCenario.equals("Corredor")) {

            caminhoImagem = "/tela/Cenarios/corredor.png";

        } else if (nomeCenario.equals("Bar")) {

            caminhoImagem = "/tela/Cenarios/bar.png";

        } else if (nomeCenario.equals("Escritorio")) {

            caminhoImagem = "/tela/Cenarios/escritorio.png";

        } else if (nomeCenario.equals("Quarto")) {

            caminhoImagem = "/tela/Cenarios/quarto.png";

        } else if (nomeCenario.equals("Prateleira")) {

            caminhoImagem = "/tela/Cenarios/prateleira.png";

        } else if (nomeCenario.equals("CofreFechado")) {

            caminhoImagem = "/tela/Cenarios/cofre_fechado.png";

        } else if (nomeCenario.equals("CofreAberto")) {

            caminhoImagem = "/tela/Cenarios/cofre_aberto.png";

        } else {

            System.out.println("CENARIO DESCONHECIDO: " + nomeCenario);
            return;
        }

        try {

            InputStream stream = getClass().getResourceAsStream(caminhoImagem);

            if (stream == null) {
                throw new RuntimeException("Cenário não encontrado: " + caminhoImagem);
            }

            background = ImageIO.read(stream);

        } catch (Exception e) {

            System.out.println("ERRO AO CARREGAR CENARIO: " + caminhoImagem);
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (background != null) {
            g2.drawImage(background, 0, 0, screenWidth, screenHeight, null);
        }

        if (cenario.deveDesenharJogador(cenarioAtual)) {
            jogador.draw(g2);
        }

        g2.dispose();
    }
}