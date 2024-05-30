import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

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

    // Método para escribir el producto en formato binario
    public void writeToBinary(DataOutputStream dos) throws IOException {
        dos.writeInt(id);
        dos.writeDouble(price);

        byte[] nameBytes = name.getBytes();
        dos.writeShort(nameBytes.length);
        dos.write(nameBytes);

        byte[] descBytes = description.getBytes();
        dos.writeShort(descBytes.length);
        dos.write(descBytes);
    }

    // Método para leer el producto desde formato binario
    public static Product readFromBinary(DataInputStream dis) throws IOException {
        int id = dis.readInt();
        double price = dis.readDouble();

        short nameLength = dis.readShort();
        byte[] nameBytes = new byte[nameLength];
        dis.readFully(nameBytes);
        String name = new String(nameBytes);

        short descLength = dis.readShort();
        byte[] descBytes = new byte[descLength];
        dis.readFully(descBytes);
        String description = new String(descBytes);

        return new Product(description, price, name, id);
    }
}
