package ua.nure.st.kpp.example.demo.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class AddStudentForm {

    @Min(0)
    @Max(100)
    private int math;
    @Min(0)
    @Max(100)
    private int physics;
    @Min(0)
    @Max(100)
    private int english;
    @NotBlank
    private String studName;
    @NotBlank
    private String groupName;

    public AddStudentForm() {
        // default
    }

    public AddStudentForm(int math, int physics, int english, String studName, String groupName) {
        this.math = math;
        this.physics = physics;
        this.english = english;
        this.studName = studName;
        this.groupName = groupName;
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getPhysics() {
        return physics;
    }

    public void setPhysics(int physics) {
        this.physics = physics;
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    public String getStudName() {
        return studName;
    }

    public void setStudName(String studName) {
        this.studName = studName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
