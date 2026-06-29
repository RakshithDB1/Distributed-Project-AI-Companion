package com.distributed_project_companion.common_lib.dto;


public record FileNode(
        String path
) {

    @Override
    public String toString() {
        return path;
    }
}