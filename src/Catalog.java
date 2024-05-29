import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Catalog {

    private final Scanner scanner = new Scanner(System.in);
    private final ArrayList<Product> products = new ArrayList<>();

    public Catalog(String path) {
        loadFromCSV(path);
    }

    public void findById() throws ProductNotFoundException {
        try {
            System.out.println("Ingresa el id del producto a buscar: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            if (id <= 0) {
                throw new ProductNotFoundException("ID no válido");
            }

            for (Product product : products) {
                if (product.getId() == id) {
                    printProduct(product);
                    return;
                }
            }
            throw new ProductNotFoundException("No se puede encontrar el producto");
        } catch (InputMismatchException e) {
            scanner.nextLine();  // Clear the buffer
            System.out.println("Entrada inválida. Por favor, ingresa un número entero para el ID.");
        }
    }

    public void findByName() throws ProductNotFoundException {
        System.out.println("Ingresa el nombre del producto a buscar: ");
        String name = scanner.nextLine();

        if (name.isEmpty()) {
            throw new ProductNotFoundException("Nombre no válido");
        }

        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)) {
                printProduct(product);
                return;
            }
        }
        throw new ProductNotFoundException("Producto no encontrado");
    }

    public void findByDescription() throws ProductNotFoundException {
        System.out.println("Ingresa la descripcion del producto a buscar: ");
        String description = scanner.nextLine();

        if (description.isEmpty()) {
            throw new ProductNotFoundException("Descripción no válida");
        }

        for (Product product : products) {
            if (product.getDescription().equalsIgnoreCase(description)) {
                printProduct(product);
                return;
            }
        }
        throw new ProductNotFoundException("Producto no encontrado");
    }

    public void addProduct() {
        try {
            System.out.println("Ingresa el nombre del nuevo producto: ");
            String newName = scanner.nextLine();
            System.out.println("Ingresa el id del nuevo producto: ");
            int newId = scanner.nextInt();
            System.out.println("Ingresa el precio del producto: ");
            double newPrice = scanner.nextDouble();
            scanner.nextLine();  // Consume newline
            System.out.println("Ingresa la descripcion del producto: ");
            String newDescription = scanner.nextLine();

            Product newProduct = new Product(newDescription, newPrice, newName, newId);
            products.add(newProduct);
            System.out.println("Producto agregado correctamente");
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Entrada inválida. Por favor, ingresa datos válidos.");
        }
    }

    public void removeProduct() throws ProductNotFoundException {
        try {
            System.out.println("Ingresa el id del producto a eliminar: ");
            int idToRemove = scanner.nextInt();
            scanner.nextLine();

            if (idToRemove <= 0) {
                throw new ProductNotFoundException("ID no válido");
            }

            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getId() == idToRemove) {
                    products.remove(i);
                    System.out.println("Producto eliminado correctamente");
                    return;
                }
            }
            throw new ProductNotFoundException("Producto no encontrado");
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Entrada inválida. Por favor, ingresa un número entero para el ID.");
        }
    }
    private void printProduct(Product product) {
        System.out.println("Producto encontrado: ");
        System.out.println("Nombre: " + product.getName());
        System.out.println("Id: " + product.getId());
        System.out.println("Precio: " + product.getPrice());
        System.out.println("Descripcion: " + product.getDescription());
    }

    public void exportToCSV(String path) {
        try (PrintWriter writer = new PrintWriter(path)) {
            StringBuilder sb = new StringBuilder();
            for (Product product : products) {
                sb.append(product.getName()).append(",");
                sb.append(product.getId()).append(",");
                sb.append(product.getPrice()).append(",");
                sb.append(product.getDescription()).append("\n");
            }
            writer.write(sb.toString());
            if (products.isEmpty()){
                System.out.println("No se han exportados los productos");
            }else {
                System.out.println("Productos exportados correctamente a " + path);

            }
        } catch (FileNotFoundException e) {
            System.out.println("Error al exportar los productos: " + e.getMessage());
        }
    }
    public void loadFromCSV(String path) {
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 4) {
                    String name = values[0];
                    int id = Integer.parseInt(values[1]);
                    double price = Double.parseDouble(values[2]);
                    String description = values[3];
                    Product product = new Product(description, price, name, id);
                    products.add(product);
                }
            }
            if (products.isEmpty()){
                System.out.println("No se han cargado los productos");
            }else{
                System.out.println("Productos cargados correctamente desde " + path);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}