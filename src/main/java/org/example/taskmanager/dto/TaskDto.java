package org.example.taskmanager.dto;

public record TaskDto(Long id, String title, String description, Long userId) {
}