package com.example.mini_project_cm.scheduler;

import com.example.mini_project_cm.entity.MemberInfoEntity;
import com.example.mini_project_cm.entity.MemberPhotoEntity;
import com.example.mini_project_cm.repository.MemberInfoRepository;
import com.example.mini_project_cm.repository.MemberPhotoRepository;
import com.example.mini_project_cm.vo.BasicResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class LeaveMemberSystem {
    private final MemberInfoRepository miRepo;
    private final MemberPhotoRepository mpRepo;
    @Value("${file.image.member}") String member_image_path;

    @Scheduled(cron = "00 00 00 * * *") //매일 자정에 회원탈퇴실행 6개월 지난 회원정보 모두 삭제
    public void memberInfoDelete() {
        for(MemberInfoEntity member : miRepo.findByMiStatus(3)) {

            if(member != null) {
                MemberPhotoEntity photo = mpRepo.findByMpMiSeq(member.getMiSeq());

                Date day = new Date();
                Calendar today = Calendar.getInstance();
                today.setTime(day);

                Calendar leaveDt = Calendar.getInstance();

                leaveDt.setTime(member.getMiLeaveDt());
                Long diffSec = (today.getTimeInMillis() - leaveDt.getTimeInMillis())/1000;
                Long diffDay = diffSec / (24*60*60);

                if(diffDay >= 180) {
                    if(photo != null) {
                        String oldFileName = photo.getMpFileName();
                        Path oldfolderLocation = Paths.get(member_image_path);
                        Path oldtargetFile = oldfolderLocation.resolve(oldFileName);
                        try {
                            Files.delete(oldtargetFile);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mpRepo.delete(photo);
                    }
                    miRepo.delete(member);
                }
                else
                    continue;
            }
            else if(member == null) {
                break;
            }
        }
    }
}
