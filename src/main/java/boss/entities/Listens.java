package boss.entities;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDate;

public class Listens {
    @PrePersist
    public void save(Company company) {
        company.setCreatedEt(LocalDate.now());
        company.setUpdatedEt(LocalDate.now());
    }

    @PreUpdate
    public void onPreUpdate(Company company) {
        company.setUpdatedEt(LocalDate.now());
    }
}
