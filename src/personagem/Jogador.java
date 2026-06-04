package personagem;

import com.sun.source.tree.ReturnTree;
import tela.Cenario;

import java.io.InputStream; //Unico diferente da classe painelJogo, carrega arquivos que estão dentro do projeto
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Jogador {

    Controle controle;

    //Inventario inventario;

    public boolean usaPorta = false;

    //===== POSIÇÃO =====
    public int x = 0;

    public final int tamanhoPersonagem = 185;

    final int chaoY = 527; // É a altura onde esta o topo da cabeça

    int y = chaoY - tamanhoPersonagem;

    int velocidade = 5;

    // GUARDA PARA QUAL LADO O BOB TA OLHANDO
    boolean olhandoPraDireita = true;

    double velocidadeY = 0;
    double gravidade = 0.6;

    boolean pulando = false;


    //===== SPRITES =====
    BufferedImage[] framesAndando;
    BufferedImage[] framesParado;

    BufferedImage framePulando;

    //===== ANIMAÇÃO =====
    int controleSprite = 0;
    int spriteNum = 0;

    public Jogador(Controle controle){

        this.controle = controle;    //<<<<<========== questionar o pq

        try{

            //
            BufferedImage recorteAndando =
                    carregarImagem("/personagem/animacoes/BobAndando.png");
            BufferedImage recorteParado =
                    carregarImagem("/personagem/animacoes/BobParado.png");
            BufferedImage recortePulo =
                    carregarImagem("/personagem/animacoes/BobPulando.png");

            //===== RECORTAR O SPRITES =====
            framesAndando = cortandoFrames(recorteAndando, 3, 3, 8);
            framesParado = cortandoFrames(recorteParado, 5, 5, 25);
            framePulando = recortePulo;

        } catch(Exception e){

            System.out.println("ERRO AO CARREGAR SPRITES");
            e.printStackTrace();
        }
    }

    private BufferedImage carregarImagem(String caminho) throws Exception {

        InputStream stream = getClass().getResourceAsStream(caminho);

        //VERIFICA SE ACHOU A IMAGEM
        if(stream == null) {
            throw new RuntimeException("Imagem não encontrada: " + caminho);
        }

        return ImageIO.read(stream);
    }

    private BufferedImage[] cortandoFrames(BufferedImage recorte, int colunas, int linhas, int numFrames){

        BufferedImage[] frames = new BufferedImage[numFrames];

        int larguraFrame = recorte.getWidth() / colunas;
        int alturaFrame = recorte.getHeight() / linhas;

        int frame = 0;

        for(int linha = 0; linha < linhas; linha++){

            for(int coluna = 0; coluna < colunas; coluna++){

                if(frame < numFrames){

                    frames[frame] = recorte.getSubimage(coluna * larguraFrame, linha * alturaFrame, larguraFrame, alturaFrame);

                    frame++;
                }
            }
        }
        return frames;
    }

    public void update(String cenarioAtual, Cenario cenario){

        boolean podeIrDireita = false;
        boolean podeIrEsquerda = false;

        if(controle.interagirAcionado){
            Interacao(cenarioAtual, cenario);
        }


        if(cenarioAtual.equals("Entrada") || cenarioAtual.equals("Corredor")){
            podeIrDireita = true;
        }
        if(cenarioAtual.equals("Corredor") || cenarioAtual.equals("Bar")){
            podeIrEsquerda = true;
        }

        //===== MOVIMENTO HORIZONTAL =====
        if(controle.esquerdaAcionado){
            if(!podeIrEsquerda){
                if(x > -55){
                    x -= velocidade;
                }
            }else {
                x -= velocidade;
            }
            olhandoPraDireita = false;
        }
        if(controle.direitaAcionado){
            if(!podeIrDireita){
                if(x < 1210){
                    x += velocidade;
                }
            }else {
                x += velocidade;
            }
            olhandoPraDireita = true;
        }

        //System.out.println(x);
        //===== ANIMACAO =====
        controleSprite++; // SERVE PARA CONTROLAR A VELOCIDADE NA TROCA DE FRAMES

        if(controleSprite > 10){

            spriteNum++;

            int maxFrames;

            if(pulando){
                maxFrames = 1;
            }else if(controle.direitaAcionado || controle.esquerdaAcionado){
                maxFrames = framesAndando.length;
            } else {
                maxFrames = framesParado.length;
            }

            if(spriteNum >= maxFrames){
                spriteNum = 0;
            }

            controleSprite = 0;
        }

        if(controle.puloAcionado && !pulando) {
            velocidadeY = -13;
            pulando = true;
        }

        velocidadeY += gravidade;
        y += velocidadeY;

        int alturaChao = chaoY - tamanhoPersonagem;

        if(y >= alturaChao){

            y = alturaChao;

            velocidadeY = 0;

            pulando = false;
        }
    }

    public void Interacao(String cenarioAtual, Cenario cenario){

        //===== TROCAS DE CENARIOS POR INTERAÇÃO =====

        //PORTA DO HOTEL
        if (cenarioAtual.equals("Entrada") && x <= 195 && cenario.portas.isPortaHotel()){

            //Se tiver trancada
            if (!cenario.portas.isPortaHotel()){
                cenario.portas.destrancar(x, cenarioAtual);
            }
            //Se tiver destrancada
            else {
                usaPorta = true;
                System.out.println("Conclui jogo");
            }
        }

        //PORTA PRO ESCRITORIO
        if (cenarioAtual.equals("Entrada") && (x >= 910 && x <= 1065) && cenario.portas.isPortaEscritorio()){
            System.out.println("Troca pro escritorio");
        }

        //PORTA PRA SAIR ESCRITORIO
        if (cenarioAtual.equals("Escritorio") && x <= 1100){
            System.out.println("Troca pra entrada");
        }

        //PORTA PRO QUARTO
        if (cenarioAtual.equals("Corredor") && (x >= 360 && x <= 510) && cenario.portas.isPortaQuarto()){
            System.out.println("Troca pro quarto");
        }

        //PORTA PRA SAIR QUARTO
        if (cenarioAtual.equals("Quarto")){

        }

        //===== s =====
        //MESA ENTRADA
        if (cenarioAtual.equals("Entrada") && (x >= 450 && x <= 610)){
            System.out.println("Chave do Escritorio adquirida");
        }

        //MAQUINA DE SALGADINHO
        if (cenarioAtual.equals("Bar") && (x >= 910 && x <= 1065)){
            System.out.println("Papel enigma adquirido");
        }

        //MESINHA QUARTO
        if (cenarioAtual.equals("Quarto") && (x >= 600 && x <= 730)){
            System.out.println("Nota de 1 dolar adquirido");
        }

        //ESTANTE
        if (cenarioAtual.equals("Escritorio") && x <= 125){
            System.out.println("Troca pra estante");
        }

        //LIXEIRA
        if (cenarioAtual.equals("Escritorio") && (x >= 125 && x <= 250)){
            System.out.println("Troca pro escritorio");
        }

        //COFRE
        if (cenarioAtual.equals("Escritorio") && (x >= 400 && x <= 780)){
            System.out.println("Troca pro cofre");
        }

        //PORTA PRA SAIR ESCRITORIO
        if (cenarioAtual.equals("Escritorio") && x <= 1100){
            System.out.println("Troca pra entrada");
        }

        //===== DESTRANCAR PORTAS =====
        if (cenarioAtual.equals("Entrada") && x <= 195){

        }
    }

    public void draw(Graphics2D g2){

        BufferedImage frameAtual;

        if(pulando){
            frameAtual =framePulando;
        }
        else if(controle.esquerdaAcionado || controle.direitaAcionado){
            int frameAux = spriteNum % framesAndando.length;
            frameAtual = framesAndando[frameAux];
        }
        else {
            int frameAux = spriteNum % framesParado.length;
            frameAtual = framesParado[frameAux];
        }


        if(olhandoPraDireita){
            g2.drawImage(frameAtual, x, y, tamanhoPersonagem, tamanhoPersonagem, null);
        }
        else {
            g2.drawImage(frameAtual, x + tamanhoPersonagem, y, -tamanhoPersonagem, tamanhoPersonagem, null);
        }
    }

}
