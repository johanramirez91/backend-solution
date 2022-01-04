package co.com.sofka.questions.usecase;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.mappers.QuestionMapper;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class CreateUseCase implements SaveQuestion {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public CreateUseCase(QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    @Override
    public Mono<String> apply(QuestionDTO newQuestion) {
        return questionRepository
                .save(questionMapper.mapToQuestion(null).apply(newQuestion))
                .map(Question::getId);
    }
}
