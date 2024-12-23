package br.com.alura.desafio_screenmatch.service;

import br.com.alura.desafio_screenmatch.models.Veiculo;
import br.com.alura.desafio_screenmatch.models.VeiculoFinal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.List;

public class Conversor implements IConversor{
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T converte(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Veiculo> criaListaVeiculos(String json, String tipo) {
        try {
            if (tipo.equals("marcas")){
                int index = json.indexOf(",\"anos\":[");
                json = json.substring(11, index);
                json = json + "}";
            }
            if (tipo.equals("modelos")) json = json.replace("-","");

            JsonNode rootNode = mapper.readTree(json);
            List<JsonNode> smallerJsonObjetics = new ArrayList<>();
            for(JsonNode node : rootNode){
                ArrayNode arrayNode = mapper.createArrayNode();
                arrayNode.add(node);
                smallerJsonObjetics.add(arrayNode);
            }

            List<Veiculo> listaDeVeiculos = new ArrayList<>();
            smallerJsonObjetics.forEach(s -> {
                Veiculo veiculo = converte(s.toString().replace("[","").replace("]",""), Veiculo.class);
                listaDeVeiculos.add(veiculo);
            });

            return listaDeVeiculos;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public VeiculoFinal criaVeiculoFinal(String json){

        return converte(json, VeiculoFinal.class);
    }
}
