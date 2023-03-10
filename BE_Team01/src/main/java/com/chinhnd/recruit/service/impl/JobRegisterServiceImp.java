package com.chinhnd.recruit.service.impl;

import com.chinhnd.recruit.dto.BookInterviewDto;
import com.chinhnd.recruit.filter.JobRgfilter;
import com.chinhnd.recruit.repository.JobRegisterRepository;
import com.chinhnd.recruit.repository.StatusJobRegisterRepository;
import com.chinhnd.recruit.service.mapper.JobRegisterMapper;
import com.chinhnd.recruit.specification.JobRegisterSpecification;
import com.chinhnd.recruit.web.vm.PageVM;
import com.chinhnd.recruit.dto.JobRegisterDTO;
import com.chinhnd.recruit.dto.StatusJobRegisterDTO;
import com.chinhnd.recruit.entity.Job;
import com.chinhnd.recruit.entity.JobRegister;
import com.chinhnd.recruit.entity.StatusJobRegister;
import com.chinhnd.recruit.service.JobRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
@Service
@Transactional

public class JobRegisterServiceImp implements JobRegisterService {

    @Autowired
    public JobRegisterMapper jobRegisterMapper;
    @Autowired
    public JobRegisterRepository jobRegisterRepository;
    @Autowired
    public StatusJobRegisterRepository statusJobRegisterRepository;

    @Autowired
    public JavaMailSender javaMailSender;

    @Override
    public  Page<JobRegisterDTO> getAllJobRegister(PageVM pageVM, String search, String sortBy, JobRgfilter jobRgfilter) {
        Pageable firstPageWithTwoElements;
        if(sortBy == null){
            firstPageWithTwoElements = PageRequest.of(pageVM.getPageNumber(), pageVM.getPageSize());
        }else {
            firstPageWithTwoElements = PageRequest.of(pageVM.getPageNumber(), pageVM.getPageSize(), Sort.by(sortBy));
        }
        Specification<JobRegister> where = JobRegisterSpecification.buildWhere(search, jobRgfilter);
        return  jobRegisterRepository.findAll(where,firstPageWithTwoElements).map(jobRegisterMapper::toDto);

    }

    @Override
    public Optional<JobRegister> findById(Long id) {
        return jobRegisterRepository.findById(id);
    }

    @Override
    public JobRegister addJobRegister(JobRegisterDTO dto) {
        JobRegister jobRegister = jobRegisterMapper.toEntity(dto);
        return jobRegisterRepository.save(jobRegister);

    }

    @Override
    public JobRegisterDTO save(JobRegisterDTO jobRegisterDTO) {
//        return jobRegisterRepository.save(jobRegisterDTO);
        return null;
    }


    @Override
    public void deleteById(Long id) {
        jobRegisterRepository.deleteById(id);
    }

    @Override
    public JobRegister updateById(JobRegisterDTO jobRegisterDTO) {
        Optional<JobRegister> jobRegisterOptional = jobRegisterRepository.findById(jobRegisterDTO.getId());
        if (jobRegisterOptional.isPresent()){
            JobRegister jobRegister = jobRegisterMapper.toEntity(jobRegisterDTO);
            jobRegisterRepository.save(jobRegister);
            jobRegister.setId(jobRegisterDTO.getId());
            return jobRegisterOptional.get();
        }
        return null;
    }

//    @Override
//    public Job updateStatus(StatusJobDTO statusJobDTO) {
//        Job job = jobRepository.findById(statusJobDTO.getJobId()).get();
//        job.setStatusJob(statusJobRepository.findStatusById(statusJobDTO.getStatusId()));
//        return jobRepository.save(job);
//    }

    @Override
    public JobRegister updateStatusJobRegister(StatusJobRegisterDTO statusJobRegisterDTO) {
        JobRegister jobRegister = jobRegisterRepository.findById(statusJobRegisterDTO.getJobRegisterId()).get();
        jobRegister.setStatusJobRegister(statusJobRegisterRepository.findStatusById(statusJobRegisterDTO.getStatusJobRegisterId()));
        jobRegister.setReason(statusJobRegisterDTO.getReason());

        if(statusJobRegisterDTO.getJobRegisterId() == 4){
            Job job = jobRegister.getJob();
            job.setQtyPerson(job.getQtyPerson()-1);
            jobRegister.setJob(job);
        }
        return jobRegisterRepository.save(jobRegister);
    }

