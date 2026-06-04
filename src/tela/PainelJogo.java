package tela;

import javax.swing.JPanel; //importa componente visual para gerar a tela
import java.awt.*; //usado para configurar tamanho da tela e desenhar imagens
import java.awt.image.BufferedImage; //armazena imagens carregadas
import java.io.InputStream;
import javax.imageio.ImageIO; //carrega imagens de arquivos, no nosso caso os cenarios

import personagem.Controle;
import personagem.Jogador;

//Aqui determinamos que nosso painel é um JPanel, e runnable fica atualizando a tela constantemente
public class PainelJogo extends JPanel implements Runnable{

    //Parametros do tamanho da tela
    final int screenWidth = 1365;
    final int screenHeight = 562;

    //É oque permite o jogo ficar rodando
    Thread gameThread;

    //Variável responsavel por verificar quais teclas estão precionadas
    Controle controle = new Controle();

    Jogador jogador = new Jogador(controle);
    Cenario cenario = new Cenario();

    BufferedImage background;
    String cenarioAtual;

    public PainelJogo(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true); // coloquei p resolver problema da tela piscando

        // Ativando tudo pro teclado funcionar
        this.addKeyListener(controle); // fiscaliza as teclas

        this.setFocusable(true);

        //carregamento do cenário
        try {

            background = ImageIO.read(
                    getClass().getResourceAsStream("/tela/Cenarios/entrada.png")
            );
            cenarioAtual = "Entrada";

        } catch (Exception e) {

            System.out.println("ERRO AO CARREGAR CENARIO");
            e.printStackTrace();
        }
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    //equanto o threads existir, o jogo roda
    @Override
    public void run() {

        while (gameThread != null){

            update(); //atualiza o estado do jogador (andando, pulando, posição, etc)

            repaint(); //redesnha o painel

            try {
                Thread.sleep(16); // o threads pausa por 16 milli-segundos, oque da em torno de 60fps
            } catch(Exception e) {}
        }
    }


    public void update(){

        jogador.update(cenarioAtual, cenario);

        String novoCenario = cenario.updateCenario(cenarioAtual, jogador, screenWidth);

        if (!novoCenario.equals(cenarioAtual)) {
            cenarioAtual = novoCenario;
            carregarCenario(cenarioAtual);
        }
        if (jogador.usaPorta){
            if (cenarioAtual.equals("Entrada")) {
                /*if (jogador.x <= 195){
                    carregarCenario("");
                }*/
                carregarCenario("Escritorio");
            }
            if (cenarioAtual.equals("Corredor")){
                carregarCenario("Quarto");
            }
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

    public void paintComponent(Graphics g) {

        super.paintComponent(g); //limpa a tela antes de pintar denovo

        Graphics2D g2 = (Graphics2D) g;

        // DESENHA CENARIO
        g2.drawImage(background, 0, 0, screenWidth, screenHeight, null);

        // DESENHA PLAYER
        jogador.draw(g2);

        g2.dispose(); //finaliza o graphics2D
    }
}
