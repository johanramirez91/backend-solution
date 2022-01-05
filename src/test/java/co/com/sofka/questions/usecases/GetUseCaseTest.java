package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import co.com.sofka.questions.usecase.GetUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetUseCaseTest {

    @MockBean
    private QuestionRepository questionRepository;

    @SpyBean
    private GetUseCase getUseCase;

    @Test
    void getQuestion(){
        //Arrange
        var questionDto = new QuestionDTO(
                "1",
                "¿Qué es SpingBoot?",
                "OPEN",
                "SOFTWARE DEVELOPMENT"
        );

        var question = new Question();
        question.setId("005");
        question.setQuestion("¿Qué es React");
        question.setCategory("TECHNOLOGY");
        question.setType("OPEN");
        question.setUserId("125");

        Mockito.when(questionRepository.findById(Mockito.anyString())).thenReturn(Mono.just(question));

        //Act
        var response = getUseCase.apply(questionDto.getQuestion());

        //Assert
        Assertions.assertEquals(response.block().getQuestion(), question.getQuestion());
        Assertions.assertEquals(response.block().getCategory(), question.getCategory());
        Assertions.assertEquals(response.block().getUserId(), question.getUserId());
        Assertions.assertEquals(response.block().getType(), question.getType());
    }

}