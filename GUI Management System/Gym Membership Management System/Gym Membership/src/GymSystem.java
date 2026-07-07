import java.util.*;

public class GymSystem {
    private List<Gym> gym = new ArrayList<>();

    public void addMember(Gym g) {
        gym.add(g);
    }

    public List<Gym> getMember() {
        return gym;
    }

    public void updateMember(int index, Gym updateGym) {
        gym.set(index, updateGym);
    }

    public void removeMember(int index) {
        gym.remove(index);
    }
}
