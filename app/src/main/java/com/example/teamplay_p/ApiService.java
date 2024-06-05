package com.example.teamplay_p;

import com.example.teamplay_p.dto.auth.LoginRequest;
import com.example.teamplay_p.dto.auth.LoginResponse;
import com.example.teamplay_p.dto.major.Major;
import com.example.teamplay_p.dto.major.MajorReqDto;
import com.example.teamplay_p.dto.meeting.CreateMeetingRequest;
import com.example.teamplay_p.dto.meeting.Meeting;
import com.example.teamplay_p.dto.meeting.MeetingResponse;
import com.example.teamplay_p.dto.subject.subject;
import com.example.teamplay_p.dto.user.JoinRequest;
import com.example.teamplay_p.dto.user.UpdateRequest;
import com.example.teamplay_p.dto.user.UserResponse;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    /// <전공 major>
    // 전공 등록
    @POST("/v1/major")
    Call<Major> createMajor(@Body MajorReqDto majorReqDto);

    // 전공목록 리스트
    @GET("/v1/major")
    Call<List<Major>> getAllMajors();


    /// <과목 subject>
    // 과목리스트(전체)
    @GET("/v1/subjects")
    Call<List<subject>> getAllSubjects(@Query("major") UUID major);


    /// <로그인, 로그아웃 auth>
    // 로그인
    @POST("/v1/auth/login")
    Call<LoginResponse> loginUser( @Body LoginRequest loginRequest);

    // 로그아웃
    @POST("/v1/auth/logout")
    Call<Void> logoutUser();


    /// <회원가입 user>
    // 회원정보 조회
    @GET("/v1/user/{userUuid}")
    Call<UserResponse> getUserInfo(@Path("userUuid") String userUuid);
    // 회원정보 수정
    @PUT("/v1/user/{userUuid}")
    Call<UserResponse> updateUserInfo(@Path("userUuid") String userUuid, @Body UpdateRequest request);
    // 회원가입
    @POST("/v1/user/register")
    Call<UserResponse> registerUser(@Body JoinRequest request);
    // ID 중복 확인(중복 - true, 아니면 false)
    @GET("/v1/user/register/check-userid")
    Call<Boolean> checkUserIdDuplicate(@Query("userId") String userId);
    // 학번 중복 확인(중복 - true, 아니면 false)
    @GET("/v1/user/register/check-studentnumber")
    Call<Boolean> checkStudentNumberDuplicate(@Query("studentNumber") String studentNumber);


    /// <모임 meeting>
    // 모임 생성
    @POST("/v1/meetings")
    Call<Meeting> createMeeting(@Body CreateMeetingRequest request);

    // 필터 및 검색
    @GET("/v1/meetings/search")
    Call<List<MeetingResponse>> filterAndSearchMeetings(@Query("majorUuid") UUID majorUuid, @Query("subjectUuid") UUID subjectUuid,
                                                        @Query("teamTypes") List<String> teamTypes, @Query("desiredCount") Integer desiredCount, @Query("searchText") String searchText);



    @GET("/v1/meetings")
    Call<List<MeetingResponse>> getAllMeetings();


}

    /*

    @GET("path/to/your/endpoint")
    Call<YourResponseType> getYourData();

    @POST("path/to/your/endpoint")
    Call<YourResponseType> postYourData(@Body YourRequestType request);

    @PUT("path/to/your/endpoint")
    Call<YourResponseType> putYourData(@Body YourRequestType request);
    */

