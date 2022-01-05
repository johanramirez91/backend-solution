package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.mappers.QuestionMapper;
import co.com.sofka.questions.reposioties.QuestionRepository;
import co.com.sofka.questions.usecase.ListUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
class ListUseCaseTest {

    QuestionRepository repository;
    ListUseCase listUseCase;


    @BeforeEach
    public void setup() {
        QuestionMapper mapperUtils = new QuestionMapper();
        repository = Mockito.mock(QuestionRepository.class);
        listUseCase = new ListUseCase(repository, mapperUtils);
    }

    @Test
    void getValidationTest() {

        var question = new Question();
        question.setUserId("xxxx-xxxx");
        question.setType("tech");
        question.setCategory("software");
        question.setQuestion("¿Que es java?");
        Mockito.when(repository.findAll()).thenReturn(Flux.just(question));

        StepVerifier.create(listUseCase.get())
                .expectNextMatches(questionDTO -> {
                    assert questionDTO.getUserId().equals("xxxx-xxxx");
                    assert questionDTO.getCategory().equals("software");
                    assert questionDTO.getQuestion().equals("¿Que es java?");
                    assert questionDTO.getType().equals("tech");
                    return true;
                })
                .verifyComplete();

        Mockito.verify(repository).findAll();
    }
}
