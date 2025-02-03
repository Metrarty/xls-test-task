package com.example.xlstesttask.controller;

import com.example.xlstesttask.service.NumberService;
import com.example.xlstesttask.service.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class FileController {

    private final NumberService numberService;

    public FileController(NumberService numberService) {
        this.numberService = numberService;
    }

    @PostMapping("/find-nth-max")
    public int findNthMax(@RequestParam("filePath") String filePath, @RequestParam("n") int n) throws CustomException {
        try {
            return numberService.findNthMax(filePath, n);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException e) {
        return new ResponseEntity<>("Invalid input: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
