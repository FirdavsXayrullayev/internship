package uz.intership.servise;

import com.itextpdf.text.DocumentException;
import uz.intership.dto.ResponseDto;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {
    ResponseDto<String> exelCreate() throws IOException;

    ResponseDto<String> pdfGeneration() throws DocumentException;
}
