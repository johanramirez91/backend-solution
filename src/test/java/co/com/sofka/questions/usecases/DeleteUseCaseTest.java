package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.usecase.DeleteUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeleteUseCaseTest {

    @MockBean
    private AnswerRepository answerRepository;

    @SpyBean
    private DeleteUseCase deleteUseCase;

    @Test
    void deleteAnswer(){

        //Arrange
        var answerDto = new AnswerDTO(
                "001",
                "007",
                "Python",
                "200"
        );

        Mockito.when(answerRepository.deleteByQuestionId(answerDto.getId())).thenReturn(Mono.empty());

        //Act
        var response = deleteUseCase.apply(answerDto.getId()).thenReturn(Mono.empty());

        Assertions.assertEquals(response.block(), Mono.empty());
    }

}