package com.rindus.app.infrastructure.repository.persistance.entity;

import com.rindus.app.domain.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

/**
 * Persistence entity for User, using Panache.
 */
@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false, updatable = false, length = 40)
  public UUID id;

  @Column(name = "name", nullable = false, length = 255)
  public String name;

  @Column(name = "email", nullable = false, length = 255, unique = true)
  public String email;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 50)
  public UserStatus status;

  @Column(name = "email_verified", nullable = false)
  public boolean emailVerified;

  @Column(name = "email_verified_at")
  public Instant emailVerifiedAt;

  @Column(name = "created_at", nullable = false, updatable = false)
  public Instant createdAt;

  @Column(name = "updated_at")
  public Instant updatedAt;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserStatus getStatus() {
    return status;
  }

  public void setStatus(UserStatus status) {
    this.status = status;
  }

  public boolean isEmailVerified() {
    return emailVerified;
  }

  public void setEmailVerified(boolean emailVerified) {
    this.emailVerified = emailVerified;
  }

  public Instant getEmailVerifiedAt() {
    return emailVerifiedAt;
  }

  public void setEmailVerifiedAt(Instant emailVerifiedAt) {
    this.emailVerifiedAt = emailVerifiedAt;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }
}
