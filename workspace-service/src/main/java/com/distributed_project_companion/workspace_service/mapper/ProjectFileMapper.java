package com.distributed_project_companion.workspace_service.mapper;


import com.distributed_project_companion.common_lib.dto.FileNode;
import com.distributed_project_companion.workspace_service.entity.ProjectFile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectFileMapper {

    List<FileNode> toListOfFileNode(List<ProjectFile> projectFileList);
}
