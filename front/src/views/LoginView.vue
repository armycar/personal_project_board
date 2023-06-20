<template>
  <div>
    <span style="font-size: 18px;">로그인</span>
    <br><br>
    <form @submit.prevent="login">
       <el-row justify="center" align="middle">
    <el-col :span="5">
      <el-form-item>
        <el-input type="email" v-model="email" placeholder="이메일"></el-input>
      </el-form-item>
      </el-col>
  </el-row>

  <el-row justify="center" align="middle">
    <el-col :span="5">
      <el-form-item >
        <el-input type="password" v-model="pwd" placeholder="비밀번호"></el-input>
      </el-form-item>
      </el-col>
  </el-row>
      <el-button type="button" @click="login" class="email-login">이메일로 로그인</el-button>
      <br><br>
      <div class="button-wrapper">
      <span class="forgot-password"><a href="/api/member/search/pwd" style="text-decoration: none;">비밀번호 찾기</a></span>
      <span style="margin-left: 20px; margin-right: 20px;" >|</span>
      <span class="member-join" ><a href="/api/member/join" style="text-decoration: none;">회원가입</a></span>
      </div>
    </form>
  </div>
</template>

<script>
import apiMember from '@/api/member';

export default {
  data() {
    return {
      email: '',
      pwd: ''
    }
  },

  methods: {
    login() {
      apiMember.loginMember(this.email, this.pwd)
        .then(response => {
          console.log(response);
          const token = response.data.miSeq;
          const username = response.data.nickname;
          if(!this.email || !this.pwd) {
            this.$message.error("아이디와 비밀번호를 입력해주세요");
            return;
          }
          else if (!token) {
            this.$message.error('아이디와 비밀번호를 확인해주세요');
            return;
            //this.logout();
            //this.$router.push('/');
          } else {
            sessionStorage.setItem('token', token);
            sessionStorage.setItem('username', username); //로그인 되었을때 화면에 username 보이게하기
            this.$router.push('/');
            this.$message({
              message: '로그인 되었습니다',
              type: 'success'
            })
          }
        })
        .catch(error => {
          if (error.response) {
            if (error.response.status === 400) {
              alert(error.response.data.message);
            } else if (error.response.status === 401) {
              alert('로그인 정보가 올바르지 않습니다');
            }
          } else {
            console.error(error);
            alert('로그인에 실패했습니다');
          }
        });
    },
    logout() {
      sessionStorage.removeItem('token');
      this.$router.push('/login');
    }
  }
}
</script>

<style>
.button-wrapper {
  font-size: 14px;
  cursor: pointer;
  margin-top: 8px;
  display: block;
  cursor: pointer;
}
.email-login {
  padding: 8px 16px;
  border-radius: 24px;
  background-color: lightgrey;
  text-decoration: none;
  cursor: pointer;
}
</style>
