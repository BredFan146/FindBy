import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Catalog {

    private final ArrayList<Product> products = new ArrayList<>();
    private final String[] headers = {"nombre", "id", "precio", "desc"};
    private final Scanner scanner = new Scanner(System.in);
    private final String path;

    public Catalog(String path) {
        this.path = path;
        loadFromCSV(path);
    }

    // Otros métodos (findById, findByName, findByDescription, addProduct, removeProduct, printProduct) permanecen igual

    public void exportToCSV(String exportPath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(exportPath))) {
            writer.println(String.join(",", headers));

            for (Product product : products) {
                writer.println(product.toCSV());
            }

            if (products.isEmpty()) {
                System.out.println("No se han exportado los productos");
            } else {
                System.out.println("Productos exportados correctamente a " + exportPath);
            }
        } catch (IOException e) {
            System.out.println("Error al exportar los productos: " + e.getMessage());
        }
    }

    private void loadFromCSV(String path) {
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String headerLine = bufferedReader.readLine();
            String[] headerColumns = headerLine.split(",");

            boolean orderChanged = !arrayEquals(headers, headerColumns);

            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 4) {
                    String name = "", description = "";
                    int id = 0;
                    double price = 0.0;

                    for (int i = 0; i < headerColumns.length; i++) {
                        if (headerColumns[i].equalsIgnoreCase("nombre")) {
                            name = values[i].trim();
                        } else if (headerColumns[i].equalsIgnoreCase("id")) {
                            id = Integer.parseInt(values[i].trim());
                        } else if (headerColumns[i].equalsIgnoreCase("precio")) {
                            price = Double.parseDouble(values[i].trim());
                        } else if (headerColumns[i].equalsIgnoreCase("desc")) {
                            description = values[i].trim();
                        }
                    }

                    Product product = new Product(description, price, name, id);
                    products.add(product);
                }
            }

            if (products.isEmpty()) {
                System.out.println("No se han cargado los productos");
            } else {
                if (orderChanged) {
                    System.out.println("Se ha detectado un cambio en el orden de las columnas");
                }
                System.out.println("Productos cargados correctamente desde " + path);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir valores numéricos: " + e.getMessage());
        }
    }

    public void exportToBinary(String exportPath) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(exportPath))) {
            for (Product product : products) {
                product.writeToBinary(dos);
            }
            System.out.println("Productos exportados correctamente a formato binario");
        } catch (IOException e) {
            System.out.println("Error al exportar los productos: " + e.getMessage());
        }
    }

    public void importFromBinary(String importPath) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(importPath))) {
            products.clear();
            while (dis.available() > 0) {
                Product product = Product.readFromBinary(dis);
                products.add(product);
            }
            System.out.println("Productos importados correctamente desde formato binario");
        } catch (IOException e) {
            System.out.println("Error al importar los productos: " + e.getMessage());
        }
    }

    private boolean arrayEquals(String[] arr1, String[] arr2) {
        if (arr1.length != arr2.length) return false;
        for (int i = 0; i < arr1.length; i++) {
            if (!arr1[i].equalsIgnoreCase(arr2[i])) return false;
        }
        return true;
    }
}
