package ru.service.maintenance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.service.maintenance.entyties.WorkSite;

import java.util.List;

@Repository
public interface WorkSiteRepository extends JpaRepository<WorkSite, Long> {

    @Query("select ba from WorkSite ba where ba.streets.id in :idStreets")
    List<WorkSite> findAllByStreetId(@Param("idStreets") List<Long> idStreets);

    @Modifying
    @Query(value = "update work_sites set title = ?1 where id = ?2", nativeQuery = true)
    void changeWorkSite(String title, Long id);

    @Modifying
    @Query("update WorkSite u set u.updatedAt = CURRENT_TIMESTAMP where u.id = ?1")
    void changeUpdateAt(Long id);

    @Modifying
    @Query(value = "update work_sites set at_work = ?1 where id = ?2", nativeQuery = true)
    void changeAtWork(Boolean atWork, Long id);

    @Modifying
    @Query(value = "update work_sites set done = ?1 where id = ?2", nativeQuery = true)
    void changeDone(Boolean done, Long id);

    @Modifying
    @Query(value = "update work_sites set no_done = ?1 where id = ?2", nativeQuery = true)
    void changeNoDone(Boolean noDone, Long id);
}
