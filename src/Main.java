
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String sourcePath = "C:\\Users\\user\\IdeaProjects\\FindBy_2\\Products.csv";
        String exportPath = "C:\\Users\\user\\IdeaProjects\\FindBy_2\\ExportedProducts.bin";
        Catalog catalog = new Catalog(sourcePath);
        catalog.exportToBinary(exportPath);
        catalog.importFromBinary(exportPath);
    }
}
