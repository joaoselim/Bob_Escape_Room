package tela;

import javax.swing.JOptionPane;

public class Cofre {
    private boolean destrancado = false;
    private String senha = "91815";

    private void Destrancar(){ destrancado = true; }

    public void inserirSenha(){
        String senhaInserida = JOptionPane.showInputDialog(null, "Digite a senha:");
        if (senhaInserida.equals(senha)){
            System.out.println("Cofre aberto!");
            Destrancar();
        } else {
            System.out.println("Senha incorreta!");
        }
    }

    public boolean isDestrancado() {
        return destrancado;
    }
}
