package com.chinhnd.recruit.web;

import com.chinhnd.recruit.dto.BookInterviewDto;
import com.chinhnd.recruit.dto.JobRegisterDTO;
import com.chinhnd.recruit.dto.StatusJobRegisterDTO;
import com.chinhnd.recruit.entity.JobRegister;
import com.chinhnd.recruit.entity.ResponseObject;
import com.chinhnd.recruit.filter.JobRgfilter;
import com.chinhnd.recruit.service.JobRegisterService;
import com.chinhnd.recruit.service.mapper.JobRegisterMapper;
import com.chinhnd.recruit.web.vm.PageVM;
import com.chinhnd.recruit.core.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = Constants.Api.Path.PUBLIC)
@CrossOrigin
public class JobRegisterController {

    @Autowired
    private JobRegisterService jobRegisterService;
    private JobRegisterMapper jobRegisterMapper;

    @PostMapping("/job_register")
    public ResponseEntity<List<JobRegisterDTO>> getAllDESC(@RequestBody PageVM pageVM , @RequestParam(value = "search", required = false ) String search, JobRgfilter jobRgfilter, @RequestParam(value = "sortBy", required = false) String sortBy){

        Page<JobRegisterDTO> page = jobRegisterService.getAllJobRegister(pageVM, search, sortBy, jobRgfilter);
        return ResponseEntity.ok().body(page.getContent());
    }

    @GetMapping("/job_register{id}")
    public ResponseEntity<Optional<JobRegister>> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(jobRegisterService.findById(id));
    }

    @PostMapping("/job_register/add")
    public ResponseEntity<?> add(@Valid @RequestBody JobRegisterDTO dto) {
        jobRegisterService.addJobRegister(dto);
        return ResponseEntity.ok().body("OK");
    }


    @DeleteMapping("/job_register/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        jobRegisterService.deleteById(id);
        return ResponseEntity.ok().body("delete successfully");
    }

    @PutMapping("/job_register/update/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id, @RequestBody JobRegisterDTO jobRegisterDTO){
//       jobRegisterMapper.toEntity(jobRegisterDTO);
        jobRegisterService.updateById(jobRegisterDTO);
        return ResponseEntity.ok().body((new ResponseObject(HttpStatus.OK, "update th??nh c??ng", "")));
    }


    @PostMapping("/job_register/updateStatusJobRegister")
    public ResponseEntity<ResponseObject> updateStatus(@RequestBody StatusJobRegisterDTO statusJobRegisterDTO){
        JobRegister jobRegister = jobRegisterService.updateStatusJobRegister(statusJobRegisterDTO);
        if (jobRegister == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ResponseObject(HttpStatus.BAD_REQUEST, "Kh??ng t???n t???i", ""));
        }
        return ResponseEntity.ok().body(new ResponseObject(HttpStatus.OK,"C???p nh???t tr???ng th??i th??nh c??ng", jobRegisterService.updateStatusJobRegister(statusJobRegisterDTO)));
    }
//    @PutMapping("/job/updateStatus")
//    public ResponseEntity<ResponseObject> updateStatus(@RequestBody StatusJobDTO statusJobDTO) {
//        Job job = jobService.updateStatus(statusJobDTO);
//        if (job == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    new ResponseObject(HttpStatus.BAD_REQUEST, "Job kh??ng t???n t???i", ""));
//        }
//        return ResponseEntity.ok().body(new ResponseObject(HttpStatus.OK, "c???p nh???t tr???ng th??i th??nh c??ng", jobService.updateStatus(statusJobDTO)));
//    }
    @GetMapping("number/jobreg")
    public int countNumberOfJobReg() {
        return jobRegisterService.countAll();
    }

    @GetMapping("number/jobregWitStatus")
    public int countNumberOfJobRegWithStatus(@RequestParam("statusId") Long statusId,
                                             @RequestParam("smallDate") String smallDate,
                                             @RequestParam("bigDate") String bigDate) {
        return jobRegisterService.countJobRegByStatus(statusId, smallDate, bigDate);
    }

    @GetMapping("number/jobreg/success")
    public int countSuccessJobReg(@RequestParam("month") int month) {
        return jobRegisterService.countSuccessfullJobReg(month);
    }

   @PutMapping("book/jobreg/interview")
        public JobRegister bookInterview(@RequestBody BookInterviewDto bookInterviewDto){
       try {
           return jobRegisterService.bookInterView(bookInterviewDto);
       } catch (ParseException e) {
           throw new RuntimeException(e);
       }
   }


}
