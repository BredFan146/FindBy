import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String path="C:\\Users\\mrmia\\IdeaProjects\\FindBy\\Products.csv";
        Scanner scanner=new Scanner(System.in);
        Catalog catalog=new Catalog(path);
        boolean exit = false;


        while (!exit) {
            System.out.println("Menu:");
            System.out.println("1. Exportar productos a CSV");
            System.out.println("2. Agregar producto");
            System.out.println("3. Buscar producto por ID");
            System.out.println("4. Buscar producto por nombre");
            System.out.println("5. Buscar producto por descripción");
            System.out.println("6. Eliminar producto por ID");
            System.out.println("7. Salir");
            System.out.print("Elige una opción: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    catalog.exportToCSV(path);
                    break;
                case 2:
                    catalog.addProduct();
                    break;
                case 3:
                    try {
                        catalog.findById();
                    } catch (ProductNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        catalog.findByName();
                    } catch (ProductNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        catalog.findByDescription();
                    } catch (ProductNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    try {
                        catalog.removeProduct();
                    } catch (ProductNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }
}