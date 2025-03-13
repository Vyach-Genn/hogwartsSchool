SELECT s.name, s.age, f.name
FROM student s
         JOIN faculty f ON s.faculty_id = f.id;

SELECT DISTINCT s.name, s.age
FROM avatar a
         JOIN student s ON a.student_id = s.id
WHERE a.file_path IS NOT NULL;