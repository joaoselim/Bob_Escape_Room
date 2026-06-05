package tela;

import javax.swing.JOptionPane;

public class Cofre {
    private boolean destrancado = false;
    private String senha = "91815";

    private void Destrancar(){ destrancado = true; }

    public boolean analisarSenha(String senhaInserida){

        if (senhaInserida == null){
            return false;
        }
        if (senhaInserida.equals(senha)){
            System.out.println("Cofre aberto!");
            Destrancar();

            return true;
        } else {
            System.out.println("Senha incorreta!");

            return false;
        }
    }

    public boolean isDestrancado() {
        return destrancado;
    }
}