    public Page<JobRegisterDTO> findAlD(Pageable pageable){
        return (Page<JobRegisterDTO>) jobRegisterRepository.findAll((Sort) pageable).stream().map(jobRegisterMapper::toDto);
    }

    @Override
    public int countAll() {
        return jobRegisterRepository.countAll();
    }

    @Override
    public int countJobRegByStatus(Long statusId, String smallDate, String bigDate) {
        return jobRegisterRepository.countJobRegisterByStatus(statusId, smallDate,  bigDate);
    }

    @Override
    public int countSuccessfullJobReg(int param) {
        return jobRegisterRepository.countSuccessfullJobReg(param);
    }

    @Override
    public JobRegister bookInterView(BookInterviewDto dto) throws ParseException {
        JobRegister jobRegister = jobRegisterRepository.findById(dto.getId()).get();
        jobRegister.setMethodInterview(dto.getMethodInterview());
        jobRegister.setAddressInterview(dto.getAddressInterview());
        StatusJobRegister statusJobRegister = new StatusJobRegister(4L, "??ang ph???ng v???n", "??ang ph???ng v???n", false);
        jobRegister.setStatusJobRegister(statusJobRegister);
        String date = dto.getDateInterview();
    String dateInterviewFix = date.split("-")[2].split("T")[0]
            + "-" + date.split("-")[1] + '-' + date.split("-")[0]
            + ' ' + date.split("-")[2].split("T")[1] + ":00";
        Date simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss").parse(dateInterviewFix);
        jobRegister.setDateInterview(simpleDateFormat);

        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("hulkhulk1245@gmail.com");
            mimeMessageHelper.setTo(jobRegister.getUser().getEmail());
            mimeMessageHelper.setText("Dear anh/ch??? "+ jobRegister.getUser().getName() + ",\n" +
                    "\n" +
                    "C??ng ty ITSOL r???t vui v?? vinh h???nh khi nh???n ???????c h??? s?? ???ng tuy???n c???a anh/ch??? v??o v??? tr?? "+ jobRegister.getJob().getName()+"." +
                    " Ch??ng t??i ???? nh???n ???????c CV c???a anh/ch??? v?? mong mu???n c?? m???t cu???c ph???ng v???n ????? trao ?????i tr???c ti???p v??? ki???n th???c c??ng nh?? c??ng vi???c m?? anh/ch??? ???? ???ng tuy???n.\n" +
                    "\n" +
                    "Th???i gian ph???ng v???n d??? ki???n v??o l??c "+ dateInterviewFix.split(" ")[1] +" v??o ng??y " +dateInterviewFix.split(" ")[0] +" qua c??ng c??? "
                    +dto.getAddressInterview()+ " " +
                    "(ch??ng t??i s??? g???i l???i link sau khi anh/ch??? x??c nh???n ?????ng ?? ph???ng v???n b???ng c??c reply l???i mail n??y).\n" +
                    "\n" +
                    "Ch??ng t??i r???t hy v???ng anh/ch??? s???m ph???n h???i v?? mong r???ng ch??ng ta s??? ???????c h???p t??c c??ng nhau trong t????ng lai.\n" +
                    "\n" +
                    "M???i th???c m???c xin vui l??ng li??n h??? t???i anh Nguyen Van A, S??T: 0912345678 trong gi??? h??nh ch??nh ????? ???????c gi???i ????p.\n" +
                    "\n" +
                    "Thanks & best regards,\n" +
                    "ITSOL JSC\n" +
                    "Head office: T???ng 3, t??a nh?? 3A, ng?? 82, ph??? Duy T??n, ph?????ng D???ch V???ng H???u, qu???n C???u Gi???y, H?? N???i\n" +
                    "Hotline: 0123456789\n");
            mimeMessageHelper.setSubject("Th?? m???i ph???ng v???n");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return jobRegisterRepository.save(jobRegister);
    }

}
