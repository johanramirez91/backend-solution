package co.com.sofka.questions.bussinesusecase;

import co.com.sofka.questions.mappers.AnswerMapper;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@Validated
public class VerifyAnswerVotesUseCase {

    private final AnswerRepository answerRepository;
    private final AnswerMapper mapper;

    public VerifyAnswerVotesUseCase(AnswerRepository answerRepository, AnswerMapper mapper) {
        this.answerRepository = answerRepository;
        this.mapper = mapper;
    }

    public Mono<AnswerDTO> verifyAnswerVote(AnswerDTO answerDTO) {
        return answerRepository.findByUserIdAndAnswerAndId(answerDTO.getId(), answerDTO.getUserId())
                .switchIfEmpty(Mono.error(new IllegalAccessException("Usuario no autorizado")))
                .flatMap(response -> {
                    if (answerDTO.getVote() == 0){
                        responseAnswer(answerDTO);
                    }
                    return Mono.error(new IllegalStateException("El usuario ya votó"));
                });
    }

    private Mono<AnswerDTO> responseAnswer(AnswerDTO answerDTO) {
        var response = mapper.mapAnswerDTOToAnswer().apply(answerDTO);
        return answerRepository.save(response).map(mapper.mapAnswerToAnswerDTO());
    }
}
