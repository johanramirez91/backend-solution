package co.com.sofka.questions.usecase;

import co.com.sofka.questions.mappers.AnswerMapper;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class AddAnswerUseCase implements SaveAnswer {

    private final AnswerRepository answerRepository;
    private final AnswerMapper mapperUtils;
    private final GetUseCase getUseCase;

    public AddAnswerUseCase(AnswerRepository answerRepository, AnswerMapper mapperUtils, GetUseCase getUseCase) {
        this.answerRepository = answerRepository;
        this.mapperUtils = mapperUtils;
        this.getUseCase = getUseCase;
    }

    @Override
    public Mono<QuestionDTO> apply(AnswerDTO answerDTO) {
        Objects.requireNonNull(answerDTO.getQuestionId(), "Id of the answer is required");
        return getUseCase.apply(answerDTO.getQuestionId()).flatMap(question ->
                answerRepository.save(mapperUtils.mapAnswerDTOToAnswer().apply(answerDTO))
                        .map(answer -> {
                            question.getAnswers().add(answerDTO);
                            return question;
                        })
        );
    }
}
