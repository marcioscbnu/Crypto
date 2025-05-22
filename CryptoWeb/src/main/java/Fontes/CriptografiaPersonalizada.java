package Fontes;

import java.util.*;
import java.util.Map;

public class CriptografiaPersonalizada {

    private static final Map<Character, Integer> mapaLetraNumero = new HashMap<>();
    private static final Map<Integer, Character> mapaNumeroLetra = new HashMap<>();

    static {
        // Mapeamento de letras para números
        mapaLetraNumero.put('A', 19);
        mapaLetraNumero.put('B', 2);
        mapaLetraNumero.put('C', 21);
        mapaLetraNumero.put('D', 0);
        mapaLetraNumero.put('E', 4);
        mapaLetraNumero.put('F', 7);
        mapaLetraNumero.put('G', 6);
        mapaLetraNumero.put('H', 14);
        mapaLetraNumero.put('I', 17);
        mapaLetraNumero.put('J', 3);
        mapaLetraNumero.put('L', 13);
        mapaLetraNumero.put('M', 8);
        mapaLetraNumero.put('N', 9);
        mapaLetraNumero.put('O', 12);
        mapaLetraNumero.put('P', 16);
        mapaLetraNumero.put('Q', 15);
        mapaLetraNumero.put('R', 1);
        mapaLetraNumero.put('S', 18);
        mapaLetraNumero.put('T', 20);
        mapaLetraNumero.put('U', 11);
        mapaLetraNumero.put('V', 22);
        mapaLetraNumero.put('X', 10);
        mapaLetraNumero.put('Z', 5);

        // Mapeamento inverso
        for (Map.Entry<Character, Integer> entry : mapaLetraNumero.entrySet()) {
            mapaNumeroLetra.put(entry.getValue(), entry.getKey());
        }
    }

    private static final int[][] MATRIZ_CHAVE = {
        {2, 3},
        {4, 2},
        {2, 3}
    };

    public static String criptografar(String texto) {
        texto = normalizarTexto(texto);

        // Se número ímpar de caracteres, adicionar X
        if (texto.length() % 2 != 0) {
            texto += "X";
        }

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < texto.length(); i += 2) {
            char a = texto.charAt(i);
            char b = texto.charAt(i + 1);

            int n1 = mapaLetraNumero.getOrDefault(a, 0);
            int n2 = mapaLetraNumero.getOrDefault(b, 0);

            // Aplicar matriz 3x2
            int[] vetor = new int[3];
            vetor[0] = (MATRIZ_CHAVE[0][0] * n1 + MATRIZ_CHAVE[0][1] * n2) % 23;
            vetor[1] = (MATRIZ_CHAVE[1][0] * n1 + MATRIZ_CHAVE[1][1] * n2) % 23;
            vetor[2] = (MATRIZ_CHAVE[2][0] * n1 + MATRIZ_CHAVE[2][1] * n2) % 23;

            // Converter para letras criptografadas
            for (int v : vetor) {
                resultado.append(mapaNumeroLetra.getOrDefault(v, 'X'));
            }
        }

        return resultado.toString();
    }

    private static String normalizarTexto(String texto) {
        return texto.toUpperCase()
                    .replaceAll("[^A-Z]", "")
                    .replace("K", "C")
                    .replace("W", "V")
                    .replace("Y", "I");
    }

    // Exemplo de uso para testes
    public static void main(String[] args) {
        String senhaOriginal = "PAPEL";
        String senhaCriptografada = criptografar(senhaOriginal);
        System.out.println("Senha original: " + senhaOriginal);
        System.out.println("Senha criptografada: " + senhaCriptografada);
    }
}
