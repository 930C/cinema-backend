package de.cinetastisch.backend.controller;

//import de.cinetastisch.backend.dto.RoomPlanResponseDto;
import de.cinetastisch.backend.dto.response.ScreeningFullResponseDto;
import de.cinetastisch.backend.dto.request.ScreeningRequestDto;
import de.cinetastisch.backend.dto.response.ScreeningResponseDto;
import de.cinetastisch.backend.service.ScreeningService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class ScreeningControllerTest {
    @InjectMocks
    ScreeningController screeningController;
    @Mock
    ScreeningService screeningService;


    @Test
    void getAll() {
        ScreeningResponseDto firstScreeningResponseDto = new ScreeningResponseDto(null,null,null,null,null,null,false, false, null);
        ScreeningResponseDto secoundScreeningResponseDto = new ScreeningResponseDto(null,null,null,null,null,null,false, false, null);
        List<ScreeningResponseDto> screeningResponseDtoList = List.of(firstScreeningResponseDto,secoundScreeningResponseDto);

        when(screeningService.getAllScreenings(null,null)).thenReturn(screeningResponseDtoList);

        ResponseEntity<?> response = screeningController.getAll(null,null);
        assertEquals(screeningResponseDtoList, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void getOne() {
        ScreeningFullResponseDto screeningResponseDto = new ScreeningFullResponseDto((long)1.2, null, null, null, null, null, null, false, false, null);

        when(screeningService.getScreening((long)1.2)).thenReturn(screeningResponseDto);

        ResponseEntity<ScreeningFullResponseDto> response = screeningController.getOne((long)1.2);
        assertEquals(screeningResponseDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void add() {
        ScreeningRequestDto requestDto = new ScreeningRequestDto(null,null,null,null,"false", "false", null);
        ScreeningFullResponseDto responseDto = new ScreeningFullResponseDto(null,null,null,null,null,null,null, false, false, null);
        when(screeningService.addScreening(requestDto)).thenReturn(responseDto);

        ResponseEntity<?> response = screeningController.add(requestDto);
        assertEquals(responseDto,response.getBody());
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }

    @Test
    void replaceOne() {
        ScreeningRequestDto requestDto = new ScreeningRequestDto(null,null,null,null,"false", "false", null);
        ScreeningFullResponseDto responseDto = new ScreeningFullResponseDto(null,null,null,null,null,null,null,false, false, null);

        when(screeningService.replaceScreening((long)1.2, requestDto)).thenReturn(responseDto);
        ResponseEntity<?> response = screeningController.replaceOne(requestDto,(long)1.2);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void delete() {
        ResponseEntity<?> response = screeningController.delete((long)1.2);
        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());

    }

//    @Test
//    void getSeatingPlan() {
//        RoomPlanResponseDto roomPlanResponseDto = new RoomPlanResponseDto(null,null,null);
//        when(screeningService.getSeatingPlan((long)1.2)).thenReturn(roomPlanResponseDto);
//
//        ResponseEntity<?> response = screeningController.getSeatingPlan((long)1.2);
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        assertEquals(roomPlanResponseDto,response.getBody());
//    }
}