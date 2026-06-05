package tela;

public class Cofre {
    private boolean destrancado = false;

    public void Destrancar(){
        destrancado = true;
    }

    public boolean isDestrancado() {
        return destrancado;
    }
}
