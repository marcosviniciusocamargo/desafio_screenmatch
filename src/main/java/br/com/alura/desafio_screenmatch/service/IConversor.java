package br.com.alura.desafio_screenmatch.service;

public interface IConversor {
    <T> T converte(String json, Class<T> classe);
}
