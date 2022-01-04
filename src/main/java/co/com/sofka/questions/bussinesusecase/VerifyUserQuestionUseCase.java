package co.com.sofka.questions.bussinesusecase;

import co.com.sofka.questions.mappers.QuestionMapper;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class VerifyUserQuestionUseCase {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public VerifyUserQuestionUseCase(QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    public Mono<QuestionDTO> verifyUserQuestion(QuestionDTO questionDTO){
        return questionRepository.findByIdAndUserId(questionDTO.getId(), questionDTO.getUserId())
                .switchIfEmpty(Mono.error(new IllegalAccessException("Usuario no autorizado")))
                .flatMap(response -> responseQuestion(questionDTO));
    }

    private Mono<QuestionDTO> responseQuestion(QuestionDTO questionDTO){
        var response = questionMapper.mapToQuestion(questionDTO.getId()).apply(questionDTO);
        return questionRepository.save(response).map(questionMapper.mapQuestionToQuestionDTO());
    }
}
