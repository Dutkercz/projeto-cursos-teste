package com.github.Dutkercz.demoPlataformaDeCursos;

import com.github.Dutkercz.demoPlataformaDeCursos.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class TerminalDeTeste implements CommandLineRunner {

    private final UserService userService;
    private final Scanner scanner = new Scanner(System.in);

    public TerminalDeTeste(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        String cpf;
        String escolha;

        System.out.println("Novo Cadastro");
        System.out.print("Nome ");
        var name = scanner.nextLine();
        System.out.print("Senha ");
        var password = scanner.nextLine();

        do {
            System.out.print("CPF: ");
            cpf = scanner.nextLine();
        }while (cpf.length()!= 11);

        System.out.print("Email: ");
        var email = scanner.nextLine();

        do {
            System.out.println("Aluno ou Instrutor");
            System.out.println("1 - Aluno\n2 - Instrutor");
            escolha = scanner.nextLine();
        }while (!escolha.equals("1") && !escolha.equals("2"));
//        if (escolha.equals("1")){
//            Student student = new Student(null, name, password, email, cpf);
//            userService.saveStudent(student);
//            System.out.println("Aluno cadastrado");
//        } else {
//            Instructor instructor = new Instructor(null, name, password, email, cpf);
//            userService.saveInstructor(instructor);
//            System.out.println("Instrutor cadastrado");
//
//        }
//
//        System.out.println("Mostrando todos os alunos");
//        var students = studentService.findAllStudents();
//        students.forEach(x -> System.out.println("Aluno: " + x.getName()));
//
//        System.out.println("\nMostrando todos os instrutores");
//        var instructors = instructorService.findAllInstructors();
//        instructors.forEach(x -> System.out.println("Instrutor: " + x.getName()));

    }
}
