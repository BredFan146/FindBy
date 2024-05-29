
public class Product {
    private int id;
    private String name;
    private double price;
    private String description;

    public Product(String description, double price, String name, int id) {
        this.description = description;
        this.name = name;
        this.price = price;
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Método para convertir el producto a una fila CSV
    public String toCSV() {
        return name + "," + id + "," + price + "," + description;
    }
}
