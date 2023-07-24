<template>
  <span style="font-size: 18px;">회원정보 변경</span>

  <br><br>
  <div>
    <form @submit.prevent="join">
      <el-row justify="center" align="middle">
        <el-col :span="4">
          <el-form-item style="width: 500px;">
            <el-row>
              <el-col :span="16">
                <el-input type="text" id="nickname" v-model="nickname" placeholder="닉네임"></el-input>
              </el-col>
              <el-col :span="8">
                <el-button type="button" @click="dupchkName">중복확인</el-button>
              </el-col>
            </el-row>
          </el-form-item>
        </el-col>
      </el-row>
      <br>

      <el-row justify="center" align="middle">
        <el-col :span="4">
          <el-form-item style="width: 280px;">
            <el-input type="password" id="oldpwd" v-model="oldpwd" placeholder="기존 비밀번호">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <br>

      <el-row justify="center" align="middle">
        <el-col :span="4">
          <el-form-item style="width: 280px;">
            <el-input type="password" id="pwd" v-model="pwd" placeholder="새로운 비밀번호">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <br>

      <el-row justify="center" align="middle">
        <el-col :span="4">
          <el-form-item style="width: 280px;">
            <el-input type="password" id="pwdconfirm" v-model="pwdconfirm" placeholder="새로운 비밀번호 확인">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <br>

      <el-button type="primary" @click="updateInfo">변경하기</el-button>
    </form>
  </div>
</template>

<script>
import apiMember from '@/api/member';

export default {
data() {
    return {
        oldpwd: '',
        pwd: '',
        pwdconfirm: '',
        nickname: '',
        namecheck: false
    }
},

methods: {
    updateInfo() {
      if(this.nickname && !this.namecheck) {
        this.$message.warning('닉네임 중복확인을 해주세요');
        return;
      }
    
      const token = sessionStorage.getItem('token');
      const seq = token;

    if(this.pwd !=='' && this.oldpwd !=='' && this.pwdconfirm !=='') {
         if (this.pwd !== this.pwdconfirm) {
        this.$message.error('비밀번호는 같아야 합니다');
        return;
      }

      else if (this.pwd.length < 8) {
        this.$message.error('비밀번호는 8자리 이상이어야 합니다');
        return;
      }
}

      apiMember.updateMemberInfo(seq, this.nickname, this.oldpwd, this.pwd, this.pwdconfirm)
      .then(response => {
        console.log(response);
        if(response.data.status === false) {
          this.$message.error('기존 비밀번호가 틀리거나 비밀번호에 포함할수 없는 문자가 있습니다')
        }
        else {
          this.$message.success('정보변경이 완료되었습니다')
          this.$router.push('/');
        }
      })
      .catch(error => {
        console.log(error);
        this.$message.error('에러발생');
      });
    },
  dupchkName() {
    apiMember.dupchkName(this.nickname)
    .then(response=> {
      if(response.data.count ==1) {
        this.$message.warning('이미 사용중인 닉네임입니다');
      }
      else if(this.nickname=="") {
        this.$message.warning('사용 할 닉네임을 입력해주세요');
      }
      else {
        this.$message.success('사용 가능한 닉네임입니다');
        this.namecheck = true;
      }
    })
    .catch(error =>{
      console.log(error);
      this.$message.error("중복확인 중 에러발생");
    }) 
  }
  }
}


</script>

<style>
    
</style>
