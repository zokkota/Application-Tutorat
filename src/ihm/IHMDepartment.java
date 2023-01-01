package ihm;

import graphs.Tutoring;
import oop.Department;
import oop.Resource;
import oop.Teacher;
import utility.ToolsCSV;

public class IHMDepartment extends Department{
    public Tutoring tutoring;    
    public Teacher teacher = new Teacher("Lorraine Ipsome");

    public IHMDepartment() {
        super("Informatique");
        this.addStudent(ToolsCSV.importStudents());
        for (Resource resource : Resource.values()) {
            this.newTutoring(resource, teacher);
            registerStudent(resource);
        }
    }
    
}
