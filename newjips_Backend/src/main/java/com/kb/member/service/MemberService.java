package com.kb.member.service;

import com.kb.member.dto.Auth;
import com.kb.member.dto.ChangePasswordDTO;
import com.kb.member.dto.UpdatePasswordDTO;
import com.kb.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.kb.member.dto.Member;
import com.kb.member.exception.PasswordMissmatchException;
import com.kb.member.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource({"classpath:/application.properties"})
public class MemberService{

    @Value("#{'${os_type}' == 'win' ? '${file_save_location_win}':'${file_save_location_other}'}")
    public String LOCATION;

    final PasswordEncoder passwordEncoder;
    final MemberMapper mapper;
//    final S3Uploader s3Uploader;
    final long MAX_FILE_SIZE = 5 * 1024 * 1024;
//    final String default_img = "https://cdn-nj.s3.ap-northeast-2.amazonaws.com/default.jpg";
    final String default_img = "https://cdn-icons-png.flaticon.com/512/149/149071.png";


    public Member login(Member member) {
        Member saveMember = mapper.selectById(member.getUserId());
        if(passwordEncoder.matches(member.getPassword(), saveMember.getPassword())) {
            saveMember.setPassword(""); //비밀번호 제거
            saveMember.setUno(0); //회원 번호 초기화
            return saveMember;
        }else{
            return null; //인증 실패
        }
    }

    public boolean checkDuplicate(String userId) {
        Member member = mapper.selectById(userId);
        return member != null; //중복 확인
    }

    public boolean isBuddiz(long uno){
        Long member = mapper.findBuddiz(uno);
        return member != null; //null이면 false, 있으면 true
    }

    public Member getMember(String userId) {
        return Optional.ofNullable(mapper.selectById(userId))
                        .orElseThrow(NoSuchElementException::new);
    }

    public Member getPassword(String userId,String name){
        return Optional.ofNullable(mapper.selectBypassword(userId, name))
                .orElseThrow(NoSuchElementException::new);
    }

    public String findUserId(String name, String password){
        Member member = Optional.ofNullable(mapper.selectByPassword(name))
                .orElseThrow(NoSuchElementException::new);
        // 비밀번호 확인
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new PasswordMissmatchException(); // 비밀번호가 일치하지 않는 경우
        }
        return member.getUserId(); // 비밀번호가 일치하면 userId 반환
    }

    public void updatePassword(UpdatePasswordDTO updatePassword){
        String userId = updatePassword.getUserId();
        System.out.println("User ID: " + userId); // 추가된 로그
        Member member = mapper.selectById(updatePassword.getUserId());

        if (member == null) {
            System.out.println("사용자 ID: " + updatePassword.getUserId() + "에 대한 회원 정보를 찾을 수 없습니다.");
        }
        //사용자 userId 확인
        if(member != null ){
            // 사용자가 존재할 경우, 새로운 비밀번호 설정
            String encodedPassword = passwordEncoder.encode(updatePassword.getNewPassword()); // 비밀번호 암호화
            member.setPassword(encodedPassword);

            int result = mapper.uupdatePassword(member); //비밀번호 업데이트 쿼리 호출
            if(result != 1){
                throw new RuntimeException("비밀번호 업데이트에 실패했습니다.");
            }
        } else{
            throw new RuntimeException("사용자 정보를 찾을 수 없습니다.");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Member join(Member member, MultipartFile avatar) throws IllegalAccessException {
        if(member.checkRequiredValue()){
            throw new IllegalAccessException("필수 값이 누락되었습니다."); //예외 처리
        }
        member.setPassword(passwordEncoder.encode(member.getPassword())); //비밀번호 암호화
        member.setProfilePic(default_img);

        int result = mapper.insertMember(member);
        if(result != 1){
            throw new IllegalAccessException("회원 가입에 실패했습니다."); //예외 처리
        }

        return mapper.selectById(member.getUserId());
    }

//    public Member update(Member updateMember, MultipartFile file) throws IllegalAccessException {
//        Member oldMember = mapper.selectByUno(updateMember.getUno());
//        updateMember.setUno(oldMember.getUno());
//        if(file != null && !file.isEmpty()) {
//            if (file.getSize() > MAX_FILE_SIZE) {
//                throw new IllegalArgumentException("파일 크기는 최대 5MB 입니다.");
//            }
//
//            if (!oldMember.getProfilePic().equals(default_img)) {
//                s3Uploader.deleteFile(oldMember.getProfilePic());
//            }
//
//            // 이미지 업로드
//            String url = s3Uploader.saveFile(file);
//            updateMember.setProfilePic(url);
//        } else {
//            updateMember.setProfilePic(default_img);
//        }
//        mapper.updateMember(updateMember);
//        return mapper.selectByUno(updateMember.getUno());
//    }

    public Member update(Member updateMember, String profilePicUrl) throws IllegalAccessException {
        Member oldMember = mapper.selectByUno(updateMember.getUno());
        updateMember.setUno(oldMember.getUno());
        if(profilePicUrl != null && !profilePicUrl.isEmpty()) {

            // 이미지 업로드
            updateMember.setProfilePic(profilePicUrl);
        } else {
            updateMember.setProfilePic(default_img);
        }
        mapper.updateMember(updateMember);
        return mapper.selectByUno(updateMember.getUno());
    }

    public void changePassword(ChangePasswordDTO changePassword) {
        Member member = mapper.selectByUno(changePassword.getUno());

        if(!passwordEncoder.matches(
            changePassword.getOldPassword(),
            member.getPassword()
        )) {
              throw new PasswordMissmatchException();
        }


        member.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
        int result = mapper.updatePassword(member);
        if(result != 1){
            throw new NoSuchElementException();
        }
    }
}
