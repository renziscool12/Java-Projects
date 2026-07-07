public abstract class Hospital {
    private String patientName;
    private int patientAge;
    private String patientGender;
    private boolean isCompleted;

    public Hospital(String patientName, int patientAge, String patientGender) {
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.patientGender = patientGender;
        this.isCompleted = false;
    }

    public String getPatientName() {
        return patientName;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public String getCheckedBox() {
        return isCompleted ? "Checked [/]" : "Unchecked [X]";
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    abstract String getPatientInfo();

}

class Patient extends Hospital {
    private String patientId;

    public Patient(String patientName, int patientAge, String patientGender, String patientId) {
        super(patientName, patientAge, patientGender);
        this.patientId = patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientId() {
        return patientId;
    }

    @Override
    public String getPatientInfo() {
        return "Patient Name: " + getPatientName() + "\nAge: " + getPatientAge() + "\nGender: " + getPatientGender()
                + "\nAdmitted: " + getCheckedBox() + "\nPatient ID: " + patientId;
    }
}