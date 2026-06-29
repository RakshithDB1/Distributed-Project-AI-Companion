package com.distributed_project_companion.workspace_service.service;


import com.distributed_project_companion.common_lib.dto.FileTreeDto;

public interface ProjectFileService {
    FileTreeDto getFileTree(Long projectId);

    String getFileContent(Long projectId, String path);

    void saveFile(Long projectId, String filePath, String fileContent);
}
