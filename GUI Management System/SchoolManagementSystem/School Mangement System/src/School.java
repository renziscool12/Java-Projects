public class School {
    private int studentId, studentEnrollFee;
    private String studentFullName, studentGender;

    public School(int studentId, String studentFullName, String studentGender, int studentEnrollFee) {
        this.studentId = studentId;
        this.studentFullName = studentFullName;
        this.studentGender = studentGender;
        this.studentEnrollFee = studentEnrollFee;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getStudentEnrollFee() {
        return studentEnrollFee;
    }

    public void setStudentEnrollFee(int studentEnrollFee) {
        this.studentEnrollFee = studentEnrollFee;
    }

    public String getStudentFullName() {
        return studentFullName;
    }

    public void setStudentFullName(String studentFullName) {
        this.studentFullName = studentFullName;
    }

    public String getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }

}
