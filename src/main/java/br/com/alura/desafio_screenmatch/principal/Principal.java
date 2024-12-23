package br.com.alura.desafio_screenmatch.principal;

import br.com.alura.desafio_screenmatch.models.Veiculo;
import br.com.alura.desafio_screenmatch.models.VeiculoFinal;
import br.com.alura.desafio_screenmatch.service.Conversor;
import br.com.alura.desafio_screenmatch.service.GetUserInput;
import br.com.alura.desafio_screenmatch.service.WebRequest;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private final WebRequest webRequest = new WebRequest();
    private final Conversor conversor = new Conversor();
    private final GetUserInput getUserInput = new GetUserInput();
    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu() {
        System.out.println("+++++BEM-VINDO AO CONSULTA FIPE+++++");

        //PESQUISA O TIPO DE VEÍCULO ESCOLHIDO
        var decisaoTipo = getUserInput.getUserInput("O que deseja pesquisar?\n1) Carro\n2) Moto\n3) Caminhão");
        String veiculo = null;
        switch (decisaoTipo) {
            case "1":
                veiculo = "carros";
                break;
            case "2":
                veiculo = "motos";
                break;
            case "3":
                veiculo = "caminhoes";
                break;
            default:
                System.out.println("Opção não encontrada, tente novamente.");
                System.exit(400);
        }
        String json = webRequest.getResponse(ENDERECO + veiculo + "/marcas");
        List<Veiculo> listaDeVeiculos = conversor.criaListaVeiculos(json, "");
        listaDeVeiculos.stream().sorted(Comparator.comparingInt(v -> Integer.parseInt(v.codigo())))
                .forEach(v -> {System.out.println("Código: " + v.codigo() + " Marca: " + v.nome());
                });

        //PESQUISA OS MODELOS DA MARCA ESCOLHIDA
        var decisaoMarca = getUserInput.getUserInput("Insira o código da marca que deseja pesquisar: ");
        json = webRequest.getResponse(ENDERECO + veiculo + "/marcas/" + decisaoMarca + "/modelos");
        listaDeVeiculos = conversor.criaListaVeiculos(json, "marcas");
        listaDeVeiculos.stream().sorted(Comparator.comparingInt(v -> Integer.parseInt(v.codigo())))
                .forEach(v -> {System.out.println("Código: " + v.codigo() + " Nome: " + v.nome());
                });

        //PESQUISA OS ANOS DO MODELO ESCOLHIDO
        var decisaoModelo = getUserInput.getUserInput("Insira o código do modelo que deseja pesquisar: ");
        json = webRequest.getResponse(ENDERECO + veiculo + "/marcas/" + decisaoMarca + "/modelos/" + decisaoModelo + "/anos");
        listaDeVeiculos = conversor.criaListaVeiculos(json, "modelos");
        listaDeVeiculos.stream().sorted(Comparator.comparingInt(v -> Integer.parseInt(v.codigo())))
                .forEach(v -> {System.out.println("Código: " + v.codigo() + " Ano: " + v.nome());
                });

        //PESQUISA O VALOR DO ANO ESCOLHIDO
        var decisaoAno = getUserInput.getUserInput("Insira o código do ano que deseja pesquisar: ");
        decisaoAno = decisaoAno.substring(0, decisaoAno.length() -1) + "-1";
        json = webRequest.getResponse(ENDERECO + veiculo + "/marcas/" + decisaoMarca + "/modelos/" + decisaoModelo + "/anos/" + decisaoAno);
        VeiculoFinal veiculoFinal = conversor.criaVeiculoFinal(json);

        //EXIBE O VEICULO FINAL
        exibeVeiculoFinal(veiculoFinal);
    }

    private void exibeVeiculoFinal(VeiculoFinal veiculoFinal) {
        System.out.println("Marca: " + veiculoFinal.marca());
        System.out.println("Modelo: " + veiculoFinal.modelo());
        System.out.println("Ano: " + veiculoFinal.ano());
        System.out.println("Combustível: " + veiculoFinal.combustivel());
        System.out.println("Codigo Fipe: " + veiculoFinal.codigoFipe());
        System.out.println("Mes de Referência: " + veiculoFinal.mesReferencia());
        System.out.println("Valor: " + veiculoFinal.valor());
    }
}
