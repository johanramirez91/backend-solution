package co.com.sofka.questions.usecase;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.mappers.QuestionMapper;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class UpdateUseCase implements SaveQuestion{

    private final QuestionRepository questionRepository;
    private final QuestionMapper mapperUtils;

    public UpdateUseCase(QuestionRepository questionRepository, QuestionMapper mapperUtils) {
        this.questionRepository = questionRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<String> apply(QuestionDTO questionDTO) {
        Objects.requireNonNull(questionDTO.getId(), "Id of the question is required");
        return questionRepository
                .save(mapperUtils.mapToQuestion(questionDTO.getId()).apply(questionDTO))
                .map(Question::getId);
    }
}
