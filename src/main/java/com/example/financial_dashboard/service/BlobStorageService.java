package com.example.financial_dashboard.service;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.example.financial_dashboard.config.BlobStorageConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class BlobStorageService {

    private final BlobContainerClient blobContainerClient;

    public BlobStorageService(BlobContainerClient blobContainerClient) {
        this.blobContainerClient = blobContainerClient;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        // Nome único para o ficheiro
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Cria o blob e faz o upload

        blobContainerClient.getBlobClient(filename)
                .upload(file.getInputStream(), file.getSize(), true);

        // (Opcional) Definir tipo de conteúdo
        blobContainerClient.getBlobClient(filename)
                .setHttpHeaders(new BlobHttpHeaders().setContentType(file.getContentType()));

        // Retorna a URL do ficheiro
        return blobContainerClient.getBlobClient(filename).getBlobUrl();
    }
}
