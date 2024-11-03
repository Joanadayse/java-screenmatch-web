package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.EpisodiosDTO;
import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repositorio;

    public List<SerieDTO> obterTodasAsSeries(){
        return convertDados(repositorio.findAll());

    }

    public List<SerieDTO> obterTop5Series() {
         return convertDados( repositorio.findTop5ByOrderByAvaliacaoDesc());

    }

    private List<SerieDTO> convertDados(List<Serie> series){
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());

    }

    public List<SerieDTO> obterLancamentos() {
        return convertDados(repositorio.lancamentoMaisRecentes());
    }

    public SerieDTO obeterPorId(Long id) {
        Optional <Serie> serie= repositorio.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse());
        }
        return null;
    }

    public List<EpisodiosDTO> obterTodasAsTemporadas(Long id) {
        Optional <Serie> serie= repositorio.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e -> new EpisodiosDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
