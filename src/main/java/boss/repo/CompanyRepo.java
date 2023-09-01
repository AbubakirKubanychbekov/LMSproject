package boss.repo;

import boss.dto.response.CompanyResponse;
import boss.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepo extends JpaRepository<Company,Long> {

    @Query("select new boss.dto.response.CompanyResponse(c.id,c.name,c.country,c.address,c.phoneNumber) from Company c")
    List<CompanyResponse> getAllCompanies();

    Optional<CompanyResponse> getCompanyById(Long id);
}
