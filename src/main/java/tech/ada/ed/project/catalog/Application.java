package tech.ada.ed.project.catalog;

import tech.ada.ed.project.catalog.app.Catalog;
import tech.ada.ed.project.catalog.utils.DataReaderCSV;
import tech.ada.ed.project.catalog.utils.EntradaDeDados;

public class Application {
    public static void main(String[] args) {
        try(EntradaDeDados leitor = new EntradaDeDados()) {
            new Catalog(leitor, new DataReaderCSV()).processar();
        }
    }
}
