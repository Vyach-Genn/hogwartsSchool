package prosky.ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import prosky.ru.hogwarts.school.exception.NotFoundException;
import prosky.ru.hogwarts.school.model.Faculty;
import prosky.ru.hogwarts.school.model.Student;
import prosky.ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        studentRepository.save(student);
        return student;
    }

    public Student getStudentById(Long id) {
        logger.info("The method was invoked to get a student by ID");
        checkStudentExists(id);
        return studentRepository.findById(id).get();
    }

    public Student updateStudent(Long id, Student student) {
        logger.info("A method was invoked to change a student by its ID");
        checkStudentExists(id);
        student.setId(id);
        return studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        logger.info("the method to delete a student by ID was invoked");
        checkStudentExists(studentId);
        studentRepository.deleteById(studentId);
    }

    public Collection<Student> findStudentByAge(int studentAge) {
        logger.info("A method was called to find students with the same age");
        return studentRepository.findByAge(studentAge);
    }

    public Collection<Student> findByMinAgeAndMaxAge(int minAge, int maxAge) {
        logger.info("a method was called to find students in one age range");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Collection<Student> getAllStudent() {
        logger.info("the method to output all students was called");
        return studentRepository.findAll();
    }

    public Faculty getFacultyByStudentId(Long studentId) {
        logger.info("a method was called to find all students of a particular faculty");
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Студент с id " + studentId + " не найден"));

        Faculty faculty = student.getFaculty();
        faculty.setStudentName(student.getName());

        return faculty;
    }

    public Long getCountAllStudents() {
        logger.info("the method was called to count all students");
        return studentRepository.findCountAllStudents();
    }

    public Double getAverageAgeOfAllStudents() {
        logger.info("a method was called for calculating the average age of students");
        return studentRepository.findAverageAgeOfAllStudents();
    }

    public Collection<Student> getTop5ByOrderById() {
        logger.info("The method was called to find five students");
        return studentRepository.findTop5ByOrderByIdDesc()
                .stream()
                .toList();
    }

    public void checkStudentExists(Long id) {
        logger.error("There is not student with id = {}", id);
        if (!studentRepository.existsById(id)) {
            throw new NotFoundException("Error: Студент с id " + id + " не найден");
        }
    }

    public Collection<String> getStudentsNamesStartingWithA() {
        logger.info("The method was sorts the list of students with letter A");
        return studentRepository.findAll()
                .stream()
                .filter(student -> student.getName().startsWith("A"))
                .map(student -> student.getName().toUpperCase())
                .sorted()
                .collect(Collectors.toList());
    }

    public Double getAveAgeOfAllStudents() {
        logger.info("a method was called for calculating the average age of students in using stream");
        Collection<Student> students = studentRepository.findAll();
        double totalAge = students
                .stream()
                .mapToDouble(Student::getAge)
                .sum();
        return totalAge / students.size();
    }

    public String printStudentsParallel() {
        List<Student> students = studentRepository.find6StudentByOrder();

        if (students.size() < 6) {
            logger.warn("Недостаточно студентов в базе: {}", students.size());
            return "Недостаточно студентов для выполнения операции";
        }

        logger.info("Main thread started");
        System.out.println(students.get(0).getId() + " (main thread) " + students.get(0).getName());
        System.out.println(students.get(1).getId() + " (main thread) " + students.get(1).getName());

        Thread thread1 = new Thread(() -> {
            logger.info("Parallel thread 1 started");
            System.out.println(students.get(2).getId() + " (thread1) " + students.get(2).getName());
            System.out.println(students.get(3).getId() + " (thread1) " + students.get(3).getName());
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            logger.info("Parallel thread 2 started");
            System.out.println(students.get(4).getId() + " (thread2) " + students.get(4).getName());
            System.out.println(students.get(5).getId() + " (thread2) " + students.get(5).getName());
        });
        thread2.start();
        return "Parallel thread";
    }

    public String printStudentsSynchronizedParallel() {
        List<Student> students = studentRepository.find6StudentByOrder();

        if (students.size() < 6) {
            logger.warn("Недостаточно студентов в базе: {}", students.size());
            return "Недостаточно студентов для выполнения операции";
        }

        logger.info("Main synchronized thread started");
        printStudentSynchronized(students.get(0), "main thread");
        printStudentSynchronized(students.get(1), "main thread");

        Thread thread1 = new Thread(() -> {
            logger.info("Parallel synchronized thread 1 started");
            printStudentSynchronized(students.get(2), "thread1");
            printStudentSynchronized(students.get(3), "thread1");
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            logger.info("Parallel synchronized thread 2 started");
            printStudentSynchronized(students.get(4), "thread2");
            printStudentSynchronized(students.get(5), "thread2");
        });
        thread2.start();
        return "Parallel synchronized thread";
    }

    private synchronized void printStudentSynchronized(Student student, String threadName) {
        System.out.println(student.getId() + " (" + threadName + ") " + student.getName());
    }
}

