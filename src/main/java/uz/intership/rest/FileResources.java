package uz.intership.rest;

import com.itextpdf.text.DocumentException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.intership.dto.ResponseDto;
import uz.intership.repository.ProductRepository;
import uz.intership.servise.FileService;

import java.io.IOException;

@RestController
@RequestMapping("file")
@RequiredArgsConstructor
public class FileResources {
    private final FileService fileService;
    @GetMapping("exel")
    public ResponseDto<String> exelGeneration() throws IOException {
        return fileService.exelCreate();
    }
    @GetMapping("pdf")
    public ResponseDto<String> pdfGeneration() throws DocumentException {
        return fileService.pdfGeneration();
    }
}
