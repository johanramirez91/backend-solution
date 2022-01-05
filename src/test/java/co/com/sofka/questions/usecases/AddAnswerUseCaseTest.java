package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.usecase.AddAnswerUseCase;
import co.com.sofka.questions.usecase.GetUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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

    @MockBean
    GetUseCase getUseCase;

    @SpyBean
    private AddAnswerUseCase addAnswerUseCase;

    @Test
    @DisplayName("Test add new answer")
    void setAddAnswerUseCase() {

        //Arrange
        var answerDto = new AnswerDTO(
                "001",
                "005",
                "SpringBoot",
                "001"
        );
        var questionDto = new QuestionDTO(
                "1",
                "¿Qué es SpingBoot?",
                "OPEN",
                "SOFTWARE DEVELOPMENT"
        );

        var answer = new Answer();
        answer.setQuestionId("001");
        answer.setUserId("005");
        answer.setAnswer("Framework");
        answer.setQuestionId("001");

        Mockito.when(answerRepository.save(Mockito.any(Answer.class))).thenReturn(Mono.just(answer));
        Mockito.when(getUseCase.apply(Mockito.anyString())).thenReturn(Mono.just(questionDto));

        //Act
        var response = addAnswerUseCase.apply(answerDto);
        var resultQuestion = response.block();

        //Assert
        assert resultQuestion != null;
        Assertions.assertEquals(resultQuestion.getId(), questionDto.getId());
        Assertions.assertEquals(resultQuestion.getQuestion(), questionDto.getQuestion());
        Assertions.assertEquals(resultQuestion.getAnswers().get(0).getQuestionId(), answerDto.getQuestionId());
    }
}