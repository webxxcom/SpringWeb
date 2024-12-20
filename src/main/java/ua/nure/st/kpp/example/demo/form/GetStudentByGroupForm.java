package ua.nure.st.kpp.example.demo.form;

public class GetStudentByGroupForm {
    private String groupName;

    public GetStudentByGroupForm() {
		// default
    }

    public GetStudentByGroupForm(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
