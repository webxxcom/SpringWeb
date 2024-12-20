package ua.nure.st.kpp.example.demo.form;

public class DeleteStudentForm {
    private String studName;

    public DeleteStudentForm() {
        // default
    }

    public DeleteStudentForm(String studName) {
        this.studName = studName;
    }

    public String getStudName() {
        return studName;
    }

    public void setStudName(String studName) {
        this.studName = studName;
    }
}
