package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.usecase.AddAnswerUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

@SpringBootTest
class AddAnswerUseCaseTest {


    @MockBean
    private AnswerRepository answerRepository;

    @SpyBean
    private AddAnswerUseCase addAnswerUseCase;

    @Test
    void setAddAnswerUseCase() {
        var answerDto = new AnswerDTO(
                "001",
                "005",
                "SpringBoot",
                "001"
        );
        var answer = new Answer();
        answer.setQuestionId("001");
        answer.setUserId("005");
        answer.setAnswer("SpringBoot");
        answer.setQuestionId("001");

        Mockito.when(answerRepository.save(Mockito.any(Answer.class))).thenReturn(Mono.just(answer));
        var response = addAnswerUseCase.apply(answerDto).block();
        Assertions.assertEquals(response.getAnswers(), "SpringBoot");
    }
}