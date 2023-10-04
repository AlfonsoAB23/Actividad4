import java.io.*;
import java.util.*;

public class AddressBook {
    private Map<String, String> contacts;
    private String fileName;

    public AddressBook(String fileName) {
        this.fileName = fileName;
        this.contacts = new HashMap<>();
        load();
    }
    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String phoneNumber = parts[0].trim();
                    String name = parts[1].trim();
                    contacts.put(phoneNumber, name);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar los contactos: " + e.getMessage());
        }
    }
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                writer.write(entry.getKey() + " : " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar los contactos: " + e.getMessage());
        }
    }
    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
    public void create(String phoneNumber, String name) {
        contacts.put(phoneNumber, name);
        save();
    }
    public void delete(String phoneNumber) {
        if (contacts.containsKey(phoneNumber)) {
            contacts.remove(phoneNumber);
            save();
        } else {
            System.out.println("El número telefónico no se encuentra en la agenda.");
        }
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook("contacts.txt");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenú:");
            System.out.println("1. Mostrar contactos");
            System.out.println("2. Agregar contacto");
            System.out.println("3. Borrar contacto");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addressBook.list();
                    break;
                case 2:
                    System.out.print("Ingrese el número telefónico: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Ingrese el nombre: ");
                    String name = scanner.nextLine();
                    addressBook.create(phoneNumber, name);
                    break;
                case 3:
                    System.out.print("Ingrese el número telefónico a eliminar: ");
                    phoneNumber = scanner.nextLine();
                    addressBook.delete(phoneNumber);
                    break;
                case 4:
                    addressBook.save();
                    System.out.println("¡Hasta luego!");
                    System.exit(0);
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
