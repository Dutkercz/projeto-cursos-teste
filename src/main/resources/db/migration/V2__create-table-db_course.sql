CREATE TABLE db_course(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    instructor_id BIGINT,

    CONSTRAINT fk_course__instructor FOREIGN KEY (instructor_id) REFERENCES db_user(id)
);

CREATE TABLE db_course_student(
    course_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,

    PRIMARY KEY (course_id, student_id),
    FOREIGN KEY (course_id) REFERENCES db_course(id),
    FOREIGN KEY (student_id) REFERENCES db_user(id)
);