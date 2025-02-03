package com.example.xlstesttask.service;

import com.example.xlstesttask.service.exception.CustomException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.PriorityQueue;

@Service
public class NumberService {

    public int findNthMax(String filePath, int n) throws Exception {
        if (n <= 0) {
            throw new CustomException("n should be more than 0");
        }

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            PriorityQueue<Integer> minHeap = new PriorityQueue<>(n);

            int count = 0;

            for (Row row : sheet) {
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.NUMERIC) {
                        int number = (int) cell.getNumericCellValue();
                        count++;

                        if (minHeap.size() < n) {
                            minHeap.add(number);
                        } else if (number > minHeap.peek()) {
                            minHeap.poll();
                            minHeap.add(number);
                        }
                    }
                }
            }

            if (count < n) {
                throw new CustomException("File has less than " + n + " numbers");
            }

            return minHeap.isEmpty() ? Integer.MIN_VALUE : minHeap.peek();
        }
    }
}
