//package ua.nure.st.kpp.example.demo.dao.collectiondao;
//
//import ua.nure.st.kpp.example.demo.dao.abstr.StudentDao;
//import ua.nure.st.kpp.example.demo.entity.Student;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
//public class CollectionLDAO implements StudentDao {
//
//	Logger log = LoggerFactory.getLogger(CollectionLDAO.class);
//
//    private static List<Student> students = new ArrayList<>();
//    private final IdGeneratorService idGenerator = new IdGeneratorService();
//
//    static {
//        students.add(new Student(100, 80, 100, "Smith", "KN-14-2"));
//        students.add(new Student(100, 80, 100, "Johnson", "KN-14-4"));
//        students.add(new Student(100, 80, 100, "Bush", "KN-14-4"));
//    }
//
//
//    @Override
//    public synchronized void addStud(Student stud) {
//    	stud.setId(idGenerator.nextId());
//        students.add(stud);
//        log.debug("student added: {}", stud);
//    }
//
//    @Override
//    public synchronized List<Student> getAllStudents() {
//    	log.debug("found: {}", students.size());
//        return students;
//    }
//
//    @Override
//    public synchronized List<Student> getStudentsByGroupName(String nameGroup) {
//
//        List<Student> studs = new ArrayList<>();
//
//        for (Student stud : students) {
//            if (stud.getGroupName().equals(nameGroup)) {
//                studs.add(stud);
//            }
//        }
//        return studs;
//    }
//
//    @Override
//    public synchronized void deleteStudentsByName(String studentName) {
//        students = students.stream().filter(stud -> !stud.getStudName().equalsIgnoreCase(studentName))
//                .collect(Collectors.toList());
//    }
//}
