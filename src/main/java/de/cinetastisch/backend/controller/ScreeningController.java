package de.cinetastisch.backend.controller;

import de.cinetastisch.backend.dto.request.ScreeningRequestDto;
import de.cinetastisch.backend.dto.response.ScreeningFullResponseDto;
import de.cinetastisch.backend.dto.response.ScreeningResponseDto;
import de.cinetastisch.backend.service.ScreeningService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/screenings")
public class ScreeningController {

    private final ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @Operation(
            tags = {"Screenings"},
            parameters = {
                    @Parameter(
                            name = "startTime",
                            description = "Get all screenings after the specified start datetime",
                            example = "2022-12-30T19:34:50.63"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<ScreeningResponseDto>> getAll(@RequestParam(value = "startTime", required = false) String startTime,
                                                             @RequestParam(value = "movieId", required = false) Long movieId){
        return ResponseEntity.ok(screeningService.getAllScreenings(startTime, movieId));
    }

    @Operation(
            tags = {"Screenings"},
            summary = "Returns screening with a seating plan of reservations"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ScreeningFullResponseDto> getOne(@PathVariable Long id){
        return ResponseEntity.ok(screeningService.getScreening(id));
    }

    @Operation(
            tags = {"Screenings"},
            description = "EndDateTime is optional and can be automatically calculated if ommited",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = ScreeningRequestDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )
    )
    @PostMapping
    public ResponseEntity<ScreeningFullResponseDto> add(@RequestBody ScreeningRequestDto screeningRequestDto){
        return new ResponseEntity<>(screeningService.addScreening(screeningRequestDto), HttpStatus.CREATED);
    }


    @Operation(
            tags = {"Screenings"}
    )
    @PutMapping("{id}")
    public ResponseEntity<ScreeningFullResponseDto> replaceOne(@Valid @RequestBody ScreeningRequestDto screeningDto,
                                                @Valid @PathVariable("id") Long id){
        return new ResponseEntity<>(screeningService.replaceScreening(id, screeningDto), HttpStatus.OK);
    }

    @Operation(
            tags = {"Screenings"},
            summary = "Cancel a Screening",
            description = "Specified Screening will get cancelled, all reservations will be deleted and paid Orders will be refunded"
    )
    @PutMapping("{id}/cancel")
    public ResponseEntity<ScreeningFullResponseDto> cancelScreening(@Valid @PathVariable("id") Long id){
        return ResponseEntity.ok(screeningService.cancelScreening(id));
    }

    @Operation(
            tags = {"Screenings"}
    )
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@Valid @PathVariable("id") Long id){
        screeningService.deleteScreening(id);
        return ResponseEntity.noContent().build();
    }
}
