package tech.ada.ed.project.catalog.app;

import tech.ada.ed.project.catalog.model.Title;
import tech.ada.ed.project.catalog.utils.DataReader;
import tech.ada.ed.project.catalog.utils.EntradaDeDados;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Catalog {
    private List<Title> titleList = new LinkedList<>();
    private Map<String, Title> titleMap = new HashMap<>();
    private Map<String, List<Title>> titleNameMap = new HashMap<>();

    private final EntradaDeDados input;
    private final DataReader reader;
    private final String DIGITE_OPCAO_DESEJADA = "Digite a opção desejada: ";
    private final String OPCAO_SAIR = "x";
    private final String OPCAO_CADASTRAR_EM_LOTE = "1";
    private final String OPCAO_CADASTRAR_EM_LOTE_TOP10 = "2";
    private final String OPCAO_LISTAR_TITULOS = "3";
    private final String OPCAO_BUSCA_POR_ID = "4";
    private final String OPCAO_BUSCA_POR_NOME = "5";

    public Catalog(EntradaDeDados input, DataReader reader){
        this.input = input;
        this.reader = reader;
        iniciaApp();
    }

    public void processar(){
        String opcaoDigitada = obterEntradaDoUsuario(input);

        while(!escolheuSair(opcaoDigitada)){
            tratarOpcaoSelecionada(opcaoDigitada);
            opcaoDigitada = obterEntradaDoUsuario(input);
        }

        finalizaApp();
    }

    private void tratarOpcaoSelecionada(String opcaoDigitada) {
        switch (opcaoDigitada){
            case OPCAO_SAIR:
                break;
            case OPCAO_CADASTRAR_EM_LOTE, OPCAO_CADASTRAR_EM_LOTE_TOP10:
                carregarTitulosEmLote(opcaoDigitada);
                break;
            case OPCAO_LISTAR_TITULOS:
                listarTitulos();
                pularLinha(2);
                break;
            case OPCAO_BUSCA_POR_ID:
                buscaPorIdHashMap();
                break;
            case OPCAO_BUSCA_POR_NOME:
                buscaPorNomeHashMap();
                break;
            default:
                opcaoInvalida();
                break;
        }
    }

    private List<Title> removerDuplicados(List<Title> lista){
        var listaLimpa = new ArrayList<>(new HashSet<>(lista));
        System.out.println(lista.size() - listaLimpa.size() + " itens duplicados removidos");
        return listaLimpa;
    }

    private void carregarTitulosEmLote(String opcaoDigitada){
        List<Title> novostitulos = reader.loadCatalog(Integer.parseInt(opcaoDigitada));
        titleList = new LinkedList<>();
        this.inserirTitulos(removerDuplicados(novostitulos));
    }

    private void inserirTitulos(List<Title> titulos){
        for (Title t : titulos){
            inserirTitulos(t);
        }
    }

    private void inserirTitulos(Title title){
        this.titleList.add(title);
        this.titleMap.put(title.id(), title);
        String[] nomes = title.title().split(" ");

        for (String nome : nomes){
            nome = nome.toLowerCase();
            List<Title> titles = titleNameMap.get(nome);

            if(titles != null){
                titles.add(title);
            } else {
                List<Title> titleList = new ArrayList<>();
                titleList.add(title);
                titleNameMap.put(nome, titleList);
            }
        }
    }

    public void pularLinha(int numeroDeLinhas){
        for (int i = 1; i <= numeroDeLinhas; i++) {
            System.out.println();
        }
    }

    private void buscaPorIdHashMap() {
        System.out.print("Digite o id do titulo: ");
        String id = input.obterEntrada();

        System.out.println("Pesquisou o id: " + id);

        if (titleMap.containsKey(id)) {
            System.out.println("Titulo localizado!");
            System.out.println(titleMap.get(id));
            return;
        }
        System.out.println("Nenhum Titulo localizado para o id: " + id);
    }

    private void buscaPorNomeHashMap(){
        System.out.print("Digite o primeiro nome do titulo: ");
        String primeiroNome = input.obterEntrada().toLowerCase();

        if(titleNameMap.containsKey(primeiroNome)){
            System.out.println("Titulos(s) localizado(s)!");
            for (Title t : titleNameMap.get(primeiroNome)){
                System.out.println(t);
            }
        } else {
            System.out.println("Nenhum titulo localizado para o nome: " + primeiroNome);
        }
    }

    private void listarTitulos(){
        StringBuilder sb = new StringBuilder();

        if (titleList.isEmpty()) {
            sb.append("[]");
        } else {
            Collections.sort(titleList);

            sb.append("[\n");
            for (Title titulo : titleList) {
                sb.append("\t").append(titulo).append(",\n");
            }
            sb.setLength(sb.length() - 2);
            sb.append("\n]");
        }

        System.out.println(sb);
    }

    private boolean escolheuSair(String opcaoDigitada){
        return opcaoDigitada.equals(OPCAO_SAIR);
    }

    private String obterEntradaDoUsuario(EntradaDeDados leitor){
        carregaMenu();
        System.out.print(DIGITE_OPCAO_DESEJADA);
        return leitor.obterEntrada().toLowerCase();
    }

    private void finalizaApp(){
        System.out.println("Até logo!!");
    }

    private void opcaoInvalida(){
        System.out.println("Opção INVÁLIDA. Tente novamente.");
    }

    private void iniciaApp(){
        carregaNomeApp();
    }

    private void carregaMenu() {
        System.out.println("********  DIGITE A OPÇÃO DESEJADA   ******");
        System.out.println("1 - CADASTRO EM LOTE (CSV)");
        System.out.println("2 - CADASTRO EM LOTE TOP10 (CSV)");
        System.out.println("3 - LISTAR TITULOS");
        System.out.println("4 - PESQUISAR POR ID");
        System.out.println("5 - PESQUISAR POR NOME");
        System.out.println("X - SAIR");
        System.out.println();
    }

    private void carregaNomeApp(){
        System.out.println("******************************************");
        System.out.println("******* CATALOGO DE TÍTULOS *********");
        System.out.println("******************************************");
    }

}
