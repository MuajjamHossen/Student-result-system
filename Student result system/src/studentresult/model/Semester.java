package studentresult.model;

import java.io.Serializable;

public class Semester implements Serializable {
    private String name;

    public Semester(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
