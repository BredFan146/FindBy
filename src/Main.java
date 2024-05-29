import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String sourcePath = "C:\\Users\\user\\IdeaProjects\\FindBy\\Products.csv";
        String exportPath = "C:\\Users\\user\\IdeaProjects\\FindBy\\ExportedProducts";
        Catalog catalog = new Catalog(sourcePath);
        catalog.exportToCSV(exportPath);
}
    }
