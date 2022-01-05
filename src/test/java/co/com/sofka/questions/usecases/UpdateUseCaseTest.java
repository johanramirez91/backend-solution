package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import co.com.sofka.questions.usecase.UpdateUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UpdateUseCaseTest {

    @SpyBean
    private UpdateUseCase updateUseCase;

    @MockBean
    private QuestionRepository questionRepository;

    @Test
    void updateQuestion(){

        //Arrange
        var questionDTO = new QuestionDTO("1","1","What is the best JavaScript Framework?", "OPEN","TECHNOLOGY AND COMPUTER");
        var question = new Question();
        question.setId("2");
        question.setUserId("1");
        question.setQuestion("What is the best JavaScript Framework?");
        question.setType("OPEN");
        question.setCategory("TECNOLOGIA");

        Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(Mono.just(question));

        //Act
        var result = updateUseCase.apply(questionDTO);

        //Assert
        Assertions.assertEquals(Objects.requireNonNull(result.block()),"2");
    }
}