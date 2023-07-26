<template>
<b-navbar toggleable="lg" type="dark" variant="success">
    <b-navbar-brand href="/">Board</b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
    </b-collapse>
  </b-navbar>
  <br><br>
  <div>
    <span style="font-size: 18px;">비밀번호 찾기</span>
    <br><br>
    <form @submit.prevent="confirm">
      <el-row justify="center" align="middle">
        <el-col :span="5">
          <el-form-item>
            <el-input type="email" v-model="email" placeholder="이메일"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-button type="button" @click="searchPwd">재설정 비밀번호 발송</el-button>
    </form>
  </div>
</template>

<script>
import apiMember from '@/api/member';

export default {
  data() {
    return {
      email: ''
    }
  },
  methods: {
    // 이메일 발송 로직 구현
    searchPwd() {
      if (!this.email) {
        this.$message.error("이메일을 입력해주세요");
        return;
      }

      apiMember.searchMemberPwd(this.email)
        .then(response => {
          console.log(response);
          if (response.data.status === true) {
            this.$message.success('이메일로 재설정한 비밀번호가 발송되었습니다');
          } else if(response.data.status === false){
            console.log(response);
            this.$message.error('해당하는 이메일이 존재하지 않습니다');
          }
        })
        .catch(error => {
          console.log(error);
          this.$message.error("에러 발생");
        });
    }
  }
}
</script>

<style scoped>
</style>
