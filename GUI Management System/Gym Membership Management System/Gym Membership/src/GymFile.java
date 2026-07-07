import java.io.*;

public class GymFile {

    public void saveToFile(GymSystem system, String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Gym g : system.getMember()) {
                pw.println(g.getMemberName() + "," + g.getMembershipType() + "," + g.getMonthlyPayment() + ","
                        + g.getExpirationDate());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(GymSystem system, String filename) {
        File file = new File(filename);

        if (!file.exists()) {
            return;
        }

        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 4) {
                    String memberName = parts[0];
                    String membershipType = parts[1];
                    int monthlyPayment = Integer.parseInt(parts[2]);
                    int expirationDate = Integer.parseInt(parts[3]);

                    Gym g = new Gym(memberName, membershipType, monthlyPayment, expirationDate);
                    system.addMember(g);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
