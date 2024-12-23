package br.com.alura.desafio_screenmatch.service;

import java.util.Scanner;

public class GetUserInput {

    private final Scanner scanner = new Scanner(System.in);
    public String getUserInput(String texto){
        System.out.println(texto);
        return scanner.nextLine();
    }
}
