package tela;

import java.util.Scanner;

public class Cofre {
    private boolean destrancado = false;
    private String senha = "91815";

    Scanner scanner = new Scanner(System.in);

    private void Destrancar(){ destrancado = true; }

    public void inserirSenha(){
        if (destrancado){
            System.out.println("");
        }
    }

    public boolean isDestrancado() {
        return destrancado;
    }
}
