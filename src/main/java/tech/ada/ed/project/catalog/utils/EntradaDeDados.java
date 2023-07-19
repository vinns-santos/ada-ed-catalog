package tech.ada.ed.project.catalog.utils;

import java.util.Locale;
import java.util.Scanner;

public class EntradaDeDados implements AutoCloseable {

    private final Scanner scanner;

    public EntradaDeDados(){
        scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);
    }

    public String obterEntrada(){
        return scanner.nextLine();
    }

    public Double obterEntradaAsDouble(){
        double retorno = scanner.nextDouble();
        scanner.nextLine(); // limpar buffer
        return retorno;
    }

    public Integer obterEntradaAsInt(){
        Integer retorno = scanner.nextInt();
        scanner.nextLine(); // limpar buffer
        return retorno;
    }

    @Override
    public void close() {
        scanner.close();
    }
}
