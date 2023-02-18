package com.chinhnd.recruit.repository;

import com.chinhnd.recruit.repository.repoext.JobRegisterRepositoryExt;
import com.chinhnd.recruit.entity.JobRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRegisterRepository extends JpaRepository<JobRegister, Long>, JobRegisterRepositoryExt, JpaSpecificationExecutor<JobRegister> {


//    Page<JobRegister> findAll(Pageable pageable, Specification<JobRegister> where);
    Optional<JobRegister> findById(Long id);

//    JobRegister findByOneId(Long id);
    @Query(value = "select count(*)  from job_register", nativeQuery = true)
    int countAll();

    @Query(value = "select count(*)  from job_register j where j.status_id = :statusId and  ((( select  TO_DATE((:smallDate),'DD-MM-YYYY')FROM dual)  < j.DATE_INTERVIEW \n" +
            "and j.DATE_INTERVIEW <  ( select  TO_DATE((:bigDate),'DD-MM-YYYY')FROM dual))\n" +
            "            or (  ( select  TO_DATE((:smallDate),'DD-MM-YYYY')FROM dual)  < date_register\n" +
            "            and j.date_register <  ( select  TO_DATE((:bigDate),'DD-MM-YYYY')FROM dual)))", nativeQuery = true)
    int countJobRegisterByStatus(Long statusId,String smallDate, String bigDate);

    @Query(value = "select count(*) from job_register j join status_job_register s on j.status_id = s.id  " +
            "where s.code = 'đã tuyển' and extract(month from j.date_register) = :param", nativeQuery = true)
    int countSuccessfullJobReg(int param);


}
