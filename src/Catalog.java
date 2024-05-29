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


    public void findById(Scanner scanner) throws ProductNotFoundException {
        try {
            System.out.println("Ingresa el id del producto a buscar: ");
            int id = this.scanner.nextInt();
            this.scanner.nextLine();
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
            this.scanner.nextLine();  // Clear the buffer
            System.out.println("Entrada inválida. Por favor, ingresa un número entero para el ID.");
        }
    }

    public void findByName(Scanner scanner) throws ProductNotFoundException {
        System.out.println("Ingresa el nombre del producto a buscar: ");
        String name = this.scanner.nextLine();

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

    public void findByDescription(Scanner scanner) throws ProductNotFoundException {
        System.out.println("Ingresa la descripcion del producto a buscar: ");
        String description = this.scanner.nextLine();

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

    public void addProduct(Scanner scanner) {
        try {
            System.out.println("Ingresa el nombre del nuevo producto: ");
            String newName = this.scanner.nextLine();
            System.out.println("Ingresa el id del nuevo producto: ");
            int newId = this.scanner.nextInt();
            System.out.println("Ingresa el precio del producto: ");
            double newPrice = this.scanner.nextDouble();
            this.scanner.nextLine();  // Consume newline
            System.out.println("Ingresa la descripcion del producto: ");
            String newDescription = this.scanner.nextLine();

            Product newProduct = new Product(newDescription, newPrice, newName, newId);
            products.add(newProduct);
            System.out.println("Producto agregado correctamente");

            // Después de agregar el producto, exportamos todos los productos al archivo CSV del catálogo

        } catch (InputMismatchException e) {
            this.scanner.nextLine();
            System.out.println("Entrada inválida. Por favor, ingresa datos válidos.");
        }
    }

    public void removeProduct(Scanner scanner) throws ProductNotFoundException {
        try {
            System.out.println("Ingresa el id del producto a eliminar: ");
            int idToRemove = this.scanner.nextInt();
            this.scanner.nextLine();

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
            this.scanner.nextLine();
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

    public void exportToCSV(String exportPath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(exportPath))) {
            // Escribir encabezado
            writer.println(String.join(",", headers));

            // Escribir datos de productos
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
            // Leer la primera línea para obtener el encabezado
            String headerLine = bufferedReader.readLine();
            String[] headerColumns = headerLine.split(",");

            // Verificar si el orden de las columnas ha cambiado
            boolean orderChanged = !arrayEquals(headers, headerColumns);

            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 4) {
                    String name = "", description = "";
                    int id = 0;
                    double price = 0.0;

                    // Asignar valores según el orden de las columnas
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

                    // Crear el producto con los valores asignados
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

    // Método auxiliar para comparar dos arrays de Strings
    private boolean arrayEquals(String[] arr1, String[] arr2) {
        if (arr1.length != arr2.length) return false;
        for (int i = 0; i < arr1.length; i++) {
            if (!arr1[i].equalsIgnoreCase(arr2[i])) return false;
        }
        return true;
    }
}
