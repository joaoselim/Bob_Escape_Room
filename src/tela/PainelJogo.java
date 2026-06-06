package tela;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    BufferedImage imagemEnigma;

    boolean mostrarEnigma = false;

    String cenarioAtual;

    public PainelJogo() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);

        this.addKeyListener(controle);
        this.setFocusable(true);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                analisarClique(e.getX(), e.getY());
            }
        });

        cenarioAtual = "Entrada";
        carregarCenario(cenarioAtual);
        carregarImagemEnigma();
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

        if (controle.consumirInventario()) {
            mostrarEnigma = !mostrarEnigma;
        }

        if (cenario.permiteMovimentoJogador(cenarioAtual)) {
            jogador.update(cenarioAtual);
        }

        String novoCenario = cenario.updateCenario(cenarioAtual, jogador, screenWidth);

        if (!novoCenario.equals(cenarioAtual)) {

            cenarioAtual = novoCenario;
            carregarCenario(cenarioAtual);
        }
    }

    private void analisarClique(int mouseX, int mouseY) {

        if (cenarioAtual.equals("CofreFechado")) {

            inputSenhaCofre();

            this.requestFocusInWindow();
        }
    }
    private void inputSenhaCofre(){

        String senhaInserida = JOptionPane.showInputDialog(null, "Digite a senha:");
        boolean abriu = cenario.getCofre().analisarSenha(senhaInserida);

        if (abriu) {
            cenarioAtual = "CofreAberto";
            carregarCenario(cenarioAtual);
            repaint();

        } else {

            if (senhaInserida != null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Senha incorreta!"
                );
            }
        }

    }

    private void carregarImagemEnigma() {

        String caminhoImagem = "/personagem/Inventario/Itens/ObjetosInterativos/Enigma_kakatua.png";

        try {

            InputStream stream = getClass().getResourceAsStream(caminhoImagem);

            if (stream == null) {
                throw new RuntimeException("Imagem do enigma não encontrada: " + caminhoImagem);
            }

            imagemEnigma = ImageIO.read(stream);

        } catch (Exception e) {

            System.out.println("ERRO AO CARREGAR IMAGEM DO ENIGMA");
            e.printStackTrace();
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

    private void desenharEnigma(Graphics2D g2) {

        if (imagemEnigma == null) {
            return;
        }

        int larguraOriginal = imagemEnigma.getWidth();
        int alturaOriginal = imagemEnigma.getHeight();

        int larguraMaxima = (int) (screenWidth * 0.85);
        int alturaMaxima = (int) (screenHeight * 0.85);

        double escalaLargura = (double) larguraMaxima / larguraOriginal;
        double escalaAltura = (double) alturaMaxima / alturaOriginal;
        double escala = Math.min(escalaLargura, escalaAltura);

        if (escala > 1) {
            escala = 1;
        }

        int larguraFinal = (int) (larguraOriginal * escala);
        int alturaFinal = (int) (alturaOriginal * escala);

        int x = (screenWidth - larguraFinal) / 2;
        int y = (screenHeight - alturaFinal) / 2;

        g2.drawImage(imagemEnigma, x, y, larguraFinal, alturaFinal, null);
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

        if (mostrarEnigma && jogador.possuiItem("papel")) {
            desenharEnigma(g2);
        }

        g2.dispose();

    }
}