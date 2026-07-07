import java.util.*;
import java.io.*;

public class BreadSystem {
    private List<Bread> bread = new ArrayList<>();

    public void addBread(Bread b) {
        bread.add(b);
    }

    public List<Bread> getAllBread() {
        return bread;
    }

    public void updateBread(int index, Bread updateBread) {
        if (index >= 0 && index < bread.size()) {
            Bread b = bread.get(index);
            b.setBreadId(updateBread.getBreadId());
            b.setBreadType(updateBread.getBreadType());
            b.setQuantity(updateBread.getQuantity());
            b.setPrice(updateBread.getPrice());
        }
    }

    public void deleteBread(int index) {
        if (index >= 0 && index < bread.size()) {
            bread.remove(index);
        }
    }

    public void saveToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Bread b : bread) {
                pw.println(b.getBreadId() + "," +
                        b.getBreadType() + "," +
                        b.getQuantity() + "," +
                        b.getPrice());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String filename) {
        File file = new File(filename);

        if (!file.exists()) {
            return;
        }
        String line;
        bread.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                int breadId = Integer.parseInt(data[0]);
                String breadType = data[1];
                int quantity = Integer.parseInt(data[2]);
                int price = Integer.parseInt(data[3]);

                Bread b = new Bread(breadId, breadType, quantity, price);
                bread.add(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
