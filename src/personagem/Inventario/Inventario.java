package personagem.Inventario;
import java.util.ArrayList;
import personagem.Inventario.Itens.Item;
public class Inventario {
    private static ArrayList<Itens.Item> itens;

    public Inventario() {
        itens = new ArrayList<>();
    }

    // Adiciona um item ao inventário
    public void adicionarItem(Itens.Item item) {
        itens.add(item);
    }

    // Verifica se possui um tipo específico de item
    public static boolean possuiItem(String tipoItem) {
        for(Itens.Item item : itens) {
            switch(tipoItem) {
                case "chave_escritorio":
                    if(item.isChave_Escritorio()) return true;
                    break;
                case "chave_quarto":
                    if(item.isChave_quarto()) return true;
                    break;
                case "chave_hotel":
                    if(item.isChave_hotel()) return true;
                    break;
                case "dollar":
                    if(item.isDollar()) return true;
                    break;
                case "papel":
                    if(item.isPapel()) return true;
                    break;
            }
        }
        return false;
    }

    public ArrayList<Itens.Item> getItens() {
        return itens;
    }
}