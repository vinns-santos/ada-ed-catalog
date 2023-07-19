package tech.ada.ed.project.catalog.utils;

import tech.ada.ed.project.catalog.model.Title;

import java.util.List;

public interface DataReader {
    List<Title> loadCatalog(int option);
}
