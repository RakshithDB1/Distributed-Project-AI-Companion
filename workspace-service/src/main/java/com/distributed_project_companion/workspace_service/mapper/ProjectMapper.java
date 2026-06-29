package com.distributed_project_companion.workspace_service.mapper;


import com.distributed_project_companion.common_lib.enums.ProjectRole;
import com.distributed_project_companion.workspace_service.dto.project.ProjectResponse;
import com.distributed_project_companion.workspace_service.dto.project.ProjectSummaryResponse;
import com.distributed_project_companion.workspace_service.entity.Project;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectResponse toProjectResponse(Project project);

    ProjectSummaryResponse toProjectSummaryResponse(Project project, ProjectRole role);

    List<ProjectSummaryResponse> toListOfProjectSummaryResponse(List<Project> projects);

}
