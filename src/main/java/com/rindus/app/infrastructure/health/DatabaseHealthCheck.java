package com.rindus.app.infrastructure.health;

import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

/**
 * Health check for the database.
 */
@Readiness
@ApplicationScoped
public class DatabaseHealthCheck implements HealthCheck {

  private final AgroalDataSource agroalDataSource;

  @Inject
  public DatabaseHealthCheck(AgroalDataSource agroalDataSource) {
    this.agroalDataSource = agroalDataSource;
  }

  @Override
  public HealthCheckResponse call() {
    try (var conn = agroalDataSource.getConnection()) {
      return HealthCheckResponse.up("Database connection");
    } catch (Exception e) {
      return HealthCheckResponse.down("Database connection");
    }
  }
}
