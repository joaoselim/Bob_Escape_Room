package personagem;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controle implements KeyListener{

    public boolean interagirAcionado;
    public boolean inventarioAcionado;
    public boolean esquerdaAcionado;
    public boolean direitaAcionado;
    public boolean puloAcionado;

    //Verifica quais teclas são acionadas
    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            interagirAcionado = true;
        }

        if(code == KeyEvent.VK_E) {
            inventarioAcionado = true;
        }

        if(code == KeyEvent.VK_A) {
            esquerdaAcionado = true;
        }

        if(code == KeyEvent.VK_D) {
            direitaAcionado = true;
        }

        if(code == KeyEvent.VK_SPACE) {
            puloAcionado = true;
        }
    }

    //Verifica quais teclas foram soltas

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            interagirAcionado = false;
        }

        if(code == KeyEvent.VK_E) {
            inventarioAcionado = false;
        }

        if(code == KeyEvent.VK_A) {
            esquerdaAcionado = false;
        }

        if(code == KeyEvent.VK_D) {
            direitaAcionado = false;
        }

        if(code == KeyEvent.VK_SPACE) {
            puloAcionado = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
