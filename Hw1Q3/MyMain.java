//Hw1Q3
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

class PersonList implements Serializable {
    LinkedList LL;

    public PersonList() {
        this.LL = new LinkedList();
    }

    void store(ObjectInputStream istream) {
        while (true) {
            Person p;
            try {
                Object obj = istream.readObject();
                p = (Person) obj;
                this.LL.add(p);
            } catch (EOFException e) {
                break;
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    };
    void display(OutputStream ostream) {
        for (int i = 1; i<this.LL.size(); i++) {
            Person p = (Person) this.LL.get(i);
            try {
                byte[] pBytes = p.toString().getBytes();
                ostream.write(pBytes);
                ostream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    };
    int find(String sID) {
        for (int i = 0; i < this.LL.size(); i++) {
            Person p = (Person) this.LL.get(i);
            String pSID = p.getId();
            if (pSID.equals(sID)) {
                return i;
            };
        };
        throw new RuntimeException("ID not found within the person list");
    };
}

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
        PersonList PL = new PersonList();
        try (FileInputStream fis = new FileInputStream("myFile.dat");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            PL.store(ois);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        PL.display(System.out);

        int JaneDoeIndex = PL.find("47");
        System.out.println("Jane Doe's (ID: 47) index " + JaneDoeIndex);

        int JohnSmithIndex = PL.find("28");
        System.out.println("John Smith's (ID: 28) index " + JohnSmithIndex);
    }
};
