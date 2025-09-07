import java.io.*;
import java.util.LinkedList;

// Person data type
class Person implements Serializable {
    String firstName;
    String lastName;
    String id;

    // Constructor
    public Person (String firstName, String lastName, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    // Accessors
    String getFirstName() {
        return this.firstName;
    };

    String getLastName() {
        return this.lastName;
    };

    String getId () {
        return this.id;
    };

    public String toString() {
        return this.firstName + " " + this.lastName + " " + this.id + "\n";
    }
};

// MyMain class
class MyMain {
    // Store method
    static void store(ObjectInputStream istream, LinkedList LL) {
        while (true) {
            Person p;
            try {
                Object obj = istream.readObject();
                p = (Person) obj;
                LL.add(p);
            } catch (EOFException e) {
                break;
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    };
    static void display(OutputStream ostream, LinkedList LL) {
        for (int i = 1; i<LL.size(); i++) {
            Person p = (Person) LL.get(i);
            try {
                byte[] pBytes = p.toString().getBytes();
                ostream.write(pBytes);
                ostream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    };
    static int find(String sID, LinkedList LL) {
        for (int i = 0; i < LL.size(); i++) {
            Person p = (Person) LL.get(i);
            String pSID = p.getId();
            if (pSID.equals(sID)) {
                return i;
            };
        };
        return -1;
    };

    //Main Method
    public static void main(String[] args) throws IOException {
        LinkedList LL = new LinkedList();
        try (FileInputStream fis = new FileInputStream("myFile.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            store(ois, LL);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        display(System.out, LL);

        int JaneDoeIndex = find("47", LL);
        System.out.println("Jane Doe's (ID: 47) index " + JaneDoeIndex);

        int JohnSmithIndex = find("28", LL);
        System.out.println("John Smith's (ID: 27) index " + JohnSmithIndex);
    }
};
