import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ProjetoNomes {

    private static final String ARQUIVO_JSON = "nomes.json";
    private static final Gson gson = new Gson();
    private static final ArrayList<String> nomes = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1 - Adicionar nome");
            System.out.println("2 - Listar nomes");
            System.out.println("3 - Remover nome");
            System.out.println("4 - Buscar nome");
            System.out.println("5 - Salvar nomes em arquivo JSON");
            System.out.println("6 - Carregar nomes de arquivo JSON");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = sc.nextInt();
            sc.nextLine(); 

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome: ");
                    String nome = sc.nextLine().trim();

                    if (nome.isEmpty()) {
                        System.out.println("Nome não pode ser vazio.");
                    } else if (nomes.contains(nome)) {
                        System.out.println("Nome já existe na lista.");
                    } else {
                        nomes.add(nome);
                        System.out.println("Nome adicionado com sucesso.");
                    }
                    break;

                case 2:
                    if (nomes.isEmpty()) {
                        System.out.println("A lista está vazia.");
                    } else {
                        Collections.sort(nomes);
                        System.out.println("Lista de nomes:");
                        for (int i = 0; i < nomes.size(); i++) {
                            System.out.println((i + 1) + ". " + nomes.get(i));
                        }
                    }
                    break;

                case 3:
                    System.out.print("Digite o nome a ser removido: ");
                    String nomeRemover = sc.nextLine().trim();

                    if (nomes.remove(nomeRemover)) {
                        System.out.println("Nome removido com sucesso.");
                    } else {
                        System.out.println("Nome não encontrado na lista.");
                    }
                    break;

                case 4:
                    System.out.print("Digite o nome a ser buscado: ");
                    String nomeBuscar = sc.nextLine().trim();

                    if (nomes.contains(nomeBuscar)) {
                        System.out.println("Nome encontrado: " + nomeBuscar);
                    } else {
                        System.out.println("Nome não encontrado.");
                    }
                    break;

                case 5:
                    try (FileWriter writer = new FileWriter(ARQUIVO_JSON)) {
                        gson.toJson(nomes, writer);
                        System.out.println("Nomes salvos em " + ARQUIVO_JSON);
                    } catch (IOException e) {
                        System.out.println("Erro ao salvar: " + e.getMessage());
                    }
                    break;

                case 6:
                    try (FileReader reader = new FileReader(ARQUIVO_JSON)) {
                        Type tipoLista = new TypeToken<List<String>>() {}.getType();
                        List<String> nomesLidos = gson.fromJson(reader, tipoLista);

                        if (nomesLidos != null) {
                            nomes.clear();
                            nomes.addAll(nomesLidos);
                            System.out.println("Nomes carregados de " + ARQUIVO_JSON);
                        } else {
                            System.out.println("Arquivo vazio ou mal formatado.");
                        }
                    } catch (IOException e) {
                        System.out.println("Erro ao carregar: " + e.getMessage());
                    }
                    break;

                case 0:
                    System.out.println("Encerrando o programa.");
                    sc.close();
                    return;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }
}
